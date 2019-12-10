package censusanalyser;

import com.google.gson.Gson;
//import com.myopencsv.*;
//import com.myopencsv.ICsvBuilder;
//import com.myopencsv.OpenCsvException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
            Iterator<IndiaCensusCSV> csvIterator = null;
            try {
                csvIterator = csvBuilder.getCsvFileIterator(reader,IndiaCensusCSV.class);
            } catch (RuntimeException e){
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
            while (csvIterator.hasNext()){
                this.censusList.add(new IndiaCensusDAO(csvIterator.next()));
            }
            return censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (OpenCsvException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
            List<IndiaStateCode> stateCodeCsvList = csvBuilder.getCsvFileAsList(reader,IndiaStateCode.class);
            return stateCodeCsvList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (OpenCsvException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    private <E> int getCountUsingIterator(Iterator<E> csvIterator) {
        Iterable csvIterable = () -> csvIterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numOfEntries;
    }

    public String getStateWiseSortedDataForCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0 ) {
            throw new CensusAnalyserException("no census data!",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusList, censusComparator);
        String sorted = new Gson().toJson(censusList);
        return sorted;
    }

    private void sort(List<IndiaCensusDAO> list, Comparator<IndiaCensusDAO> censusComparator) {
        for( int i=0 ; i<list.size() ; i++){
            for ( int j=0 ; j<list.size()-i-1 ; j++ ){
                IndiaCensusDAO censusObj1 = list.get(j);
                IndiaCensusDAO censusObj2 = list.get(j+1);
                if (censusComparator.compare(censusObj1,censusObj2) > 0){
                    list.set(j,censusObj2);
                    list.set(j+1,censusObj1);
                }
            }
        }
    }

}

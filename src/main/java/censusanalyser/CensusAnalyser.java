package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCsvList = null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
            censusCsvList = csvBuilder.getCsvFileAsList(reader,IndiaCensusCSV.class);
            return censusCsvList.size();
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
        if (censusCsvList == null || censusCsvList.size() == 0 ) {
            throw new CensusAnalyserException("no census data!",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusCsvList, censusComparator);
        String sorted = new Gson().toJson(censusCsvList);
        return sorted;

    }

    private void sort(List list, Comparator<IndiaCensusCSV> censusComparator) {
        for( int i=0 ; i<list.size() ; i++){
            for ( int j=0 ; j<list.size()-i-1 ; j++ ){
                IndiaCensusCSV censusObj1 = (IndiaCensusCSV) list.get(j);
                IndiaCensusCSV censusObj2 = (IndiaCensusCSV) list.get(j+1);
                if (censusComparator.compare(censusObj1,censusObj2) > 0){
                    IndiaCensusCSV temp = censusObj1;
                    list.set(j,censusObj2);
                    list.set(j+1,temp);
                }
            }
        }
    }

}

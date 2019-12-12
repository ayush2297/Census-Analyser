package censusanalyser;

import com.google.gson.Gson;
import com.myopencsv.CsvBuilderFactory;
import com.myopencsv.ICsvBuilder;
import com.myopencsv.OpenCsvException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {



    public enum ComparatorType {
        STATE_NAME, POPULATION, AREA, DENSITY;
    }
    Map<String, CensusDAO> censusDAOMap = null;

    Map<Enum,Comparator<CensusDAO>> myComparators = null;
    public CensusAnalyser() {
        this.censusDAOMap = new HashMap<>();
        this.myComparators = new HashMap<>();
        this.myComparators.put(ComparatorType.STATE_NAME,Comparator.comparing(census -> census.state));
        this.myComparators.put(ComparatorType.POPULATION,Comparator.comparing(census -> census.population,Comparator.reverseOrder()));
        this.myComparators.put(ComparatorType.AREA,Comparator.comparing(census -> census.areaInSqKm,Comparator.reverseOrder()));
        this.myComparators.put(ComparatorType.DENSITY,Comparator.comparing(census -> census.densityPerSqKm,Comparator.reverseOrder()));
    }
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        int count = this.loadCenusData(csvFilePath,IndiaCensusCSV.class);
        return count;
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
            Iterator<IndiaStateCode> stateCodeIterator = csvBuilder.getCsvFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> stateCodeIterable = () -> stateCodeIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(), false)
                    .filter(csvStatev -> censusDAOMap.get(csvStatev.state) != null)
                    .forEach(csvState -> censusDAOMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusDAOMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (OpenCsvException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE);
        }
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        int count = this.loadCenusData(csvFilePath,USCensusData.class);
        return count;
    }

    private <E> int loadCenusData(String csvFilePath, Class<E> censusDataClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
            Iterator<E> usCensusDataIterator = csvBuilder.getCsvFileIterator(reader,censusDataClass);
            Iterable<E> usCensusDataIterable = () -> usCensusDataIterator;
            if (censusDataClass.getName().equals("censusanalyser.USCensusData")) {
                StreamSupport.stream(usCensusDataIterable.spliterator(), false)
                        .map(USCensusData.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusDataClass.getName().contains("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(usCensusDataIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusDAOMap.size();
        } catch (NullPointerException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (OpenCsvException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE);
        }
    }

    public boolean isNull(Map thisList) {
        if (censusDAOMap == null || censusDAOMap.size() == 0 ) {
            return true;
        }
        return false;
    }

    public String getSortedData(ComparatorType comparatorType) throws CensusAnalyserException {
        if (isNull(censusDAOMap)) {
            throw new CensusAnalyserException("no census data!",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> censusDAOS = censusDAOMap.values().stream()
                                            .collect(Collectors.toList());
        this.sort(censusDAOS, myComparators.get(comparatorType));
        String sorted = new Gson().toJson(censusDAOS);
        return sorted;
    }

    private void sort(List<CensusDAO> list, Comparator<CensusDAO> censusComparator) {
        for( int i=0 ; i<list.size()-1; i++){
            for ( int j=0 ; j<list.size()-i-1 ; j++ ){
                CensusDAO censusObj1 = list.get(j);
                CensusDAO censusObj2 = list.get(j+1);
                if (censusComparator.compare(censusObj1,censusObj2) > 0){
                    list.set(j,censusObj2);
                    list.set(j+1,censusObj1);
                }
            }
        }
    }

}

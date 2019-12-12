package censusanalyser;

import com.myopencsv.CsvBuilderFactory;
import com.myopencsv.ICsvBuilder;
import com.myopencsv.OpenCsvException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    Map<String,CensusDAO> censusDAOMap = null;

    public CensusLoader() {
        this.censusDAOMap = new HashMap<>();
    }

    public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusDataClass, String... csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
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
            if (csvFilePath.length == 1){
                return censusDAOMap;
            }
            this.loadIndiaStateCodeData(csvFilePath[1]);
            return censusDAOMap;
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

    private void loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
            Iterator<IndiaStateCode> stateCodeIterator = csvBuilder.getCsvFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> stateCodeIterable = () -> stateCodeIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(), false)
                    .filter(csvStatev -> censusDAOMap.get(csvStatev.state) != null)
                    .forEach(csvState -> censusDAOMap.get(csvState.state).stateCode = csvState.stateCode);
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

}

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

    public <E> Map<String, CensusDAO> loadCensusData(String csvFilePath, Class<E> censusDataClass) throws CensusAnalyserException {
        Map<String,CensusDAO> censusDAOMap = new HashMap<>();
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
}

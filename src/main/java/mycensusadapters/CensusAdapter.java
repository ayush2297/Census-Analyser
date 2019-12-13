package mycensusadapters;

import censusanalyser.CensusAnalyserException;
import com.myopencsv.OpenCsvBuilder;
import pojos.CensusDAO;
import pojos.IndiaCensusCSV;
import pojos.USCensusData;
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

public abstract class CensusAdapter {

    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;

    protected <E> Map<String, CensusDAO> loadCensusData(Class<E> censusDataClass, String csvFilePath) throws CensusAnalyserException {
        Map<String,CensusDAO> censusDAOMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            OpenCsvBuilder csvBuilder = (OpenCsvBuilder) CsvBuilderFactory.createCsvBuilder();
            Iterator<E> censusDataIterator = csvBuilder.getCsvFileIterator(reader,censusDataClass);
            Iterable<E> censusDataIterable = () -> censusDataIterator;
            if (censusDataClass.getName().equals("pojos.USCensusData")) {
                StreamSupport.stream(censusDataIterable.spliterator(), false)
                        .map(USCensusData.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusDataClass.getName().contains("pojos.IndiaCensusCSV")) {
                StreamSupport.stream(censusDataIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusDAOMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (OpenCsvException e) {
            throw new CensusAnalyserException("delimiter or header error", CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE);
        } catch (NullPointerException e) {
            throw new CensusAnalyserException("no file input...(null)",
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE);
        }
    }
}

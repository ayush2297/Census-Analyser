package mycensusadapters;

import censusanalyser.CensusAnalyserException;
import pojos.CensusDAO;
import pojos.IndiaCensusCSV;
import pojos.IndiaStateCode;
import com.myopencsv.CsvBuilderFactory;
import com.myopencsv.ICsvBuilder;
import com.myopencsv.OpenCsvException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        try {
            Map<String,CensusDAO> censusDAOMap = super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
            censusDAOMap = this.loadIndiaStateCodeData(censusDAOMap, csvFilePath[1]);
            return censusDAOMap;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CensusAnalyserException("second file not passed!!",CensusAnalyserException.ExceptionType.INSUFFICIENT_NUMBER_OF_FILES);
        }
    }

    private Map<String, CensusDAO> loadIndiaStateCodeData(Map<String, CensusDAO> censusDAOMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
            Iterator<IndiaStateCode> stateCodeIterator = csvBuilder.getCsvFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> stateCodeIterable = () -> stateCodeIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(), false)
                    .filter(csvStatev -> censusDAOMap.get(csvStatev.state) != null)
                    .forEach(csvState -> censusDAOMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusDAOMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (OpenCsvException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE);
        } catch (NullPointerException e) {
            throw new CensusAnalyserException("no file input...(null)",
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE);
        }
    }
}

package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCsvFileIterator(reader,IndiaCensusCSV.class);
            int numOfEntries = getCountUsingIterator(censusCSVIterator);
            return numOfEntries;
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
            List<IndiaStateCode> censusCSVList = csvBuilder.getCsvFileAsList(reader,IndiaStateCode.class);
            return censusCSVList.size();
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

}

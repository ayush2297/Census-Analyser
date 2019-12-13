package mycensusadapters;

import censusanalyser.CensusAnalyser;
import censusanalyser.CensusAnalyserException;
import censusanalyser.CensusDAO;
import mycensusadapters.IndiaCensusAdapter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndiaCensusAdapterTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String CSV_WITH_ERROR_FILE_PATH = "./src/test/resources/IndiaStateCensusDataWithErrors.csv";
    private static final String EMPTY_FILE_PATH = "./src/test/resources/empty.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData1.csv";

    @Test
    public void givenAStateCensusDataCsvFile_ShouldReturnNumberOfRecords() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> testHashmap = indiaCensusAdapter.loadCensusData(
                    INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29,testHashmap.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenOnlyOneFileToIndianCensusLoader_ShouldThrowException() {
        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();
        try {
            Map<String,CensusDAO> testHashmap = indiaCensusAdapter.loadCensusData(
                    INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INSUFFICIENT_NUMBER_OF_FILES,e.type);
        }
    }

    @Test
    public void givenWrongFile_ToIndianCensusCsvData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,
                    WRONG_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenWrongCsvFile_ToIndianCensusCsvData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,
                    INDIA_STATE_CODE_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithEmptyFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,
                    EMPTY_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenPassedNullAsFilePath_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,
                    null, INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,
                    CSV_WITH_ERROR_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,
                    INDIA_STATE_CODE_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

}

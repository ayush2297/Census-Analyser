package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String CSV_WITH_ERROR_FILE_PATH = "./src/test/resources/IndiaStateCensusDataWithErrors.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData1.csv";
    private static final String EMPTY_FILE_PATH = "./src/test/resources/empty.csv";

    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenWrongFile_ToIndianCensusCsvData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
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
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
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
            censusAnalyser.loadIndiaCensusData(EMPTY_FILE_PATH);
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
            censusAnalyser.loadIndiaCensusData(null);
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
            censusAnalyser.loadIndiaCensusData(CSV_WITH_ERROR_FILE_PATH);
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
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndianCensusList_WhenEmptyOrNull_ShouldThrowCensusAnalyserException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,
                    censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.STATE_NAME));
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnStateName_ShouldReturnSortedList() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.STATE_NAME);
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedString, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) { e.printStackTrace(); }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnPopulation_ShouldReturnSortedList() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.POPULATION);
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedString, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) { e.printStackTrace(); }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnArea_ShouldReturnSortedList() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.AREA);
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedString, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) { e.printStackTrace(); }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnDensity_ShouldReturnSortedList() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.DENSITY);
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedString, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) { e.printStackTrace(); }
    }

    @Test
    public void givenIndianStateCodeCsv_ShouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenWrongFile_ToIndianStateCodeCsvData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenWrongCsvFile_ToIndianStateCodeCsvData_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCodeData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

}

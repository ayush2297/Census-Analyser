package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import pojos.IndiaCensusCSV;
import pojos.USCensusData;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String CENSUS_CSV_WITH_ERROR_FILE_PATH = "./src/test/resources/IndiaStateCensusDataWithErrors.csv";
    private static final String CODE_CSV_WITH_ERROR_FILE_PATH = "./src/test/resources/IndiaStateCodeWithErrors.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String US_CENSUS_CSV_WITH_WRONG_HEADER_FILE_PATH = "./src/test/resources/USCensusDataWithHeaderErrors.csv";
    private static final String US_CENSUS_CSV_WITH_WRONG_DELIMITER_FILE_PATH = "./src/test/resources/USCensusDataWithDelimiterErrors.csv";
    private static final String EMPTY_FILE_PATH = "./src/test/resources/empty.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData1.csv";

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
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.STATE_NAME);
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedString, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnPopulation_ShouldReturnSortedList() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.POPULATION);
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedString, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnArea_ShouldReturnSortedList() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.AREA);
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedString, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) { e.printStackTrace();}
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnDensity_ShouldReturnSortedList() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.DENSITY);
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedString, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButWithWrongFirstFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,WRONG_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButWithWrongSecondFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButWithFirstFileEmpty_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,EMPTY_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButWithSecondFileEmpty_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH,EMPTY_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButWithFirstFileHavingDelimiterErrors_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA, CENSUS_CSV_WITH_ERROR_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButWithSecondFileHavingDelimiterErrors_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH, CODE_CSV_WITH_ERROR_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButWithFirstFileAsNull_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,null,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButWithSecondFileAsNull_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH,null);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedBasedOnAnyField_ButOnlyOneFilePassed_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INSUFFICIENT_NUMBER_OF_FILES,e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedBasedOnStateName_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.US,US_CENSUS_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.STATE_NAME);
            USCensusData[] usCensusData = new Gson().fromJson(sortedString, USCensusData[].class);
            Assert.assertEquals("Alabama", usCensusData[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedBasedOnArea_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.US,US_CENSUS_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.AREA);
            USCensusData[] usCensusData = new Gson().fromJson(sortedString, USCensusData[].class);
            Assert.assertEquals("Alaska", usCensusData[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedBasedOnPopulation_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.US,US_CENSUS_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.POPULATION);
            USCensusData[] usCensusData = new Gson().fromJson(sortedString, USCensusData[].class);
            Assert.assertEquals("California", usCensusData[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedBasedOnPopulationDensity_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.US,US_CENSUS_CSV_FILE_PATH);
            String sortedString = censusAnalyser.getSortedData(CensusAnalyser.ComparatorType.DENSITY);
            USCensusData[] usCensusData = new Gson().fromJson(sortedString, USCensusData[].class);
            Assert.assertEquals("District of Columbia", usCensusData[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedBasedOnAnyField_ButPassingFileWithDelimiterErrors_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.US,US_CENSUS_CSV_WITH_WRONG_DELIMITER_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedBasedOnAnyField_ButPassingFileWithHeaderError_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.US,US_CENSUS_CSV_WITH_WRONG_HEADER_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DATA_ISSUE,e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedBasedOnAnyField_ButPassingNullFile_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.US,null);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedBasedOnAnyField_ButPassingWrongFile_ShouldReturnTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.US,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenAnyCsvFile_IfTriedToInputAnyOtherCountry_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.CountryName.OTHER,INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_COUNTRY_ENTERED,e.type);
        }
    }
}

package censusanalyser;

public class CensusAnalyserException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, NO_CENSUS_DATA, INCORRECT_DATA_ISSUE, INCORRECT_COUNTRY_ENTERED,
        INSUFFICIENT_NUMBER_OF_FILES;
    }
    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}

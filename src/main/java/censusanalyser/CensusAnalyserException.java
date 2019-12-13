package censusanalyser;

public class CensusAnalyserException extends Exception {
    public ExceptionType type;

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, NO_CENSUS_DATA, INCORRECT_DATA_ISSUE, INCORRECT_COUNTRY_ENTERED,
        INSUFFICIENT_NUMBER_OF_FILES;
    }

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}

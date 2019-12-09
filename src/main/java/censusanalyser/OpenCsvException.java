package censusanalyser;

public class OpenCsvException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE
    }
    ExceptionType type;

    public OpenCsvException(String exceptionMessage, ExceptionType exceptionType) {
        super(exceptionMessage);
        this.type = exceptionType;
    }
}

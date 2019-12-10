package censusanalyser;

public class OpenCsvException extends Exception {

    public enum ExceptionType {
        UNABLE_TO_GET_ITERATOR, UNABLE_TO_CREATE_BEAN, UNABLE_TO_PARSE_AS_LIST, FILE_IS_EMPTY
    }
    public ExceptionType type;

    public OpenCsvException(String exceptionMessage, ExceptionType exceptionType) {
        super(exceptionMessage);
        this.type = exceptionType;
    }
}

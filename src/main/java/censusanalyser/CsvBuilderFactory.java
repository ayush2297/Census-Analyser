package censusanalyser;

public class CsvBuilderFactory<E> {

    public static ICsvBuilder createCsvBuilder() {
        return new OpenCsvBuilder();
    }
}

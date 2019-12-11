package censusanalyser;
//
//import com.myopencsv.CommonCsvBuilder;
//import com.myopencsv.ICsvBuilder;
//import com.myopencsv.OpenCsvBuilder;

public class CsvBuilderFactory<E> {

    public static ICsvBuilder createCsvBuilder() {
        return new OpenCsvBuilder();
    }

    public static ICsvBuilder createCommonCsvBuilder() {
        return new CommonCsvBuilder();
    }
}

package censusanalyser;

//import com.myopencsv.ICsvBuilder;
//import com.myopencsv.OpenCsvException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CommonCsvBuilder implements ICsvBuilder {

    @Override
    public <E> List<E> getCsvFileAsList(Reader reader, Class<E> csvClass) throws OpenCsvException {
        List<CSVRecord> csvRecords = null;
        List<E> myRecords = null;
        try {
            csvRecords = CSVFormat.DEFAULT.parse(reader).getRecords();
//            for ( CSVRecord record : records){
//                myRecords.add((E) record);
//            }
            return (List<E>) csvRecords;
        } catch (IOException e) {
            throw new OpenCsvException(e.getMessage(),
                    OpenCsvException.ExceptionType.UNABLE_TO_PARSE_AS_LIST);
        }
    }

    @Override
    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class<E> csvClass) throws OpenCsvException {
        Iterator<E> records = getCsvFileAsList(reader,csvClass).iterator();
        return records;
    }

}

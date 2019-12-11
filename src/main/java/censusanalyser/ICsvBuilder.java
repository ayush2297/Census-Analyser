package censusanalyser;

//import com.myopencsv.OpenCsvException;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICsvBuilder {

    public <E> List getCsvFileAsList(Reader reader, Class<E> csvClass) throws OpenCsvException;

    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class<E> csvClass) throws OpenCsvException;
}

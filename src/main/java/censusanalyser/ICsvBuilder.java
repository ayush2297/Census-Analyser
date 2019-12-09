package censusanalyser;

import com.opencsv.bean.CsvToBean;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICsvBuilder {
    public <E> List getCsvFileAsList(Reader reader, Class<E> csvClass) throws OpenCsvException;
    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class<E> csvClass) throws OpenCsvException;
}

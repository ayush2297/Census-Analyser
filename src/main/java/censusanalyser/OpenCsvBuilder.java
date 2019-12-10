package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCsvBuilder<E> implements ICsvBuilder {

    private  <E> CsvToBean<E> getCsvFileBean(Reader reader, Class<E> csvClass) throws OpenCsvException {
        try {
            if (reader == null){
                throw new OpenCsvException("file does not have any data",
                        OpenCsvException.ExceptionType.FILE_IS_EMPTY);
            }
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean;
        } catch (RuntimeException e){
            throw new OpenCsvException(e.getCause().getMessage(),
                    OpenCsvException.ExceptionType.UNABLE_TO_CREATE_BEAN);
        }
    }

    @Override
    public <E> List getCsvFileAsList(Reader reader, Class<E> csvClass) throws OpenCsvException {
        try {
            return this.getCsvFileBean(reader, csvClass).parse();
        } catch (OpenCsvException e){
            throw new OpenCsvException(e.getMessage(),
                    OpenCsvException.ExceptionType.UNABLE_TO_PARSE_AS_LIST);
        }
    }

    @Override
    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class<E> csvClass) throws OpenCsvException {
        try {
            return this.getCsvFileBean(reader, csvClass).iterator();
        } catch (OpenCsvException e){
            throw new OpenCsvException(e.getMessage(),
                    OpenCsvException.ExceptionType.UNABLE_TO_GET_ITERATOR);
        }
    }

}

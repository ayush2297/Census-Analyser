package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCsvBuilder<E> implements ICsvBuilder {

    public  <E> Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) throws OpenCsvException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException e){
            throw new OpenCsvException(e.getMessage(),
                    OpenCsvException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}

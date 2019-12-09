package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCsvBuilder<E> implements ICsvBuilder {

    private  <E> CsvToBean<E> getCsvFileBean(Reader reader, Class<E> csvClass) throws OpenCsvException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        } catch (IllegalStateException e){
            throw new OpenCsvException(e.getMessage(),
                    OpenCsvException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    @Override
    public <E> List getCsvFileAsList(Reader reader, Class<E> csvClass) throws OpenCsvException {
        return this.getCsvFileBean(reader, csvClass).parse();
    }

    @Override
    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class<E> csvClass) throws OpenCsvException {
        return this.getCsvFileBean(reader, csvClass).iterator();
    }


}

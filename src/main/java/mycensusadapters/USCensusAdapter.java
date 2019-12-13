package mycensusadapters;

import censusanalyser.CensusAnalyserException;
import pojos.CensusDAO;
import pojos.USCensusData;
import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        try {
            Map<String, CensusDAO> censusDAOMap = super.loadCensusData(USCensusData.class, csvFilePath[0]);
            return censusDAOMap;
        } catch (NullPointerException e) {
            throw new CensusAnalyserException("no file passed....",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
    }
}

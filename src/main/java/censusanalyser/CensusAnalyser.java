package censusanalyser;

import com.google.gson.Gson;
import mycensusadapters.CensusAdapter;
import mycensusadapters.CensusAdapterFactory;
import pojos.CensusDAO;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    public CensusAnalyserDataFactory data;

    public enum CountryName {
        INDIA,US
    }

    public enum ComparatorType {
        STATE_NAME, POPULATION, AREA, DENSITY;
    }

    public CensusAnalyser() {
        this.data = new CensusAnalyserDataFactory();
    }

    public int loadCensusData(CountryName countryName, String... csvFilePath) throws CensusAnalyserException {
        CensusAdapter censusAdapter = CensusAdapterFactory.getAdapter(countryName);
        data.censusDAOMap = censusAdapter.loadCensusData(csvFilePath);
        return data.censusDAOMap.size();
    }

    private boolean isNull(Map thisList) {
        if (data.censusDAOMap == null || data.censusDAOMap.size() == 0 ) {
            return true;
        }
        return false;
    }

    public String getSortedData(ComparatorType comparatorType) throws CensusAnalyserException {
        if (isNull(data.censusDAOMap)) {
            throw new CensusAnalyserException("no census data!",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> censusDAOS = data.censusDAOMap.values().stream()
                                            .collect(Collectors.toList());
        this.sort(censusDAOS, data.myComparators.get(comparatorType));
        List<Object> list = new ArrayList<>();
        for (CensusDAO censusDAO: censusDAOS) {
            list.add(censusDAO.dtoObject);
        }
        String sorted = new Gson().toJson(censusDAOS);
        return sorted;
    }

    private void sort(List<CensusDAO> list, Comparator<CensusDAO> censusComparator) {
        for( int i=0 ; i<list.size()-1; i++){
            for ( int j=0 ; j<list.size()-i-1 ; j++ ){
                CensusDAO censusObj1 = list.get(j);
                CensusDAO censusObj2 = list.get(j+1);
                if (censusComparator.compare(censusObj1,censusObj2) > 0){
                    list.set(j,censusObj2);
                    list.set(j+1,censusObj1);
                }
            }
        }
    }

}

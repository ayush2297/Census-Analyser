package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    Map<String, CensusDAO> censusDAOMap = null;
    Map<Enum,Comparator<CensusDAO>> myComparators = null;

    public enum CountryName {
        INDIA,US

    }

    public enum ComparatorType {
        STATE_NAME, POPULATION, AREA, DENSITY;
    }

    public CensusAnalyser() {
        this.censusDAOMap = new HashMap<>();
        this.myComparators = new HashMap<>();
        this.myComparators.put(ComparatorType.STATE_NAME,Comparator.comparing(census -> census.state));
        this.myComparators.put(ComparatorType.POPULATION,Comparator.comparing(census -> census.population,Comparator.reverseOrder()));
        this.myComparators.put(ComparatorType.AREA,Comparator.comparing(census -> census.areaInSqKm,Comparator.reverseOrder()));
        this.myComparators.put(ComparatorType.DENSITY,Comparator.comparing(census -> census.densityPerSqKm,Comparator.reverseOrder()));
    }

    public int loadCensusData(CountryName countryName, String... csvFilePath) throws CensusAnalyserException {
        CensusAdapter censusAdapter = CensusAdapterFactory.getAdapter(countryName);
        censusDAOMap = censusAdapter.loadCensusData(csvFilePath);
        return censusDAOMap.size();
    }

    public boolean isNull(Map thisList) {
        if (censusDAOMap == null || censusDAOMap.size() == 0 ) {
            return true;
        }
        return false;
    }

    public String getSortedData(ComparatorType comparatorType) throws CensusAnalyserException {
        if (isNull(censusDAOMap)) {
            throw new CensusAnalyserException("no census data!",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> censusDAOS = censusDAOMap.values().stream()
                                            .collect(Collectors.toList());
        this.sort(censusDAOS, myComparators.get(comparatorType));
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

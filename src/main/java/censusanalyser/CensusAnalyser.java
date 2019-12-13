package censusanalyser;

import com.google.gson.Gson;
import mycensusadapters.CensusAdapter;
import mycensusadapters.CensusAdapterFactory;
import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {

    public CensusAnalyserDataFactory data;

    public enum CountryName {
        INDIA,US,OTHER
    }

    public enum ComparatorType {
        STATE_NAME, POPULATION, AREA, DENSITY;
    }

    public CensusAnalyser() {
        this.data = new CensusAnalyserDataFactory();
    }

    public int loadCensusData(CountryName countryName, String... csvFilePath) throws CensusAnalyserException {
        this.data.country = countryName;
        CensusAdapter censusAdapter = CensusAdapterFactory.getAdapter(countryName);
        data.censusDAOMap = censusAdapter.loadCensusData(csvFilePath);
        return data.censusDAOMap.size();
    }

    public String getSortedData(ComparatorType comparatorType) throws CensusAnalyserException {
        if (data.censusDAOMap.size() == 0 || data.censusDAOMap == null){
            throw new CensusAnalyserException("no census data!",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList outputList = null;
        CountryName country = this.data.country;
        if (country.equals(CountryName.INDIA)) {
            outputList = this.data.censusDAOMap.values().stream()
                    .sorted(this.data.myComparators.get(comparatorType))
                    .map(dto -> dto.getIndDtoObject())
                    .collect(toCollection(ArrayList::new));
        } else if (country.equals(CountryName.US)) {
            outputList = this.data.censusDAOMap.values().stream()
                    .sorted(this.data.myComparators.get(comparatorType))
                    .map(dto -> dto.getUsDtoObject())
                    .collect(toCollection(ArrayList::new));
        }
        String sorted = new Gson().toJson(outputList);
        return sorted;
    }

}

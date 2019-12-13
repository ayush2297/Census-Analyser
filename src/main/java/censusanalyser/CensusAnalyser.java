package censusanalyser;

import com.google.gson.Gson;
import mycensusadapters.CensusAdapter;
import mycensusadapters.CensusAdapterFactory;
import java.util.*;
import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {
    public CensusAnalyserDataFactory censusAnalyserData;

    public enum CountryName {
        INDIA,US,OTHER
    }

    public enum ComparatorType {
        STATE_NAME, POPULATION, AREA, DENSITY;
    }

    public CensusAnalyser() {
        this.censusAnalyserData = new CensusAnalyserDataFactory();
    }

    public int loadCensusData(CountryName countryName, String... csvFilePath) throws CensusAnalyserException {
        this.censusAnalyserData.country = countryName;
        CensusAdapter censusAdapter = CensusAdapterFactory.getAdapter(countryName);
        censusAnalyserData.censusDAOMap = censusAdapter.loadCensusData(csvFilePath);
        return censusAnalyserData.censusDAOMap.size();
    }

    public String getSortedData(ComparatorType comparatorType) throws CensusAnalyserException {
        if (censusAnalyserData.censusDAOMap.size() == 0 || censusAnalyserData.censusDAOMap == null){
            throw new CensusAnalyserException("no census data!",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList outputList = null;
        CountryName country = this.censusAnalyserData.country;
        if (country.equals(CountryName.INDIA)) {
            outputList = this.censusAnalyserData.censusDAOMap.values().stream()
                    .sorted(this.censusAnalyserData.myComparators.get(comparatorType))
                    .map(dto -> dto.getIndDtoObject())
                    .collect(toCollection(ArrayList::new));
        } else if (country.equals(CountryName.US)) {
            outputList = this.censusAnalyserData.censusDAOMap.values().stream()
                    .sorted(this.censusAnalyserData.myComparators.get(comparatorType))
                    .map(dto -> dto.getUsDtoObject())
                    .collect(toCollection(ArrayList::new));
        }
        String sorted = new Gson().toJson(outputList);
        return sorted;
    }

}

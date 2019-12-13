package censusanalyser;

import pojos.CensusDAO;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CensusAnalyserDataFactory {
    Map<String, CensusDAO> censusDAOMap = new HashMap<>();
    Comparator<CensusDAO> tempComp = Comparator.comparing(censusDAO -> censusDAO.population,Comparator.reverseOrder());
    Map<Enum, Comparator<CensusDAO>> myComparators = new HashMap<Enum, Comparator<CensusDAO>>() {{
        put(CensusAnalyser.ComparatorType.STATE_NAME, Comparator.comparing(census -> census.state));
        put(CensusAnalyser.ComparatorType.POPULATION, tempComp.thenComparing(census -> census.densityPerSqKm,Comparator.reverseOrder()));
        put(CensusAnalyser.ComparatorType.AREA, Comparator.comparing(census -> census.areaInSqKm, Comparator.reverseOrder()));
        put(CensusAnalyser.ComparatorType.DENSITY, Comparator.comparing(census -> census.densityPerSqKm, Comparator.reverseOrder()));
    }};
    CensusAnalyser.CountryName country;

}

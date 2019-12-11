package censusanalyser;

public class IndiaCensusDAO {

    public String state;
    public String stateCode;
    public int areaInSqKm;
    public int densityPerSqKm;
    public int population;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCsv) {
        state = indiaCensusCsv.state;
        areaInSqKm = indiaCensusCsv.areaInSqKm;
        densityPerSqKm = indiaCensusCsv.densityPerSqKm;
        population = indiaCensusCsv.population;
    }
}

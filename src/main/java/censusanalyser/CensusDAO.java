package censusanalyser;

public class CensusDAO {

    public String state;
    public String stateCode;
    public double areaInSqKm;
    public double densityPerSqKm;
    public int population;

    public CensusDAO(IndiaCensusCSV indiaCensusCsv) {
        state = indiaCensusCsv.state;
        areaInSqKm = indiaCensusCsv.areaInSqKm;
        densityPerSqKm = indiaCensusCsv.densityPerSqKm;
        population = indiaCensusCsv.population;
    }

    public CensusDAO(USCensusData usCensusCsv) {
        state = usCensusCsv.state;
        stateCode = usCensusCsv.stateId;
        population = usCensusCsv.population;
        densityPerSqKm = usCensusCsv.populationDensity;
        areaInSqKm = usCensusCsv.totalArea;
    }

    public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state,population,(int)densityPerSqKm,(int)areaInSqKm);
    }
}

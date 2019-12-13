package pojos;

public class CensusDAO {

    public String state;
    public String stateCode=null;
    public double areaInSqKm;
    public double densityPerSqKm;
    public int population;
    public Object dtoObject;

    public CensusDAO(IndiaCensusCSV indiaCensusCsv) {
        this.state = indiaCensusCsv.state;
        this.areaInSqKm = indiaCensusCsv.areaInSqKm;
        this.densityPerSqKm = indiaCensusCsv.densityPerSqKm;
        this.population = indiaCensusCsv.population;
        this.dtoObject = indiaCensusCsv;
    }

    public CensusDAO(USCensusData usCensusCsv) {
        this.state = usCensusCsv.state;
        this.stateCode = usCensusCsv.stateId;
        this.population = usCensusCsv.population;
        this.densityPerSqKm = usCensusCsv.populationDensity;
        this.areaInSqKm = usCensusCsv.totalArea;
        this.dtoObject = usCensusCsv;
    }

    public Object getDtoObject(){
        return this.dtoObject;
    }
}

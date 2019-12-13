package pojos;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int densityPerSqKm;

    public IndiaCensusCSV() {
    }

    public IndiaCensusCSV(String state, int population, int densityPerSqKm, int areaInSqKm) {
        this.state = state;
        this.population = population;
        this.densityPerSqKm = densityPerSqKm;
        this.areaInSqKm = areaInSqKm;
    }
}

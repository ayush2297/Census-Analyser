package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusData {

    @CsvBindByName(column = "State Id")
    public String stateId;

    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "Population")
    public int population;

    @CsvBindByName(column = "Housing")
    public int housing;

    @CsvBindByName(column = "units")
    public int units;

    @CsvBindByName(column = "Total area")
    public double totalArea;

    @CsvBindByName(column = "Water area")
    public double waterArea;

    @CsvBindByName(column = "Land area")
    public double landArea;

    @CsvBindByName(column = "Population Density")
    public double populationDensity;

    @CsvBindByName(column = "Housing Density")
    public double housingDensity;


}

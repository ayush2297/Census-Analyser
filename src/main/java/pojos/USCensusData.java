package pojos;

import com.opencsv.bean.CsvBindByName;

public class USCensusData {

    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Housing units", required = true)
    public int housing;

    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;

    @CsvBindByName(column = "Water area", required = true)
    public double waterArea;

    @CsvBindByName(column = "Land area", required = true)
    public double landArea;

    @CsvBindByName(column = "Population Density", required = true)
    public double populationDensity;

    @CsvBindByName(column = "Housing Density", required = true)
    public double housingDensity;

}

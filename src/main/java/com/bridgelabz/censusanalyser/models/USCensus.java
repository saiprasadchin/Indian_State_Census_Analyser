package com.bridgelabz.censusanalyser.models;

import com.opencsv.bean.CsvBindByName;

public class USCensus {
    @CsvBindByName(column = "state Id", required = true)
    public Integer stateId;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public Integer population;

    @CsvBindByName(column = "Housing units", required = true)
    public Integer housingUnits;

    @CsvBindByName(column = "Total area", required = true)
    public Double area;

    @CsvBindByName(column = "Water area", required = true)
    public Double waterArea;

    @CsvBindByName(column = "Land area", required = true)
    public Double landArea;

    @CsvBindByName(column = "Population Density", required = true)
    public Double populationDensity;

    @CsvBindByName(column = "Housing Density", required = true)
    public Double housingDensity;
}

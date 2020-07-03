package com.bridgelabz.censusanalyser.models;

public class CensusDAO {
    public String state;
    public Integer population;
    public Integer areaInSqKm;
    public Integer densityPerSqKm;
    public Integer stateId;
    public Integer housingUnits;
    public Double area;
    public Double waterArea;
    public Double landArea;
    public Double populationDensity;
    public Double housingDensity;

    public CensusDAO(CSVStateCensus csvStateCensus){
        this.state = csvStateCensus.state;
        this.population = csvStateCensus.population;
        this.areaInSqKm = csvStateCensus.areaInSqKm;
        this.densityPerSqKm  = csvStateCensus.densityPerSqKm;
    }

    public CensusDAO(USCensus usCensus){
        this.stateId = usCensus.stateId;
        this.state = usCensus.state;
        this.population = usCensus.population;
        this.housingUnits = usCensus.housingUnits;
        this.area = usCensus.area;
        this.waterArea = usCensus.waterArea;
        this.landArea = usCensus.landArea;
        this.populationDensity = usCensus.populationDensity;
        this.housingDensity = usCensus.housingDensity;

    }

    public String getState() {
        return state;
    }
}

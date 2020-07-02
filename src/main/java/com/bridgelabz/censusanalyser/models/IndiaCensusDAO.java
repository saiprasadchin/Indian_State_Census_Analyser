package com.bridgelabz.censusanalyser.models;

public class IndiaCensusDAO {
    public String state;
    public Integer population;
    public Integer areaInSqKm;
    public Integer densityPerSqKm;

    public IndiaCensusDAO(CSVStateCensus csvStateCensus){
        this.state = csvStateCensus.state;
        this.population = csvStateCensus.population;
        this.areaInSqKm = csvStateCensus.areaInSqKm;
        this.densityPerSqKm  = csvStateCensus.densityPerSqKm;
    }

    public String getState() {
        return state;
    }
}

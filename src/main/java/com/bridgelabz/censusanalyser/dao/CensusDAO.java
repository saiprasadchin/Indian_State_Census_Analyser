package com.bridgelabz.censusanalyser.dao;

import com.bridgelabz.censusanalyser.controller.CensusAnalyser;
import com.bridgelabz.censusanalyser.models.CSVStateCensus;
import com.bridgelabz.censusanalyser.models.CSVStateCode;
import com.bridgelabz.censusanalyser.models.USCensus;

public class CensusDAO {
    public String state;
    public Integer population;
    public Integer areaInSqKm;
    public Integer densityPerSqKm;
    public String stateId;
    public Integer housingUnits;
    public Double area;
    public Double waterArea;
    public Double landArea;
    public Double populationDensity;
    public Double housingDensity;
    public String stateCode;

    public CensusDAO(String state, Integer i, double v, double v1, String od) {
        this.state = state;
        this.population = i;
        this.area = v;
        this.stateCode = od;
    }

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

    public CensusDAO(CSVStateCode csvStateCode){
        this.state = csvStateCode.stateName;
        this.stateCode = csvStateCode.stateCode;
    }

    public Object getSpecificCensusData(CensusAnalyser.Country country){
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new CSVStateCensus(this.state,this.population,this.areaInSqKm,this.densityPerSqKm);
        return new USCensus(this.stateId,this.state,this.population,this.area,this.populationDensity);
    }
    public String getState() {
        return state;
    }
}

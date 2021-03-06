package com.bridgelabz.censusanalyser.models;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public Integer population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public Integer areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public Integer densityPerSqKm;

    public CSVStateCensus(){

    }

    public CSVStateCensus(String state,Integer population,Integer areaInSqKm ,Integer densityPerSqKm){
        this.state = state;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqKm = densityPerSqKm;

    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}

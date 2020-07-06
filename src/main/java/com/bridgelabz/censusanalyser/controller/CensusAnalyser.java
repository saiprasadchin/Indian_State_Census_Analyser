package com.bridgelabz.censusanalyser.controller;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.*;
import com.bridgelabz.censusanalyser.utilities.CensusLoader;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CensusAnalyser {
    public enum Country {
        INDIA, INDIA_STATE, US
    }
    List<CensusDAO> censusDAOList = null;

    public CensusAnalyser(){
        this.censusDAOList = new ArrayList<CensusDAO>();
    }

    public int loadCensusData(Country country, String... csvFilePath)throws CensusAnalyserException{
        Map<String, CensusDAO> censusDataMap = new CensusLoader().loadCensusData(country, csvFilePath);
        censusDAOList.addAll(censusDataMap.values());
        return censusDataMap.values().size();
    }

    public String getStateWiseSortedCensusData(String sortingParam ) {

            switch(sortingParam){
                case ParamConstants.STATE :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c1.state.compareTo(c2.state));
                    break;
                case ParamConstants.POPULAS_STATE :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c2.population.compareTo(c1.population));
                    break;
                case ParamConstants.POPULATION_DENSITY :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c2.densityPerSqKm.compareTo(c1.densityPerSqKm));
                    break;
                case ParamConstants.AREA :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c2.areaInSqKm.compareTo(c1.areaInSqKm));
                    break;
                case ParamConstants.US_POPULATION_DENSITY :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c2.populationDensity.compareTo(c1.populationDensity));
                    break;
                default:
                    System.out.println("Fail=======================");
            }

            String sortedStateCensusJson = new Gson().toJson(this.censusDAOList);
            return sortedStateCensusJson;
    }

    public void jsonFileWriter(String csvFilePath,String jsonString)  {
        FileWriter writer = null;
        try {
            writer = new FileWriter(csvFilePath);
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

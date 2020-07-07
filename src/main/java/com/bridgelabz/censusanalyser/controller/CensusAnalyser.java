package com.bridgelabz.censusanalyser.controller;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.*;
import com.bridgelabz.censusanalyser.services.CensusAdaptorFactory;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    public enum Country {
        INDIA,US
    }
    List<CensusDAO> censusDAOList = null;
    Map<String, CensusDAO> censusDataMap = null;

    public CensusAnalyser(){
        this.censusDAOList = new ArrayList<CensusDAO>();
        this.censusDataMap = new HashMap<String, CensusDAO>();
    }

    public int loadCensusData(Country country, String... csvFilePath)throws CensusAnalyserException{
        this.censusDataMap = new CensusAdaptorFactory().getCensusAdaptor(country, csvFilePath);
        censusDAOList.addAll(censusDataMap.values());
        return censusDataMap.values().size();
    }

    public String getStateWiseSortedCensusData(String sortingParam,Country country) {
        String sortedStateCensusJson = "";
        ArrayList<Object> censusDTOs = new ArrayList<>();
            switch(sortingParam){
                case ParamConstants.STATE :
                    censusDTOs = this.censusDataMap.values()
                                                    .stream().sorted((CensusDAO c1, CensusDAO c2) -> c1.state.compareTo(c2.state))
                                                    .map(censusDAO -> censusDAO.getSpecificCensusData(country))
                                                    .collect(Collectors.toCollection(ArrayList::new));
                    break;
                case ParamConstants.POPULAS_STATE :
                    censusDTOs = this.censusDataMap.values()
                            .stream().sorted((CensusDAO c1, CensusDAO c2) -> c2.population.compareTo(c1.population))
                            .map(censusDAO -> censusDAO.getSpecificCensusData(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;
                case ParamConstants.POPULATION_DENSITY :
                    censusDTOs = this.censusDataMap.values()
                            .stream().sorted((CensusDAO c1, CensusDAO c2) -> c2.densityPerSqKm.compareTo(c1.densityPerSqKm))
                            .map(censusDAO -> censusDAO.getSpecificCensusData(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;
                case ParamConstants.AREA :
                    censusDTOs = this.censusDataMap.values()
                            .stream().sorted((CensusDAO c1, CensusDAO c2) -> c2.areaInSqKm.compareTo(c1.areaInSqKm))
                            .map(censusDAO -> censusDAO.getSpecificCensusData(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;
                case ParamConstants.US_POPULATION_DENSITY :
                    censusDTOs = this.censusDataMap.values()
                            .stream().sorted((CensusDAO c1, CensusDAO c2) -> c2.populationDensity.compareTo(c1.populationDensity))
                            .map(censusDAO -> censusDAO.getSpecificCensusData(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;
                default:
                    System.out.println("Fail=======================");
            }
        sortedStateCensusJson = new Gson().toJson(censusDTOs);
        return sortedStateCensusJson;
    }
}

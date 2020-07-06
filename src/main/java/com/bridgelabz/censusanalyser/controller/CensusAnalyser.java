package com.bridgelabz.censusanalyser.controller;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.*;
import com.bridgelabz.censusanalyser.utilities.CensusAdaptorFactory;
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

    public String getStateWiseSortedCensusData(String sortingParam ) {
        String sortedStateCensusJson = "";
            switch(sortingParam){
                case ParamConstants.STATE :
                    ArrayList<Object> censusDTOs = this.censusDataMap.values()
                            .stream().sorted((CensusDAO c1, CensusDAO c2) -> c1.state.compareTo(c2.state))
                            .map(censusDAO -> censusDAO.getSpecificCensusData(Country.INDIA))
                            .collect(Collectors.toCollection(ArrayList::new));
                    sortedStateCensusJson = new Gson().toJson(censusDTOs);
                    break;
                case ParamConstants.POPULAS_STATE :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c2.population.compareTo(c1.population));
                    sortedStateCensusJson = new Gson().toJson(this.censusDAOList);
                    break;
                case ParamConstants.POPULATION_DENSITY :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c2.densityPerSqKm.compareTo(c1.densityPerSqKm));
                    sortedStateCensusJson = new Gson().toJson(this.censusDAOList);
                    break;
                case ParamConstants.AREA :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c2.areaInSqKm.compareTo(c1.areaInSqKm));
                    sortedStateCensusJson = new Gson().toJson(this.censusDAOList);
                    break;
                case ParamConstants.US_POPULATION_DENSITY :
                    this.censusDAOList.sort((CensusDAO c1, CensusDAO c2) -> c2.populationDensity.compareTo(c1.populationDensity));
                    sortedStateCensusJson = new Gson().toJson(this.censusDAOList);
                    break;
                default:
                    System.out.println("Fail=======================");
            }

             //sortedStateCensusJson = new Gson().toJson(this.censusDAOList);
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

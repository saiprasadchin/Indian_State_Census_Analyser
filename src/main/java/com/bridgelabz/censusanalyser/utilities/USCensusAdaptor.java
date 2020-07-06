package com.bridgelabz.censusanalyser.utilities;

import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.models.USCensus;

import java.util.Map;

public class USCensusAdaptor extends CensusAdaptor{
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) {
        Map<String, CensusDAO> censusDTOMap = super.loadCensusData(USCensus.class, csvFilePath[0]);
        return censusDTOMap;
    }
}

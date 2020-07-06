package com.bridgelabz.censusanalyser.utilities;

import com.bridgelabz.censusanalyser.controller.CensusAnalyser;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.CensusDAO;

import java.util.Map;

public class CensusAdaptorFactory {
    public static Map<String, CensusDAO> getCensusAdaptor(CensusAnalyser.Country country, String... csvFilePath) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdaptor().loadCensusData(csvFilePath);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdaptor().loadCensusData(csvFilePath);
        throw new CensusAnalyserException("Invalid Country Name", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}

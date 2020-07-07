package com.bridgelabz.censusanalyser.services;

import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.CSVStateCensus;
import com.bridgelabz.censusanalyser.models.CSVStateCode;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.opencsvbuilder.CSVBuilderException;
import com.bridgelabz.censusanalyser.opencsvbuilder.CSVBuilderFactory;
import com.bridgelabz.censusanalyser.opencsvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdaptor extends CensusAdaptor {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) {
        Map<String, CensusDAO> censusStateMap = super.loadCensusData(CSVStateCensus.class, csvFilePath[0]);
        this.loadIndianStateCode(censusStateMap, csvFilePath[1]);
        return censusStateMap;
    }

    private int loadIndianStateCode(Map<String, CensusDAO> censusCSVMap, String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCode.class);
            Iterable<CSVStateCode> csvIterable = () -> censusCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).filter(csvState -> censusCSVMap.get(csvState.stateName) != null)
                    .forEach(csvState -> censusCSVMap.get(csvState.stateName).stateId = csvState.stateCode);

            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_DELIMETER_WRONG_HEADER_FILE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}

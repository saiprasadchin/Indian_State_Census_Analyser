package com.bridgelabz.censusanalyser.utilities;

import com.bridgelabz.censusanalyser.controller.CensusAnalyser;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.CSVStateCensus;
import com.bridgelabz.censusanalyser.models.CSVStateCode;
import com.bridgelabz.censusanalyser.models.CensusDAO;
import com.bridgelabz.censusanalyser.models.USCensus;
import com.bridgelabz.censusanalyser.opencsvbuilder.CSVBuilderException;
import com.bridgelabz.censusanalyser.opencsvbuilder.CSVBuilderFactory;
import com.bridgelabz.censusanalyser.opencsvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {

        public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) {
            if (country.equals(CensusAnalyser.Country.INDIA))
                return loadCensusData(CSVStateCensus.class, csvFilePath);
            if (country.equals(CensusAnalyser.Country.US))
                return loadCensusData(USCensus.class, csvFilePath);
            if (country.equals(CensusAnalyser.Country.INDIA_STATE))
                return loadCensusData(CSVStateCode.class, csvFilePath);
            throw new CensusAnalyserException("Invalid Country Name", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
        }
        public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) {
            Map<String, CensusDAO> censusMap = new HashMap<>();
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
                Iterable<E> csvIterable = () -> csvFileIterator;
                if (censusCSVClass.getName().equals("com.bridgelabz.censusanalyser.models.CSVStateCensus")) {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(CSVStateCensus.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                } else if (censusCSVClass.getName() == "com.bridgelabz.censusanalyser.models.USCensus") {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(USCensus.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDAO(csvState)));
                } else if (censusCSVClass.getName() == "com.bridgelabz.censusanalyser.models.CSVStateCode") {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(CSVStateCode.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.stateName, new CensusDAO(csvState)));
                }
                return censusMap;
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


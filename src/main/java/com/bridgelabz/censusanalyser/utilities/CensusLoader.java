package com.bridgelabz.censusanalyser.utilities;

import com.bridgelabz.censusanalyser.controller.StateCensusAnalyser;
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

        public Map<String, CensusDAO> loadCensusData(StateCensusAnalyser.Country country, String... csvFilePath) {
            if (country.equals(StateCensusAnalyser.Country.INDIA))
                return loadCensusData(CSVStateCensus.class, csvFilePath);
            if (country.equals(StateCensusAnalyser.Country.US))
                return loadCensusData(USCensus.class, csvFilePath);
            if (country.equals(StateCensusAnalyser.Country.INDIA_STATE))
                return loadCensusData(CSVStateCode.class, csvFilePath);
            throw new CensusAnalyserException("Invalid Country Name", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
        }
        public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) {
            Map<String, CensusDAO> censusStateMap = new HashMap<>();
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
                System.out.println(censusCSVClass.getName());
                Iterable<E> csvIterable = () -> csvFileIterator;
                if (censusCSVClass.getName().equals("com.bridgelabz.censusanalyser.models.CSVStateCensus")) {
                    System.out.println("test");
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(CSVStateCensus.class::cast)
                            .forEach(csvState -> censusStateMap.put(csvState.state, new CensusDAO(csvState)));
                } else if (censusCSVClass.getName() == "com.bridgelabz.censusAnalyser.models.USCensus") {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(USCensus.class::cast)
                            .forEach(csvState -> censusStateMap.put(csvState.state, new CensusDAO(csvState)));
                } else if (censusCSVClass.getName() == "com.bridgelabz.censusAnalyser.models.CSVStateCode") {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(CSVStateCode.class::cast)
                            .forEach(csvState -> censusStateMap.put(csvState.stateName, new CensusDAO(csvState)));
                }
                System.out.println(censusStateMap.size());
                return censusStateMap;
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
//}

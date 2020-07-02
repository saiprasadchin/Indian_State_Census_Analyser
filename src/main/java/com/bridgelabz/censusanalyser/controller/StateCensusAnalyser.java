package com.bridgelabz.censusanalyser.controller;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.CSVStateCensus;
import com.bridgelabz.censusanalyser.models.CSVStateCode;
import com.bridgelabz.censusanalyser.models.IndiaCensusDAO;
import com.bridgelabz.censusanalyser.models.ParamConstants;
import com.bridgelabz.censusanalyser.opencsvbuilder.CSVBuilderException;
import com.bridgelabz.censusanalyser.opencsvbuilder.CSVBuilderFactory;
import com.bridgelabz.censusanalyser.opencsvbuilder.ICSVBuilder;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    List<IndiaCensusDAO> indiaCensusDAOList = null;

    public StateCensusAnalyser(){
        this.indiaCensusDAOList = new ArrayList<IndiaCensusDAO>();
    }

    public Map<String, IndiaCensusDAO> loadCensusData(String csvFilePath) {
        loadIndiaCensusData(csvFilePath);
        Map<String, IndiaCensusDAO> censusDataMap = this.indiaCensusDAOList.stream().collect(Collectors.toMap(IndiaCensusDAO::getState,Function.identity()));
        return censusDataMap;
    }

    public int loadIndiaCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCensus> csvStateCensusList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
            csvStateCensusList.stream().forEach(c->{
                IndiaCensusDAO indiaCensusDAO = new IndiaCensusDAO(c);
                this.indiaCensusDAOList.add(indiaCensusDAO);
            });
            return csvStateCensusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_DELIMETER_WRONG_HEADER_FILE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) {
        try (Reader readerState = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCode> stateCodeList = csvBuilder.getCSVFileList(readerState, CSVStateCode.class);
            return stateCodeList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.STATE_CODE_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_DELIMETER_WRONG_HEADER_FILE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false)
                .count();
        return numOfEnteries;
    }

    public String getStateWiseSortedCensusData(String sortingParam ) {

            switch(sortingParam){
                case ParamConstants.STATE :
                    this.indiaCensusDAOList.sort((IndiaCensusDAO c1, IndiaCensusDAO c2) -> c1.state.compareTo(c2.state));
                    break;
                case ParamConstants.POPULAS_STATE :
                    this.indiaCensusDAOList.sort((IndiaCensusDAO c1, IndiaCensusDAO c2) -> c2.population.compareTo(c1.population));
                    break;
                case ParamConstants.POPULATION_DENSITY :
                    this.indiaCensusDAOList.sort((IndiaCensusDAO c1, IndiaCensusDAO c2) -> c2.densityPerSqKm.compareTo(c1.densityPerSqKm));
                    break;
                case ParamConstants.AREA :
                    System.out.println("area");
                    this.indiaCensusDAOList.sort((IndiaCensusDAO c1, IndiaCensusDAO c2) -> c2.areaInSqKm.compareTo(c1.areaInSqKm));
                    break;
                default:
                    System.out.println("Fail=======================");
            }

            String sortedStateCensusJson = new Gson().toJson(this.indiaCensusDAOList);
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

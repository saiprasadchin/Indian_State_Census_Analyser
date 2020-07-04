package com.bridgelabz.censusanalyser.controller;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.*;
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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StateCensusAnalyser {
    List<CensusDAO> censusDAOList = null;

    public StateCensusAnalyser(){
        this.censusDAOList = new ArrayList<CensusDAO>();
    }

    public Map<String, CensusDAO> loadCensusData(String csvFilePath) {
        Map<String, CensusDAO> censusDataMap = this.censusDAOList.stream().collect(Collectors.toMap(CensusDAO::getState,Function.identity()));
        return censusDataMap;
    }

    public int loadIndiaCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCensus> csvStateCensusList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
            csvStateCensusList.stream().forEach(c->{
            CensusDAO censusDAO = new CensusDAO(c);
            this.censusDAOList.add(censusDAO);
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

    public int loadUSCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<USCensus> csvStateCensusList = csvBuilder.getCSVFileList(reader, USCensus.class);
            csvStateCensusList.stream().forEach(c->{
                CensusDAO censusDAO = new CensusDAO(c);
                this.censusDAOList.add(censusDAO);
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
            stateCodeList.stream().forEach(c->{
                CensusDAO censusDAO = new CensusDAO(c);
                this.censusDAOList.add(censusDAO);
            });
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

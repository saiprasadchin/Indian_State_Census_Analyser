package com.bridgelabz.censusanalyser.controller;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.CSVStateCensus;
import com.bridgelabz.censusanalyser.models.ParamConstants;
import com.bridgelabz.censusanalyser.opencsvbuilder.CSVBuilderException;
import com.bridgelabz.censusanalyser.opencsvbuilder.CSVBuilderFactory;
import com.bridgelabz.censusanalyser.opencsvbuilder.ICSVBuilder;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    private static final String SAMPLE_JSON_FILE_PATH = "./json-sample.json";
    public int loadIndiaCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCensus> censusCSVList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
            return censusCSVList.size();
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
            List<CSVStateCensus> censusCSVList = csvBuilder.getCSVFileList(readerState, CSVStateCensus.class);
            return censusCSVList.size();
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

    public String getStateWiseSortedCensusData(String csvFilePath, String sortingParam) {
        try (Reader readerState = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCensus> censusCSVList = csvBuilder.getCSVFileList(readerState, CSVStateCensus.class);
            switch(sortingParam){
                case ParamConstants.STATE :
                    censusCSVList.sort((CSVStateCensus c1, CSVStateCensus c2) -> c1.state.compareTo(c2.state));
                    break;
                case ParamConstants.POPULAS_STATE :
                    censusCSVList.sort((CSVStateCensus c1, CSVStateCensus c2) -> c2.population.compareTo(c1.population));
                    break;
                case ParamConstants.POPULATION_DENSITY :
                    censusCSVList.sort((CSVStateCensus c1, CSVStateCensus c2) -> c2.densityPerSqKm.compareTo(c1.densityPerSqKm));
                    break;
                case ParamConstants.AREA :
                    censusCSVList.sort((CSVStateCensus c1, CSVStateCensus c2) -> c2.areaInSqKm.compareTo(c1.areaInSqKm));
                    break;
                default:
                    System.out.println("Fail");
            }

            String sortedStateCensusJson = new Gson().toJson(censusCSVList);
            return sortedStateCensusJson;
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

    public void jsonFileWriter(String csvFilePath){
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            FileWriter writer = new FileWriter(SAMPLE_JSON_FILE_PATH);
            writer.write(getStateWiseSortedCensusData(csvFilePath, "AREA"));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

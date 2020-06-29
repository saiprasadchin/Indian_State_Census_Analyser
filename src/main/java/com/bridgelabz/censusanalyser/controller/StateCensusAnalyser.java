package com.bridgelabz.censusanalyser.controller;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) {
        int noOfRecords = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            System.out.println("test");
            CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStateCensus> censusCSVIterator = csvToBean.iterator();
            Iterable<CSVStateCensus> csvIterable = () -> censusCSVIterator;
            noOfRecords = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        return noOfRecords;
    }
}
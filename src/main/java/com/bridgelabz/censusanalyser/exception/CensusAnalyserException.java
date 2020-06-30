package com.bridgelabz.censusanalyser.exception;

public class CensusAnalyserException extends RuntimeException {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,WRONG_DELIMETER_WRONG_HEADER_FILE,STATE_CODE_FILE_PROBLEM
    }
    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}

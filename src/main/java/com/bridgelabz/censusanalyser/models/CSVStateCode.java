package com.bridgelabz.censusanalyser.models;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCode {
    @CsvBindByName(column = "SrNo", required = true)
    public int srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "CSVStateCode{" +
                "SrNo='" + srNo + '\'' +
                ", State Name='" + stateName + '\'' +
                ", TIN='" + tin + '\'' +
                ", State Code='" + stateCode + '\'' +
                '}';
    }
}
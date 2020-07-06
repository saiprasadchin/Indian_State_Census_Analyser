import com.bridgelabz.censusanalyser.controller.CensusAnalyser;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.models.CSVStateCensus;
import com.bridgelabz.censusanalyser.models.USCensus;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;


public class StateCensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_INCORRECT_TYPE = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData1.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_INCORRECT_HEADER = "./src/test/resources/IndiaStateCensusDataWrong.csv";
    private static final String INDIA_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_STATE_CODE_CSV_INCORRECT_TYPE = "./src/test/resources/IndiaStateCode.txt";
    private static final String WRONG_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode1.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH_INCORRECT_HEADER = "./src/test/resources/IndiaStateCodeWrong.csv";
    private static final String AREA_JSON_FILE_PATH = "./src/test/output/json-area.json";
    private static final String POPULOUS_JSON_FILE_PATH = "./src/test/output/json-populous.json";
    private static final String POPULATION_DENSITY_JSON_FILE_PATH = "./src/test/output/population-density.json";

    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int numOfRecords = 0;
        try {
             numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            Assert.assertEquals(29, numOfRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH,INDIA_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH_INCORRECT_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH_INCORRECT_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_WRONG_HEADER_FILE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH_INCORRECT_HEADER,INDIA_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_WRONG_HEADER_FILE, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFile_ReturnsCorrectRecords() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int numOfRecords = 0;
        try {
            numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            Assert.assertEquals(29, numOfRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_STATE_CODE_CSV_INCORRECT_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_STATE_CODE_CSV_FILE_PATH_INCORRECT_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_WRONG_HEADER_FILE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_STATE_CODE_CSV_FILE_PATH_INCORRECT_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMETER_WRONG_HEADER_FILE, e.type);
        }
    }

    @Test
    public void getIndianCensusData_WhenSortedCheckOnFirstState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData("STATE");
        CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
    }

    @Test
    public void getIndianCensusData_WhenSortedCheckOnEndState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData("STATE");
        CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        Assert.assertEquals("West Bengal", censusCSV[28].state);
    }

    @Test
    public void getIndianCensusData_WhenSortedOnPopulasState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData("POPULAS_STATE");
        CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
    }

    @Test
    public void getIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData("POPULATION_DENSITY");
        CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        Assert.assertEquals("Bihar", censusCSV[0].state);
    }

    @Test
    public void getIndianCensusData_WhenSortedOnAreaFile_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String jsonString  = censusAnalyser.getStateWiseSortedCensusData("AREA");
        censusAnalyser.jsonFileWriter(AREA_JSON_FILE_PATH,jsonString);
        CSVStateCensus[] censusCSV = new Gson().fromJson(jsonString, CSVStateCensus[].class);
        Assert.assertEquals("Rajasthan", censusCSV[0].state);
    }

    @Test
    public void getIndianCensusData_WhenSortedOnPopulationFile_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int actualAvlue = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String jsonString  = censusAnalyser.getStateWiseSortedCensusData("POPULAS_STATE");
        censusAnalyser.jsonFileWriter(POPULOUS_JSON_FILE_PATH,jsonString);
        Assert.assertEquals(29, actualAvlue);
    }

    @Test
    public void getIndianCensusData_WhenSortedOnPopulationDensityFile_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String jsonString  = censusAnalyser.getStateWiseSortedCensusData("POPULATION_DENSITY");
        censusAnalyser.jsonFileWriter(POPULATION_DENSITY_JSON_FILE_PATH,jsonString);
        CSVStateCensus[] censusCSV = new Gson().fromJson(jsonString, CSVStateCensus[].class);
        Assert.assertEquals(1102, (int)censusCSV[0].densityPerSqKm);
    }
    //UC9
    @Test
    public void getUSCensusData_WhenSortedOnPopulasState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData("POPULAS_STATE");
        CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
        Assert.assertEquals(37253956, (int)censusCSV[0].population);
    }
    //UC10
    @Test
    public void getUSCensusData_WhenSortedOnPopulasDensity_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData("US_POPULATION_DENSITY");
        USCensus[] censusCSV = new Gson().fromJson(sortedCensusData, USCensus[].class);
        Assert.assertEquals((Double) 3805.61, censusCSV[0].populationDensity);
    }
    //UC10
    @Test
    public void getUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData("US_POPULATION_DENSITY");
        USCensus[] censusCSV = new Gson().fromJson(sortedCensusData, USCensus[].class);
        Assert.assertEquals((Double) 177.0, censusCSV[0].area);
    }
    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecord() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int numOfState = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, numOfState);
    }
}

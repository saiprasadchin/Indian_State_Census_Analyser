import com.bridgelabz.censusanalyser.controller.StateCensusAnalyser;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateCensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";

    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData1.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();
        int numOfRecords = 0;
        try {
            numOfRecords = censusAnalyser.loadIndiaCensusData(StateCensusAnalyserTest.INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
}

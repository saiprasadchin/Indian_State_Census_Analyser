import com.bridgelabz.censusanalyser.controller.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
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
}

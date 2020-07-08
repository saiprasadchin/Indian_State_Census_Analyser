import com.bridgelabz.censusanalyser.controller.CensusAnalyser;
import com.bridgelabz.censusanalyser.dao.CensusDAO;
import com.bridgelabz.censusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.censusanalyser.services.CensusAdaptor;
import com.bridgelabz.censusanalyser.services.IndiaCensusAdaptor;
import com.bridgelabz.censusanalyser.services.USCensusAdaptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StateCensusAnalyserMockitoTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String INDIA_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";

    Map<String, CensusDAO> censusDAOMap;

    @Before
    public void setUp(){
        this.censusDAOMap = new HashMap<>();
        this.censusDAOMap.put("Odisha", new CensusDAO("Odisha", 298757, 2987.56, 5678.98, "OD"));
        this.censusDAOMap.put("Maharastra", new CensusDAO("Maharastra", 5489364, 89564.9, 768.44, "MH"));
        this.censusDAOMap.put("Karnataka", new CensusDAO("Karnataka", 7689699, 7686.8, 7568.8, "KA"));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenIndianCensusCSVFile_withMockito_ReturnsCorrectRecords() {
        CensusAnalyser censusAnalyser =  mock(CensusAnalyser.class);
        when(censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE)).thenReturn(29);
        try {
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            Assert.assertEquals(29, numOfRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_withMockito_ShouldReturnCorrectRecord() {
        CensusAnalyser censusAnalyser =  mock(CensusAnalyser.class);
        when(censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH)).thenReturn(51);
        int numOfState = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, numOfState);
    }

    @Test
    public void givenIndiaCensusAdaptorMoke_ShouldReturnCorrectRecords() {
        try {
            CensusAdaptor censusAdapter = mock(IndiaCensusAdaptor.class);
            when(censusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE)).thenReturn(this.censusDAOMap);
            Map<String, CensusDAO> censusDAOMapActual = censusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            Assert.assertEquals(3, censusDAOMapActual.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusAdaptorMock_ShouldReturnCorrectRecords() {
        try {
            CensusAdaptor censusAdapter = mock(USCensusAdaptor.class);
            when(censusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH)).thenReturn(this.censusDAOMap);
            Map<String, CensusDAO> censusDAOMapActual = censusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(3, censusDAOMapActual.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
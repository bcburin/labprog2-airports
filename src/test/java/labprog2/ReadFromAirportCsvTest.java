package labprog2;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.opencsv.exceptions.CsvException;

import labprog2.model.Airport;

import java.io.IOException;

public class ReadFromAirportCsvTest {

    @Test
    public void testReadAirportDataCsv() throws IOException, CsvException {

        Airport airports[] = Airport.readFromAirportCsv();

        assertTrue(airports[0].getIata().equals("CZS"));
        assertTrue(airports[2].getIata().equals("MCZ"));
        assertTrue(airports[7].getIata().equals("FOR"));
        assertTrue(airports[14].getIata().equals("CMG"));
        assertTrue(airports[20].getIata().equals("CWB"));

    }

}

package watherInfo;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CoreTest {
    @Test
    public void jsonToMap() throws Exception {
        Core core = new Core();
       }

    @Test
    public void init() throws Exception {
    }

    @Test
    public void getCityWithGreaterTemperatureRange() throws Exception {
    }

    @Test
    public void getCityWithMaxTemperature() throws Exception {
    }

    @Test
    public void getCityWithMinTemperature() throws Exception {
    }

    @Test
    public void createStringURL() throws Exception {
    }

    @Test
    public void getDataFromURL() throws Exception {
    }

    @Test
    public void getDataFromFile() throws Exception {
        Core core = new Core();
        List<String> s = core.getDataFromFile();
        assertFalse(s.isEmpty());
    }

}
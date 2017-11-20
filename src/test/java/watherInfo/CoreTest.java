package watherInfo;

import org.json.simple.JSONObject;
import org.junit.Test;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CoreTest {

    public boolean isJSONValid(Map<String, Object> test) {
        new JSONObject(test);
        return true;
    }

    @Test
    public void jsonToMap() throws Exception {
        Core core = new Core();
        List<String> cites = core.getDataFromFile();
        for (String city : cites) {
            String urlString = core.createStringURL(city);
            StringBuilder result = new StringBuilder();
            core.getDataFromURL(result, urlString);
            Map<String, Object> responseMap = core.jsonToMap(result.toString());
            assertTrue(isJSONValid(responseMap));
            assertNotNull(isJSONValid(responseMap));
        }
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
        Core core = new Core();
        List<String> cites = core.getDataFromFile();
        for(String city : cites) {
            String urlString = core.createStringURL(city);
            assertNotNull(core.createStringURL(city));
            assertTrue(urlString.startsWith("http://api.openweathermap.org/data/2.5/weather?q="));
        }
    }

    @Test
    public void getDataFromURL() throws Exception {
        Core core = new Core();
        List<String> cites = core.getDataFromFile();

        for (String city : cites) {
            String urlString = core.createStringURL(city);
            assertTrue(urlString.startsWith("http://api.openweathermap.org/data/2.5/weather?q="));
        }
    }

    @Test
    public void getDataFromFile() throws Exception {
        Core core = new Core();
        List<String> s = core.getDataFromFile();
        assertFalse(s.isEmpty());
    }
}
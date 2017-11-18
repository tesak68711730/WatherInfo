package watherInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class App
{
    private static Map<String, Object> jsonToMap(String str){
        return new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>(){}.getType()
        );
    }

    public static void main( String[] args )
    {
        List<String> cites = getDataFromFile();
        Map<String, Double> citesOfMinTemp = new HashMap<String, Double>();
        Map<String, Double> citesOfMaxTemp = new HashMap<String, Double>();
        Map<String, Double> citesMaxRangeOfTemp = new HashMap<String, Double>();
        for(String city : cites){
            StringBuilder result = new StringBuilder();
            String urlString = createStringURL(city);

            getDataFromURL(result, urlString);

            Map<String, Object> responseMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(responseMap.get("main").toString());
            citesOfMinTemp.put(city, (Double) mainMap.get("temp_min"));
            citesOfMaxTemp.put(city, (Double) mainMap.get("temp_max"));
            citesMaxRangeOfTemp.put(city, (Double)mainMap.get("temp_max") - (Double)mainMap.get("temp_min"));
        }

        List<String> temporaryMap = new ArrayList<String>();

//        System.out.println(citesOfMinTemp); //for view all cites With Min Temp
        getCityWithMinTemperature(citesOfMinTemp, temporaryMap);
        System.out.println("The city with the lowest minimum temperature : " + temporaryMap.get(0));
        temporaryMap.clear();

//        System.out.println(citesOfMaxTemp); //for view all cites With Max Temp
        getCityWithMaxTemperature(citesOfMaxTemp, temporaryMap);
        System.out.println("The city with the highest maximum temperature : " + temporaryMap.get(0));
        temporaryMap.clear();

//        System.out.println(citesMaxRangeOfTemp); //for view all cites With greater temperature range
        getCityWithGreaterTemperatureRange(citesMaxRangeOfTemp, temporaryMap);
        System.out.println("The city with the greater temperature range : " + temporaryMap.get(0));
        temporaryMap.clear();
    }

    private static void getCityWithGreaterTemperatureRange(Map<String, Double> citesMaxRangeOfTemp, List<String> temporary) {
        Double maxCityOfMaxRangeTemp = 0.0;
        Set< Map.Entry<String, Double>> citesMaxRangeOfTempSet = citesMaxRangeOfTemp.entrySet();
        for(Map.Entry<String, Double> me:citesMaxRangeOfTempSet) {
            if(me.getValue() > maxCityOfMaxRangeTemp)
                maxCityOfMaxRangeTemp = me.getValue();
        }
        for(Map.Entry<String, Double> me:citesMaxRangeOfTempSet) {
            if(me.getValue().equals(maxCityOfMaxRangeTemp))
                temporary.add(me.getKey());
        }
    }

    private static void getCityWithMaxTemperature(Map<String, Double> citesOfMaxTemp, List<String> temporary) {
        Double maxCityOfMaxTemp = -1000.0;
        Set< Map.Entry<String, Double>> citesOfMaxTempSet = citesOfMaxTemp.entrySet();
        for(Map.Entry<String, Double> me:citesOfMaxTempSet) {
            if(me.getValue() > maxCityOfMaxTemp)
                maxCityOfMaxTemp = me.getValue();
        }
        for(Map.Entry<String, Double> me:citesOfMaxTempSet) {
            if(me.getValue().equals(maxCityOfMaxTemp))
        temporary.add(me.getKey());
    }
    }

    private static void getCityWithMinTemperature(Map<String, Double> citesOfMinTemp, List<String> temporary) {
        Double maxCityOfMinTemp = 1000.0;
        Set< Map.Entry<String, Double>> citesOfMinTempSet = citesOfMinTemp.entrySet();
        for(Map.Entry<String, Double> me:citesOfMinTempSet) {
            if(me.getValue() < maxCityOfMinTemp)
                maxCityOfMinTemp = me.getValue();
        }
        for(Map.Entry<String, Double> me:citesOfMinTempSet) {
            if(me.getValue().equals(maxCityOfMinTemp))
                temporary.add(me.getKey());
        }
    }

    private static String createStringURL(String name) {
        String api_KEY = "619e23437c293f2daeffbdca424ae23f";
        String units = "metric"; //metric or Imperial
        return "http://api.openweathermap.org/data/2.5/weather?q=" +
                name + "&appid="+
                api_KEY+"&units=" + units;
    }

    private static void getDataFromURL(StringBuilder result, String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                result.append(line);
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getDataFromFile() {
        BufferedReader br = null;
        List<String> cites = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader("C:\\file.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                cites.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return cites;
    }
}

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Double > citesOfMinTemp = new HashMap<String, Double>();
        Map<String, Double> citesOfMaxTemp = new HashMap<String, Double>();
        Map<String, Double> citesMaxRangeOfTemp = new HashMap<String, Double>();
        for(String city : cites){
            StringBuilder result = new StringBuilder();
            String urlString = getDataOfCity(city);
            System.out.println(city);
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
            Map<String, Object> responseMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(responseMap.get("main").toString());
            citesOfMinTemp.put(city, (Double) mainMap.get("temp_min"));
            citesOfMaxTemp.put(city, (Double) mainMap.get("temp_max"));
            citesMaxRangeOfTemp.put(city, (Double)mainMap.get("temp_max") - (Double)mainMap.get("temp_min"));
        }
        System.out.println(citesOfMinTemp);
        System.out.println(citesOfMaxTemp);
        System.out.println(citesMaxRangeOfTemp);
    }

    private static String getDataOfCity(String name) {
        String api_KEY = "619e23437c293f2daeffbdca424ae23f";
        String units = "metric"; //metric or Imperial
        return "http://api.openweathermap.org/data/2.5/weather?q=" +
                name + "&appid="+
                api_KEY+"&units=" + units;
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

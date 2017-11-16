package watherInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
        String api_KEY = "619e23437c293f2daeffbdca424ae23f";
        String location = "London";
        String units = "metric"; //metric or imperial
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" +
                location + "&appid="+
                api_KEY+"&units=" + units;
        StringBuilder result = new StringBuilder();

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
        Map<String, Object> windMap = jsonToMap(responseMap.get("wind").toString());

        System.out.println("Response Map" + responseMap);
        System.out.println("Main Map" + mainMap);
        System.out.println("Wind Map" + windMap + "/n/n/n");
        System.out.println("-----------------------------------------");

        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(String.valueOf(result));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(json);
        System.out.println(json.get("main"));
        System.out.println(json.get("name"));

        JSONObject main = (JSONObject) json.get("main");
        System.out.println(main.get("pressure"));
        System.out.println("-----------------------------------------");

        List<String> cites = getDataFromFile();
        for(String city : cites){
            System.out.println(city);
        }
    }

    private static List<String> getDataFromFile() {
        BufferedReader br = null;
        List<String> cites = null;
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

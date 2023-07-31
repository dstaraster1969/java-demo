package helpers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Helpers {
    public String getURL() throws Exception  {
        Yaml yaml = new Yaml();
        String s = File.separator;
        String configFilePath = "." + s + "src" + s + "main" + s + "java" + s + "helpers" + s + "config.yml";
        InputStream inputStream = new FileInputStream(new File(configFilePath));
        Map<String, String> map = yaml.load(inputStream);
        System.out.println(map);

        return map.get("URL");
    }

    public JSONArray getDataFromAPI(String URL, Integer numPages)
            throws IOException, InterruptedException {
        JSONArray results = new JSONArray();
        RequestMaker requestMaker = new RequestMaker(URL, numPages);
        // call into the API once for each page requested
        for(int i = 0; i < numPages; i++) {
            // makeRequest returns a string representation of a JSONArray
            JSONArray returnData = new JSONArray(requestMaker.makeRequest(URL, i));

            // iterate over the JSONArray and iterate over the JSONObjects and add to results
            // just appending the JSONArray means I end up with an array of JSONArrays
            // we want an array made up of JSONObjects
            for(int j = 0; j < returnData.length(); j++) {
                results.put(returnData.getJSONObject(j));
            }
        }
        return results;
    }

    public void run() {

    }
}

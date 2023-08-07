import org.json.JSONArray;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpHelpers {
    public String getURL() throws Exception  {
        Yaml yaml = new Yaml();
        String s = File.separator;
        String configFilePath = "." + s + "src" + s + "main" + s + "java" + s + "resources" + s + "config.yml";
        InputStream inputStream = new FileInputStream(new File(configFilePath));
        Map<String, String> map = yaml.load(inputStream);

        return map.get("URL");
    }

    public JSONArray getDataFromAPI(String URL, Integer numPages)
            throws IOException, InterruptedException {
        JSONArray results = new JSONArray();

        // call into the API once for each page requested
        for(int i = 0; i < numPages; i++) {
            // makeRequest returns a string representation of a JSONArray;
            // Run calls to makeRequest() concurrently
//            RequestRunnable requestRunnable = new RequestRunnable(URL, numPages);
            RequestThread thread = new RequestThread(URL, numPages);
            thread.start();
            thread.join();
            JSONArray returnData = new JSONArray(thread.getResponse());

            // iterate over the JSONArray and iterate over the JSONObjects and add to results
            // just appending the JSONArray means ends up with an array of JSONArrays
            // we want an array made up of JSONObjects
            for(int j = 0; j < returnData.length(); j++) {
                results.put(returnData.getJSONObject(j));
            }
        }
        return results;
    }

    protected String makeRequest(String URL, Integer pageNum) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + pageNum))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}

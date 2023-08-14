import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class RequestHelpers {
    public String getURL() throws Exception  {
        Yaml yaml = new Yaml();
        String s = File.separator;
        String configFilePath = "." + s + "src" + s + "main" + s + "java" + s + "helpers" + s + "config.yml";
        InputStream inputStream = new FileInputStream(new File(configFilePath));
        Map<String, String> map = yaml.load(inputStream);

        return map.get("URL");
    }

    public JSONArray getDataFromAPI(String URL, Integer numPages)
            throws IOException, InterruptedException, ExecutionException {
        JSONArray results = new JSONArray();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        List<Future<String>> futures = new ArrayList<>();
        // call into the API once for each page requested
        for(int i = 0; i < numPages; i++) {
            // for each of numPages, kick off getting data
            Callable<String> callable = new MyCallable(URL, i);
            futures.add(executorService.submit(callable));
            }
        // process the results
        for (Future<String> future : futures) {
//            JSONArray jsonArray = new JSONArray(future.get());
                JSONArray jsonArray = new JSONArray(future.get());
            String name = jsonArray.getJSONObject(0).getString("name");
            for (int j = 0; j < jsonArray.length(); j++) {
                results.put(jsonArray.getJSONObject(j));
            }
        }
        executorService.shutdown();
        return results;
    }

    protected static String makeRequest(String URL, Integer pageNum) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + pageNum))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}

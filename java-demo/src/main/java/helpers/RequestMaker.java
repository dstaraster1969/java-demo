package helpers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestMaker {
    String URL;
    Integer pageNum;
    public RequestMaker(String URL, Integer pageNum) {
        this.URL = URL;
        this.pageNum = pageNum;
    }

    public String makeRequest(String URL, Integer pageNum) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + pageNum))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}

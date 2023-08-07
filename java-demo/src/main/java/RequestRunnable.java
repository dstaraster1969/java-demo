import java.io.IOException;

public class RequestRunnable implements Runnable{
    private final String url;
    private final Integer pageNum;
    private String response;

    public RequestRunnable(String url, Integer pageNum) {
        this.url = url;
        this.pageNum = pageNum;
    }

    @Override
    public void run() {
        try {
            response = new HttpHelpers().makeRequest(url, pageNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getResponse() {
        return response;
    }
}

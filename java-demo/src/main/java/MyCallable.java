import java.util.concurrent.Callable;

// MyCallable was implemented to allow for multithreading
public class MyCallable implements Callable {
    String url;
    Integer pageNum;
    MyCallable(String url, Integer pageNum) {
        this.url = url;
        this.pageNum = pageNum;
    }

    @Override
    public String call() throws Exception {
        // makeRequest returns a string representation of a JSONArray
        return RequestHelpers.makeRequest(url, pageNum);
    }
}

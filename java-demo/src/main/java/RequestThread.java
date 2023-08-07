public class RequestThread extends Thread {
    private final String url;
    private final Integer pageNum;
    private String response;

    public RequestThread(String url, Integer pageNum) {
        this.url = url;
        this.pageNum = pageNum;
    }

    @Override
    public void run() {
        System.out.println("In thread.run() " + this.getName() + "\n");
        try {
            response = new HttpHelpers().makeRequest(url, pageNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getResponse() {
        System.out.println("In getResponse() " + this.getName() + "\n");
        return response;
    }
}

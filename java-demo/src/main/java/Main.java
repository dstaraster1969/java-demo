import org.json.JSONArray;

public class Main {
    public static void main(String[] args) {
        // Parse command line args
        if(args.length != 2) {
            System.out.println("java main.java numPages, filters (a JSON object)");
            System.exit(1);
        }
        Integer numPages = Integer.parseInt(args[0]);
        String filters = args[1];

        HttpHelpers httpHelpers = new HttpHelpers();
        try {
            String URL = httpHelpers.getURL();
            JSONArray results = httpHelpers.getDataFromAPI(URL, numPages);
//            System.out.println(results.toString(4));
        } catch(Exception e) {
            System.exit(1);
        }

    }
}

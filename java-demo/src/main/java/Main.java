import helpers.Helpers;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Parse command line args
        if(args.length != 2) {
            System.out.println("java main.java numPages, filters (a JSON object)");
            System.exit(1);
        }
        Integer numPages = Integer.parseInt(args[0]);
        String filters = args[1];

        Helpers helpers = new Helpers();
        try {
            String URL = helpers.getURL();
            JSONArray results = helpers.getDataFromAPI(URL, numPages);
            System.out.println(results.toString(4));
        } catch(Exception e) {
            System.exit(1);
        }

    }
}

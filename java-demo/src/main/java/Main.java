import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // Parse command line args
        if(args.length != 2) {
            System.out.println("java main.java numPages, filters (a JSON object)");
            System.exit(1);
        }
        Integer numPages = Integer.parseInt(args[0]);
        String filters = args[1];

        RequestHelpers requestHelpers = new RequestHelpers();
        try {
            String URL = requestHelpers.getURL();
            JSONArray results = requestHelpers.getDataFromAPI(URL, numPages);
            BufferedWriter writer = new BufferedWriter(new FileWriter("." + File.separator + "results.json"));
            writer.write(results.toString(4));
            writer.close();

        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Elapsed time " + (end - start));
    }
}

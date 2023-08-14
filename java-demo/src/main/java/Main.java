import org.json.JSONArray;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        // Get the number of pages to fetch and the filters from the command line.
        if(args.length != 2) {
            System.out.println("\nTo run: java main.java numPages, filters (a JSON object, can be nested)\n"
                    + "Filtered results will be saved to results.json\n");
            System.exit(1);
        }
        Integer numPages = Integer.parseInt(args[0]);
        String filters = args[1];

        RequestHelpers requestHelpers = new RequestHelpers();
        try {
            // Get the URL from the YAML config file
            String URL = requestHelpers.getURL();

            // Request all data returned from the first numPages
            JSONArray results = requestHelpers.getDataFromAPI(URL, numPages);

            // Send all raw data and the array of filters to filterAll()
            JSONArray filtered_results = new Filter().filterAll(results, filters);

            // Write the results to a file. This could easily be modified to be saved differently.
            BufferedWriter writer = new BufferedWriter(new FileWriter("." + File.separator + "results.json"));
            writer.write(filtered_results.toString(4));
            writer.close();

        } catch(Exception e) {
            // If something went wrong, bail
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}

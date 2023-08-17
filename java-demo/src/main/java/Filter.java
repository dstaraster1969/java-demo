import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class Filter {
    // filterAll() takes all of the results from the API and all of the filters
    // then it filters the data on each filter
    protected JSONArray filterAll(JSONArray unfiltered, String filters) throws JsonProcessingException {
        JSONArray filter_array = new JSONArray(filters);
        JSONArray results = applyFilter(unfiltered, filter_array.getJSONObject(0));
        filter_array.remove(0);
        for(Object f : filter_array) {
            results = applyFilter(results, (JSONObject) f);
        }
        return results;
    }

    // applyFilter() applies a single filter to all shows
    private JSONArray applyFilter(JSONArray objs, JSONObject filter) throws JsonProcessingException {
        JSONArray results = new JSONArray();
        // checks each json represented object against the filter
        for(int i = 0; i < objs.length(); i++) {
            JSONObject show = objs.getJSONObject(i);
            if (checkForMatch(show, filter)) {
                results.put(show);
            }
        }
        return results;
    }

    // Recursively traverses both the JSON representation of the show and of the filter looking for
    // a leaf match
    private Boolean checkForMatch(JSONObject show, JSONObject filter) throws JsonProcessingException {
        // If the filter is empty, then of course the show meets that criterion.
        if(Objects.equals(filter, new JSONObject("{}"))) {
            return true;
        }

        // Convert both JSON representations to map objects in order to manipulate them
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> show_map = mapper.readValue(show.toString(), HashMap.class);
        HashMap<String, Object> filter_map = mapper.readValue(filter.toString(), HashMap.class);
        String key = (String) filter_map.keySet().toArray()[0];

        // If we're at a leaf whose value is null, see if the null matches both the show and filter.
        // This has to be in its own check because subsequent checks look at the class of the value
        // which doesn't work for null
        if(show_map.get(key) == null || filter_map.get(key) == null) {
            return show_map.get(key) == filter_map.get(key);
        }
        if(show_map.get(key).getClass() != LinkedHashMap.class) {
            // if the value for the key isn't a hashmap, then it's a leaf
            return show.get(key).equals(filter.get(key));
        } else {
            // We aren't at a leaf, so call recursively
            return checkForMatch((JSONObject) show.get(key), (JSONObject) filter.get(key));
        }
    }
}

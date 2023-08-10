import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyException;
import java.util.*;

public class Filter {
    protected JSONArray filterAll(JSONArray unfiltered, String filters) throws JsonProcessingException {
        JSONArray filter_array = new JSONArray(filters);
        JSONArray results = applyFilter(unfiltered, filter_array.getJSONObject(0));
        for(Object f : filter_array) {
            results = applyFilter(results, (JSONObject) f);
        }
        return results;
    }

    private JSONArray applyFilter(JSONArray objs, JSONObject filter) throws JsonProcessingException {
        JSONArray results = new JSONArray();
        for(int i = 0; i < objs.length(); i++) {
            JSONObject show = objs.getJSONObject(i);
            if (checkForMatch(show, filter)) {
                results.put(show);
            }
        }
        return results;
    }

    private Boolean checkForMatch(JSONObject show, JSONObject filter) throws JsonProcessingException {
        if(Objects.equals(filter, new JSONObject("{}"))) {
            return true;
        }
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> show_map = mapper.readValue(show.toString(), HashMap.class);
        HashMap<String, Object> filter_map = mapper.readValue(filter.toString(), HashMap.class);
        String key = (String) filter_map.keySet().toArray()[0];
        if(show_map.get(key) == null) {
            return false;
        } else if(show_map.get(key).getClass() != LinkedHashMap.class) {
            return show.get(key).equals(filter.get(key));
        } else {
            return checkForMatch((JSONObject) show.get(key), (JSONObject) filter.get(key));
        }
    }
}

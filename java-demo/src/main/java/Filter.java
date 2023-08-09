import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Filter {
    protected JSONArray filterAll(JSONArray unfiltered, String filters) throws JsonProcessingException {
        JSONArray array = new JSONArray(filters);
        JSONArray results = applyFilter(unfiltered, array.getJSONObject(0));
        //    JSONArray filtered = applyFilter(unfiltered, filters[0]
        return null;
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
        if(Objects.equals(filter, new JSONObject(""))) {
            return true;
        }
        HashMap<String, Object> hashMap = new ObjectMapper().readValue(show.toString(), HashMap.class);
        String firstKey = (String) hashMap.keySet().toArray()[0];
        Object firstKeyValue = hashMap.get(firstKey);
        if (firstKeyValue == null) {
            if (show.get(firstKey).equals(filter.get(firstKey))) {
                return true;
            } else {
                return false;
            }
        } else {
            return checkForMatch((JSONObject) show.get(firstKey), (JSONObject) filter.get(firstKey));
        }
    }
}

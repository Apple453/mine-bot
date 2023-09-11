package net.applee.minecraft.utils.string;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    static List<Object> obj = new ArrayList<>();
    public static Object getKey(String jsonString, String key) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return json.get(key);
        } catch (Exception q) {
            return null;
        }
    }

    public static Object[] getAllValues(JSONObject json) {
        return getAllValues(json, true);
    }

    private static Object[] getAllValues(JSONObject json, boolean main) {
        for (String key : json.keySet()) check(json.get(key));
        if (main) {
            Object[] arr = obj.toArray();
            obj.clear();
            return arr;
        }
        return null;
    }

    private static void collectArray(JSONArray array) {
        for (Object o : array) check(o);
    }

    private static void check(Object object) {
        if (object instanceof JSONObject json2) getAllValues(json2, false);
        else if (object instanceof JSONArray array2) collectArray(array2);
        else obj.add(object);
    }

    public static boolean isJsonValid(String jsonString) {
        try {
            new JSONObject(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

package net.applee.minecraft.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonHelper extends JSONObject {

    public JsonHelper() {
    }

    public JsonHelper(String source) throws JSONException {
        super(source);
    }

    public JsonHelper(Object source) throws JSONException {
        super(String.valueOf(source));
    }

    @Override
    public Object get(String key) {
        if (!has(key)) return null;
        return super.get(key);
    }

    @Override
    public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) {
        if (!has(key)) return null;
        return super.getEnum(clazz, key);
    }

    @Override
    public boolean getBoolean(String key) {
        if (!has(key)) return false;
        return super.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defaultBool) {
        if (!has(key)) return defaultBool;
        return super.getBoolean(key);
    }

    @Override
    public BigInteger getBigInteger(String key) {
        if (!has(key)) return new BigInteger("0");
        return super.getBigInteger(key);
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        if (!has(key)) return new BigDecimal("0");
        return super.getBigDecimal(key);
    }

    @Override
    public double getDouble(String key) {
        if (!has(key)) return 0;
        return super.getDouble(key);
    }

    @Override
    public float getFloat(String key) {
        if (!has(key)) return 0;
        return super.getFloat(key);
    }

    @Override
    public Number getNumber(String key) {
        if (!has(key)) return null;
        return super.getNumber(key);
    }

    @Override
    public int getInt(String key) {
        if (!has(key)) return 0;
        return super.getInt(key);
    }

    @Override
    public JSONArray getJSONArray(String key) {
        if (!has(key)) return null;
        return super.getJSONArray(key);
    }

    @Override
    public JsonHelper getJSONObject(String key) {
        if (!has(key)) return null;
        return new JsonHelper(super.getJSONObject(key).toString());
    }

    @Override
    public long getLong(String key) {
        if (!has(key)) return 0;
        return super.getLong(key);
    }

    @Override
    public String getString(String key) {
        if (!has(key)) return "";
        return super.getString(key);
    }
}

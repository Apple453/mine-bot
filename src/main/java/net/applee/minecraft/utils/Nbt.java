package net.applee.minecraft.utils;

import net.applee.minecraft.utils.math.BlockPos;
import org.json.JSONArray;
import org.json.JSONObject;

public class Nbt extends JSONObject {
    public static final Nbt EMPTY = new Nbt();

    public Nbt() {
        super();
    }

    public Nbt(Object nbtData) {
        this(String.valueOf(nbtData));
    }

    public Nbt(String nbtData) {
        super(nbtData);
    }

    public boolean getBool(String key) {
        String val = super.getString(key);
        if (val == null) return false;
        return val.charAt(0) != '0';
    }

    public void putBool(String key, boolean bool) {
        super.put(key, bool);
    }

    public String getString(String key) {
        return super.getString(key);
    }

    public int getInt(String key) {
        String val = super.getString(key);
        if (val == null) return 0;
        return Short.parseShort(val.substring(0, val.length()-1));
    }

    public Nbt putBlockPos(BlockPos pos) {
        super.put("X", pos.getX());
        super.put("Y", pos.getY());
        super.put("Z", pos.getZ());
        return this;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(super.getInt("X"), super.getInt("Y"), super.getInt("Z"));
    }

    public Nbt getNbt(String key) {
        return new Nbt(super.getJSONObject(key));
    }

    public JSONArray getArray(String key) {
        return super.getJSONArray(key);
    }

    public boolean contains(String key) {
        return super.has(key);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Nbt copy() {
        return new Nbt(super.toString());
    }

}

package net.applee.minecraft.client;

import org.json.JSONObject;

public class SkinParts {

    public enum Parts {
        CAPE,
        JACKET,
        LEFT_SLEEVE,
        RIGHT_SLEEVE,
        LEFT_LEG,
        RIGHT_LEG,
        HAT;

        public int bit() {
            return 1 << this.ordinal();
        }
    }

    public boolean capeActive = false;
    public boolean jacketActive = false;
    public boolean leftSleeveActive = false;
    public boolean rightSleeveActive = false;
    public boolean leftLegActive = false;
    public boolean rightLegActive = false;
    public boolean hatActive = false;

    public SkinParts() {
    }

    public SkinParts(byte flags) {
        fromFlags(flags);
    }
    public SkinParts(boolean all) {
        if (all) fromFlags((byte) 127);
    }

    public JSONObject writeJson(JSONObject json) {
        json.put("capeActive", capeActive);
        json.put("jacketActive", jacketActive);
        json.put("leftSleeveActive", leftSleeveActive);
        json.put("rightSleeveActive", rightSleeveActive);
        json.put("hatActive", hatActive);
        json.put("leftLegActive", leftLegActive);
        json.put("rightLegActive", rightLegActive);
        return json;
    }

    public void readJson(JSONObject json) {
        if (json == null) return;
        capeActive = json.getBoolean("capeActive");
        jacketActive = json.getBoolean("jacketActive");
        leftSleeveActive = json.getBoolean("leftSleeveActive");
        rightSleeveActive = json.getBoolean("rightSleeveActive");
        leftLegActive = json.getBoolean("leftLegActive");
        rightLegActive = json.getBoolean("rightLegActive");
        hatActive = json.getBoolean("hatActive");
    }


    public byte getFlags() {
        byte mask = 0;
        if (hatActive) mask |= Parts.HAT.bit();
        if (capeActive) mask |= Parts.CAPE.bit();
        if (jacketActive) mask |= Parts.JACKET.bit();
        if (leftLegActive) mask |= Parts.LEFT_LEG.bit();
        if (rightLegActive) mask |= Parts.RIGHT_LEG.bit();
        if (leftSleeveActive) mask |= Parts.LEFT_SLEEVE.bit();
        if (rightSleeveActive) mask |= Parts.RIGHT_SLEEVE.bit();
        return mask;
    }

    public void fromFlags(byte mask) {
        capeActive = (Parts.CAPE.bit() & mask) != 0;
        jacketActive = (Parts.JACKET.bit() & mask) != 0;
        leftSleeveActive = (Parts.LEFT_SLEEVE.bit() & mask) != 0;
        rightSleeveActive = (Parts.RIGHT_SLEEVE.bit() & mask) != 0;
        leftLegActive = (Parts.LEFT_LEG.bit() & mask) != 0;
        rightLegActive = (Parts.RIGHT_LEG.bit() & mask) != 0;
        hatActive = (Parts.HAT.bit() & mask) != 0;
    }

    @Override
    public String toString() {
        return "SkinParts{" +
                "capeActive=" + capeActive +
                ", jacketActive=" + jacketActive +
                ", leftSleeveActive=" + leftSleeveActive +
                ", rightSleeveActive=" + rightSleeveActive +
                ", leftLegActive=" + leftLegActive +
                ", rightLegActive=" + rightLegActive +
                ", hatActive=" + hatActive +
                '}';
    }
}

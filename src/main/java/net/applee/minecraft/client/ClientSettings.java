package net.applee.minecraft.client;

import net.applee.minecraft.enums.Arm;
import net.applee.minecraft.utils.JsonHelper;
import net.applee.minecraft.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ClientSettings {

    public static final String SETTINGS_FILE_NAME = "settings.json";

    private int chunkDistance = 3;
    private int simulationDistance = 10;
    private String language = "en_us";
    private Arm mainArm = Arm.RIGHT;
    private final SkinParts skinParts = new SkinParts(true);

    private boolean chunkLoading = true;
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public ClientSettings() {


        try {
            loadSettings(new JsonHelper(Utils.readOrCreateFile(SETTINGS_FILE_NAME)));
        } catch (JSONException | IOException e) {
            try {
                Utils.createFile(SETTINGS_FILE_NAME, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            mc.logger.warning("Can't parse settings file. Settings file recreated.");
        }
        saveSettings();

    }

    private void fillJson(JsonHelper json) {
        json.put("chunk_loading", chunkLoading);
        json.put("chunk_distance", chunkDistance);
        json.put("simulation_distance", simulationDistance);
        json.put("main_arm", mainArm.getName());
        json.put("language", language);
        json.put("skin_parts", skinParts.writeJson(new JSONObject()));
    }

    private void loadSettings(JsonHelper json) {
        chunkDistance = json.getInt("chunk_distance");
        simulationDistance = json.getInt("simulation_distance");
        mainArm = Arm.fromName(json.getString("main_arm"));
        language = json.getString("language");
        skinParts.readJson(json.getJSONObject("skin_parts"));
        chunkLoading = json.getBoolean("chunk_loading");
    }

    private void saveSettings() {
        JsonHelper json = new JsonHelper();
        fillJson(json);
        try {
            Utils.saveFile(SETTINGS_FILE_NAME, json.toString(4));
        } catch (IOException e) {
            mc.logger.error("Save settings error:", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "ClientSettings{" +
                "chunkDistance=" + chunkDistance +
                ", simulationDistance=" + simulationDistance +
                ", language='" + language + '\'' +
                ", mainArm=" + mainArm +
                ", skinParts=" + skinParts +
                ", chunkLoading=" + chunkLoading +
                '}';
    }

    public int getChunkDistance() {
        return chunkDistance;
    }

    public int getSimulationDistance() {
        return simulationDistance;
    }

    public String getLanguage() {
        return language;
    }

    public Arm getMainArm() {
        return mainArm;
    }

    public SkinParts getSkinParts() {
        return skinParts;
    }

    public boolean isChunkLoading() {
        return chunkLoading;
    }

}

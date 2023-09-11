package net.applee.minecraft.translation;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static net.applee.minecraft.utils.Utils.prints;

public class Translation {

    private static JSONObject defaultStorage;
    private static JSONObject translationStorage;

    private static final File translateFolder = new File("translate\\");

    public static String get(String key) {
        if (translationStorage.has(key)) return translationStorage.getString(key);
        if (defaultStorage.has(key)) return defaultStorage.getString(key);
        return key;
    }

    public static void init(String lang) {
        try {
            defaultStorage = new JSONObject(new String(Translation.class.getResourceAsStream("/translate/en_us.json").readAllBytes()))
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        translationStorage = getTranslateJson(lang);
    }

    public static JSONObject getTranslateJson(String name) {
        for (File f : translateFolder.listFiles()) {
            if (f.getName().startsWith(name)) {
                try {
                    FileInputStream fileData = new FileInputStream(f);
                    String jsonString = new String(fileData.readAllBytes(), StandardCharsets.UTF_8);
                    return new JSONObject(jsonString);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new JSONObject();
    }
}

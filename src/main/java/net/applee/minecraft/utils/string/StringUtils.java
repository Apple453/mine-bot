package net.applee.minecraft.utils.string;

public class StringUtils {

    public static String[] split(String string, int partLength) {
        String part = "";
        int cnt = 0;
        for (int i = string.length() - 1; i >= 0; i--) {
            part = string.charAt(i) + part;
            cnt++;
            if (cnt == partLength) {
                part = " " + part;
                cnt = 0;
            }
        }
        return part.split(" ");
    }
}

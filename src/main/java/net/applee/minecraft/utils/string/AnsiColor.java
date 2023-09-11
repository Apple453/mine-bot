package net.applee.minecraft.utils.string;

import net.applee.minecraft.utils.math.MathHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public enum AnsiColor {

    RESET(0, "r"),
    BOLD(1, "l"),
    ITALIC(3, "o"),
    UNDERLINED(4, "n"),
    INVERTED(7, "k"),
    STRIKE(9, "m"),
    SQUARED(51),
    SQUARED2(51),
    UNDERLINED_BOLD(21),

    BLACK(30, "black", "#000000", "0"),
    DARK_RED(31, "dark_red", "#aa0000",  "4"),
    DARK_GREEN(32, "dark_green", "#00aa00", "2"),
    DARK_YELLOW(33, "gold", "#ffaa00", "6"),
    DARK_BLUE(34, "dark_blue", "#0000aa", "1"),
    DARK_PURPLE(35, "dark_purple", "#aa00aa", "5"),
    DARK_CYAN(36, "dark_aqua", "#00aaaa", "3"),
    DARK_GRAY(90, "dark_gray", "#555555", "8"),

    GRAY(37, "gray", "#aaaaaa", "7"),
    RED(91, "red", "#ff5555", "c"),
    GREEN(92, "green", "#55ff55", "a"),
    YELLOW(93, "yellow", "#ffff55", "e"),
    BLUE(94, "blue", "#5555ff", "9"),
    PURPLE(95, "light_purple", "#ff55ff", "d"),
    CYAN(96, "aqua", "#55ffff", "b"),
    WHITE(97, "white", "#ffffff", "f"),

    INVERTED_DARK_BLACK(40),
    INVERTED_DARK_RED(41),
    INVERTED_DARK_GREEN(42),
    INVERTED_DARK_YELLOW(43),
    INVERTED_DARK_BLUE(44),
    INVERTED_DARK_PURPLE(45),
    INVERTED_DARK_CYAN(46),
    INVERTED_DARK_GRAY(47),

    INVERTED_GRAY(100),
    INVERTED_RED(101),
    INVERTED_GREEN(102),
    INVERTED_YELLOW(103),
    INVERTED_BLUE(104),
    INVERTED_PURPLE(105),
    INVERTED_CYAN(106),
    INVERTED_WHITE(107);

    public static final char TAG_PREFIX = '§';

    private final int value;
    @Nullable
    private final String name;
    @Nullable
    private final String hex;
    private final String tag;

    AnsiColor(int value) {
        this.value = value;
        this.name = null;
        this.hex = null;
        this.tag = null;
    }

    AnsiColor(int value, String tag) {
        this.value = value;
        this.name = null;
        this.hex = null;
        this.tag = tag;
    }

    AnsiColor(int value, String name, String hex, String tag) {
        this.value = value;
        this.name = name;
        this.hex = hex;
        this.tag = tag;
    }

    public int getValue() {
        return value;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getHex() {
        return hex;
    }

    @Nullable
    public String getTag() {
        if (tag == null) return null;
        return TAG_PREFIX + tag;
    }

    public int getRgb() {
        if (hex == null) return -1;
        return Integer.parseInt(hex.replace("#", ""), 16);
    }

    public static String parseTags(String text) {
        for (AnsiColor color : values()) {
            if (color.getTag() == null) continue;
            text = text.replace(color.getTag(), color.toString());
        }

        return text;
    }

    public static AnsiColor ofName(String name) {
        return ofName(name, AnsiColor.WHITE);
    }

    public static AnsiColor ofName(String name, AnsiColor def) {
        for (AnsiColor color : AnsiColor.values()) {
            if (name.equals(color.name)) return color;
        }
        return def;
    }

    public static AnsiColor ofHex(String hex) {
        Map<Integer, AnsiColor> diff = new HashMap<>();

        hex = hex.replace("#", "");

        int r1 = Integer.parseInt(hex.substring(0, 2), 16);
        int g1 = Integer.parseInt(hex.substring(2, 4), 16);
        int b1 = Integer.parseInt(hex.substring(4, 6), 16);

        for (AnsiColor color : AnsiColor.values()) {
            if (color.hex == null) continue;
            String cHex = color.hex.replace("#", "");

            int r = Integer.parseInt(cHex.substring(0, 2), 16) - r1;
            int g = Integer.parseInt(cHex.substring(2, 4), 16) - g1;
            int b = Integer.parseInt(cHex.substring(4, 6), 16) - b1;

            diff.put(MathHelper.abs(r*r + g*g + b*b), color);
        }

        int min = diff.keySet().toArray(Integer[]::new)[0];
        for (int i : diff.keySet()) {
            if (i < min) min = i;
        }

        return diff.get(min);
    }

    public static boolean isColored(String text) {
        for (AnsiColor color : values()) {
            if (text.contains(color.toString())) return true;
        }
        return false;
    }

    public static AnsiColor lastColor(String text) {
        AnsiColor last = null;
        int lastIndex = 0;

        for (AnsiColor color : values()) {
            int index = text.indexOf(color.toString());
            if (index > lastIndex) {
                lastIndex = index;
                last = color;
            }
        }
        return last;
    }

    public static String removeColors(String text) {
        for (AnsiColor color : values()) {
            text = text.replace(color.toString(), "");
        }

        return text;
    }

    public String toString() {
        return "\u001B[%sm".formatted(value);
    }


}

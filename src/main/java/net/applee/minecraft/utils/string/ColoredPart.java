package net.applee.minecraft.utils.string;

import net.applee.minecraft.utils.Utils;

public class ColoredPart {

    public final Color color;
    private final StringBuilder textBuilder = new StringBuilder();

    public static ColoredPart white(Object... args) {
        return of(AnsiColor.WHITE, args);
    }

    public static ColoredPart of(AnsiColor color, Object... args) {
        return new ColoredPart(color).add(args);
    }

    public ColoredPart(AnsiColor color) {
        this.color = Color.of(color);
    }

    public ColoredPart(Color color) {
        this.color = color;
    }

    public ColoredPart(Color color, Object... args) {
        this.color = color;
        add(args);
    }

    public ColoredPart add(Object... args) {
        if (textBuilder.isEmpty()) textBuilder.append(color);
        String formatted = Utils.formatString(" ", "", "", false, args);
        formatted = AnsiColor.removeColors(formatted);
        textBuilder.append(formatted);
        return this;
    }

    public ColoredPart add(ColoredPart part) {
        textBuilder.append(part);
        if (!part.color.equals(color)) textBuilder.append(color);
        return this;
    }

    @Override
    public String toString() {
        textBuilder.append(AnsiColor.RESET);
        return textBuilder.toString();
    }

    public static class Color {
        public AnsiColor main;
        private AnsiColor extra;

        public static Color of(AnsiColor main) {
            return of(main, null);
        }

        public static Color of(AnsiColor main, AnsiColor extra) {
            Color color = new Color();
            color.main = main;
            color.extra = extra;
            return color;
        }

        @Override
        public String toString() {
            return main.toString() + (extra != null ? extra.toString() : "");
        }
    }
}

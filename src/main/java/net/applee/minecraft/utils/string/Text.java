package net.applee.minecraft.utils.string;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.translation.Translation;
import net.applee.minecraft.utils.JsonHelper;
import net.applee.minecraft.utils.Nbt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.util.*;

import static net.applee.minecraft.utils.Utils.equalsNull;

public class Text {

    public String rawJson = null;

    private final List<Text> siblings = new ArrayList<>();
    private Style style = Style.EMPTY;
    private final MinecraftClient mc;

    public Text() {
        mc = MinecraftClient.getInstance();
    }

    public static Text literal(String text) {
        return new LiteralText(text);
    }

    public static Text of(String jsonString) {
        if (JsonUtil.isJsonValid(jsonString))
            return of(new JsonHelper(jsonString));
        return literal(jsonString);
    }

    public static Text of(JsonHelper json) {
        return empty().deserialize(json);
    }

    public static Text empty() {
        return new Text();
    }

    public static Text translatable(String key) {
        return new TranslatableText(key);
    }

    public void append(Text text) {
        siblings.add(text);
    }

    private void setStyle(Style style) {
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    public List<Text> getSiblings() {
        return siblings;
    }


    public String getText() {
        StringBuilder sb = new StringBuilder();
        convertToString(sb, this, false);
        return sb.toString();
    }

    public String getStyledText() {
        StringBuilder sb = new StringBuilder();
        convertToString(sb, this, true);
        return sb.toString();
    }

    private void convertToString(StringBuilder sb, Text text, boolean addStyle) {

        Style style = text.style;

        if (addStyle) {
            if (style.bold) sb.append(AnsiColor.BOLD);
            if (style.italic) sb.append(AnsiColor.ITALIC);
            if (style.strikethrough) sb.append(AnsiColor.STRIKE);
            if (style.underlined) sb.append(AnsiColor.UNDERLINED);
            if (style.color != null && style.color.color != null) sb.append(style.color.color);
        }
        if (text instanceof LiteralText literal) sb.append(literal.string);
        else if (style.insertion != null) sb.append(text.style.insertion);
        if (text instanceof TranslatableText translatable) {

            for (String key : translatable.translatable.keySet()) {
                Object[] insertions = translatable.translatable.get(key);

                String message = Translation.get(key);

                int insertIndex = 1;
                for (Object insert : insertions) {
                    StringBuilder insertion = new StringBuilder();
                    if (insert instanceof Text insertText) {
                        convertToString(insertion, insertText, addStyle);
                    } else insertion.append(insert);

                    String replacement = "%" + insertIndex + "$s";

                    message = message.replace(replacement, insertion.toString());
                    message = message.replaceFirst("%s", insertion.toString());
                    insertIndex++;
                }

                sb.append(message);
            }
        }

        for (Text sbl : text.siblings) {
            if (!text.siblings.isEmpty()) {
                convertToString(sb, sbl, addStyle);
            }
        }

        if (addStyle) sb.append(AnsiColor.RESET);
        text.siblings.clear();
    }

    private Text deserialize(Object jsonElement) {

        rawJson = String.valueOf(jsonElement);
        if (jsonElement instanceof JSONObject jsonObject0) {
            JsonHelper jsonObject = new JsonHelper(String.valueOf(jsonObject0));
            Text text;
            String nbt;
            if (jsonObject.has("text")) {

                nbt = jsonObject.getString("text");
                text = nbt.isEmpty() ? Text.empty() : new LiteralText(nbt);

            } else if (jsonObject.has("translate")) {
                nbt = jsonObject.getString("translate");
                if (jsonObject.has("with")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("with");
                    Object[] objects = new Object[jsonArray.length()];

                    for (int i = 0; i < objects.length; i++) {
                        objects[i] = optimizeArgument(this.deserialize(jsonArray.get(i)));
                    }

                    text = new TranslatableText(nbt, objects);
                } else {
                    text = new TranslatableText(nbt);
                }

            } else if (jsonObject.has("score")) {
                JsonHelper jsonObject2 = jsonObject.getJSONObject("score");

                if (!jsonObject2.has("name") || !jsonObject2.has("objective"))
                    throw new JSONException("A score component needs a least a name and an objective");

                text = new ScoreText(jsonObject2.getString("name"), jsonObject2.getString("objective"));
            } else if (jsonObject.has("selector")) {
                Optional<Text> optional = this.getSeparator(jsonObject);
                text = new SelectorText(jsonObject.getString("selector"), optional);
            } else if (jsonObject.has("keybind")) {
                text = new KeybindText(jsonObject.getString("keybind"));
            } else {

                if (!jsonObject.has("nbt")) {
                    throw new JSONException("Don't know how to turn " + jsonElement + " into a Component");
                }

                nbt = jsonObject.getString("nbt");
                Optional<Text> separator = this.getSeparator(jsonObject);

                boolean interpret = jsonObject.getBoolean("interpret", false);
                if (jsonObject.has("block")) {
//                    BlockNbtDataSource blockNbtDataSource = new BlockNbtDataSource(JsonHelper.getString(jsonObject, "block"));
                } else if (jsonObject.has("entity")) {
//                    EntityNbtDataSource entityNbtDataSource = new EntityNbtDataSource(JsonHelper.getString(jsonObject, "entity"));
                } else {
                    if (!jsonObject.has("storage")) {
                        throw new JSONException("Don't know how to turn " + jsonElement + " into a Component");
                    }
//                    StorageNbtDataSource storageNbtDataSource = new StorageNbtDataSource(new Identifier(JsonHelper.getString(jsonObject, "storage")));
                }

                text = new NbtText(new Nbt(nbt), interpret, separator);
            }
            if (jsonObject.has("extra")) {
                JSONArray jsonArray2 = jsonObject.getJSONArray("extra");
                if (jsonArray2.length() <= 0) throw new JSONException("Unexpected empty array of components");
                for (int j = 0; j < jsonArray2.length(); ++j) {
                    text.append(this.deserialize(jsonArray2.get(j)));
                }
            }
            text.setStyle(Style.of(jsonElement));

            return text;
        }

        if (jsonElement instanceof JSONArray jsonArray) {
            Text mutableText = null;
            for (Object i : jsonArray) {
                if (i instanceof JSONObject jsonElement2) {
                    Text mutableText2 = this.deserialize(jsonElement2);
                    if (mutableText == null) {
                        mutableText = mutableText2;
                        continue;
                    }
                    mutableText.append(mutableText2);
                }
            }
            return mutableText;
        }

        return Text.literal(String.valueOf(jsonElement));
    }

    private static Object optimizeArgument(Object text) {
        Text text2;
        if (text instanceof Text && (text2 = (Text) text).getStyle().isEmpty() && text2.getSiblings().isEmpty() && text2 instanceof LiteralText literalText) {
            return literalText.string;
        }
        return text;
    }

    private Optional<Text> getSeparator(JsonHelper json) {
        if (json.has("separator")) {
            return Optional.of(this.deserialize(json.get("separator")));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "Text{" +
                "siblings=" + siblings +
                ", style=" + style +
                '}';
    }

    public static class LiteralText extends Text {
        public final String string;

        public LiteralText(String text) {
            this.string = AnsiColor.parseTags(text);
        }

    }

    public static class TranslatableText extends Text {
        private final Map<String, Object[]> translatable = new HashMap<>();

        public TranslatableText(String key, Object[] vals) {
            translatable.put(key, vals);
        }

        public TranslatableText(String key) {
            translatable.put(key, new Object[0]);
        }

    }

    public static class ScoreText extends Text {

        private final String name;
        private final String objective;

        public ScoreText(String name, String objective) {
            this.name = name;
            this.objective = objective;
        }

    }

    public static class SelectorText extends Text {

        protected final Optional<Text> separator;
        private final String pattern;

        public SelectorText(String pattern, Optional<Text> separator) {
            this.pattern = pattern;
            this.separator = separator;
        }
    }

    public static class KeybindText extends Text {

        private final String key;

        public KeybindText(String key) {
            this.key = key;
        }
    }

    public static class NbtText extends Text {

        private final Nbt nbt;
        private final boolean interpret;
        private final Optional<Text> separator;

        public NbtText(Nbt nbt, boolean interpret, Optional<Text> separator) {
            this.nbt = nbt;
            this.interpret = interpret;
            this.separator = separator;
        }
    }

    public static class Style {
        public static final Style EMPTY = new Style(null, false, false, false, false, false, null, null, null, null);

        @Nullable
        private final TextColor color;
        private final boolean bold;
        private final boolean italic;
        private final boolean underlined;
        private final boolean strikethrough;
        private final boolean obfuscated;
        @Nullable
        private final ClickEvent clickEvent;
        @Nullable
        private final HoverEvent hoverEvent;
        @Nullable
        private final String insertion;
        @Nullable
        private final String font;

        public Style(@Nullable TextColor color, boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean obfuscated, @Nullable ClickEvent clickEvent, @Nullable HoverEvent hoverEvent, @Nullable String insertion, @Nullable String font) {
            this.color = color;
            this.bold = bold;
            this.italic = italic;
            this.underlined = underlined;
            this.strikethrough = strikethrough;
            this.obfuscated = obfuscated;
            this.clickEvent = clickEvent;
            this.hoverEvent = hoverEvent;
            this.insertion = insertion;
            this.font = font;
        }

        public static Style of(Object jsonObj) {
            if (jsonObj == null) return EMPTY;

            JsonHelper json = new JsonHelper(jsonObj);

            String col = json.getString("color");

            return new Style(
                    col == null || col.isBlank() ? null : new TextColor(col),
                    json.getBoolean("bold"),
                    json.getBoolean("italic"),
                    json.getBoolean("underlined"),
                    json.getBoolean("strikethrough"),
                    json.getBoolean("obfuscated"),
                    new ClickEvent(json.getJSONObject("clickEvent")),
                    new HoverEvent(json.getJSONObject("hoverEvent")),
                    json.getString("insertion"),
                    json.getString("font")

            );

        }

        public boolean isEmpty() {
            return EMPTY.equals(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Style style = (Style) o;
            return bold == style.bold &&
                    italic == style.italic &&
                    underlined == style.underlined &&
                    strikethrough == style.strikethrough &&
                    obfuscated == style.obfuscated &&
                    equalsNull(color, style.color) &&
                    equalsNull(clickEvent, style.clickEvent) &&
                    equalsNull(hoverEvent, style.hoverEvent) &&
                    equalsNull(insertion, style.insertion) &&
                    equalsNull(font, style.font);
        }

        @Override
        public String toString() {
            return "Style{" +
                    "color=" + color +
                    ", bold=" + bold +
                    ", italic=" + italic +
                    ", underlined=" + underlined +
                    ", strikethrough=" + strikethrough +
                    ", obfuscated=" + obfuscated +
                    ", clickEvent=" + clickEvent +
                    ", hoverEvent=" + hoverEvent +
                    ", insertion='" + insertion + '\'' +
                    ", font='" + font + '\'' +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(color, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);
        }
    }

    public static class TextColor {

        private final AnsiColor color;

        public TextColor(AnsiColor color) {
            this.color = color;
        }

        public TextColor(String color) {
            if (color.startsWith("#"))
                this.color = AnsiColor.ofHex(color);
            else this.color = AnsiColor.ofName(color);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TextColor textColor = (TextColor) o;
            return Objects.equals(color, textColor.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color);
        }
    }

    public static class ClickEvent {

        public ClickEvent(JsonHelper json) {
            if (json != null) {
//                prints("CLICK EVENT");
//                prints(json);
            }
        }

    }

    public static class HoverEvent {

        public HoverEvent(JsonHelper json) {
            if (json != null) {
//                prints("HOVER EVENT");
//                prints(json);
            }
        }

    }

}

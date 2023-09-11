package net.applee.minecraft.utils;


import net.applee.minecraft.utils.string.AnsiColor;
import net.applee.minecraft.utils.string.ColoredPart;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Utils {

    public static void printex(Exception e) {
        ColoredPart stackTrace = new ColoredPart(AnsiColor.DARK_RED);
        stackTrace.add();
        stackTrace.add(e.getClass().getSimpleName() + ":", e.getMessage(), "\n");
        for (StackTraceElement s : e.getStackTrace()) {
            stackTrace.add("\t" + s + "\n");
        }
        printc(stackTrace);
    }

    public static void printc(ColoredPart formatter) {
        print(formatter.toString());
    }

    public static void printb(int num) {
        print(Integer.toBinaryString(num));
    }

    public static void prints(Object... args) {
        printf(" ", "", "", false, args);
    }

    public static void print(Object... args) {
        printf(", ", "", "", true, args);
    }

    public static void printf(String delimiter, String p, String s, boolean stringBrackets, Object... args) {
        print(formatString(delimiter, p, s, stringBrackets, args));
    }

    public static void print(String s) {
        System.out.println(s);
    }

    public static String formatString(String delimiter, String p, String s, boolean stringBrackets, Object... args) {
        if (args == null) return "null";
        else {
            StringJoiner joiner = new StringJoiner(delimiter, p, s);
            for (Object o : args) {

                if (o == null) joiner.add("null");
                else {

                    Class<?> oClass = o.getClass();
                    if (oClass.isArray()) {

                        StringJoiner arrayJoiner = new StringJoiner(", ", "[", "]");
                        if (oClass.getComponentType().isPrimitive()) {
                            int length = Array.getLength(o);
                            for (int i = 0; i < length; i++) {
                                Object arrayObject = Array.get(o, i);
                                if (stringBrackets && arrayObject instanceof Character character)
                                    arrayJoiner.add("'" + character + "'");
                                else arrayJoiner.add(String.valueOf(arrayObject));
                            }
                        } else {
                            Object[] array = (Object[]) o;
                            for (Object arrayObject : array) {
                                if (stringBrackets && arrayObject instanceof String string)
                                    arrayJoiner.add("\"" + string + "\"");
                                else arrayJoiner.add(String.valueOf(arrayObject));
                            }
                        }

                        joiner.add(arrayJoiner.toString());

                    } else {
                        if (stringBrackets && o instanceof Character character) joiner.add("'" + character + "'");
                        else if (stringBrackets && o instanceof String string) joiner.add("\"" + string + "\"");
                        else joiner.add(String.valueOf(o));
                    }
                }
            }

            return joiner.toString();
        }
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ignored) {
        }
    }

    public static String transliterate(String message) {
        String[] abcRus = {"а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"};
        String[] abcEng = {"a", "b", "v", "g", "d", "e", "yo", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "c", "ch", "sh", "sch", "", "i", "'", "e", "yu", "ya"};

        for (int i = 0; i < abcRus.length; i++) {
            message = message.replace(abcRus[i], abcEng[i]);
            message = message.replace(abcRus[i].toUpperCase(), abcEng[i].toUpperCase());
        }

        return message;
    }

    public static byte[] concArrays(byte[] arr1, byte[] arr2) {
        byte[] concated = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, concated, arr1.length, arr2.length);
        return concated;
    }

    public static <T> List<T> createList(int size, T fill) {
        List<T> array = new ArrayList<>();
        for (int i = 0; i < size; i++)
            array.add(fill);
        return array;
    }

    public static List<Integer> createList(int size, int start) {
        List<Integer> array = new ArrayList<>();
        for (int i = start; i < size + start; i++)
            array.add(i);
        return array;
    }

    public static <T> void rewriteList(List<T> from, List<T> to) {
        for (int i = 0; i < to.size(); i++) {
            from.set(i, to.get(i));
        }
    }

    public static <T> List<T> fromList(@Nullable List<T> mainArray, List<T> pieceArray) {
        List<T> main = mainArray != null ? mainArray : new ArrayList<>();
        for (T obj : pieceArray) main.add(obj);
        return main;
    }

    public static <T> List<T> copyList(List<T> from) {
        List<T> copy = new ArrayList<>();
        copyList(from, copy);
        return copy;
    }

    public static <T> void copyList(Collection<T> from, List<T> to) {
        List<T> listFrom = new ArrayList<>();
        for (T i : from) {
            try {
                listFrom.add(i);
            } catch (Exception e) {
                break;
            }
        }
        copyList(listFrom, to);
    }

    public static <T> void copyList(List<T> from, List<T> to) {
        int i = 0;
        while (true) {
            try {
                to.add(from.get(i));
                i++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
    }

    public static int findElementIndex(Object[] elements, Class<?> type) {
        for (int i = 0; i < elements.length; i++) {
            Object element = elements[i];
            if (element.equals(type)) return i;
        }
        return -1;
    }

    public static byte[] convertBuffer(List<Byte> buffer, int offset) {
        byte[] aBuff = new byte[buffer.size() - offset];
        for (int i = 0; i < aBuff.length; i++) aBuff[i] = buffer.get(i + offset);
        return aBuff;
    }

    public static short[] convertBufferUnsigned(List<Byte> buffer, int offset) {
        short[] aBuff = new short[buffer.size() - offset];
        for (int i = 0; i < aBuff.length; i++) aBuff[i] = (short) (buffer.get(i + offset) & 0xFF);
        return aBuff;
    }

    public static String readOrCreateFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            if (!file.createNewFile() || !file.exists()) throw new RuntimeException("Can't create a file.");
            else return "";

        try (DataInputStream fileStream = new DataInputStream(new FileInputStream(file))) {
            return new String(fileStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static boolean createFile(String path, boolean recreate) throws IOException {
        File file = new File(path);
        if (recreate && file.exists()) {
            Files.deleteIfExists(file.toPath());
        }
        return file.createNewFile();
    }

    public static void saveFile(String path, String fileData) throws IOException {
        File file = new File(path);

        if (!file.exists() && !file.createNewFile()) throw new RuntimeException("File create error.");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(fileData.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static <T> T make(Supplier<T> factory) {
        return factory.get();
    }

    public static <T> T make(T object, Consumer<T> initializer) {
        initializer.accept(object);
        return object;
    }

    public static boolean equalsNull(Object obj1, Object obj2) {
        return ((obj1 != null && obj1.equals(obj2)) || (obj1 == null && obj2 == null));
    }

    public static BufferedImage imageFromBase64(String ico) {
        if (ico != null && !ico.isBlank()) {
            try {
                byte[] icoBytes;
                if (ico.contains(",")) {
                    icoBytes = Base64.getDecoder().decode(ico.split(",")[1]);
                } else icoBytes = Base64.getDecoder().decode(ico);
                return ImageIO.read(new ByteArrayInputStream(icoBytes));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String decodeBase64(String base64) {
        return new String(Base64.getDecoder().decode(base64));
    }

    public static String addZeros(long num, int length) {
        String string = String.valueOf(num);

        while (string.length() < length) {
            string = "0" + string;
        }

        return string;
    }

}


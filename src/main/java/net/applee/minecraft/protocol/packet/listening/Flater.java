package net.applee.minecraft.protocol.packet.listening;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Flater {

    public static byte[] compress(byte[] uncompressedData) {
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);

        compressor.setInput(uncompressedData);
        compressor.finish();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressedData.length);

        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        try {
            bos.close();
        } catch (IOException e) {
        }

        return bos.toByteArray();
    }

    public static byte[] decompress(byte[] compressed) {
        Inflater decompressor = new Inflater();

        decompressor.setInput(compressed);

        ByteArrayOutputStream output = new ByteArrayOutputStream(compressed.length);

        byte[] buffer = new byte[1024];
        while (!decompressor.finished()) {
            int value;
            try {
                value = decompressor.inflate(buffer);
            } catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
            output.write(buffer, 0, value);
        }

        try {
            output.close();
        } catch (IOException e) {
        }

        return output.toByteArray();
    }
}

package net.applee.minecraft.protocol.exceptions;

public class BufferLengthException extends DecoderException {

    public BufferLengthException(int index, int length) {
        super(String.format("Buffer reader index %s out of buffer length %s", index, length));
    }
}

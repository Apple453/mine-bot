package net.applee.minecraft.protocol.exceptions;

public class EncoderException extends RuntimeException {

    public EncoderException(String message) {
        super(message);
    }

    public EncoderException(Throwable e) {
        super(e);
    }
}

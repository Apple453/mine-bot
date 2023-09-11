package net.applee.minecraft.protocol.exceptions;

public class OverflowException extends RuntimeException {

    public OverflowException(long value, long maxValue, String type) {
        super("Value %s bigger than the max value %s for type: %s".formatted(value, maxValue, type));
    }
}

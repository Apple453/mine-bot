package net.applee.minecraft.protocol.exceptions;

public class PacketHandleException extends RuntimeException {

    public PacketHandleException(int ID, Exception parent) {
        super("Error handle packet. ID: 0x%s. Error: %s".formatted(Integer.toHexString(ID), parent.toString()));
    }
}

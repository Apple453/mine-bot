package net.applee.minecraft.protocol.exceptions;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.Utils;

public class PacketNotFoundException extends RuntimeException {

    public PacketNotFoundException(PacketNotFoundException parent, byte[] data) {
        super(parent.getMessage() + " Data:\n%s".formatted(Utils.formatString(" ", "", "", false, data)));
    }

    public PacketNotFoundException(PacketNotFoundException parent) {
        super(parent.getMessage());
    }

    public PacketNotFoundException(Class<?> packet) {
        super("Id for %s not found.".formatted(packet.getSimpleName()));
    }


    public PacketNotFoundException(S2CPacket<?> packet) {
        super("Id for %s not found.".formatted(packet.getClass().getSimpleName()));
    }


    public PacketNotFoundException(C2SPacket packet) {
        super("Id for %s not found.".formatted(packet.getClass().getSimpleName()));
    }

    public PacketNotFoundException(int id) {
        super("Packet with id 0x%s not found.".formatted(Integer.toHexString(id)));
    }
}

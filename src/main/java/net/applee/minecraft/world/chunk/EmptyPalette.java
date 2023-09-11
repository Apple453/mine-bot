package net.applee.minecraft.world.chunk;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public class EmptyPalette implements Palette {

    private final int id;

    public EmptyPalette(PacketByteBuffer buffer) {
        id = buffer.readVarInt();
    }

    public int getId() {
        return id;
    }
}

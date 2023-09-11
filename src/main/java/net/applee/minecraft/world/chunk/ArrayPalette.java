package net.applee.minecraft.world.chunk;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public class ArrayPalette implements Palette {

    private final int[] palette;

    public int[] getData() {
        return palette;
    }

    public ArrayPalette(PacketByteBuffer buffer) {
        palette = new int[buffer.readVarInt()];
        for (int i = 0; i < palette.length; i++)
            palette[i] = buffer.readVarInt();
    }
}

package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record EditBookC2SPacket(int slot, String[] entries, String title) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeVarInt(slot);
        buffer.writeVarInt(entries.length);
        for (String s : entries)
            buffer.writeString(s);

        if (title == null) buffer.writeBoolean(false);
        else {
            buffer.writeBoolean(true);
            buffer.writeString(title);
        }
    }
}

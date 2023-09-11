package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record SendPluginMessageC2SPacket(String channel, byte[] data) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeString(channel);
        buffer.writeByteArray(data);
    }
}

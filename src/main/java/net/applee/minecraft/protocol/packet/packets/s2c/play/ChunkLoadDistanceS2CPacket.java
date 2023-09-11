package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ChunkLoadDistanceS2CPacket implements S2CPacket<IEventHandler> {

    int distance;

    public int getDistance() {
        return distance;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onChunkLoadDistanceUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        distance = buffer.readVarInt();
    }
}

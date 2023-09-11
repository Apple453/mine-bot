package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.ChunkPos;

public class ChunkRenderDistanceCenterS2CPacket implements S2CPacket<IEventHandler> {

    ChunkPos pos;

    public ChunkPos getPos() {
        return pos;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onChunkCenterUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = new ChunkPos(buffer.readVarInt(), buffer.readVarInt());
    }
}

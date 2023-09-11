package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.ChunkPos;

public class UnloadChunkS2CPacket implements S2CPacket<IEventHandler> {

    ChunkPos chunkPos;

    public ChunkPos getChunkPos() {
        return chunkPos;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onChunkUnload(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        chunkPos = new ChunkPos(buffer.readInt(), buffer.readInt());
    }
}

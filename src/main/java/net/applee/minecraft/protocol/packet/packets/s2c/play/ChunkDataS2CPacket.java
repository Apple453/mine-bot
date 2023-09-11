package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.ChunkPos;
import net.applee.minecraft.world.chunk.ChunkData;

public class ChunkDataS2CPacket implements S2CPacket<IEventHandler> {
    ChunkData data;
    boolean skip = false;

    public ChunkData getData() {
        return data;
    }

    @Override
    public void apply(IEventHandler handler) {
        if (!skip) handler.onChunkData(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        ChunkPos pos = new ChunkPos(buffer.readInt(), buffer.readInt());
        if (pos.checkDistance()) data = new ChunkData(buffer, pos);
        else skip = true;
    }
}

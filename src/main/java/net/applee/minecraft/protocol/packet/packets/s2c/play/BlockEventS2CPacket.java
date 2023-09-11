package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.BlockPos;

public class BlockEventS2CPacket implements S2CPacket<IEventHandler> {

    int blockId;
    short eventId;
    short eventParam;
    BlockPos pos;

    public BlockPos getPos() {
        return pos;
    }

    public short getEventId() {
        return eventId;
    }

    public short getEventParam() {
        return eventParam;
    }

    public int getBlockId() {
        return blockId;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onBlockEvent(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = buffer.readBlockPos();
        eventId = buffer.readUnsignedByte();
        eventParam = buffer.readUnsignedByte();
        blockId = buffer.readVarInt();
    }
}

package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.BlockPos;

public class WorldEventS2CPacket implements S2CPacket<IEventHandler> {

    int eventId;
    BlockPos pos;
    int data;
    boolean global;

    public int getEventId() {
        return eventId;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getData() {
        return data;
    }

    public boolean isGlobal() {
        return global;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onWorldEvent(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        eventId = buffer.readInt();
        pos = buffer.readBlockPos();
        data = buffer.readInt();
        global = buffer.readBoolean();
    }
}

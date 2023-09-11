package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class EntityStatusS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    int status;

    public int getEntityID() {
        return entityID;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityStatus(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readInt();
        status = buffer.readByte();
    }
}

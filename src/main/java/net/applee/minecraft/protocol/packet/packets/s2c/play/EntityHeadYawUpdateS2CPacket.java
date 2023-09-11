package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class EntityHeadYawUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    int yaw;

    public int getEntityID() {
        return entityID;
    }

    public int getYaw() {
        return yaw;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityHeadRotationUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        yaw = buffer.readByte();
    }
}

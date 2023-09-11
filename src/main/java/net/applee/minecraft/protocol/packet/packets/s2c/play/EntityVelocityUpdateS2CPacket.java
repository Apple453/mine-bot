package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Vec3i;

public class EntityVelocityUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Vec3i velocity;

    public int getEntityID() {
        return entityID;
    }

    public Vec3i getVelocity() {
        return velocity;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityVelocityUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        velocity = new Vec3i(buffer.readShort(), buffer.readShort(), buffer.readShort());
    }
}

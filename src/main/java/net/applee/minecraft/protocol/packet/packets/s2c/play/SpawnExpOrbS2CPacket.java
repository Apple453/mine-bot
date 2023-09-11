package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Vec3d;

public class SpawnExpOrbS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Vec3d pos;
    int count;

    public int getEntityID() {
        return entityID;
    }

    public Vec3d getPos() {
        return pos;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onExpOrbSpawn(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        pos = buffer.readVec3d();
        count = buffer.readVarInt();
    }
}

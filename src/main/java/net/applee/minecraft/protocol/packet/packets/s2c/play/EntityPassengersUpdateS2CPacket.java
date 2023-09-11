package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.ArrayList;

public class EntityPassengersUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    Integer[] passengersIDS;

    public int getEntityID() {
        return entityID;
    }

    public Integer[] getPassengersIDS() {
        return passengersIDS;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityPassengersUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        passengersIDS = buffer.readCollection(ArrayList::new, PacketByteBuffer::readVarInt).toArray(Integer[]::new);
    }
}

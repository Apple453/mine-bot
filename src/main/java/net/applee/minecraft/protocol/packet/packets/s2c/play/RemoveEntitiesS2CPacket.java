package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.ArrayList;

public class RemoveEntitiesS2CPacket implements S2CPacket<IEventHandler> {

    Integer[] entitiesID;

    public Integer[] getEntitiesID() {
        return entitiesID;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntitiesRemove(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entitiesID = buffer.readCollection(ArrayList::new, PacketByteBuffer::readVarInt).toArray(Integer[]::new);
    }
}

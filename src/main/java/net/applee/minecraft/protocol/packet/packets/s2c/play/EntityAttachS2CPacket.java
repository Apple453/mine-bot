package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class EntityAttachS2CPacket implements S2CPacket<IEventHandler> {

    int ownerID;
    int leashedID;

    public int getOwnerID() {
        return ownerID;
    }

    public int getLeashedID() {
        return leashedID;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityLeashed(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        ownerID = buffer.readInt();
        leashedID = buffer.readInt();
    }
}

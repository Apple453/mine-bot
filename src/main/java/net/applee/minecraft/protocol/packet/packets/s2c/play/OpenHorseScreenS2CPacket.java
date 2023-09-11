package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class OpenHorseScreenS2CPacket implements S2CPacket<IEventHandler> {

    int windowID;
    int slotCount;
    int entityId;

    public int getWindowID() {
        return windowID;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public int getEntityId() {
        return entityId;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onHorseScreenOpened(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        windowID = buffer.readUnsignedByte();
        slotCount = buffer.readVarInt();
        entityId = buffer.readInt();
    }
}

package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class EntityMetadataUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int entityID;
    PacketByteBuffer metadata;

    public int getEntityID() {
        return entityID;
    }

    public PacketByteBuffer getMetadata() {
        return metadata;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onEntityMetadataUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityID = buffer.readVarInt();
        metadata = new PacketByteBuffer(buffer.getBuffer());
    }
}

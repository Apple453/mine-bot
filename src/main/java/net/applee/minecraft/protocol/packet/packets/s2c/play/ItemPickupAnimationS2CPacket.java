package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ItemPickupAnimationS2CPacket implements S2CPacket<IEventHandler> {

    int itemId;
    int collectorId;
    int count;

    public int getItemId() {
        return itemId;
    }

    public int getCollectorId() {
        return collectorId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onItemPickupAnimation(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        itemId = buffer.readVarInt();
        collectorId = buffer.readVarInt();
        count = buffer.readVarInt();
    }
}

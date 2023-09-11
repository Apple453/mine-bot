package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class AwardStatisticsS2CPacket implements S2CPacket<IEventHandler> {

    int count;
    int categoryId;
    int statisticId;
    int value;

    public int getCount() {
        return count;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getStatisticId() {
        return statisticId;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onAwardStatistic(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        count = buffer.readVarInt();
        categoryId = buffer.readVarInt();
        statisticId = buffer.readVarInt();
        value = buffer.readVarInt();
    }
}

package net.applee.minecraft.protocol.packet.packets.s2c.play.todo;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class MerchantOffersListS2CPacket implements S2CPacket<IEventHandler> {

    // Todo: made packet
    @Override
    public void apply(IEventHandler handler) {
        handler.onMerchantOffersList(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {

    }
}

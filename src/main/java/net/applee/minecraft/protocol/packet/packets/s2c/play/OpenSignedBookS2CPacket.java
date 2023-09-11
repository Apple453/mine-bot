package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class OpenSignedBookS2CPacket implements S2CPacket<IEventHandler> {

    Hand hand;

    public Hand getHand() {
        return hand;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onSignedBookOpened(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        hand = buffer.readEnumConstant(Hand.class);
    }
}

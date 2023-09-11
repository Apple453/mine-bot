package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class TitleFadeS2CPacket implements S2CPacket<IEventHandler> {

    int stay;
    int fadeIn;
    int fadeOut;

    public int getStay() {
        return stay;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onTitleAnimateUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        fadeIn = buffer.readInt();
        stay = buffer.readInt();
        fadeOut = buffer.readInt();
    }
}

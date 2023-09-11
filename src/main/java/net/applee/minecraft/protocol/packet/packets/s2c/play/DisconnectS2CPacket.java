package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.string.Text;

public class DisconnectS2CPacket implements S2CPacket<IEventHandler> {

    Text reason;

    public Text getReason() {
        return reason;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onPlayDisconnect(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        reason = buffer.readText();
    }
}

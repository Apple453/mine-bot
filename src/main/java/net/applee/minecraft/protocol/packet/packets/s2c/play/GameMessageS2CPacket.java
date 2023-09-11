package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class GameMessageS2CPacket implements S2CPacket<IEventHandler> {

    String content = "";
    boolean overlay;

    public String getContent() {
        return content;
    }

    public boolean getOverlay() {
        return overlay;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onGameChatMessage(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        content += buffer.readString();
        overlay = buffer.readBoolean();
    }
}

package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import javax.annotation.Nullable;

public class ServerMetadataS2CPacket implements S2CPacket<IEventHandler> {

    boolean previewsChat = false;
    boolean enforcesSecureChat = false;

    @Nullable
    String motd = null;

    @Nullable
    String icon = null;

    public boolean isPreviewsChat() {
        return previewsChat;
    }

    public boolean isEnforcesSecureChat() {
        return enforcesSecureChat;
    }

    @Nullable
    public String getMotd() {
        return motd;
    }

    @Nullable
    public String getIcon() {
        return icon;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onServerMetadata(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        if (buffer.readBoolean()) motd = buffer.readString();
        if (buffer.readBoolean()) icon = buffer.readString();
        previewsChat = buffer.readBoolean();
        enforcesSecureChat = buffer.readBoolean();
    }
}

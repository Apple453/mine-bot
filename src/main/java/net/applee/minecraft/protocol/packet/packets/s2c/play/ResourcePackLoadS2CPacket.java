package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ResourcePackLoadS2CPacket implements S2CPacket<IEventHandler> {

    boolean forced;
    String url;
    String hash;
    String promtMessage;

    public boolean isForced() {
        return forced;
    }

    public String getUrl() {
        return url;
    }

    public String getHash() {
        return hash;
    }

    public String getPromtMessage() {
        return promtMessage;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onResourcePackLoad(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        url = buffer.readString();
        hash = buffer.readString(40);
        forced = buffer.readBoolean();
        promtMessage = buffer.readBoolean() ? buffer.readString() : null;
    }
}

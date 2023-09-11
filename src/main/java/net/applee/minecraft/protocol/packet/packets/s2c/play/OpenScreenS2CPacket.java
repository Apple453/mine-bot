package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class OpenScreenS2CPacket implements S2CPacket<IEventHandler> {

    int id;
    int type;
    String title;

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onScreenOpen(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        id = buffer.readVarInt();
        type = buffer.readVarInt();
        title = buffer.readString();
    }
}

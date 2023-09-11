package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class CommandSuggestionsS2CPacket implements S2CPacket<IEventHandler> {

    int id;
    int start;
    int length;
    int count;
    String match;
    boolean hasTooltip;
    String tooltip;

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public int getCount() {
        return count;
    }

    public String getMatch() {
        return match;
    }

    public boolean isHasTooltip() {
        return hasTooltip;
    }

    public String getTooltip() {
        return tooltip;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onCommandsSuggest(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        id = buffer.readVarInt();
        start = buffer.readVarInt();
        length = buffer.readVarInt();
        count = buffer.readVarInt();
        match = buffer.readString();
        hasTooltip = buffer.readBoolean();
        tooltip = hasTooltip ? buffer.readString() : null;
    }
}

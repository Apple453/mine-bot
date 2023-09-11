package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.ArrayList;

public class ChatSuggestionsS2CPacket implements S2CPacket<IEventHandler> {

    Action action;
    String[] entries;

    public Action getAction() {
        return action;
    }

    public String[] getEntries() {
        return entries;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onChatSuggestions(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        action = buffer.readEnumConstant(Action.class);
        entries = buffer.readCollection(ArrayList::new, PacketByteBuffer::readString).toArray(String[]::new);
    }

    public enum Action {
        Add,
        Remove,
        Update
    }
}

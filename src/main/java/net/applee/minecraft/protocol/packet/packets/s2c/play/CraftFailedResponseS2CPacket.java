package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class CraftFailedResponseS2CPacket implements S2CPacket<IEventHandler> {

    int windowID;
    String recipe;

    public int getWindowID() {
        return windowID;
    }

    public String getRecipe() {
        return recipe;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onCraftFailed(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        windowID = buffer.readByte();
        recipe = buffer.readString();
    }
}

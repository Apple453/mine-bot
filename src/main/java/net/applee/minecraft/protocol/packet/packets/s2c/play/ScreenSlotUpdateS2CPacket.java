package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ScreenSlotUpdateS2CPacket implements S2CPacket<IEventHandler> {
    public static final int UPDATE_CURSOR_SYNC_ID = -1;
    public static final int UPDATE_PLAYER_INVENTORY_SYNC_ID = -2;

    int windowId;
    int stateId;
    int slotId;
    ItemStack stack;

    public int getWindowId() {
        return windowId;
    }

    public int getStateId() {
        return stateId;
    }

    public int getSlotId() {
        return slotId;
    }

    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onScreenSlotUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        windowId = buffer.readByte();
        stateId = buffer.readVarInt();
        slotId = buffer.readShort();
        stack = buffer.readItemStack();
    }
}

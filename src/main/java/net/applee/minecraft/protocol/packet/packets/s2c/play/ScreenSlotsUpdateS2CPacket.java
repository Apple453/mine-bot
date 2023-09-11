package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import java.util.ArrayList;
import java.util.List;

public class ScreenSlotsUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int windowId;
    int stateId;
    List<ItemStack> stacks;
    ItemStack carriedStack;

    public int getWindowId() {
        return windowId;
    }

    public int getStateId() {
        return stateId;
    }

    public List<ItemStack> getStacks() {
        return stacks;
    }

    public ItemStack getCarriedStack() {
        return carriedStack;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onScreenSlotsUpdateUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        windowId = buffer.readUnsignedByte();
        stateId = buffer.readVarInt();
        stacks = buffer.readCollection(ArrayList::new, PacketByteBuffer::readItemStack);
        carriedStack = buffer.readItemStack();
    }
}

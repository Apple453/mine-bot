package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.enums.SlotActionType;
import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.List;

public record ContainerClickC2SPacket(int windowId, int stateId, int clickedSlot, SlotActionType mode, int button, List<ItemStack> slots, ItemStack cursor) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeByte(windowId);
        buffer.writeVarInt(stateId);
        buffer.writeShort(clickedSlot);
        buffer.writeByte(button);
        buffer.writeEnumConstant(mode);
        buffer.writeCollection(slots, PacketByteBuffer::writeItemStack);
        buffer.writeItemStack(cursor);
    }
}

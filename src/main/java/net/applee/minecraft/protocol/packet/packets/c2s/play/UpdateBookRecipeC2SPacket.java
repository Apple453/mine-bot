package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record UpdateBookRecipeC2SPacket(BookType type, boolean open, boolean filter) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeEnumConstant(type);
        buffer.writeBoolean(open);
        buffer.writeBoolean(filter);
    }

    public enum BookType {
        Crafting,
        Furnace,
        BlastFurnace,
        Smoker
    }
}

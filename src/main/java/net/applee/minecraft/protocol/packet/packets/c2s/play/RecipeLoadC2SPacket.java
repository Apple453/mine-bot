package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record RecipeLoadC2SPacket(int windowId, String recipe, boolean makeAll) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeByte(windowId);
        buffer.writeString(recipe);
        buffer.writeBoolean(makeAll);
    }
}

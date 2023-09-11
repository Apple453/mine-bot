package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record AdvancementsSeenC2SPacket(AdvancementTabAction action) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeEnumConstant(action);
    }

    public enum AdvancementTabAction {
        Opened,
        Closed
    }
}

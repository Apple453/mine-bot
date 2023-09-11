package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.client.SkinParts;
import net.applee.minecraft.enums.Arm;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;

public record UpdateClientSettingsC2SPacket(String locale, byte viewDistance, ChatVisibility chatVisibility, boolean chatHasColors, SkinParts skinParts, Arm mainHandSide, boolean textFiltering, boolean serverListings) implements C2SPacket {
    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeString(locale, 16);
        buffer.writeByte(viewDistance);
        buffer.writeEnumConstant(chatVisibility);
        buffer.writeBoolean(chatHasColors);
        buffer.writeByte(skinParts.getFlags());
        buffer.writeEnumConstant(mainHandSide);
        buffer.writeBoolean(textFiltering);
        buffer.writeBoolean(serverListings);
    }

    public enum ChatVisibility {
        Enabled,
        Commands,
        Hidden
    }
}

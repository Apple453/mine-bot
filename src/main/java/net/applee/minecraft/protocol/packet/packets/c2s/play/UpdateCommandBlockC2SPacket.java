package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.BlockPos;

public record UpdateCommandBlockC2SPacket(BlockPos pos, String command, CommandBlockMode mode, boolean conditional, boolean alwaysActive, boolean track) implements C2SPacket {
    private static final int TRACK_OUTPUT_MASK = 1;
    private static final int CONDITIONAL_MASK = 2;
    private static final int ALWAYS_ACTIVE_MASK = 4;

    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeString(command);
        buffer.writeEnumConstant(mode);

        byte mask = 0;
        if (conditional) mask |= CONDITIONAL_MASK;
        if (alwaysActive) mask |= ALWAYS_ACTIVE_MASK;
        if (track) mask |= TRACK_OUTPUT_MASK;

        buffer.writeByte(mask);
    }

    public enum CommandBlockMode {
        Sequence,
        Auto,
        Redstone
    }
}

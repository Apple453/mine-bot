package net.applee.minecraft.protocol.packet.packets.c2s.play;

import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.MathHelper;
import net.applee.minecraft.utils.math.Vec3i;

public record UpdateStructureBlockC2SPacket(String name, BlockPos pos, Action action, Mode mode, Mirror mirror, RotationSides rotation, Vec3i offset, Vec3i size, String metadata, float integrity, long seed, byte flags, boolean ignoreEntities, boolean showAirMask, boolean showBoxMask) implements C2SPacket {
    private static final int IGNORE_ENTITIES_MASK = 1;
    private static final int SHOW_AIR_MASK = 2;
    private static final int SHOW_BOUNDING_BOX_MASK = 4;

    @Override
    public void writeBuffer(PacketByteBuffer buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeEnumConstant(action);
        buffer.writeEnumConstant(mode);
        buffer.writeString(name);
        buffer.writeByte(MathHelper.clamp(offset.getX(), -32, 32));
        buffer.writeByte(MathHelper.clamp(offset.getY(), -32, 32));
        buffer.writeByte(MathHelper.clamp(offset.getZ(), -32, 32));
        buffer.writeByte(MathHelper.clamp(size.getX(), 0, 32));
        buffer.writeByte(MathHelper.clamp(size.getY(), 0, 32));
        buffer.writeByte(MathHelper.clamp(size.getZ(), 0, 32));
        buffer.writeEnumConstant(mirror);
        buffer.writeEnumConstant(rotation);
        buffer.writeString(metadata);
        buffer.writeFloat(integrity);
        buffer.writeVarLong(seed);

        byte mask = 0;
        if (ignoreEntities) mask |= IGNORE_ENTITIES_MASK;
        if (showAirMask) mask |= SHOW_AIR_MASK;
        if (showBoxMask) mask |= SHOW_BOUNDING_BOX_MASK;

        buffer.writeByte(mask);
    }

    public enum Action {
        UpdateData,
        SaveStructure,
        LoadStructure,
        DetectSize
    }

    public enum Mirror {
        None,
        LeftRight,
        FrontBack
    }

    public enum RotationSides {
        None,
        ClockWise_90,
        ClockWise_180,
        CounterClockWise_90
    }

    public enum Mode {
        Save,
        Load,
        Corner,
        Data
    }

}

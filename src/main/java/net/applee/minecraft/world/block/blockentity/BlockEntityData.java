package net.applee.minecraft.world.block.blockentity;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.Nbt;

import javax.annotation.Nullable;

public class BlockEntityData {
    private final int localXZ;
    private final int y;
    private final BlockEntityType type;
    @Nullable
    private final Nbt data;

    public BlockEntityData(PacketByteBuffer buffer) {
        this.localXZ = buffer.readUnsignedByte();
        this.y = buffer.readShort();
        this.type = buffer.readEnumConstant(BlockEntityType.class);
        this.data = buffer.readNbt();
    }

    public int getLocalXZ() {
        return localXZ;
    }

    public int getY() {
        return y;
    }

    public BlockEntityType getType() {
        return type;
    }

    public @Nullable Nbt getData() {
        return data;
    }

    public int unpackX() {
        return localXZ >> 4;
    }

    public int unpackZ() {
        return localXZ & 0xF;
    }


}

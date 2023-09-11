package net.applee.minecraft.world.entity.attributes;

import net.applee.minecraft.protocol.packet.PacketByteBuffer;

import java.util.UUID;

public class Modifier {
    double amount;
    UUID uuid;
    Operation op;

    public Modifier(PacketByteBuffer buffer) {
        uuid = buffer.readUuid();
        amount = buffer.readDouble();
        op = buffer.readByteEnumConstant(Operation.class);
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getAmount() {
        return amount;
    }

    public Operation getOp() {
        return op;
    }

    public enum Operation {
        ADDITION,
        MULTIPLY_BASE,
        MULTIPLY_TOTAL
    }
}

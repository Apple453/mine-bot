package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.enums.Difficulty;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class DifficultyUpdateS2CPacket implements S2CPacket<IEventHandler> {

    Difficulty difficulty;
    boolean locked;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onChangeDifficulty(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        difficulty = buffer.readEnumConstant(Difficulty.class);
        locked = buffer.readBoolean();
    }
}

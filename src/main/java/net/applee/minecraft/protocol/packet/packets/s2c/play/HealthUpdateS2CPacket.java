package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class HealthUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int food;
    float foodSaturation;
    float health;

    public int getFood() {
        return food;
    }

    public float getFoodSaturation() {
        return foodSaturation;
    }

    public float getHealth() {
        return health;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onHealthUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        health = buffer.readFloat();
        food = buffer.readVarInt();
        foodSaturation = buffer.readFloat();
    }
}

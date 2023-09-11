package net.applee.minecraft.protocol.packet.packets.s2c.play;


import net.applee.minecraft.enums.GameEvent;
import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class GameEventS2CPacket implements S2CPacket<IEventHandler> {

    GameEvent event;
    float value;

    public GameEvent getEvent() {
        return event;
    }

    public float getValue() {
        return value;
    }

    @Override
    public void apply(IEventHandler GameEventHandler) {
        GameEventHandler.onGameEvent(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        event = buffer.readEnumConstant(GameEvent.class);
        value = buffer.readFloat();
    }

}

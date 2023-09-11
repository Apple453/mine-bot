package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ScoreboardUpdateS2CPacket implements S2CPacket<IEventHandler> {

    Integer value = null;
    String entityName;
    String objectiveName;
    Action action;

    public Integer getValue() {
        return value;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onScoreboardUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        entityName = buffer.readString(40);
        action = buffer.readEnumConstant(Action.class);
        objectiveName = buffer.readString(16);
        if (action != Action.Update) value = buffer.readVarInt();
    }

    public enum Action {
        Update,
        Remove
    }
}

package net.applee.minecraft.protocol.packet.packets.s2c.play.todo;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class TeamS2CPacket implements S2CPacket<IEventHandler> {
    private static final int ADD = 0x0;
    private static final int REMOVE = 0x1;
    private static final int UPDATE = 0x2;
    private static final int ADD_PLAYERS = 0x3;
    private static final int REMOVE_PLAYERS = 0x4;
    private static final int FIRST_MAX_VISIBILITY_OR_COLLISION_RULE_LENGTH = 40;
    private static final int SECOND_MAX_VISIBILITY_OR_COLLISION_RULE_LENGTH = 40;

    // Todo: made packet
    String teamName;

    @Override
    public void apply(IEventHandler handler) {
        handler.onTeamsUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
//        teamName = buffer.readString(16);

    }

    public enum Operation {
        Create,
        Remove,
        UpdateInfo,
        AddEntities,
        RemoveEntities
    }
}

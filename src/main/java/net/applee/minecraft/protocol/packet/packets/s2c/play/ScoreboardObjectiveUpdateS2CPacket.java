package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class ScoreboardObjectiveUpdateS2CPacket implements S2CPacket<IEventHandler> {

    String name;
    String displayName;
    RenderType type;
    UpdateType mode;

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public RenderType getType() {
        return type;
    }

    public UpdateType getMode() {
        return mode;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onObjectiveUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        name = buffer.readString(16);
        mode = buffer.readByteEnumConstant(UpdateType.class);

        if (mode == UpdateType.Remove) {
            this.displayName = "";
            this.type = RenderType.INTEGER;
        } else {
            this.displayName = buffer.readString();
            this.type = buffer.readEnumConstant(RenderType.class);
        }
    }

    public enum UpdateType {
        Create,
        Remove,
        UpdateText
    }

    public enum RenderType {
        INTEGER("integer"),
        HEARTS("hearts");

        private final String name;

        RenderType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}

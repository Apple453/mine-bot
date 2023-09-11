package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.Rotation;
import net.applee.minecraft.utils.math.Vec3d;

import java.util.EnumSet;
import java.util.Set;

public class SyncPositionS2CPacket implements S2CPacket<IEventHandler> {

    Vec3d pos;
    Rotation rotation;
    Set<Flag> flags;
    int tpId;
    boolean dismountVehicle;

    public Vec3d getPos() {
        return pos;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Set<Flag> getFlags() {
        return flags;
    }

    public int getTpId() {
        return tpId;
    }

    public boolean isDismountVehicle() {
        return dismountVehicle;
    }

    @Override
    public void apply(IEventHandler eventHandler) {
        eventHandler.onSyncPlayerPos(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        pos = buffer.readVec3d();
        rotation = new Rotation(buffer.readFloat(), buffer.readFloat());
        flags = Flag.getFlags(buffer.readByte());
        tpId = buffer.readVarInt();
        dismountVehicle = buffer.readBoolean();
    }

    public enum Flag {
        X,
        Y,
        Z,
        Y_ROT,
        X_ROT;

        public static Set<Flag> getFlags(int mask) {
            EnumSet<Flag> set = EnumSet.noneOf(Flag.class);
            for (Flag flag : Flag.values()) {
                if (!flag.isSet(mask)) continue;
                set.add(flag);
            }
            return set;
        }

        public static int getBitfield(Set<Flag> flags) {
            int i = 0;
            for (Flag flag : flags) {
                i |= flag.getMask();
            }
            return i;
        }

        private int getMask() {
            return 1 << this.ordinal();
        }

        private boolean isSet(int mask) {
            return (mask & this.getMask()) == this.getMask();
        }

    }
}

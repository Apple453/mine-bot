package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.utils.math.MathHelper;

public class ExperienceBarUpdateS2CPacket implements S2CPacket<IEventHandler> {

    int level;
    int totalExp;
    double expBar;

    public int getLevel() {
        return level;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public double getExpBar() {
        return expBar;
    }

    @Override
    public void apply(IEventHandler handler) {
        handler.onExperienceBarUpdate(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        expBar = MathHelper.clamp(buffer.readFloat(), 0, 1);
        level = buffer.readVarInt();
        totalExp = buffer.readVarInt();
    }
}

package net.applee.minecraft.client;

import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.enums.PlayerCommand;
import net.applee.minecraft.protocol.message.ArgumentSignatureDataMap;
import net.applee.minecraft.protocol.message.LastSeenMessageList;
import net.applee.minecraft.protocol.message.MessageSignatureData;
import net.applee.minecraft.protocol.packet.packets.c2s.play.*;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.MathHelper;
import net.applee.minecraft.utils.math.Rotation;
import net.applee.minecraft.utils.math.Vec3d;
import net.applee.minecraft.world.block.BlockState;
import net.applee.minecraft.world.entity.EntityType;
import net.applee.minecraft.world.entity.player.PlayerEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientPlayerEntity extends PlayerEntity {

    // Info
    private int clientPermissionLevel = 0;
    private String nickname;


    // Controls
    private boolean lastSprinting = false;
    private boolean lastSneaking = false;
    private boolean lastOnGround = false;

    private boolean sneaking = false;

    private Vec3d prevPosition = Vec3d.ZERO;
    private Vec3d lastPosition = Vec3d.ZERO;
    private Rotation lastRotation = Rotation.ZERO;
    private int ticksSinceLastPositionPacketSent = 0;

    public final PlayerInventory inventory = new PlayerInventory();

    private float health = 1;
    private double food = 0;
    private double saturation = 0;

    private double experienceBar = 0;
    private int experience = 0;
    private int experienceLevel = 0;

    private boolean lastKnockback = false;
    private Vec3d knockbackVelocity = Vec3d.ZERO;

    @Override
    public void tick() {
        super.tick();
        playerTick();
    }

    public void playerTick() {
        knockbackVelocity = Vec3d.ZERO;
        position = position.add(velocity);
        sendMovementPackets();
    }

    public void setKnockbackVelocity(Vec3d vel) {
        knockbackVelocity = vel;
    }

    private void knockback() {
        if (knockback) {
            if (!knockbackVelocity.equals(Vec3d.ZERO)) {
                lastKnockback = true;
                knockbackVelocity.x /= 1.01;
                knockbackVelocity.y /= 1.01;
                knockbackVelocity.z /= 1.01;
            }

            knockbackVelocity = knockbackVelocity.round(8);

            if (lastKnockback && knockbackVelocity.round(2).equals(Vec3d.ZERO)) {
                knockbackVelocity = Vec3d.ZERO;
                knockback = false;

                lastKnockback = false;
            }
        }
    }

    public void fallTick() {
        addVelocity(0, -0.08, 0);
        setVelocity(velocity.multiply(0.98));
    }

    private List<BlockState> underPlatform() {
        List<BlockState> statesPlatform = new ArrayList<>();
        BlockPos pDown = getBlockPos().down();
        for (BlockPos pos : new BlockPos[] {pDown, pDown.east(), pDown.west(), pDown.north(), pDown.south(), pDown.south().west(), pDown.south().east(), pDown.north().west(), pDown.north().east()}) {
            statesPlatform.add(mc.world.getBlockState(pos));
        }
        return statesPlatform;
    }

    @Override
    public void onLanding() {
        super.onLanding();
        isOnGround = true;
        velocity.y = 0;
    }

    @Override
    public double getItemAttackSpeed() {
        return inventory.getMainHandStack().getItem().getAttackSpeed();
    }

    private void sendMovementPackets() {

        // Sprint update
        boolean sprinting = mc.input.sprinting;
        if (sprinting != lastSprinting) {
            PlayerCommand sprintCommand = sprinting ? PlayerCommand.StartSprinting : PlayerCommand.StopSprinting;
            mc.networkHandler.sendPacket(new PlayerCommandC2SPacket(id, sprintCommand, 0));
            lastSprinting = sprinting;
        }

        // Sneak update
        boolean sneaking = mc.input.sneaking;
        if (sneaking != this.lastSneaking) {
            PlayerCommand sneakCommand = sneaking ? PlayerCommand.StartSneaking : PlayerCommand.StopSneaking;
            mc.networkHandler.sendPacket(new PlayerCommandC2SPacket(id, sneakCommand, 0));
            lastSneaking = sneaking;
        }

        Vec3d posDiff = position.subtract(lastPosition);
        Rotation rotDiff = rotation.subtractRotation(lastRotation);

        ++this.ticksSinceLastPositionPacketSent;

        boolean updatePosition = MathHelper.squaredMagnitude(posDiff) > MathHelper.square(2.0E-4) || this.ticksSinceLastPositionPacketSent >= 20;
        boolean updateRotation = rotDiff.getYaw() != 0.0 || rotDiff.getPitch() != 0.0;

        if (this.vehicle != null) {
            Vec3d vel = this.getVelocity();
            mc.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(vel.add(0, -999, 0), rotation, isOnGround));
            updatePosition = false;
        } else if (updatePosition && updateRotation) {
            mc.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(position, rotation, isOnGround));
        } else if (updatePosition) {
            mc.networkHandler.sendPacket(new PlayerMoveC2SPacket.Move(position, isOnGround));
        } else if (updateRotation) {
            mc.networkHandler.sendPacket(new PlayerMoveC2SPacket.Look(rotation, isOnGround));
        } else if (this.lastOnGround != this.isOnGround) {
            mc.networkHandler.sendPacket(new PlayerMoveC2SPacket.Ground(isOnGround));
        }

        if (updatePosition) {
            lastPosition = position;
            this.ticksSinceLastPositionPacketSent = 0;
        }

        if (updateRotation) lastRotation = rotation;

        this.lastOnGround = this.isOnGround;
//        this.autoJumpEnabled = (Boolean) this.client.options.getAutoJump().getValue();

    }

//    protected Vec3d adjustMovementForPiston(Vec3d movement) {
//        if (movement.lengthSquared() <= 1.0E-7) {
//            return movement;
//        } else {
//            long l = this.world.getTime();
//            if (l != this.pistonMovementTick) {
//                Arrays.fill(this.pistonMovementDelta, 0.0);
//                this.pistonMovementTick = l;
//            }
//
//            double d;
//            if (movement.x != 0.0) {
//                d = this.calculatePistonMovementFactor(Axis.X, movement.x);
//                return Math.abs(d) <= 9.999999747378752E-6 ? Vec3d.ZERO : new Vec3d(d, 0.0, 0.0);
//            } else if (movement.y != 0.0) {
//                d = this.calculatePistonMovementFactor(Axis.Y, movement.y);
//                return Math.abs(d) <= 9.999999747378752E-6 ? Vec3d.ZERO : new Vec3d(0.0, d, 0.0);
//            } else if (movement.z != 0.0) {
//                d = this.calculatePistonMovementFactor(Axis.Z, movement.z);
//                return Math.abs(d) <= 9.999999747378752E-6 ? Vec3d.ZERO : new Vec3d(0.0, 0.0, d);
//            } else {
//                return Vec3d.ZERO;
//            }
//        }
//    }

    public String getSelfNickname() {
        return nickname;
    }

    public void setSelfNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setVelocity(double x, double y, double z) {
        velocity = new Vec3d(x, y, z);
    }

    public void setVelocity(Vec3d vel) {
        velocity = vel;
    }

    public void setPosition(double x, double y, double z) {
        position = new Vec3d(x, y, z);
    }

    public void setPosition(Vec3d pos) {
        position = pos;
        trackedPosition.setPos(Vec3d.ZERO);
    }

    public Vec3d getPrevPosition() {
        return this.prevPosition;
    }

    public void setPosAndPrevPos(double x, double y, double z) {
        prevPosition = new Vec3d(x, y, z);
        position = prevPosition;
    }

    public void setPosAndPrevPos(Vec3d pos) {
        prevPosition = pos;
        position = pos;
    }

    public void setRotation(double yaw, double pitch) {
        this.rotation = new Rotation(yaw, pitch);
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public void addRotation(Rotation rotation) {
        this.rotation = this.rotation.addRotation(rotation);
    }

    public void addYaw(double yaw) {
        this.rotation = this.rotation.addYaw(yaw);
    }

    public void addPitch(double pitch) {
        this.rotation = this.rotation.addPitch(pitch);
    }

    public void setPosAndRot(double x, double y, double z, double yaw, double pitch) {
        setPosition(x, y, z);
        setRotation(yaw, pitch);
    }

    public void setPosAndRot(double x, double y, double z, Rotation rotation) {
        setPosition(x, y, z);
        setRotation(rotation);
    }


    public double getYaw() {
        return rotation.getYaw();
    }

    public double getPitch() {
        return rotation.getPitch();
    }

    public void setPosAndRot(Vec3d pos, Rotation rot) {
        this.position = pos;
        this.rotation = rot;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    @Override
    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    protected void knockDownwards() {
        velocity = velocity.add(0.0, -0.03999999910593033, 0.0);
    }

    public void sendChatMessage(String message) {
        LastSeenMessageList.Acknowledgment acknowledgment = new LastSeenMessageList.Acknowledgment(LastSeenMessageList.EMPTY, Optional.empty());
        mc.networkHandler.sendPacket(new ChatMessageC2SPacket(message, Instant.now(), 0L, MessageSignatureData.EMPTY, false, acknowledgment));
    }

    public void sendChatCommand(String command) {
        LastSeenMessageList.Acknowledgment acknowledgment = new LastSeenMessageList.Acknowledgment(LastSeenMessageList.EMPTY, Optional.empty());
        mc.networkHandler.sendPacket(new ChatCommandC2SPacket(command, Instant.now(), 0L, ArgumentSignatureDataMap.EMPTY, false, acknowledgment));
    }

    public void swingHand(Hand hand) {
        super.swingHand(hand);
        mc.networkHandler.sendPacket(new SwingHandC2SPacket(hand));
    }

    public void sneak(boolean enable) {
        if (enable) mc.networkHandler.sendPacket(new PlayerCommandC2SPacket(0, PlayerCommand.StartSneaking, 0));
        else mc.networkHandler.sendPacket(new PlayerCommandC2SPacket(0, PlayerCommand.StopSneaking, 0));
    }

    public void respawn() {
        mc.networkHandler.sendPacket(new ClientCommandC2SPacket(ClientCommandC2SPacket.ClientAction.Respawn));
    }

    public void rotate(Rotation rot) {
        mc.networkHandler.sendPacket(new PlayerMoveC2SPacket.Look(rot, isOnGround));
        setRotation(rot);
    }

    public void updateSelectedSlot(int slot) {
        inventory.updateSelectedSlot(slot);
        resetLastAttackedTicks();
    }

    public int getClientPermissionLevel() {
        return clientPermissionLevel;
    }


    public double getExperienceBar() {
        return experienceBar;
    }

    public void setExperienceBar(double experienceBar) {
        this.experienceBar = experienceBar;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    @Override
    public void handleStatus(byte status) {
        if (status >= 24 && status <= 28) {
            clientPermissionLevel = status - 24;
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }
}

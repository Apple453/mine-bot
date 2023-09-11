package net.applee.minecraft.world.entity;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.item.EquipmentSlot;
import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.math.*;
import net.applee.minecraft.utils.string.Text;
import net.applee.minecraft.world.entity.attributes.Attribute;
import net.applee.minecraft.world.entity.attributes.AttributesContainer;
import net.applee.minecraft.world.entity.data.DataTracker;
import net.applee.minecraft.world.entity.data.TrackerTypes;
import net.applee.minecraft.world.entity.effect.Effect;
import net.applee.minecraft.world.entity.effect.EffectData;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class Entity {

	protected final DataTracker dataTracker = new DataTracker();

	protected final MinecraftClient mc;

	protected boolean isOnGround = false;
	protected boolean isTouchingWater = false;
	protected Rotation rotation = Rotation.ZERO;
	protected int headYaw = 0;
	protected Direction lookDirection;
	protected Vec3d position = Vec3d.ZERO;
	protected TrackedPosition trackedPosition = new TrackedPosition();
	protected Vec3d velocity = Vec3d.ZERO;
	protected int id = -1;
	protected EntityType type;
	protected UUID uuid;
	protected Map<Effect, EffectData> effects = new HashMap<>();
	protected Map<Integer, Entity> passengers = new HashMap<>();
	protected Entity vehicle;
	protected AttributesContainer attributesContainer = new AttributesContainer();
	protected boolean invulnerable = false;
	protected double fallDistance = 0;
	protected double jumpDistance = 0;
	protected long lastDamageTime = 0;

	protected boolean knockback = false;

	protected Map<EquipmentSlot, ItemStack> equipment = new HashMap<>();

	@Nullable
	protected Entity holdingEntity = null;

	protected long age = 0;


	public Entity() {
		this.mc = MinecraftClient.getInstance();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			equipment.put(slot, ItemStack.EMPTY);
		}

		dataTracker.register(TrackerTypes.BYTE, 0)
				.register(TrackerTypes.INTEGER, 300)
				.register(TrackerTypes.OPTIONAL_TEXT_COMPONENT, Optional.empty())
				.register(TrackerTypes.BOOLEAN, false)
				.register(TrackerTypes.BOOLEAN, false)
				.register(TrackerTypes.BOOLEAN, false)
				.register(TrackerTypes.ENTITY_POSE, EntityPose.STANDING)
				.register(TrackerTypes.INTEGER, 0);

	}

	public void readMetadata(PacketByteBuffer buffer) {
		while (!DataTracker.isEnd(buffer)) {
			try {
				if (!dataTracker.read(buffer)) break;
			} catch (Exception e) {
				mc.logger.warning("Error read metadata for \"%s\": %s".formatted(type.getName(), e.getMessage()));
				break;
			}
		}
	}

	private boolean getFlag(int index) {
		return getFlag(0, index);
	}

	public boolean getFlag(int flagsIndex, int index) {
		return (dataTracker.get(flagsIndex, TrackerTypes.BYTE) & 1 << index) != 0;
	}

	public boolean isOnFire() {
		return getFlag(0);
	}

	public boolean isCrouching() {
		return getFlag(1);
	}

	public boolean isUnused() {
		return getFlag(2);
	}

	public boolean isSprinting() {
		return getFlag(3);
	}

	public boolean isSwimming() {
		return getFlag(4);
	}

	public boolean isInvisible() {
		return getFlag(5);
	}

	public boolean hasGlowingEffect() {
		return getFlag(6);
	}

	public boolean isElytraFlying() {
		return getFlag(7);
	}

	public int getAirTicks() {
		return dataTracker.get(1, TrackerTypes.INTEGER);
	}

	public Optional<Text> getCustomName() {
		return dataTracker.get(2, TrackerTypes.OPTIONAL_TEXT_COMPONENT);
	}

	public boolean isCustomNameVisible() {
		return dataTracker.get(3, TrackerTypes.BOOLEAN);
	}

	public boolean isSilent() {
		return dataTracker.get(4, TrackerTypes.BOOLEAN);
	}

	public boolean hasGravity() {
		return !dataTracker.get(5, TrackerTypes.BOOLEAN);
	}

	public EntityPose getPose() {
		return dataTracker.get(6, TrackerTypes.ENTITY_POSE);
	}

	public int getFrozenTicks() {
		return dataTracker.get(7, TrackerTypes.INTEGER);
	}


	public long getAge() {
		return age;
	}

	public void tick() {
		if (velocity.y < 0) fallDistance -= velocity.y;
		if (velocity.y > 0) {
			jumpDistance += velocity.y;
			setOnGround(false);
		}
		age++;
	}


	public Vec3d getPosition() {
		return this.position.add(trackedPosition.getPos());
	}


	public double getX() {
		return getPosition().x;
	}

	public double getY() {
		return getPosition().y;
	}

	public double getZ() {
		return getPosition().z;
	}

	public TrackedPosition getTrackedPosition() {
		return trackedPosition;
	}

	public void updateTrackedPosition(TrackedPosition trackedPosition) {
		this.trackedPosition = trackedPosition;
	}

	public Rotation getRotation() {
		return new Rotation(rotation);
	}

	public BlockPos getBlockPos() {
		return new BlockPos(getPosition());
	}

	public Vec3d getVelocity() {
		return velocity;
	}

	public void setVelocity(double x, double y, double z) {
		this.velocity = new Vec3d(x, y, z);
	}

	public void setVelocity(Vec3d velocity) {
		this.velocity = velocity;
	}

	public void addVelocity(double x, double y, double z) {
		this.velocity = this.velocity.add(new Vec3d(x, y, z));
	}

	public void addVelocity(Vec3d velocity) {
		this.velocity = this.velocity.add(velocity);
	}

	public ChunkPos getChunkPos() {
		return new ChunkPos(getBlockPos());
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public void setRotation(Rotation rotation) {
		this.rotation = rotation;
		this.setLookDirection(Direction.fromRotation(rotation.getYaw()));
	}

	public void setPosition(Vec3d position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getHeadYaw() {
		return headYaw;
	}

	public void handleStatus(int status) {
		switch (status) {
			case 2, 33, 36, 37, 44, 57 -> {
				lastDamageTime = mc.world.getTime();
				knockback = true;

//                DamageSource damageSource;
//                SoundEvent soundEvent;
//                this.limbDistance = 1.5f;
//                this.timeUntilRegen = 20;
//                this.hurtTime = this.maxHurtTime = 10;
//                this.knockbackVelocity = 0.0f;
////                if (status == 33) {
////                    this.playSound(SoundEvents.ENCHANT_THORNS_HIT, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
////                }
////                if ((soundEvent = this.getHurtSound(damageSource = status == 37 ? DamageSource.ON_FIRE : (status == 36 ? DamageSource.DROWN : (status == 44 ? DamageSource.SWEET_BERRY_BUSH : (status == 57 ? DamageSource.FREEZE : DamageSource.GENERIC))))) != null) {
////                    this.playSound(soundEvent, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
////                }
//                this.damage(DamageSource.GENERIC, 0.0f);
//                this.lastDamageSource = damageSource;
			}
//            case 3 -> {
//                SoundEvent soundEvent2 = this.getDeathSound();
//                if (soundEvent2 != null) {
//                    this.playSound(soundEvent2, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
//                }
//                if (this instanceof PlayerEntity) break;
//                this.setHealth(0.0f);
//                this.onDeath(DamageSource.GENERIC);
//            }
//            case 30 -> this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + this.world.random.nextFloat() * 0.4f);
//            case 29 -> this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0f, 0.8f + this.world.random.nextFloat() * 0.4f);
//            case 46 -> {
//                for (int i = 0; i < 128; ++i) {
//                    double d = (double) i / 127.0;
//                    float f = (this.random.nextFloat() - 0.5f) * 0.2f;
//                    float g = (this.random.nextFloat() - 0.5f) * 0.2f;
//                    float h = (this.random.nextFloat() - 0.5f) * 0.2f;
//                    double e = MathHelper.lerp(d, this.prevX, this.getX()) + (this.random.nextDouble() - 0.5) * (double) this.getWidth() * 2.0;
//                    double k = MathHelper.lerp(d, this.prevY, this.getY()) + this.random.nextDouble() * (double) this.getHeight();
//                    double l = MathHelper.lerp(d, this.prevZ, this.getZ()) + (this.random.nextDouble() - 0.5) * (double) this.getWidth() * 2.0;
//                    this.world.addParticle(ParticleTypes.PORTAL, e, k, l, f, g, h);
//                }
//            }
//            case 47 -> this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.MAINHAND));
//            case 48 -> this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.OFFHAND));
//            case 49 -> this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.HEAD));
//            case 50 -> this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.CHEST));
//            case 51 -> this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.LEGS));
//            case 52 -> this.playEquipmentBreakEffects(this.getEquippedStack(EquipmentSlot.FEET));
//            case 53 -> HoneyBlock.addRegularParticles(this);
//            case 54 -> HoneyBlock.addRichParticles(this);
			case 55 -> this.swapHandStacks();
//            case 60 -> this.addDeathParticles();
		}
	}

	private void swapHandStacks() {
		ItemStack itemStack = this.getEquipmentSlot(EquipmentSlot.OFFHAND);
		this.updateEquipmentSlot(EquipmentSlot.OFFHAND, this.getEquipmentSlot(EquipmentSlot.MAINHAND));
		this.updateEquipmentSlot(EquipmentSlot.MAINHAND, itemStack);
	}

	public void setHeadYaw(int headYaw) {
		this.headYaw = headYaw;
	}

	public Direction getLookDirection() {
		return lookDirection;
	}

	public boolean isOnGround() {
		return isOnGround;
	}

	public boolean isKnockback() {
		return knockback;
	}

	public void setLookDirection(Direction lookDirection) {
		this.lookDirection = lookDirection;
	}

	public void setOnGround(boolean onGround) {
		isOnGround = onGround;
	}

	@Nullable
	public Entity getHoldingEntity() {
		return holdingEntity;
	}

	public void setHoldingEntity(@Nullable Entity holdingEntity) {
		this.holdingEntity = holdingEntity;
	}

	public void addEffect(EffectData effect) {
		effects.put(effect.getEffect(), effect);
	}

	public void removeEffect(Effect effect) {
		effects.remove(effect);
	}

	public EffectData getEffect(Effect effect) {
		return effects.get(effect);
	}

	public Iterable<EffectData> getEffects() {
		return effects.values();
	}

	public void addPassenger(int id, Entity entity) {
		passengers.put(id, entity);
	}

	public void removePassenger(int id) {
		passengers.remove(id);
	}

	public Iterable<Entity> getPassengers() {
		return passengers.values();
	}

	public AttributesContainer getAttributesContainer() {
		return attributesContainer;
	}

	public double getAttributeValue(Attribute attribute) {
		return attributesContainer.getData(attribute).getValue();
	}

	public void startRiding(Entity vehicle) {
		this.vehicle = vehicle;
		vehicle.addPassenger(this.id, this);
	}

	public void stopRiding() {
		if (this.vehicle == null) return;
		Entity entity = this.vehicle;
		this.vehicle = null;
		entity.removePassenger(this.id);
	}

	public void updateVehiclePos(Vec3d pos) {
		this.vehicle.setPosition(pos);
	}

	public void updateVehicleRotation(Rotation rot) {
		this.vehicle.setRotation(rot);
	}


	public void updateEquipmentSlot(EquipmentSlot slot, ItemStack stack) {
		equipment.put(slot, stack);
	}

	public ItemStack getEquipmentSlot(EquipmentSlot slot) {
		return equipment.get(slot);
	}

	public Map<EquipmentSlot, ItemStack> getEquipment() {
		return Map.copyOf(equipment);
	}

}

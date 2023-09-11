package net.applee.minecraft.world.entity.data;

import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.Direction;
import net.applee.minecraft.utils.math.EulerAngle;
import net.applee.minecraft.utils.string.Text;
import net.applee.minecraft.world.ParticleType;
import net.applee.minecraft.world.block.BlockState;
import net.applee.minecraft.world.entity.EntityPose;
import net.applee.minecraft.world.entity.mob.animal.villager.VillagerData;
import net.applee.minecraft.world.entity.mob.animal.villager.VillagerProfession;
import net.applee.minecraft.world.entity.mob.animal.villager.VillagerType;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

public class TrackerTypes {

	public static final TrackedType<Byte> BYTE = TrackedType.of(PacketByteBuffer::writeByte, PacketByteBuffer::readByte);
	public static final TrackedType<Integer> INTEGER = TrackedType.of(PacketByteBuffer::writeVarInt, PacketByteBuffer::readVarInt);
	public static final TrackedType<Float> FLOAT = TrackedType.of(PacketByteBuffer::writeFloat, PacketByteBuffer::readFloat);
	public static final TrackedType<String> STRING = TrackedType.of(PacketByteBuffer::writeString, PacketByteBuffer::readString);
	public static final TrackedType<Text> TEXT_COMPONENT = TrackedType.of(PacketByteBuffer::writeText, PacketByteBuffer::readText);
	public static final TrackedType<Optional<Text>> OPTIONAL_TEXT_COMPONENT = TrackedType.of(
			(buffer, oText) -> buffer.writeOptional(oText, PacketByteBuffer::writeText),
			buffer -> buffer.readOptional(PacketByteBuffer::readText));
	public static final TrackedType<ItemStack> ITEM_STACK = TrackedType.of(PacketByteBuffer::writeItemStack, PacketByteBuffer::readItemStack);
	public static final TrackedType<Boolean> BOOLEAN = TrackedType.of(PacketByteBuffer::writeBoolean, PacketByteBuffer::readBoolean);
	public static final TrackedType<EulerAngle> ROTATION = TrackedType.of(
			(buffer, rotation) -> {
				buffer.writeFloat(rotation.getPitch());
				buffer.writeFloat(rotation.getYaw());
				buffer.writeFloat(rotation.getRoll());
			},
			buffer -> new EulerAngle(buffer.readFloat(), buffer.readFloat(), buffer.readFloat())
	);
	public static final TrackedType<BlockPos> BLOCK_POS = TrackedType.of(PacketByteBuffer::writeBlockPos, PacketByteBuffer::readBlockPos);
	public static final TrackedType<Optional<BlockPos>> OPTIONAL_BLOCK_POS = TrackedType.of(
			(buffer, oPos) -> buffer.writeOptional(oPos, PacketByteBuffer::writeBlockPos),
			buffer -> buffer.readOptional(PacketByteBuffer::readBlockPos));
	public static final TrackedType<Direction> DIRECTION = TrackedType.of(
			PacketByteBuffer::writeEnumConstant,
			buffer -> buffer.readEnumConstant(Direction.class));
	public static final TrackedType<Optional<UUID>> OPTIONAL_UUID = TrackedType.of(
			(buffer, oUuid) -> buffer.writeOptional(oUuid, PacketByteBuffer::writeUuid),
			buffer -> buffer.readOptional(PacketByteBuffer::readUuid));
	public static final TrackedType<Optional<BlockState>> OPTIONAL_BLOCKSTATE = TrackedType.of(
		null, null
	);
	public static final TrackedType<Nbt> NBT = TrackedType.of(PacketByteBuffer::writeNbt, PacketByteBuffer::readNbt);
	public static final TrackedType<ParticleType> PARTICLE = TrackedType.ofEnum(ParticleType.class);
	public static final TrackedType<VillagerData> VILLAGER_DATA = TrackedType.of(
			(buffer, data) -> {
				buffer.writeEnumConstant(data.type());
				buffer.writeEnumConstant(data.profession());
				buffer.writeVarInt(data.level());
			},

			buffer -> {
				VillagerType type = buffer.readEnumConstant(VillagerType.class);
				VillagerProfession profession = buffer.readEnumConstant(VillagerProfession.class);
				int level = buffer.readVarInt();
				return new VillagerData(type, profession, level);
			}
	);
	public static final TrackedType<OptionalInt> OPTIONAL_INTEGER = TrackedType.of(
			(buffer, oInt) -> buffer.writeVarInt(oInt.orElse(-1) + 1),
			buffer -> {
				int i = buffer.readVarInt();
				return i == 0 ? OptionalInt.empty() : OptionalInt.of(i - 1);
			}
	);
	public static final TrackedType<EntityPose> ENTITY_POSE = TrackedType.ofEnum(EntityPose.class);





}

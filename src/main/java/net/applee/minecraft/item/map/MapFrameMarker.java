package net.applee.minecraft.item.map;

import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.BlockPos;

public record MapFrameMarker(BlockPos pos, int rotation, int entityId) {

	public static String getKey(BlockPos pos) {
		return "frame-" + pos.getX() + "," + pos.getY() + "," + pos.getZ();
	}

	public static MapFrameMarker fromNbt(Nbt nbt) {
		BlockPos blockPos = ((Nbt) nbt.get("Pos")).getBlockPos();
		int i = nbt.getInt("Rotation");
		int j = nbt.getInt("EntityId");
		return new MapFrameMarker(blockPos, i, j);
	}

	public Nbt toNbt() {
		Nbt nbt = new Nbt();
		nbt.put("Pos", new Nbt().putBlockPos(pos));
		nbt.put("Rotation", this.rotation);
		nbt.put("EntityId", this.entityId);
		return nbt;
	}

	public String getKey() {
		return MapFrameMarker.getKey(this.pos);
	}
}

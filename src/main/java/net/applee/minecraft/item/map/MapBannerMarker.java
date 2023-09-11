package net.applee.minecraft.item.map;

import net.applee.minecraft.enums.DyeColor;
import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.string.Text;
import net.applee.minecraft.world.World;
import net.applee.minecraft.world.block.blockentity.BlockEntity;
import net.applee.minecraft.world.block.blockentity.blockentities.BannerBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record MapBannerMarker(BlockPos pos, DyeColor color, Text name) {

	public static MapBannerMarker fromNbt(Nbt nbt) {
		BlockPos blockPos = ((Nbt) nbt.get("Pos")).getBlockPos();
		DyeColor dyeColor = DyeColor.byName(nbt.getString("Color"), DyeColor.WHITE);
		Text text = nbt.contains("Name") ? Text.of(nbt.getString("Name")) : null;
		return new MapBannerMarker(blockPos, dyeColor, text);
	}

	@Nullable
	public static MapBannerMarker fromWorldBlock(World world, BlockPos blockPos) {
		BlockEntity blockEntity = world.getBlockEntity(blockPos);


		if (blockEntity instanceof BannerBlockEntity bannerBlockEntity) {
			DyeColor dyeColor = bannerBlockEntity.getColorForState();
			Text text = bannerBlockEntity.hasCustomName() ? bannerBlockEntity.getCustomName() : null;
			return new MapBannerMarker(blockPos, dyeColor, text);
		}
		return null;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public DyeColor getColor() {
		return this.color;
	}

	public MapIcon.Type getIconType() {
		return switch (color) {
			case WHITE -> MapIcon.Type.BANNER_WHITE;
			case ORANGE -> MapIcon.Type.BANNER_ORANGE;
			case MAGENTA ->  MapIcon.Type.BANNER_MAGENTA;
			case LIGHT_BLUE -> MapIcon.Type.BANNER_LIGHT_BLUE;
			case YELLOW -> MapIcon.Type.BANNER_YELLOW;
			case LIME -> MapIcon.Type.BANNER_LIME;
			case PINK -> MapIcon.Type.BANNER_PINK;
			case GRAY -> MapIcon.Type.BANNER_GRAY;
			case LIGHT_GRAY -> MapIcon.Type.BANNER_LIGHT_GRAY;
			case CYAN -> MapIcon.Type.BANNER_CYAN;
			case PURPLE -> MapIcon.Type.BANNER_PURPLE;
			case BLUE -> MapIcon.Type.BANNER_BLUE;
			case BROWN -> MapIcon.Type.BANNER_BROWN;
			case GREEN -> MapIcon.Type.BANNER_GREEN;
			case RED -> MapIcon.Type.BANNER_RED;
			case BLACK -> MapIcon.Type.BANNER_BLACK;
		};
	}

	@Nullable
	public Text getName() {
		return this.name;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		MapBannerMarker mapBannerMarker = (MapBannerMarker) o;
		return Objects.equals(this.pos, mapBannerMarker.pos) && this.color == mapBannerMarker.color && Objects.equals(this.name, mapBannerMarker.name);
	}

	public int hashCode() {
		return Objects.hash(this.pos, this.color, this.name);
	}

	public Nbt getNbt() {
		Nbt nbt = new Nbt();
		nbt.put("Pos", new Nbt().putBlockPos(pos));
		nbt.put("Color", color.getName());
		if (this.name != null) {
			nbt.put("Name", name.rawJson);
		}
		return nbt;
	}

	public String getKey() {
		return "banner-" + this.pos.getX() + "," + this.pos.getY() + "," + this.pos.getZ();
	}
}

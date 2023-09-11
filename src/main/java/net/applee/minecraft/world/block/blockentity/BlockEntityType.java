package net.applee.minecraft.world.block.blockentity;

import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.Blocks;

public enum BlockEntityType {

	FURNACE("furnace", Blocks.FURNACE),
	CHEST("chest", Blocks.CHEST),
	TRAPPED_CHEST("trapped_chest", Blocks.TRAPPED_CHEST),
	ENDER_CHEST("ender_chest", Blocks.ENDER_CHEST),
	JUKEBOX("jukebox", Blocks.JUKEBOX),
	DISPENSER("dispenser", Blocks.DISPENSER),
	DROPPER("dropper", Blocks.DROPPER),
	SIGN("sign", Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN, Blocks.MANGROVE_SIGN, Blocks.MANGROVE_WALL_SIGN),
	MOB_SPAWNER("mob_spawner", Blocks.SPAWNER),
	PISTON("piston", Blocks.MOVING_PISTON),
	BREWING_STAND("brewing_stand", Blocks.BREWING_STAND),
	ENCHANTING_TABLE("enchanting_table", Blocks.ENCHANTING_TABLE),
	END_PORTAL("end_portal", Blocks.END_PORTAL),
	BEACON("beacon", Blocks.BEACON),
	SKULL("skull", Blocks.SKELETON_SKULL, Blocks.SKELETON_WALL_SKULL, Blocks.CREEPER_HEAD, Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_HEAD, Blocks.DRAGON_WALL_HEAD, Blocks.ZOMBIE_HEAD, Blocks.ZOMBIE_WALL_HEAD, Blocks.WITHER_SKELETON_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL, Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD),
	DAYLIGHT_DETECTOR("daylight_detector", Blocks.DAYLIGHT_DETECTOR),
	HOPPER("hopper", Blocks.HOPPER),
	COMPARATOR("comparator", Blocks.COMPARATOR),
	BANNER("banner", Blocks.WHITE_BANNER, Blocks.ORANGE_BANNER, Blocks.MAGENTA_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.YELLOW_BANNER, Blocks.LIME_BANNER, Blocks.PINK_BANNER, Blocks.GRAY_BANNER, Blocks.LIGHT_GRAY_BANNER, Blocks.CYAN_BANNER, Blocks.PURPLE_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.GREEN_BANNER, Blocks.RED_BANNER, Blocks.BLACK_BANNER, Blocks.WHITE_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER, Blocks.LIME_WALL_BANNER, Blocks.PINK_WALL_BANNER, Blocks.GRAY_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER, Blocks.CYAN_WALL_BANNER, Blocks.PURPLE_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.BLACK_WALL_BANNER),
	STRUCTURE_BLOCK("structure_block", Blocks.STRUCTURE_BLOCK),
	END_GATEWAY("end_gateway", Blocks.END_GATEWAY),
	COMMAND_BLOCK("command_block", Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.REPEATING_COMMAND_BLOCK),
	SHULKER_BOX("shulker_box", Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX),
	BED("bed", Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED),
	CONDUIT("conduit", Blocks.CONDUIT),
	BARREL("barrel", Blocks.BARREL),
	SMOKER("smoker", Blocks.SMOKER),
	BLAST_FURNACE("blast_furnace", Blocks.BLAST_FURNACE),
	LECTERN("lectern", Blocks.LECTERN),
	BELL("bell", Blocks.BELL),
	JIGSAW("jigsaw", Blocks.JIGSAW),
	CAMPFIRE("campfire", Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE),
	BEEHIVE("beehive", Blocks.BEE_NEST, Blocks.BEEHIVE),
	SCULK_SENSOR("sculk_sensor", Blocks.SCULK_SENSOR),
	SCULK_CATALYST("sculk_catalyst", Blocks.SCULK_CATALYST),
	SCULK_SHRIEKER("sculk_shrieker", Blocks.SCULK_SHRIEKER);

	public final String id;
	public final Block[] blocks;

	BlockEntityType(String id, Block... blocks) {
		this.id = id;
		this.blocks = blocks;
	}
}

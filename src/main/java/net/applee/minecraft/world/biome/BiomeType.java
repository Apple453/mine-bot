package net.applee.minecraft.world.biome;

public enum BiomeType {
	THE_VOID("the_void"),
	PLAINS("plains"),
	SUNFLOWER_PLAINS("sunflower_plains"),
	SNOWY_PLAINS("snowy_plains"),
	ICE_SPIKES("ice_spikes"),
	DESERT("desert"),
	SWAMP("swamp"),
	MANGROVE_SWAMP("mangrove_swamp"),
	FOREST("forest"),
	FLOWER_FOREST("flower_forest"),
	BIRCH_FOREST("birch_forest"),
	DARK_FOREST("dark_forest"),
	OLD_GROWTH_BIRCH_FOREST("old_growth_birch_forest"),
	OLD_GROWTH_PINE_TAIGA("old_growth_pine_taiga"),
	OLD_GROWTH_SPRUCE_TAIGA("old_growth_spruce_taiga"),
	TAIGA("taiga"),
	SNOWY_TAIGA("snowy_taiga"),
	SAVANNA("savanna"),
	SAVANNA_PLATEAU("savanna_plateau"),
	WINDSWEPT_HILLS("windswept_hills"),
	WINDSWEPT_GRAVELLY_HILLS("windswept_gravelly_hills"),
	WINDSWEPT_FOREST("windswept_forest"),
	WINDSWEPT_SAVANNA("windswept_savanna"),
	JUNGLE("jungle"),
	SPARSE_JUNGLE("sparse_jungle"),
	BAMBOO_JUNGLE("bamboo_jungle"),
	BADLANDS("badlands"),
	ERODED_BADLANDS("eroded_badlands"),
	WOODED_BADLANDS("wooded_badlands"),
	MEADOW("meadow"),
	GROVE("grove"),
	SNOWY_SLOPES("snowy_slopes"),
	FROZEN_PEAKS("frozen_peaks"),
	JAGGED_PEAKS("jagged_peaks"),
	STONY_PEAKS("stony_peaks"),
	RIVER("river"),
	FROZEN_RIVER("frozen_river"),
	BEACH("beach"),
	SNOWY_BEACH("snowy_beach"),
	STONY_SHORE("stony_shore"),
	WARM_OCEAN("warm_ocean"),
	LUKEWARM_OCEAN("lukewarm_ocean"),
	DEEP_LUKEWARM_OCEAN("deep_lukewarm_ocean"),
	OCEAN("ocean"),
	DEEP_OCEAN("deep_ocean"),
	COLD_OCEAN("cold_ocean"),
	DEEP_COLD_OCEAN("deep_cold_ocean"),
	FROZEN_OCEAN("frozen_ocean"),
	DEEP_FROZEN_OCEAN("deep_frozen_ocean"),
	MUSHROOM_FIELDS("mushroom_fields"),
	DRIPSTONE_CAVES("dripstone_caves"),
	LUSH_CAVES("lush_caves"),
	DEEP_DARK("deep_dark"),
	NETHER_WASTES("nether_wastes"),
	WARPED_FOREST("warped_forest"),
	CRIMSON_FOREST("crimson_forest"),
	SOUL_SAND_VALLEY("soul_sand_valley"),
	BASALT_DELTAS("basalt_deltas"),
	THE_END("the_end"),
	END_HIGHLANDS("end_highlands"),
	END_MIDLANDS("end_midlands"),
	SMALL_END_ISLANDS("small_end_islands"),
	END_BARRENS("end_barrens");

	private final String id;

	BiomeType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}

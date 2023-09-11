package net.applee.minecraft.world.entity.mob.animal.villager;

import net.applee.minecraft.utils.Utils;
import net.applee.minecraft.world.biome.BiomeType;

import java.util.HashMap;
import java.util.Map;

public enum VillagerType {

	DESERT("desert"),
	JUNGLE("jungle"),
	PLAINS("plains"),
	SAVANNA("savanna"),
	SNOW("snow"),
	SWAMP("swamp"),
	TAIGA("taiga");

	private static final Map<BiomeType, VillagerType> BIOME_TO_TYPE = Utils.make(new HashMap<>(), map -> {
		map.put(BiomeType.BADLANDS, DESERT);
		map.put(BiomeType.DESERT, DESERT);
		map.put(BiomeType.ERODED_BADLANDS, DESERT);
		map.put(BiomeType.WOODED_BADLANDS, DESERT);
		map.put(BiomeType.BAMBOO_JUNGLE, JUNGLE);
		map.put(BiomeType.JUNGLE, JUNGLE);
		map.put(BiomeType.SPARSE_JUNGLE, JUNGLE);
		map.put(BiomeType.SAVANNA_PLATEAU, SAVANNA);
		map.put(BiomeType.SAVANNA, SAVANNA);
		map.put(BiomeType.WINDSWEPT_SAVANNA, SAVANNA);
		map.put(BiomeType.DEEP_FROZEN_OCEAN, SNOW);
		map.put(BiomeType.FROZEN_OCEAN, SNOW);
		map.put(BiomeType.FROZEN_RIVER, SNOW);
		map.put(BiomeType.ICE_SPIKES, SNOW);
		map.put(BiomeType.SNOWY_BEACH, SNOW);
		map.put(BiomeType.SNOWY_TAIGA, SNOW);
		map.put(BiomeType.SNOWY_PLAINS, SNOW);
		map.put(BiomeType.GROVE, SNOW);
		map.put(BiomeType.SNOWY_SLOPES, SNOW);
		map.put(BiomeType.FROZEN_PEAKS, SNOW);
		map.put(BiomeType.JAGGED_PEAKS, SNOW);
		map.put(BiomeType.SWAMP, SWAMP);
		map.put(BiomeType.MANGROVE_SWAMP, SWAMP);
		map.put(BiomeType.OLD_GROWTH_SPRUCE_TAIGA, TAIGA);
		map.put(BiomeType.OLD_GROWTH_PINE_TAIGA, TAIGA);
		map.put(BiomeType.WINDSWEPT_GRAVELLY_HILLS, TAIGA);
		map.put(BiomeType.WINDSWEPT_HILLS, TAIGA);
		map.put(BiomeType.TAIGA, TAIGA);
		map.put(BiomeType.WINDSWEPT_FOREST, TAIGA);
	});
	private final String name;

	VillagerType(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}

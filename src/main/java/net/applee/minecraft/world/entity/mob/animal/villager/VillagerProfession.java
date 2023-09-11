package net.applee.minecraft.world.entity.mob.animal.villager;

import net.applee.minecraft.item.Item;
import net.applee.minecraft.world.block.Block;
import net.applee.minecraft.world.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public enum VillagerProfession {

	NONE("none", PointOfInterestType.NONE),
	ARMORER("armorer", PointOfInterestType.ARMORER),
	BUTCHER("butcher", PointOfInterestType.BUTCHER),
	CARTOGRAPHER("cartographer", PointOfInterestType.CARTOGRAPHER),
	CLERIC("cleric", PointOfInterestType.CLERIC),
	FARMER("farmer", PointOfInterestType.FARMER, List.of(Item.WHEAT, Item.WHEAT_SEEDS, Item.BEETROOT_SEEDS, Item.BONE_MEAL), List.of(Blocks.FARMLAND)),
	FISHERMAN("fisherman", PointOfInterestType.FISHERMAN),
	FLETCHER("fletcher", PointOfInterestType.FLETCHER),
	LEATHERWORKER("leatherworker", PointOfInterestType.LEATHERWORKER),
	LIBRARIAN("librarian", PointOfInterestType.LIBRARIAN),
	MASON("mason", PointOfInterestType.MASON),
	NITWIT("nitwit", PointOfInterestType.NONE),
	SHEPHERD("shepherd", PointOfInterestType.SHEPHERD),
	TOOLSMITH("toolsmith", PointOfInterestType.TOOLSMITH),
	WEAPONSMITH("weaponsmith", PointOfInterestType.WEAPONSMITH);

	private final String id;
	private final PointOfInterestType pointOfInterestType;
	private final List<Item> items;
	private final List<Block> blocks;

	VillagerProfession(String id, PointOfInterestType pointOfInterestType) {
		this(id, pointOfInterestType, new ArrayList<>(), new ArrayList<>());
	}

	VillagerProfession(String id, PointOfInterestType pointOfInterestType, List<Item> items, List<Block> blocks) {
		this.id = id;
		this.pointOfInterestType = pointOfInterestType;
		this.items = items;
		this.blocks = blocks;
	}

	public PointOfInterestType getPointOfInterestType() {
		return pointOfInterestType;
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public String toString() {
		return this.id;
	}
}
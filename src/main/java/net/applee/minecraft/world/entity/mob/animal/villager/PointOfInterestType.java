package net.applee.minecraft.world.entity.mob.animal.villager;

public enum PointOfInterestType {
	ARMORER("armorer"),
	BUTCHER("butcher"),
	CARTOGRAPHER("cartographer"),
	CLERIC("cleric"),
	FARMER("farmer"),
	FISHERMAN("fisherman"),
	FLETCHER("fletcher"),
	LEATHERWORKER("leatherworker"),
	LIBRARIAN("librarian"),
	MASON("mason"),
	SHEPHERD("shepherd"),
	TOOLSMITH("toolsmith"),
	WEAPONSMITH("weaponsmith"),
	HOME("home"),
	MEETING("meeting"),
	BEEHIVE("beehive"),
	BEE_NEST("bee_nest"),
	NETHER_PORTAL("nether_portal"),
	LODESTONE("lodestone"),
	LIGHTNING_ROD("lightning_rod"),
	NONE("");

	private final String id;

	PointOfInterestType(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}

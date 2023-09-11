package net.applee.minecraft.world.entity.mob.animal.villager;

public record VillagerData(VillagerType type, VillagerProfession profession, int level) {
	public static final int MIN_LEVEL = 1;
	public static final int MAX_LEVEL = 5;

	private static final int[] LEVEL_BASE_EXPERIENCE = new int[]{0, 10, 70, 150, 250};

	public VillagerData(VillagerType type, VillagerProfession profession, int level) {
		this.type = type;
		this.profession = profession;
		this.level = Math.max(1, level);
	}

	public static int getLowerLevelExperience(int level) {
		return VillagerData.canLevelUp(level) ? LEVEL_BASE_EXPERIENCE[level - 1] : 0;
	}

	public static int getUpperLevelExperience(int level) {
		return VillagerData.canLevelUp(level) ? LEVEL_BASE_EXPERIENCE[level] : 0;
	}

	public static boolean canLevelUp(int level) {
		return level >= MIN_LEVEL && level < MAX_LEVEL;
	}

	public VillagerData withType(VillagerType type) {
		return new VillagerData(type, this.profession, this.level);
	}

	public VillagerData withProfession(VillagerProfession profession) {
		return new VillagerData(this.type, profession, this.level);
	}

	public VillagerData withLevel(int level) {
		return new VillagerData(this.type, this.profession, level);
	}
}

package net.applee.minecraft.world.entity;

import net.applee.minecraft.world.entity.misc.*;
import net.applee.minecraft.world.entity.misc.decoration.GlowItemFrameEntity;
import net.applee.minecraft.world.entity.misc.decoration.ItemFrameEntity;
import net.applee.minecraft.world.entity.misc.decoration.LeashKnotEntity;
import net.applee.minecraft.world.entity.misc.decoration.PaintingEntity;
import net.applee.minecraft.world.entity.mob.EnderDragonEntity;
import net.applee.minecraft.world.entity.mob.animal.*;
import net.applee.minecraft.world.entity.mob.animal.tamedanimal.CatEntity;
import net.applee.minecraft.world.entity.mob.animal.tamedanimal.ParrotEntity;
import net.applee.minecraft.world.entity.mob.animal.tamedanimal.WolfEntity;
import net.applee.minecraft.world.entity.mob.animal.villager.VillagerEntity;
import net.applee.minecraft.world.entity.mob.animal.villager.WanderingTraderEntity;
import net.applee.minecraft.world.entity.mob.animal.wateranimal.*;
import net.applee.minecraft.world.entity.mob.golem.IronGolemEntity;
import net.applee.minecraft.world.entity.mob.golem.ShulkerEntity;
import net.applee.minecraft.world.entity.mob.golem.SnowGolemEntity;
import net.applee.minecraft.world.entity.mob.monster.*;
import net.applee.minecraft.world.entity.mob.monster.flying.GhastEntity;
import net.applee.minecraft.world.entity.mob.monster.flying.PhantomEntity;
import net.applee.minecraft.world.entity.mob.monster.guardian.ElderGuardianEntity;
import net.applee.minecraft.world.entity.mob.monster.guardian.GuardianEntity;
import net.applee.minecraft.world.entity.mob.monster.raider.*;
import net.applee.minecraft.world.entity.mob.monster.skeleton.SkeletonEntity;
import net.applee.minecraft.world.entity.mob.monster.skeleton.StrayEntity;
import net.applee.minecraft.world.entity.mob.monster.skeleton.WitherSkeletonEntity;
import net.applee.minecraft.world.entity.mob.monster.spider.CaveSpiderEntity;
import net.applee.minecraft.world.entity.mob.monster.spider.SpiderEntity;
import net.applee.minecraft.world.entity.mob.monster.zombie.HuskEntity;
import net.applee.minecraft.world.entity.mob.monster.zombie.ZombieEntity;
import net.applee.minecraft.world.entity.mob.monster.zombie.ZombieVillagerEntity;
import net.applee.minecraft.world.entity.mob.monster.zombie.ZombifiedPiglinEntity;
import net.applee.minecraft.world.entity.player.PlayerEntity;
import net.applee.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.applee.minecraft.world.entity.projectile.FishingHookEntity;
import net.applee.minecraft.world.entity.projectile.LlamaSpitEntity;
import net.applee.minecraft.world.entity.projectile.ShulkerBulletEntity;
import net.applee.minecraft.world.entity.projectile.arrow.ArrowEntity;
import net.applee.minecraft.world.entity.projectile.arrow.SpectralArrowEntity;
import net.applee.minecraft.world.entity.projectile.arrow.TridentEntity;
import net.applee.minecraft.world.entity.projectile.explosive.WitherSkullEntity;
import net.applee.minecraft.world.entity.projectile.explosive.fireball.DragonFireballEntity;
import net.applee.minecraft.world.entity.projectile.explosive.fireball.FireballEntity;
import net.applee.minecraft.world.entity.projectile.explosive.fireball.SmallFireballEntity;
import net.applee.minecraft.world.entity.projectile.thrown.*;
import net.applee.minecraft.world.entity.vehicle.boat.BoatEntity;
import net.applee.minecraft.world.entity.vehicle.boat.ChestBoatEntity;
import net.applee.minecraft.world.entity.vehicle.minecart.*;

import java.lang.reflect.InvocationTargetException;

public enum EntityType {

    ALLAY("allay", AllayEntity.class),
    AREA_EFFECT_CLOUD("area_effect_cloud", AreaEffectCloudEntity.class),
    ARMOR_STAND("armor_stand", ArmorStandEntity.class),
    ARROW("arrow", ArrowEntity.class),
    AXOLOTL("axolotl", AxolotlEntity.class),
    BAT("bat", BatEntity.class),
    BEE("bee", BeeEntity.class),
    BLAZE("blaze", BlazeEntity.class),
    BOAT("boat", BoatEntity.class),
    CHEST_BOAT("chest_boat", ChestBoatEntity.class),
    CAT("cat", CatEntity.class),
    CAVE_SPIDER("cave_spider", CaveSpiderEntity.class),
    CHICKEN("chicken", ChickenEntity.class),
    COD("cod", CodEntity.class),
    COW("cow", CowEntity.class),
    CREEPER("creeper", CreeperEntity.class),
    DOLPHIN("dolphin", DolphinEntity.class),
    DONKEY("donkey", DonkeyEntity.class),
    DRAGON_FIREBALL("dragon_fireball", DragonFireballEntity.class),
    DROWNED("drowned", DrownedEntity.class),
    ELDER_GUARDIAN("elder_guardian", ElderGuardianEntity.class),
    END_CRYSTAL("end_crystal", EndCrystalEntity.class),
    ENDER_DRAGON("ender_dragon", EnderDragonEntity.class),
    ENDERMAN("enderman", EndermanEntity.class),
    ENDERMITE("endermite", EndermiteEntity.class),
    EVOKER("evoker", EvokerEntity.class),
    EVOKER_FANGS("evoker_fangs", EvokerFangsEntity.class),
    EXPERIENCE_ORB("experience_orb", ExperienceOrbEntity.class),
    EYE_OF_ENDER("eye_of_ender", EyeOfEnderEntity.class),
    FALLING_BLOCK("falling_block", FallingBlockEntity.class),
    FIREWORK_ROCKET("firework_rocket", FireworkRocketEntity.class),
    FOX("fox", FoxEntity.class),
    FROG("frog", FrogEntity.class),
    GHAST("ghast", GhastEntity.class),
    GIANT("giant", GiantEntity.class),
    GLOW_ITEM_FRAME("glow_item_frame", GlowItemFrameEntity.class),
    GLOW_SQUID("glow_squid", GlowItemFrameEntity.class),
    GOAT("goat", GoatEntity.class),
    GUARDIAN("guardian", GuardianEntity.class),
    HOGLIN("hoglin", HoglinEntity.class),
    HORSE("horse", HorseEntity.class),
    HUSK("husk", HuskEntity.class),
    ILLUSIONER("illusioner", IllusionerEntity.class),
    IRON_GOLEM("iron_golem", IronGolemEntity.class),
    ITEM("item", ItemEntity.class),
    ITEM_FRAME("item_frame", ItemFrameEntity.class),
    FIREBALL("fireball", FireballEntity.class),
    LEASH_KNOT("leash_knot", LeashKnotEntity.class),
    LIGHTNING_BOLT("lightning_bolt", LightningEntity.class),
    LLAMA("llama", LlamaEntity.class),
    LLAMA_SPIT("llama_spit", LlamaSpitEntity.class),
    MAGMA_CUBE("magma_cube", MagmaCubeEntity.class),
    MARKER("marker", MarkerEntity.class),
    MINECART("minecart", MinecartEntity.class),
    CHEST_MINECART("chest_minecart", MinecartChestEntity.class),
    COMMAND_BLOCK_MINECART("command_block_minecart", MinecartCommandBlockEntity.class),
    FURNACE_MINECART("furnace_minecart", MinecartFurnaceEntity.class),
    HOPPER_MINECART("hopper_minecart", MinecartHopperEntity.class),
    SPAWNER_MINECART("spawner_minecart", MinecartSpawnerEntity.class),
    TNT_MINECART("tnt_minecart", MinecartTNTEntity.class),
    MULE("mule", MuleEntity.class),
    MOOSHROOM("mooshroom", MooshroomEntity.class),
    OCELOT("ocelot", OcelotEntity.class),
    PAINTING("painting", PaintingEntity.class),
    PANDA("panda", PandaEntity.class),
    PARROT("parrot", ParrotEntity.class),
    PHANTOM("phantom", PhantomEntity.class),
    PIG("pig", PigEntity.class),
    PIGLIN("piglin", PiglinEntity.class),
    PIGLIN_BRUTE("piglin_brute", PiglinBruteEntity.class),
    PILLAGER("pillager", PillagerEntity.class),
    POLAR_BEAR("polar_bear", PolarBearEntity.class),
    TNT("tnt", PrimedTNTEntity.class),
    PUFFERFISH("pufferfish", PufferFishEntity.class),
    RABBIT("rabbit", RabbitEntity.class),
    RAVAGER("ravager", RavagerEntity.class),
    SALMON("salmon", SalmonEntity.class),
    SHEEP("sheep", SheepEntity.class),
    SHULKER("shulker", ShulkerEntity.class),
    SHULKER_BULLET("shulker_bullet", ShulkerBulletEntity.class),
    SILVERFISH("silverfish", SilverfishEntity.class),
    SKELETON("skeleton", SkeletonEntity.class),
    SKELETON_HORSE("skeleton_horse", SkeletonHorseEntity.class),
    SLIME("slime", SlimeEntity.class),
    SMALL_FIREBALL("small_fireball", SmallFireballEntity.class),
    SNOW_GOLEM("snow_golem", SnowGolemEntity.class),
    SNOWBALL("snowball", SnowBallEntity.class),
    SPECTRAL_ARROW("spectral_arrow", SpectralArrowEntity.class),
    SPIDER("spider", SpiderEntity.class),
    SQUID("squid", SquidEntity.class),
    STRAY("stray", StrayEntity.class),
    STRIDER("strider", StriderEntity.class),
    TADPOLE("tadpole", TadpoleEntity.class),
    EGG("egg", EggEntity.class),
    ENDER_PEARL("ender_pearl", EnderPearlEntity.class),
    EXPERIENCE_BOTTLE("experience_bottle", ExperienceBottleEntity.class),
    POTION("potion", PotionEntity.class),
    TRIDENT("trident", TridentEntity.class),
    TRADER_LLAMA("trader_llama", TraderLlamaEntity.class),
    TROPICAL_FISH("tropical_fish", TropicalFishEntity.class),
    TURTLE("turtle", TurtleEntity.class),
    VEX("vex", VexEntity.class),
    VILLAGER("villager", VillagerEntity.class),
    VINDICATOR("vindicator", VindicatorEntity.class),
    WANDERING_TRADER("wandering_trader", WanderingTraderEntity.class),
    WARDEN("warden", WardenEntity.class),
    WITCH("witch", WitchEntity.class),
    WITHER("wither", WitherEntity.class),
    WITHER_SKELETON("wither_skeleton", WitherSkeletonEntity.class),
    WITHER_SKULL("wither_skull", WitherSkullEntity.class),
    WOLF("wolf", WolfEntity.class),
    ZOGLIN("zoglin", ZoglinEntity.class),
    ZOMBIE("zombie", ZombieEntity.class),
    ZOMBIE_HORSE("zombie_horse", ZombieHorseEntity.class),
    ZOMBIE_VILLAGER("zombie_villager", ZombieVillagerEntity.class),
    ZOMBIFIED_PIGLIN("zombified_piglin", ZombifiedPiglinEntity.class),
    PLAYER("player", PlayerEntity.class),
    FISHING_BOBBER("fishing_bobber", FishingHookEntity.class);

    private final String name;

    private final Class<? extends Entity> entityClass;

    EntityType(String name, Class<? extends Entity> clas) {
        this.name = name;
        entityClass = clas;
    }

    public String getName() {
        return name;
    }

    public String getTranslationKey() {
        return "entity.minecraft" + "." + name;
    }

    public Entity createEntity() {
        try {
            return entityClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}

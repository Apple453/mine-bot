package net.applee.minecraft.item;

public enum Enchantments {

    FROST_WALKER("frost_walker", Rarity.RARE, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET),
    SOUL_SPEED("soul_speed", Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET),
    SWIFT_SNEAK("swift_sneak", Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.LEGS),
    SHARPNESS("sharpness", Rarity.COMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND),
    SMITE("smite", Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND),
    BANE_OF_ARTHROPODS("bane_of_arthropods", Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND),
    KNOCKBACK("knockback", Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND),
    FIRE_ASPECT("fire_aspect", Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND),
    LOOTING("looting", Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND),
    SWEEPING("sweeping", Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND),
    EFFICIENCY("efficiency", Rarity.COMMON, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND),
    SILK_TOUCH("silk_touch", Rarity.VERY_RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND),
    UNBREAKING("unbreaking", Rarity.UNCOMMON, EnchantmentTarget.ALL, EquipmentSlot.MAINHAND),
    FORTUNE("fortune", Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND),
    POWER("power", Rarity.COMMON, EnchantmentTarget.BOW, EquipmentSlot.MAINHAND),
    PUNCH("punch", Rarity.RARE, EnchantmentTarget.BOW, EquipmentSlot.MAINHAND),
    FLAME("flame", Rarity.RARE, EnchantmentTarget.BOW, EquipmentSlot.MAINHAND),
    INFINITY("infinity", Rarity.VERY_RARE, EnchantmentTarget.BOW, EquipmentSlot.MAINHAND),
    LUCK_OF_THE_SEA("luck_of_the_sea", Rarity.RARE, EnchantmentTarget.FISHING_ROD, EquipmentSlot.MAINHAND),
    LURE("lure", Rarity.RARE, EnchantmentTarget.FISHING_ROD, EquipmentSlot.MAINHAND),
    LOYALTY("loyalty", Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND),
    IMPALING("impaling", Rarity.RARE, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND),
    RIPTIDE("riptide", Rarity.RARE, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND),
    CHANNELING("channeling", Rarity.VERY_RARE, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND),
    MULTISHOT("multishot", Rarity.RARE, EnchantmentTarget.CROSSBOW, EquipmentSlot.MAINHAND),
    QUICK_CHARGE("quick_charge", Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, EquipmentSlot.MAINHAND),
    PIERCING("piercing", Rarity.COMMON, EnchantmentTarget.CROSSBOW, EquipmentSlot.MAINHAND),
    MENDING("mending", Rarity.RARE, EnchantmentTarget.ALL, EquipmentSlot.values()),
    VANISHING_CURSE("vanishing_curse", Rarity.VERY_RARE, EnchantmentTarget.ALL, EquipmentSlot.values()),
    PROTECTION("protection", Rarity.COMMON, EnchantmentTarget.ARMOR, EquipmentSlot.ARMOR),
    FIRE_PROTECTION("fire_protection", Rarity.UNCOMMON, EnchantmentTarget.ARMOR, EquipmentSlot.ARMOR),
    FEATHER_FALLING("feather_falling", Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.ARMOR),
    BLAST_PROTECTION("blast_protection", Rarity.RARE, EnchantmentTarget.ARMOR, EquipmentSlot.ARMOR),
    PROJECTILE_PROTECTION("projectile_protection", Rarity.UNCOMMON, EnchantmentTarget.ARMOR, EquipmentSlot.ARMOR),
    RESPIRATION("respiration", Rarity.RARE, EnchantmentTarget.ARMOR_HEAD, EquipmentSlot.ARMOR),
    AQUA_AFFINITY("aqua_affinity", Rarity.RARE, EnchantmentTarget.ARMOR_HEAD, EquipmentSlot.ARMOR),
    THORNS("thorns", Rarity.VERY_RARE, EnchantmentTarget.ARMOR, EquipmentSlot.ARMOR),
    DEPTH_STRIDER("depth_strider", Rarity.RARE, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.ARMOR),
    BINDING_CURSE("binding_curse", Rarity.VERY_RARE, EnchantmentTarget.ARMOR, EquipmentSlot.ARMOR);

    public static final String LEVEL_KEY = "lvl";
    public static final String ID_KEY = "id";

    private final String key;
    private final Rarity rarity;
    private final EnchantmentTarget target;
    private final EquipmentSlot[] slots;

    Enchantments(String key, Rarity rarity, EnchantmentTarget target, EquipmentSlot slot) {
        this(key, rarity, target, new EquipmentSlot[]{slot});
    }

    Enchantments(String key, Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slots) {
        this.key = key;
        this.rarity = rarity;
        this.target = target;
        this.slots = slots;
    }

    public String getKey() {
        return key;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public EnchantmentTarget getTarget() {
        return target;
    }

    public EquipmentSlot[] getSlots() {
        return slots;
    }

    public static Enchantment fromKey(String key, int level) {
        for (Enchantments enchantment : Enchantments.values()) {
            if (key.endsWith(enchantment.getKey())) return new Enchantment(enchantment, level);
        }
        return null;
    }

    public enum Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        VERY_RARE
    }

    public enum EnchantmentTarget {
        ALL,
        ARMOR,
        ARMOR_FEET,
        ARMOR_HEAD,
        WEAPON,
        DIGGER,
        FISHING_ROD,
        TRIDENT,
        BOW,
        CROSSBOW,
    }

}

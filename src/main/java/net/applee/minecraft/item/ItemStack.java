package net.applee.minecraft.item;

import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.Utils;
import org.json.JSONArray;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemStack {

    public static final DecimalFormat MODIFIER_FORMAT = Utils.make(new DecimalFormat("#.##"), decimalFormat -> decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
    public static final ItemStack EMPTY = new ItemStack(Item.AIR, 0);
    public static final String ENCHANTMENTS_KEY = "Enchantments";
    public static final String STORED_ENCHANTMENTS_KEY = "StoredEnchantments";
    public static final String DISPLAY_KEY = "display";
    public static final String NAME_KEY = "Name";
    public static final String LORE_KEY = "Lore";
    public static final String DAMAGE_KEY = "Damage";
    public static final String COLOR_KEY = "color";
    public static final String UNBREAKABLE_KEY = "Unbreakable";
    public static final String REPAIR_COST_KEY = "RepairCost";
    public static final String CAN_DESTROY_KEY = "CanDestroy";
    public static final String CAN_PLACE_ON_KEY = "CanPlaceOn";
    public static final String HIDE_FLAGS_KEY = "HideFlags";

    private final Item item;
    private int count;
    @Nullable
    private Nbt nbt = null;
    private boolean empty = false;


    public ItemStack(Item item) {
        this(item, 1, null);
    }

    public ItemStack(Item item,  int count) {
        this(item, count, null);
    }

    public ItemStack(Item item, int count, Nbt nbt) {
        this.item = item;
        this.count = count;
        this.nbt = nbt;
        if (item == Item.AIR || count == 0) this.empty = true;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    @Nullable
    public Nbt getNbt() {
        return nbt;
    }

    public void setNbt(Nbt nbt) {
        this.nbt = nbt;
    }

    public boolean hasEnchantments() {
        return nbt != null && ((nbt.contains(ENCHANTMENTS_KEY) && !nbt.getArray(ENCHANTMENTS_KEY).isEmpty()) ||
                                (nbt.contains(STORED_ENCHANTMENTS_KEY) && !nbt.getArray(STORED_ENCHANTMENTS_KEY).isEmpty()));
    }

    public List<Enchantment> getEnchantments() {
        if (!hasEnchantments()) return new ArrayList<>();
        List<Enchantment> enchantments = new ArrayList<>();

        for (String key : new String[]{ENCHANTMENTS_KEY, STORED_ENCHANTMENTS_KEY}) {
            if (nbt.contains(key)) {
                JSONArray nbtEnchants = nbt.getArray(key);
                for (Object ench : nbtEnchants) {
                    Nbt enchant = new Nbt(ench);
                    enchantments.add(Enchantments.fromKey(enchant.getString(Enchantments.ID_KEY), enchant.getInt(Enchantments.LEVEL_KEY)));
                }
            }
        }

        return enchantments;
    }


    public boolean isEmpty() {
        return empty || isOf(Item.AIR) || isOf(null);
    }

    public boolean isOf(Item item) {
        if (item == null) return this.getItem() == null;
        return item.equals(this.getItem());
    }

    public ItemStack copy() {
        ItemStack copy = new ItemStack(item, count);
        if (nbt != null) copy.setNbt(nbt.copy());
        return copy;
    }

    @Override
    public String toString() {
        return "ItemStack{" +
                "item=" + item +
                ", count=" + count +
                ", nbt=" + nbt +
                ", empty=" + empty +
                '}';
    }

    public boolean isSword() {
        return item == Item.WOODEN_SWORD ||
                item == Item.STONE_SWORD ||
                item == Item.GOLDEN_SWORD ||
                item == Item.IRON_SWORD ||
                item == Item.DIAMOND_SWORD ||
                item == Item.NETHERITE_SWORD;
    }

    public boolean isPickaxe() {
        return item == Item.WOODEN_PICKAXE ||
                item == Item.STONE_PICKAXE ||
                item == Item.GOLDEN_PICKAXE ||
                item == Item.IRON_PICKAXE ||
                item == Item.DIAMOND_PICKAXE ||
                item == Item.NETHERITE_PICKAXE;
    }

    public boolean isAxe() {
        return item == Item.WOODEN_AXE ||
                item == Item.STONE_AXE ||
                item == Item.GOLDEN_AXE ||
                item == Item.IRON_AXE ||
                item == Item.DIAMOND_AXE ||
                item == Item.NETHERITE_AXE;
    }

    public boolean isShovel() {
        return item == Item.WOODEN_SHOVEL ||
                item == Item.STONE_SHOVEL ||
                item == Item.GOLDEN_SHOVEL ||
                item == Item.IRON_SHOVEL ||
                item == Item.DIAMOND_SHOVEL ||
                item == Item.NETHERITE_SHOVEL;
    }

    public boolean isHoe() {
        return item == Item.WOODEN_HOE ||
                item == Item.STONE_HOE ||
                item == Item.GOLDEN_HOE ||
                item == Item.IRON_HOE ||
                item == Item.DIAMOND_HOE ||
                item == Item.NETHERITE_HOE;
    }

    public boolean isHelmet() {
        return item == Item.GOLDEN_HELMET ||
                item == Item.IRON_HELMET ||
                item == Item.DIAMOND_HELMET ||
                item == Item.NETHERITE_HELMET ||
                item == Item.TURTLE_HELMET ||
                item == Item.CHAINMAIL_HELMET ||
                item == Item.LEATHER_HELMET;
    }

    public boolean isChestplate() {
        return item == Item.GOLDEN_CHESTPLATE ||
                item == Item.IRON_CHESTPLATE ||
                item == Item.DIAMOND_CHESTPLATE ||
                item == Item.NETHERITE_CHESTPLATE ||
                item == Item.CHAINMAIL_CHESTPLATE ||
                item == Item.LEATHER_CHESTPLATE;
    }

    public boolean isLeggings() {
        return item == Item.GOLDEN_LEGGINGS ||
                item == Item.IRON_LEGGINGS ||
                item == Item.DIAMOND_LEGGINGS ||
                item == Item.NETHERITE_LEGGINGS ||
                item == Item.CHAINMAIL_LEGGINGS ||
                item == Item.LEATHER_LEGGINGS;
    }

    public boolean isBoots() {
        return item == Item.GOLDEN_BOOTS ||
                item == Item.IRON_BOOTS ||
                item == Item.DIAMOND_BOOTS ||
                item == Item.NETHERITE_BOOTS ||
                item == Item.CHAINMAIL_BOOTS ||
                item == Item.LEATHER_BOOTS;
    }

}

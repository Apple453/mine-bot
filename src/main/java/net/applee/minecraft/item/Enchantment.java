package net.applee.minecraft.item;

public record Enchantment(Enchantments enchantment, int level) {

    public boolean is(Enchantments enchantment) {
        return this.enchantment.equals(enchantment);
    }
}

package net.applee.minecraft.world.entity.player;


import net.applee.minecraft.client.SkinParts;
import net.applee.minecraft.client.screenhandler.ScreenHandler;
import net.applee.minecraft.enums.Arm;
import net.applee.minecraft.enums.GameMode;
import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.item.Enchantment;
import net.applee.minecraft.item.Enchantments;
import net.applee.minecraft.item.EquipmentSlot;
import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.gameprofile.GameProfile;
import net.applee.minecraft.utils.math.MathHelper;
import net.applee.minecraft.world.entity.LivingEntity;
import net.applee.minecraft.world.entity.attributes.Attribute;
import net.applee.minecraft.world.entity.data.TrackerTypes;
import net.applee.minecraft.world.entity.effect.Effect;
import net.applee.minecraft.world.entity.effect.EffectData;

public class PlayerEntity extends LivingEntity {

    private static final String SKIN_URL = "http://skins.minecraft.net/MinecraftSkins/%s.png";

    public PlayerEntity() {
        dataTracker.register(TrackerTypes.FLOAT, 0f)
                .register(TrackerTypes.INTEGER, 0)
                .register(TrackerTypes.BYTE, (byte) 0)
                .register(TrackerTypes.BYTE, (byte) 1)
                .register(TrackerTypes.NBT, Nbt.EMPTY)
                .register(TrackerTypes.NBT, Nbt.EMPTY);
    }

    public double getExtraHearts() {
        return dataTracker.get(15, TrackerTypes.FLOAT);
    }

    public int getScore() {
        return dataTracker.get(16, TrackerTypes.INTEGER);
    }

    public SkinParts getSkinParts() {
        return new SkinParts(dataTracker.get(17, TrackerTypes.BYTE));
    }

    public Arm getMainHand() {
        return dataTracker.get(18, TrackerTypes.BYTE) == 0 ? Arm.LEFT : Arm.RIGHT;
    }

    public Nbt getLeftShoulder() {
        return dataTracker.get(19, TrackerTypes.NBT);
    }

    public Nbt getRightShoulder() {
        return dataTracker.get(20, TrackerTypes.NBT);
    }

    protected PlayerAbilities abilities = PlayerAbilities.DEFAULT;

    public int lastAttackedTicks = 0;

    public ScreenHandler currentScreenHandler = null;
    @Override
    public void tick() {
        super.tick();
        lastAttackedTicks++;
    }

    public String getNickname() {
        GameProfile profile = getProfile();
        if (profile == null) return null;
        return profile.getName();
    }

    public GameMode getGamemode() {
        GameProfile profile = getProfile();
        if (profile == null) return null;
        return profile.getGamemode();
    }


    public void onLanding() {
        this.fallDistance = 0.0f;
    }

    private GameProfile getProfile() {
        return mc.profileManager.getProfile(uuid);
    }

    public void resetLastAttackedTicks() {
        lastAttackedTicks = 0;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        if (this.abilities.allowFlying()) return false;

        boolean bl = super.handleFallDamage(fallDistance, damageMultiplier);
        int i = this.computeFallDamage(damageMultiplier);
        if (i > 0) {
            this.applyDamage(i);
            return true;
        }
        return bl;
    }

    public boolean isAttackCdOver() {
        return getAttackCooldownProgress(0.5f) >= 1;
    }

    public double getItemAttackSpeed() {
        ItemStack handStack = getEquipmentSlot(EquipmentSlot.MAINHAND);
        if (handStack.isEmpty()) return 0;
        return handStack.getItem().getAttackSpeed();
    }

    public double getEffectAttackSpeedMultiplier() {
        if (effects.get(Effect.Haste) != null) return 1.19;
        return 1;
    }

    public float getAttackCooldownProgressPerTick() {
        return (float) (1.0 / ((this.getAttributeValue(Attribute.AttackSpeed) + getItemAttackSpeed()) * getEffectAttackSpeedMultiplier()) * 20);
    }

    public float getAttackCooldownProgress(float baseTime) {
        return MathHelper.clamp(((float) this.lastAttackedTicks + baseTime) / this.getAttackCooldownProgressPerTick(), 0.0f, 1.0f);
    }

    public void swingHand(Hand hand) {
        resetLastAttackedTicks();
    }

    public double getFallDistance() {
        return fallDistance;
    }

    @Deprecated
    public int getFallDamage() {
        double mult = 0.85;
        ItemStack boots;
        if ((boots = mc.player.inventory.getArmor(3)).isBoots()) {
            for (Enchantment enchantment : boots.getEnchantments()) {
                if (enchantment.is(Enchantments.FEATHER_FALLING)) {
                    mult -= ((double) (enchantment.level() * 12)) / 100;
                }
            }

        }
        return computeFallDamage(mult);
    }

    public int computeFallDamage(double damageMultiplier) {
        EffectData statusEffectInstance = this.getEffect(Effect.JumpBoost);
        float f = statusEffectInstance == null ? 0.0f : (float) (statusEffectInstance.getAmplifier() + 1);
        int damage = MathHelper.ceil((fallDistance - 3.0f - f) * damageMultiplier);
        damage = MathHelper.clamp(damage, 0, MathHelper.ceil(fallDistance - 3));
        return damage;
    }


    public PlayerAbilities getAbilities() {
        return abilities;
    }

    public void setAbilities(PlayerAbilities abilities) {
        this.abilities = abilities;
    }

    public void handleStatus(byte status) {
        if (status == 9) {
//            this.consumeItem();
        } else if (status == 23) {
//            this.reducedDebugInfo = false;
        } else if (status == 22) {
//            this.reducedDebugInfo = true;
        } else if (status == 43) {
//            this.spawnParticles(ParticleTypes.CLOUD);
        } else {
            super.handleStatus(status);
        }
    }
}

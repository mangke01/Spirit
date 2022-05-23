package me.codexadrian.spirit.utils;

import me.codexadrian.spirit.Spirit;
import me.codexadrian.spirit.SpiritRegistry;
import me.codexadrian.spirit.Tier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;

public class SoulUtils {

    @Nullable
    public static Tier getTier(ItemStack itemStack) {
        if(!itemStack.hasTag() || !itemStack.getTag().contains("StoredEntity")) {
            return null;
        }
        int storedSouls = itemStack.getTag().getCompound("StoredEntity").getInt("Souls");
        String type = itemStack.getTag().getCompound("StoredEntity").getString("Type");
        Tier tier = null;
        for(Tier t : Spirit.getSpiritConfig().getTiers()) {
            if(Arrays.stream(t.getBlacklist()).noneMatch(b -> b.equals(type))) {
                if (t.getRequiredSouls() <= storedSouls) {
                    tier = t;
                } else {
                    break;
                }
            }
        }
        return tier;
    }

    public static int getTierIndex(ItemStack itemStack) {
        if(!itemStack.hasTag() || !itemStack.getTag().contains("StoredEntity")) {
            return -1;
        }
        int storedSouls = itemStack.getTag().getCompound("StoredEntity").getInt("Souls");
        String type = itemStack.getTag().getCompound("StoredEntity").getString("Type");
        int tier = 0;
        for(int i = 0; i < Spirit.getSpiritConfig().getTiers().length; i++) {
            Tier t = Spirit.getSpiritConfig().getTiers()[i];
            if(Arrays.stream(t.getBlacklist()).noneMatch(b -> b.equals(type))) {
                if (t.getRequiredSouls() <= storedSouls) {
                    tier = i;
                } else {
                    break;
                }
            }
        }
        return tier;
    }

    @Nullable
    public static Tier getNextTier(ItemStack itemStack) {
        if(!itemStack.hasTag() || !itemStack.getTag().contains("StoredEntity")) {
            return null;
        }
        int storedSouls = itemStack.getTag().getCompound("StoredEntity").getInt("Souls");
        String type = itemStack.getTag().getCompound("StoredEntity").getString("Type");
        Tier tier = null;
        for(Tier t : Spirit.getSpiritConfig().getTiers()) {
            if(Arrays.stream(t.getBlacklist()).noneMatch(b -> b.equals(type))) {
                if (t.getRequiredSouls() > storedSouls) {
                    tier = t;
                    break;
                }
            }
        }
        return tier;
    }

    public static int getMaxSouls(ItemStack itemStack) {
        if(!itemStack.hasTag() || !itemStack.getTag().contains("StoredEntity")) {
            return Integer.MAX_VALUE;
        }
        String type = itemStack.getTag().getCompound("StoredEntity").getString("Type");
        int requiredSouls = 0;
        for(int i = 0; i < Spirit.getSpiritConfig().getTiers().length; i++) {
            Tier t = Spirit.getSpiritConfig().getTiers()[i];
            if(Arrays.stream(t.getBlacklist()).noneMatch(b -> b.equals(type))) {
                if(requiredSouls < t.getRequiredSouls()) {
                    requiredSouls = t.getRequiredSouls();
                }
            }
        }
        return requiredSouls;
    }

    public static boolean isMaxTier(ItemStack itemStack) {
        Tier tier = getTier(itemStack);
        Tier nextTier = getNextTier(itemStack);
        if(tier == null || nextTier == null) return false;

        return tier == nextTier;
    }

    public static float getActivation(ItemStack stack) {
        Tier tier = SoulUtils.getTier(stack);
        if (tier == null) {
            return 0f;
        }

        return ((float) tier.getRequiredSouls()) / SoulUtils.getMaxSouls(stack);
    }
}

package earth.terrarium.spirit.common.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.spirit.api.abilities.armor.ArmorAbility;
import earth.terrarium.spirit.api.abilities.armor.ArmorAbilityManager;
import earth.terrarium.spirit.api.storage.util.SoulIngredient;
import earth.terrarium.spirit.common.item.armor.SoulSteelArmor;
import earth.terrarium.spirit.common.registry.SpiritItems;
import earth.terrarium.spirit.common.registry.SpiritRecipes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record ArmorInfusionRecipe(ResourceLocation id, Ingredient activationItem,
                                  List<SoulIngredient> entityInputs, List<Ingredient> itemInputs,
                                  ArmorAbility result,
                                  int duration) implements InfusionRecipe<ArmorAbility> {

    public static Codec<ArmorInfusionRecipe> codec(ResourceLocation location) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(location),
                IngredientCodec.CODEC.fieldOf("activatorIngredient").forGetter(ArmorInfusionRecipe::activationItem),
                SoulIngredient.CODEC.listOf().fieldOf("entityIngredients").forGetter(ArmorInfusionRecipe::entityInputs),
                IngredientCodec.CODEC.listOf().fieldOf("ingredients").forGetter(ArmorInfusionRecipe::itemInputs),
                Codec.STRING.fieldOf("result").xmap(ArmorAbilityManager::getAbility, ArmorAbilityManager::getName).forGetter(ArmorInfusionRecipe::result),
                Codec.INT.fieldOf("duration").orElse(60).forGetter(ArmorInfusionRecipe::duration)
        ).apply(instance, ArmorInfusionRecipe::new));
    }

    @Override
    public boolean matches(Container container, Level level) {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SpiritRecipes.ARMOR_INFUSION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return SpiritRecipes.ARMOR_INFUSION.get();
    }

    @Override
    public ItemStack getInfusionResult(ItemStack input) {
        ItemStack result = input.copy();
        if (allowInfusion(result) && input.getItem() instanceof SoulSteelArmor armor) {
            armor.addAbility(result, result());
        }
        return result;
    }

    @Override
    public boolean allowInfusion(ItemStack input) {
        return input.getItem() instanceof SoulSteelArmor armor && armor.allowInfusion(input);
    }

    public static ArmorInfusionRecipe getRecipeFromId(ResourceLocation name, RecipeManager manager) {
        return manager.getAllRecipesFor(SpiritRecipes.ARMOR_INFUSION.get()).stream()
                .filter(recipe -> recipe.getId().equals(name))
                .findFirst()
                .orElse(null);
    }
}

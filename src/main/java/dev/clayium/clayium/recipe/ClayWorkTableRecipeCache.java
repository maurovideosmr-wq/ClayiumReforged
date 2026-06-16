package dev.clayium.clayium.recipe;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.level.Level;

public final class ClayWorkTableRecipeCache {
    private static volatile List<RecipeHolder<ClayWorkTableRecipe>> clientRecipes = List.of();

    private ClayWorkTableRecipeCache() {
    }

    public static void updateClientRecipes(RecipeMap recipeMap) {
        clientRecipes = collectWorkTableRecipes(recipeMap.values());
    }

    public static void clearClientRecipes() {
        clientRecipes = List.of();
    }

    public static List<RecipeHolder<ClayWorkTableRecipe>> clientRecipes() {
        return clientRecipes;
    }

    public static List<RecipeHolder<ClayWorkTableRecipe>> recipes(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return collectWorkTableRecipes(serverLevel.recipeAccess().getRecipes());
        }
        if (level.isClientSide()) {
            return clientRecipes;
        }
        return List.of();
    }

    public static Optional<RecipeHolder<ClayWorkTableRecipe>> findBest(Level level, ClayWorkTableAction action, ItemStack input, ItemStack tool) {
        ClayWorkTableRecipeInput recipeInput = new ClayWorkTableRecipeInput(action, input, tool);
        return recipes(level).stream()
                .filter(holder -> holder.value().matches(recipeInput, level))
                .max(Comparator.comparingInt(holder -> holder.value().inputCount()));
    }

    public static boolean isKnownInput(Level level, ItemStack stack) {
        return recipes(level).stream().anyMatch(holder -> holder.value().input().test(stack));
    }

    private static List<RecipeHolder<ClayWorkTableRecipe>> collectWorkTableRecipes(Iterable<RecipeHolder<?>> recipes) {
        return stream(recipes)
                .filter(holder -> holder.value() instanceof ClayWorkTableRecipe)
                .map(ClayWorkTableRecipeCache::cast)
                .toList();
    }

    private static java.util.stream.Stream<RecipeHolder<?>> stream(Iterable<RecipeHolder<?>> recipes) {
        return java.util.stream.StreamSupport.stream(recipes.spliterator(), false);
    }

    @SuppressWarnings("unchecked")
    private static RecipeHolder<ClayWorkTableRecipe> cast(RecipeHolder<?> holder) {
        return (RecipeHolder<ClayWorkTableRecipe>) holder;
    }
}

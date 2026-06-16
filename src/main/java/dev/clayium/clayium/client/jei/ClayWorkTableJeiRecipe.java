package dev.clayium.clayium.client.jei;

import dev.clayium.clayium.recipe.ClayWorkTableRecipe;
import dev.clayium.clayium.recipe.ClayWorkTableRecipeCache;
import dev.clayium.clayium.recipe.ClayWorkTableToolRequirement;
import java.util.Comparator;
import java.util.List;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;

record ClayWorkTableJeiRecipe(
        Identifier id,
        List<ItemStack> inputs,
        List<ItemStack> tools,
        List<ItemStack> outputs,
        int buttonId,
        int workTicks
) {
    static List<ClayWorkTableJeiRecipe> all() {
        return ClayWorkTableRecipeCache.clientRecipes().stream()
                .sorted(Comparator
                        .comparingInt((RecipeHolder<ClayWorkTableRecipe> holder) -> holder.value().action().buttonId())
                        .thenComparing(holder -> holder.id().identifier().toString()))
                .map(ClayWorkTableJeiRecipe::from)
                .toList();
    }

    private static ClayWorkTableJeiRecipe from(RecipeHolder<ClayWorkTableRecipe> holder) {
        ClayWorkTableRecipe recipe = holder.value();
        return new ClayWorkTableJeiRecipe(
                holder.id().identifier(),
                inputStacks(recipe),
                toolStacks(recipe.toolRequirement()),
                recipe.createOutputs(),
                recipe.action().buttonId(),
                recipe.workTicks()
        );
    }

    ItemStack mainOutput() {
        return this.outputs.getFirst();
    }

    ItemStack byproductOutput() {
        return this.outputs.size() > 1 ? this.outputs.get(1) : ItemStack.EMPTY;
    }

    boolean hasByproductOutput() {
        return !this.byproductOutput().isEmpty();
    }

    @SuppressWarnings("deprecation")
    private static List<ItemStack> inputStacks(ClayWorkTableRecipe recipe) {
        return recipe.input().items()
                .map(holder -> new ItemStack(holder, recipe.inputCount()))
                .toList();
    }

    private static List<ItemStack> toolStacks(ClayWorkTableToolRequirement toolRequirement) {
        return toolRequirement.displayTools().stream()
                .map(item -> stack(item, 1))
                .toList();
    }

    private static ItemStack stack(ItemLike item, int count) {
        return new ItemStack(item.asItem(), count);
    }
}

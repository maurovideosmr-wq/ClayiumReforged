package dev.clayium.clayium.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ClayWorkTableRecipeInput(
        ClayWorkTableAction action,
        ItemStack inputStack,
        ItemStack toolStack
) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> this.inputStack;
            case 1 -> this.toolStack;
            default -> throw new IllegalArgumentException("No item for Clay Work Table input index " + index);
        };
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return this.inputStack.isEmpty();
    }
}

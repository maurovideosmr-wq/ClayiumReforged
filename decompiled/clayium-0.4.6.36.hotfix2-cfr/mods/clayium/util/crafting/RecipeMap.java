/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.util.crafting;

import net.minecraft.item.ItemStack;

public class RecipeMap {
    protected String recipeType = null;
    protected ItemStack[][] ingredients;
    protected ItemStack[] results;
    public static final String SHAPELESS_CRAFTING = "shapeless";

    public RecipeMap(ItemStack[] ingredients, ItemStack result, String recipeType) {
        this(RecipeMap.toSingletonArray(ingredients), RecipeMap.toSingletonArray(result), recipeType);
    }

    public RecipeMap(ItemStack[][] ingredients, ItemStack result, String recipeType) {
        this(ingredients, RecipeMap.toSingletonArray(result), recipeType);
    }

    public RecipeMap(ItemStack[] ingredients, ItemStack[] results, String recipeType) {
        this(RecipeMap.toSingletonArray(ingredients), results, recipeType);
    }

    public RecipeMap(ItemStack[][] ingredients, ItemStack[] results, String recipeType) {
        this.ingredients = ingredients;
        this.results = results;
        this.recipeType = recipeType;
    }

    private static ItemStack[][] toSingletonArray(ItemStack[] itemstacks) {
        if (itemstacks == null) {
            return null;
        }
        ItemStack[][] ret = new ItemStack[itemstacks.length][1];
        for (int i = 0; i < ret.length; ++i) {
            ret[i][0] = itemstacks[i];
        }
        return ret;
    }

    private static ItemStack[] toSingletonArray(ItemStack itemstacks) {
        return new ItemStack[]{itemstacks};
    }

    public String getRecipeType() {
        return this.recipeType;
    }

    public ItemStack[][] getIngredients() {
        return this.ingredients;
    }

    public ItemStack[] getResults() {
        return this.results;
    }
}


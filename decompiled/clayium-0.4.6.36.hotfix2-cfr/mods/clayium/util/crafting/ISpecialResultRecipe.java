/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.util.crafting;

import mods.clayium.util.crafting.RecipeMap;
import net.minecraft.item.ItemStack;

public interface ISpecialResultRecipe {
    public RecipeMap[] getUsageRecipeMap(ItemStack var1);

    public RecipeMap[] getCraftingRecipeMap(ItemStack var1);
}


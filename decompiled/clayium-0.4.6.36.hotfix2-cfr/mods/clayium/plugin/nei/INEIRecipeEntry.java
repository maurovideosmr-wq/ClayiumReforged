/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.nei.PositionedStack
 *  codechicken.nei.recipe.TemplateRecipeHandler
 *  codechicken.nei.recipe.TemplateRecipeHandler$CachedRecipe
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.plugin.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.util.List;
import net.minecraft.item.ItemStack;

public interface INEIRecipeEntry {
    public boolean matchForCraftingRecipe(ItemStack var1);

    public boolean matchForUsageRecipe(ItemStack var1);

    public void drawExtras();

    public TemplateRecipeHandler.CachedRecipe asCachedRecipe();

    public List<PositionedStack> getIngredientsToSort();

    public TemplateRecipeHandler getHandler();

    public void setHandler(TemplateRecipeHandler var1);
}


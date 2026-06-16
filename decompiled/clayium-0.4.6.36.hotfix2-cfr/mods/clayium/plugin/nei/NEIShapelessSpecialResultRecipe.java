/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.nei.NEIClientConfig
 *  codechicken.nei.recipe.ShapelessRecipeHandler
 *  codechicken.nei.recipe.ShapelessRecipeHandler$CachedShapelessRecipe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.item.crafting.IRecipe
 */
package mods.clayium.plugin.nei;

import codechicken.nei.NEIClientConfig;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import java.util.Collection;
import java.util.List;
import mods.clayium.util.crafting.ISpecialResultRecipe;
import mods.clayium.util.crafting.RecipeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class NEIShapelessSpecialResultRecipe
extends ShapelessRecipeHandler {
    public void loadCraftingRecipes(ItemStack result) {
        List allrecipes = CraftingManager.func_77594_a().func_77592_b();
        for (IRecipe irecipe : allrecipes) {
            RecipeMap[] maps;
            if (!(irecipe instanceof ISpecialResultRecipe) || (maps = ((ISpecialResultRecipe)irecipe).getCraftingRecipeMap(result)) == null) continue;
            for (RecipeMap map : maps) {
                ShapelessRecipeHandler.CachedShapelessRecipe recipe = this.toShapelessRecipe(map);
                if (recipe == null) continue;
                this.arecipes.add(recipe);
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        List allrecipes = CraftingManager.func_77594_a().func_77592_b();
        for (IRecipe irecipe : allrecipes) {
            RecipeMap[] maps;
            if (!(irecipe instanceof ISpecialResultRecipe) || (maps = ((ISpecialResultRecipe)irecipe).getUsageRecipeMap(ingredient)) == null) continue;
            for (RecipeMap map : maps) {
                ShapelessRecipeHandler.CachedShapelessRecipe recipe = this.toShapelessRecipe(map);
                if (recipe == null) continue;
                recipe.setIngredientPermutation((Collection)recipe.ingredients, ingredient);
                this.arecipes.add(recipe);
            }
        }
    }

    public ShapelessRecipeHandler.CachedShapelessRecipe toShapelessRecipe(RecipeMap map) {
        if (map == null || !"shapeless".equals(map.getRecipeType()) || map.getResults() == null || map.getResults().length == 0) {
            return null;
        }
        try {
            return new ShapelessRecipeHandler.CachedShapelessRecipe((ShapelessRecipeHandler)this, (Object[])map.getIngredients(), map.getResults()[0]);
        }
        catch (Exception e) {
            NEIClientConfig.logger.error("Error loading recipe: ", (Throwable)e);
            return null;
        }
    }
}


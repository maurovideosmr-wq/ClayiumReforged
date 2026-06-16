/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.FurnaceRecipes
 */
package mods.clayium.util.crafting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mods.clayium.util.crafting.Recipes;
import mods.clayium.util.crafting.SimpleMachinesRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class SmeltingRecipe
extends SimpleMachinesRecipes {
    protected static int methodSmelting = 0;
    protected static int tierSmelting = 0;
    public static int energySmelting = 4;
    public static int timeSmelting = 200;

    public SmeltingRecipe(String recipeid) {
        super(recipeid);
    }

    @Override
    @Deprecated
    protected Map getMaterialMap(List<ItemStack> materials, int method, int tier) {
        if (method == methodSmelting && tier >= tierSmelting && materials != null && materials.size() == 1 && FurnaceRecipes.func_77602_a().func_151395_a(materials.get(0)) != null) {
            HashMap<String, Object> keyMap = new HashMap<String, Object>();
            keyMap.put("Material", materials);
            keyMap.put("Method", methodSmelting);
            keyMap.put("Tier", tierSmelting);
            return keyMap;
        }
        Map.Entry entry = this.getResultEntry(materials, method, tier);
        if (entry == null) {
            return null;
        }
        return (Map)entry.getKey();
    }

    @Override
    @Deprecated
    protected Map getResultMap(List<ItemStack> materials, int method, int tier) {
        if (method == methodSmelting && tier >= tierSmelting && materials != null && materials.size() == 1 && FurnaceRecipes.func_77602_a().func_151395_a(materials.get(0)) != null) {
            ItemStack result = FurnaceRecipes.func_77602_a().func_151395_a(materials.get(0));
            HashMap<String, Object> valueMap = new HashMap<String, Object>();
            valueMap.put("Result", SmeltingRecipe.Array2ArrayList(new ItemStack[]{result}));
            valueMap.put("Energy", energySmelting);
            valueMap.put("Time", timeSmelting);
            return valueMap;
        }
        Map.Entry entry = this.getResultEntry(materials, method, tier);
        if (entry == null) {
            return null;
        }
        return (Map)entry.getValue();
    }

    @Override
    @Deprecated
    public boolean hasResult(List<ItemStack> materials, int tier) {
        if (tier >= tierSmelting && materials != null && materials.size() == 1 && FurnaceRecipes.func_77602_a().func_151395_a(materials.get(0)) != null) {
            return true;
        }
        return super.hasResult(materials, tier);
    }

    @Override
    public boolean isCraftable(ItemStack material, int tier) {
        if (tier >= tierSmelting && FurnaceRecipes.func_77602_a().func_151395_a(material) != null) {
            return true;
        }
        return super.isCraftable(material, tier);
    }

    @Override
    public Recipes.RecipeCondition getRecipeConditionFromRecipe(Object[] materials, int method, int tier, boolean sizeCheck) {
        if (materials == null) {
            return null;
        }
        if (method == methodSmelting && tier >= tierSmelting && materials.length == 1 && materials[0] instanceof ItemStack && FurnaceRecipes.func_77602_a().func_151395_a((ItemStack)materials[0]) != null) {
            ItemStack ingred = ((ItemStack)materials[0]).func_77946_l();
            ingred.field_77994_a = 1;
            return new Recipes.RecipeCondition(new ItemStack[]{ingred}, methodSmelting, tierSmelting);
        }
        return super.getRecipeConditionFromRecipe(materials, method, tier, sizeCheck);
    }

    @Override
    public Recipes.RecipeCondition getRecipeConditionFromInventory(ItemStack[] materials, int method, int tier, boolean sizeCheck) {
        if (materials == null) {
            return null;
        }
        if (method == methodSmelting && tier >= tierSmelting && materials.length == 1 && FurnaceRecipes.func_77602_a().func_151395_a(materials[0]) != null) {
            ItemStack ingred = materials[0].func_77946_l();
            ingred.field_77994_a = 1;
            return new Recipes.RecipeCondition(new ItemStack[]{ingred}, methodSmelting, tierSmelting);
        }
        return super.getRecipeConditionFromInventory(materials, method, tier, sizeCheck);
    }

    @Override
    public Recipes.RecipeResult getRecipeResult(Recipes.RecipeCondition condition) {
        ItemStack result;
        if (condition == null) {
            return null;
        }
        if (condition.getMethod() == methodSmelting && condition.getTier() >= tierSmelting && condition.getMaterials() != null && condition.getMaterials().length == 1 && condition.getMaterials()[0] instanceof ItemStack && (result = FurnaceRecipes.func_77602_a().func_151395_a((ItemStack)condition.getMaterials()[0])) != null) {
            return new Recipes.RecipeResult(new ItemStack[]{result}, energySmelting, timeSmelting);
        }
        return super.getRecipeResult(condition);
    }

    @Override
    public int[] getConsumedStackSize(ItemStack[] materials, int method, int tier) {
        if (method == methodSmelting && tier >= tierSmelting && materials != null && materials.length == 1 && FurnaceRecipes.func_77602_a().func_151395_a(materials[0]) != null) {
            return new int[]{1};
        }
        return super.getConsumedStackSize(materials, method, tier);
    }
}


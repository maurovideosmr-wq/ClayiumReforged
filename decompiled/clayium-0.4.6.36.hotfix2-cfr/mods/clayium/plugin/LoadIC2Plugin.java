/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.Optional$Method
 *  ic2.api.recipe.IMachineRecipeManager
 *  ic2.api.recipe.IRecipeInput
 *  ic2.api.recipe.RecipeInputItemStack
 *  ic2.api.recipe.RecipeInputOreDict
 *  ic2.api.recipe.RecipeOutput
 *  ic2.api.recipe.Recipes
 *  ic2.core.Ic2Items
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.plugin;

import cpw.mods.fml.common.Optional;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.RecipeOutput;
import ic2.core.Ic2Items;
import java.util.HashMap;
import java.util.Map;
import mods.clayium.item.CItems;
import mods.clayium.item.CMaterials;
import mods.clayium.plugin.FilterIC2CropHarvestable;
import mods.clayium.util.crafting.CRecipes;
import mods.clayium.util.crafting.OreDictionaryStack;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LoadIC2Plugin {
    @Optional.Method(modid="IC2")
    public static void loadRecipes() {
        CRecipes.register1to1Recipe(CRecipes.recipeCondenser, CMaterials.getOD(CMaterials.IRIDIUM, CMaterials.INGOT), 5, Ic2Items.iridiumOre, 60L);
        CRecipes.register1to1Recipe(CRecipes.recipeCondenser, CMaterials.getOD(CMaterials.URANIUM, CMaterials.INGOT), 5, Ic2Items.Uran238, 60L);
        CRecipes.register1to1Recipe(CRecipes.recipeCondenser, CMaterials.getOD(CMaterials.PLUTONIUM, CMaterials.INGOT), 5, Ic2Items.Plutonium, 60L);
        CRecipes.register1to1Recipe(CRecipes.recipeSmelter, Ic2Items.iridiumOre, 5, CMaterials.getODExist(CMaterials.IRIDIUM, CMaterials.INGOT), 60L);
        CRecipes.register1to1Recipe(CRecipes.recipeSmelter, Ic2Items.Uran238, 5, CMaterials.getODExist(CMaterials.URANIUM, CMaterials.INGOT), 60L);
        CRecipes.register1to1Recipe(CRecipes.recipeSmelter, Ic2Items.Plutonium, 5, CMaterials.getODExist(CMaterials.PLUTONIUM, CMaterials.INGOT), 60L);
        if (!CMaterials.existOD("itemRawRubber")) {
            CRecipes.recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150364_r), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(Ic2Items.resin), CRecipes.e(2.0, 10), 60L);
        }
        HashMap<IMachineRecipeManager, CRecipes.RecipeConditions> recipeMap = new HashMap<IMachineRecipeManager, CRecipes.RecipeConditions>();
        recipeMap.put(ic2.api.recipe.Recipes.metalformerExtruding, new CRecipes.RecipeConditions(CRecipes.recipeWireDrawingMachine, 4, 20L));
        recipeMap.put(ic2.api.recipe.Recipes.metalformerRolling, new CRecipes.RecipeConditions(CRecipes.recipePipeDrawingMachine, 4, 20L));
        recipeMap.put(ic2.api.recipe.Recipes.metalformerCutting, new CRecipes.RecipeConditions(CRecipes.recipeCuttingMachine, 4, 20L));
        recipeMap.put(ic2.api.recipe.Recipes.compressor, new CRecipes.RecipeConditions(CRecipes.recipeCondenser, 5, 20L));
        recipeMap.put(ic2.api.recipe.Recipes.macerator, new CRecipes.RecipeConditions(CRecipes.recipeGrinder, 6, 20L));
        recipeMap.put(ic2.api.recipe.Recipes.extractor, new CRecipes.RecipeConditions(CRecipes.recipeTransformer, 7, 20L));
        for (IMachineRecipeManager ic2recipes : recipeMap.keySet()) {
            Recipes crecipes = ((CRecipes.RecipeConditions)recipeMap.get((Object)ic2recipes)).recipes;
            int tier = ((CRecipes.RecipeConditions)recipeMap.get((Object)ic2recipes)).tier;
            long time = ((CRecipes.RecipeConditions)recipeMap.get((Object)ic2recipes)).time;
            for (Map.Entry entry : ic2recipes.getRecipes().entrySet()) {
                IRecipeInput input = (IRecipeInput)entry.getKey();
                RecipeOutput output = (RecipeOutput)entry.getValue();
                if (input instanceof RecipeInputItemStack) {
                    CRecipes.register1to1Recipe(crecipes, CRecipes.s(((RecipeInputItemStack)input).input.func_77946_l(), ((RecipeInputItemStack)input).amount), tier, (ItemStack)output.items.get(0), time);
                    continue;
                }
                if (!(input instanceof RecipeInputOreDict)) continue;
                CRecipes.register1to1Recipe(crecipes, new OreDictionaryStack(((RecipeInputOreDict)input).input, ((RecipeInputOreDict)input).amount), tier, (ItemStack)output.items.get(0), time);
            }
        }
        CItems.itemFilterBlockHarvestable.addSpecialFilter(new FilterIC2CropHarvestable());
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.nei.recipe.TemplateRecipeHandler
 *  codechicken.nei.recipe.TemplateRecipeHandler$CachedRecipe
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.plugin.nei;

import codechicken.nei.recipe.TemplateRecipeHandler;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import mods.clayium.block.tile.TileChemicalMetalSeparator;
import mods.clayium.item.CMaterials;
import mods.clayium.plugin.nei.INEIRecipeEntry;
import mods.clayium.plugin.nei.NEIClayMachines;
import mods.clayium.util.crafting.WeightedList;
import mods.clayium.util.crafting.WeightedResult;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class NEIMetalSeparator
extends NEIClayMachines {
    private Comparator<TemplateRecipeHandler.CachedRecipe> comp = new NEIRecipeComparator();

    public NEIMetalSeparator() {
        super("ChemicalMetalSeparator");
    }

    @Override
    public Comparator<TemplateRecipeHandler.CachedRecipe> getComparator() {
        return this.comp;
    }

    @Override
    public Iterable<INEIRecipeEntry> getMatchedSet() {
        ArrayList<INEIRecipeEntry> list = new ArrayList<INEIRecipeEntry>();
        WeightedList<ItemStack> recipeResults = TileChemicalMetalSeparator.results;
        for (int i = 0; i < recipeResults.size(); ++i) {
            list.add(this.createRecipeCacher(recipeResults, i));
        }
        return list;
    }

    public List<Object> getMaterialList(WeightedList<ItemStack> recipeResults, int index) {
        ArrayList<Object> materiallist = new ArrayList<Object>();
        materiallist.add(CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST));
        return materiallist;
    }

    public List<ItemStack> getResultList(WeightedList<ItemStack> recipeResults, int index) {
        ArrayList<ItemStack> resultlist = new ArrayList<ItemStack>();
        resultlist.add((ItemStack)((WeightedResult)recipeResults.get((int)index)).result);
        return resultlist;
    }

    public NEIRecipeEntryMetalSeparator createRecipeCacher(WeightedList<ItemStack> recipeResults, int index) {
        List<Object> materiallist = this.getMaterialList(recipeResults, index);
        List<ItemStack> resultlist = this.getResultList(recipeResults, index);
        return new NEIRecipeEntryMetalSeparator(materiallist, 0, 6, resultlist, TileChemicalMetalSeparator.baseConsumingEnergy, TileChemicalMetalSeparator.baseCraftTime, (double)((WeightedResult)recipeResults.get((int)index)).weight / (double)recipeResults.getWeightSum());
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return new NEIMetalSeparator();
    }

    public class NEIRecipeComparator
    extends NEIClayMachines.NEIRecipeComparator {
        @Override
        public int compare(TemplateRecipeHandler.CachedRecipe a1, TemplateRecipeHandler.CachedRecipe b1) {
            if (!(a1 instanceof NEIRecipeEntryMetalSeparator) || !(b1 instanceof NEIRecipeEntryMetalSeparator)) {
                return 0;
            }
            NEIRecipeEntryMetalSeparator a = (NEIRecipeEntryMetalSeparator)a1;
            NEIRecipeEntryMetalSeparator b = (NEIRecipeEntryMetalSeparator)b1;
            if (a.probability < b.probability) {
                return 1;
            }
            if (b.probability < a.probability) {
                return -1;
            }
            return super.compare(a1, b1);
        }
    }

    public class NEIRecipeEntryMetalSeparator
    extends NEIClayMachines.NEIRecipeEntry {
        public double probability;

        public NEIRecipeEntryMetalSeparator(Object[] inputs, int method, int tier, ItemStack[] results, int energy, int time, double probability) {
            super(inputs, method, tier, results, (long)energy, (long)time);
            this.probability = probability;
        }

        public NEIRecipeEntryMetalSeparator(List<Object> inputs, int method, int tier, List<ItemStack> results, int energy, int time, double probability) {
            super(inputs, method, tier, results, (long)energy, (long)time);
            this.probability = probability;
        }

        @Override
        public void drawExtras() {
            super.drawExtras();
            Minecraft.func_71410_x().field_71466_p.func_78276_b(String.format("%3.2f%%", this.probability * 100.0), 69, 10, -16777216);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.item.crafting.ShapedRecipes
 *  net.minecraft.item.crafting.ShapelessRecipes
 *  net.minecraft.world.World
 *  net.minecraftforge.oredict.ShapedOreRecipe
 *  net.minecraftforge.oredict.ShapelessOreRecipe
 */
package mods.clayium.pan;

import java.util.List;
import mods.clayium.block.tile.InventoryCraftingInTile;
import mods.clayium.pan.IPANAdapter;
import mods.clayium.pan.IPANAdapterConversionFactory;
import mods.clayium.pan.IPANConversion;
import mods.clayium.pan.PANConversion;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.ItemPatternItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class PANACFactoryCraftingTable
implements IPANAdapterConversionFactory {
    public static long energy = 10L;

    @Override
    public boolean match(World world, int x, int y, int z) {
        return world.func_147439_a(x, y, z) == Blocks.field_150462_ai;
    }

    @Override
    public IPANConversion getConversion(IPANAdapter adapter) {
        boolean flag = true;
        for (ItemStack item : adapter.getPatternItems()) {
            if (item == null) continue;
            flag = false;
            break;
        }
        if (flag) {
            return null;
        }
        List recipes = CraftingManager.func_77594_a().func_77592_b();
        for (Object recipe : recipes) {
            ShapedRecipes recipe1;
            if (!(recipe instanceof IRecipe) || !((IRecipe)recipe).func_77569_a((InventoryCrafting)InventoryCraftingInTile.getNormalCraftingGrid(adapter.getPatternItems()), adapter.getConnectedWorld())) continue;
            if (recipe instanceof ShapedRecipes) {
                recipe1 = (ShapedRecipes)recipe;
                IItemPattern[] patterns = new IItemPattern[recipe1.field_77574_d.length];
                for (int i = 0; i < recipe1.field_77574_d.length; ++i) {
                    patterns[i] = new ItemPatternItemStack(recipe1.field_77574_d[i]);
                }
                return new PANConversion(patterns, new ItemStack[]{recipe1.func_77571_b().func_77946_l()}, energy);
            }
            if (recipe instanceof ShapelessRecipes) {
                recipe1 = (ShapelessRecipes)recipe;
                IItemPattern[] patterns = new IItemPattern[recipe1.field_77579_b.size()];
                for (int i = 0; i < recipe1.field_77579_b.size(); ++i) {
                    patterns[i] = new ItemPatternItemStack((ItemStack)recipe1.field_77579_b.get(i));
                }
                return new PANConversion(patterns, new ItemStack[]{recipe1.func_77571_b().func_77946_l()}, energy);
            }
            if (recipe instanceof ShapedOreRecipe) {
                recipe1 = (ShapedOreRecipe)recipe;
                IItemPattern[] patterns = new IItemPattern[recipe1.getInput().length];
                for (int i = 0; i < recipe1.getInput().length; ++i) {
                    if (recipe1.getInput()[i] instanceof ItemStack) {
                        patterns[i] = new ItemPatternItemStack((ItemStack)recipe1.getInput()[i]);
                    }
                    if (!(recipe1.getInput()[i] instanceof List)) continue;
                    patterns[i] = new ItemPatternItemStack((List)recipe1.getInput()[i]);
                }
                return new PANConversion(patterns, new ItemStack[]{recipe1.func_77571_b().func_77946_l()}, energy);
            }
            if (!(recipe instanceof ShapelessOreRecipe)) continue;
            recipe1 = (ShapelessOreRecipe)recipe;
            IItemPattern[] patterns = new IItemPattern[recipe1.getInput().size()];
            for (int i = 0; i < recipe1.getInput().size(); ++i) {
                if (recipe1.getInput().get(i) instanceof ItemStack) {
                    patterns[i] = new ItemPatternItemStack((ItemStack)recipe1.getInput().get(i));
                }
                if (!(recipe1.getInput().get(i) instanceof List)) continue;
                patterns[i] = new ItemPatternItemStack((List)recipe1.getInput().get(i));
            }
            return new PANConversion(patterns, new ItemStack[]{recipe1.func_77571_b().func_77946_l()}, energy);
        }
        return null;
    }
}


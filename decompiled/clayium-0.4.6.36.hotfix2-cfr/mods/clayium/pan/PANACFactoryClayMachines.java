/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.pan;

import java.util.ArrayList;
import java.util.Arrays;
import mods.clayium.block.ClayEnergyLaser;
import mods.clayium.block.laser.ClayLaser;
import mods.clayium.block.tile.TileCAInjector;
import mods.clayium.block.tile.TileCAReactor;
import mods.clayium.block.tile.TileClayEnergyLaser;
import mods.clayium.block.tile.TileClayMachines;
import mods.clayium.block.tile.TileClayReactor;
import mods.clayium.pan.IPANAdapter;
import mods.clayium.pan.IPANAdapterConversionFactory;
import mods.clayium.pan.IPANConversion;
import mods.clayium.pan.PANConversion;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PANACFactoryClayMachines
implements IPANAdapterConversionFactory {
    @Override
    public boolean match(World world, int x, int y, int z) {
        return UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z) instanceof TileClayMachines;
    }

    @Override
    public IPANConversion getConversion(IPANAdapter adapter) {
        Recipes recipe;
        boolean flag = true;
        for (ItemStack itemStack : adapter.getPatternItems()) {
            if (itemStack == null) continue;
            flag = false;
            break;
        }
        if (flag) {
            return null;
        }
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)adapter.getConnectedWorld(), adapter.getConnectedXCoord(), adapter.getConnectedYCoord(), adapter.getConnectedZCoord());
        if (te instanceof TileClayMachines && (recipe = Recipes.GetRecipes(((TileClayMachines)te).getRecipeId())) != null) {
            Recipes.RecipeResult result;
            ArrayList<ItemStack> ingred = new ArrayList<ItemStack>();
            for (ItemStack itemStack : adapter.getPatternItems()) {
                if (itemStack == null) continue;
                ingred.add(itemStack);
            }
            Recipes.RecipeCondition recipeCondition = recipe.getRecipeConditionFromInventory(ingred.toArray(new ItemStack[0]), 0, ((TileClayMachines)te).getRecipeTier());
            Recipes.RecipeResult recipeResult = result = recipeCondition != null ? recipe.getRecipeResult(recipeCondition) : null;
            if (result != null) {
                ArrayList<IItemPattern> patterns = new ArrayList<IItemPattern>();
                for (Object material : recipeCondition.getMaterials()) {
                    patterns.add(Recipes.convert(material));
                }
                ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(Arrays.asList(result.getResults()));
                long energy = (long)((float)result.getEnergy() * ((TileClayMachines)te).multConsumingEnergy);
                long time = (long)((float)result.getTime() * ((TileClayMachines)te).multCraftTime);
                if (te instanceof TileClayReactor) {
                    int b = 0;
                    int g = 0;
                    int r = 0;
                    block8: for (ItemStack item : adapter.getSubItems()) {
                        Block block;
                        if (item == null || !(item.func_77973_b() instanceof ItemBlock) || !((block = ((ItemBlock)item.func_77973_b()).field_150939_a) instanceof ClayEnergyLaser)) continue;
                        switch (((ClayEnergyLaser)block).getTier(item)) {
                            case 7: {
                                b += item.field_77994_a;
                                continue block8;
                            }
                            case 8: {
                                g += item.field_77994_a;
                                continue block8;
                            }
                            case 9: {
                                r += item.field_77994_a;
                            }
                        }
                    }
                    long le = (long)(ClayLaser.getEnergy(b, g, r) + 1.0);
                    if (--time <= 0L) {
                        time = 1L;
                    }
                    time = time / le + 1L;
                    energy += (long)(TileClayEnergyLaser.consumingEnergyBlue * b);
                    energy += (long)(TileClayEnergyLaser.consumingEnergyGreen * g);
                    energy += (long)(TileClayEnergyLaser.consumingEnergyRed * r);
                }
                if (te instanceof TileCAReactor) {
                    if (((TileCAReactor)te).isConstructed() && ((TileCAReactor)te).getResultPureAntimatter() != null) {
                        energy = (long)((double)energy * ((TileCAReactor)te).getConsumingEnergyTotalMultiplier());
                        time = (long)((double)time * ((TileCAReactor)te).getCraftTimeTotalMultiplier());
                        arrayList.clear();
                        arrayList.add(((TileCAReactor)te).getResultPureAntimatter());
                    } else {
                        arrayList.clear();
                    }
                }
                if (te instanceof TileCAInjector) {
                    time = (long)((double)time * ((TileCAInjector)te).getCraftTimeMultiplier());
                }
                if (time <= 0L) {
                    time = 1L;
                }
                if (arrayList.size() > 0) {
                    return new PANConversion(patterns.toArray(new IItemPattern[0]), arrayList.toArray(new ItemStack[0]), (double)energy * (double)time);
                }
            }
        }
        return null;
    }
}


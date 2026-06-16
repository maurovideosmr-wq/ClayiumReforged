/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.FurnaceRecipes
 *  net.minecraft.world.World
 */
package mods.clayium.pan;

import java.util.Iterator;
import java.util.Map;
import mods.clayium.pan.IPANAdapter;
import mods.clayium.pan.IPANAdapterConversionFactory;
import mods.clayium.pan.IPANConversion;
import mods.clayium.pan.PANConversion;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.ItemPatternItemStack;
import mods.clayium.util.crafting.SmeltingRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

public class PANACFactoryFurnace
implements IPANAdapterConversionFactory {
    @Override
    public boolean match(World world, int x, int y, int z) {
        return world.func_147439_a(x, y, z) == Blocks.field_150460_al;
    }

    @Override
    public IPANConversion getConversion(IPANAdapter adapter) {
        Map.Entry entry;
        ItemStack ingred = null;
        for (ItemStack p : adapter.getPatternItems()) {
            if (p == null) continue;
            if (ingred != null) {
                return null;
            }
            ingred = p.func_77946_l();
        }
        if (ingred == null) {
            return null;
        }
        Iterator iterator = FurnaceRecipes.func_77602_a().func_77599_b().entrySet().iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while (!this.func_151397_a(ingred, (ItemStack)(entry = iterator.next()).getKey()));
        ingred = (ItemStack)entry.getKey();
        ItemStack result = (ItemStack)entry.getValue();
        return new PANConversion(new IItemPattern[]{new ItemPatternItemStack(ingred)}, new ItemStack[]{result}, SmeltingRecipe.timeSmelting * SmeltingRecipe.energySmelting);
    }

    private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_) {
        return p_151397_2_.func_77973_b() == p_151397_1_.func_77973_b() && (p_151397_2_.func_77960_j() == Short.MAX_VALUE || p_151397_2_.func_77960_j() == p_151397_1_.func_77960_j());
    }
}


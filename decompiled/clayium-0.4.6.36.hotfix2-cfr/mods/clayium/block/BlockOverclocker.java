/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block;

import java.util.List;
import mods.clayium.block.BlockDamaged;
import mods.clayium.block.IOverclocker;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class BlockOverclocker
extends BlockDamaged
implements IOverclocker {
    public BlockOverclocker(Material material) {
        super(material);
    }

    public BlockOverclocker() {
        this(Material.field_151576_e);
    }

    public BlockDamaged addOverclockFactor(double resonance) {
        return this.putInfo("OverclockFactor", new Double(resonance));
    }

    public double getOverclockFactor(String blockname) {
        Object obj = this.getInfo(blockname, "OverclockFactor");
        return obj instanceof Double ? (Double)obj : 1.0;
    }

    @Override
    public double getOverclockFactor(IBlockAccess world, int x, int y, int z) {
        return this.getOverclockFactor(this.getBlockName(world, x, y, z));
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        List<String> ret = UtilLocale.localizeTooltip("tooltip.Overclocker");
        ret.addAll(super.getTooltip(itemStack));
        if (UtilLocale.canLocalize("tooltip.Overclocker.overclockFactor")) {
            ret.add(UtilLocale.localizeAndFormat("tooltip.Overclocker.overclockFactor", this.getOverclockFactor(this.getBlockName(itemStack.func_77960_j()))));
        }
        return ret;
    }
}


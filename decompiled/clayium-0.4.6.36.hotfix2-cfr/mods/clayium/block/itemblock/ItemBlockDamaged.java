/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 */
package mods.clayium.block.itemblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.itemblock.ItemBlockTiered;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockDamaged
extends ItemBlockTiered {
    public ItemBlockDamaged(Block block) {
        super(block);
        this.func_77656_e(0);
        this.func_77627_a(true);
    }

    @Override
    public String getDefaultUnlocalizedName(ItemStack itemStack) {
        return this.func_77658_a() + "." + itemStack.func_77960_j();
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon func_77617_a(int p_77617_1_) {
        return this.field_150939_a.func_149691_a(2, p_77617_1_);
    }

    public int func_77647_b(int p_77647_1_) {
        return p_77647_1_;
    }
}


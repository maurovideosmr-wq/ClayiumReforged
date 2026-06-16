/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.common.ForgeHooks
 */
package mods.clayium.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.block.CBlocks;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class ClayPickaxe
extends ItemPickaxe {
    private float efficiencyOnClayOre = 32.0f;

    public ClayPickaxe() {
        super(Item.ToolMaterial.STONE);
        this.func_77656_e(500);
        this.func_77637_a(ClayiumCore.creativeTabClayium);
        this.func_77655_b("itemClayPickaxe");
        this.func_111206_d("clayium:claypickaxe");
    }

    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (block == CBlocks.blockClayOre) {
            return block.getHarvestLevel(meta) <= stack.func_77973_b().getHarvestLevel(stack, block.getHarvestTool(meta)) ? this.efficiencyOnClayOre : this.efficiencyOnClayOre * 100.0f / 30.0f;
        }
        if (ForgeHooks.isToolEffective((ItemStack)stack, (Block)block, (int)meta)) {
            return this.field_77864_a;
        }
        return super.getDigSpeed(stack, block, meta);
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
        super.func_77624_a(itemstack, player, list, flag);
        List<String> alist = UtilLocale.localizeTooltip(this.func_77667_c(itemstack) + ".tooltip");
        if (alist != null) {
            list.addAll(alist);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemSpade
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package mods.clayium.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.HarvestCoordClaySteelTools;
import mods.clayium.item.IAdvancedTool;
import mods.clayium.item.IHarvestCoord;
import mods.clayium.util.UtilAdvancedTools;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ClaySteelShovel
extends ItemSpade
implements IAdvancedTool {
    private IHarvestCoord harvestCoord;

    public ClaySteelShovel() {
        super(Item.ToolMaterial.EMERALD);
        this.func_77656_e(10000);
        this.func_77637_a(ClayiumCore.creativeTabClayium);
        this.func_77655_b("itemClaySteelShovel");
        this.func_111206_d("clayium:claysteelshovel");
        this.harvestCoord = new HarvestCoordClaySteelTools(ClayiumCore.cfgClaySteelPickaxeRange);
    }

    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return super.getDigSpeed(stack, block, meta) * 6.0f;
    }

    @Override
    public IHarvestCoord getHarvestCoord() {
        return this.harvestCoord;
    }

    public boolean func_150894_a(ItemStack itemstack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
        boolean res = super.func_150894_a(itemstack, world, block, x, y, z, entity);
        itemstack.func_77972_a(UtilAdvancedTools.onBlockDestroyed(itemstack, world, block, x, y, z, entity), entity);
        return res;
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        return this.getHarvestCoord().onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
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


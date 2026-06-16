/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block.itemblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.block.ISpecialToolTip;
import mods.clayium.block.ISpecialUnlocalizedName;
import mods.clayium.block.ITieredBlock;
import mods.clayium.block.itemblock.IOverridableBlock;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.ITieredItem;
import mods.clayium.item.ItemTiered;
import mods.clayium.plugin.multipart.UtilMultipart;
import mods.clayium.util.PlayerKey;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilKeyInput;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemBlockTiered
extends ItemBlock
implements ITieredItem {
    public ItemBlockTiered(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    public String func_77667_c(ItemStack itemStack) {
        if (this.field_150939_a instanceof ISpecialUnlocalizedName) {
            return ((ISpecialUnlocalizedName)this.field_150939_a).getUnlocalizedName(itemStack);
        }
        return this.getDefaultUnlocalizedName(itemStack);
    }

    public String getDefaultUnlocalizedName(ItemStack itemStack) {
        return this.func_77658_a();
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
        List alist;
        if (this.getTier(itemstack) >= 0) {
            list.add(ItemTiered.getTieredToolTip(this.getTier(itemstack)));
        }
        if (this.field_150939_a instanceof ISpecialToolTip && (alist = ((ISpecialToolTip)this.field_150939_a).getTooltip(itemstack)) != null) {
            list.addAll(alist);
        }
    }

    public EnumRarity func_77613_e(ItemStack itemstack) {
        switch (this.getTier(itemstack)) {
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                return EnumRarity.uncommon;
            }
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                return EnumRarity.rare;
            }
            case 12: 
            case 13: 
            case 14: 
            case 15: {
                return EnumRarity.epic;
            }
        }
        return EnumRarity.common;
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return this.field_150939_a instanceof ITieredBlock ? ((ITieredBlock)this.field_150939_a).getTier(itemstack) : -1;
    }

    public boolean place(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        return super.func_77648_a(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        return UtilKeyInput.getKeyLength(p_77648_2_, PlayerKey.KeyType.SPRINT) >= 0 && this.replace(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_) || ClayiumCore.IntegrationID.MULTI_PART.loaded() && UtilMultipart.onItemBlockMultipartUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_) || this.place(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
    }

    public boolean replace(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.func_147439_a(x, y, z);
        int meta = world.func_72805_g(x, y, z);
        NBTTagCompound tileTag = null;
        Class<?> tileClass = null;
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te != null) {
            tileTag = new NBTTagCompound();
            te.func_145841_b(tileTag);
            tileClass = te.getClass();
        }
        if (!(block instanceof IOverridableBlock) || !((IOverridableBlock)block).canOverride(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ)) {
            return false;
        }
        ((IOverridableBlock)block).onOverridden(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
        UtilBuilder.harvestBlockAndDropItems(world, x, y, z, true, false, 0);
        if (this.place(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ)) {
            Block placedBlock = world.func_147439_a(x, y, z);
            if (placedBlock instanceof IOverridableBlock) {
                ((IOverridableBlock)placedBlock).overrideTo(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ, block, meta, tileClass, tileTag);
            }
            return true;
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean func_150936_a(World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, EntityPlayer p_150936_6_, ItemStack p_150936_7_) {
        return UtilKeyInput.getKeyLength(p_150936_6_, PlayerKey.KeyType.SPRINT) >= 0 || this.canPlaceInClient(p_150936_1_, p_150936_2_, p_150936_3_, p_150936_4_, p_150936_5_, p_150936_6_, p_150936_7_);
    }

    @SideOnly(value=Side.CLIENT)
    public boolean canPlaceInClient(World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, EntityPlayer p_150936_6_, ItemStack p_150936_7_) {
        if (ClayiumCore.IntegrationID.MULTI_PART.loaded() && UtilMultipart.placeMultipart(p_150936_1_, p_150936_2_, p_150936_3_, p_150936_4_, p_150936_7_, p_150936_5_, false)) {
            return true;
        }
        int[] coord = UtilBuilder.coordTransformOnPlaceBlock(p_150936_1_, p_150936_2_, p_150936_3_, p_150936_4_, p_150936_5_);
        p_150936_2_ = coord[0];
        p_150936_3_ = coord[1];
        p_150936_4_ = coord[2];
        p_150936_5_ = coord[3];
        return ClayiumCore.IntegrationID.MULTI_PART.loaded() && UtilMultipart.placeMultipart(p_150936_1_, p_150936_2_, p_150936_3_, p_150936_4_, p_150936_7_, p_150936_5_, false) || p_150936_1_.func_147472_a(this.field_150939_a, p_150936_2_, p_150936_3_, p_150936_4_, false, p_150936_5_, (Entity)null, p_150936_7_);
    }
}


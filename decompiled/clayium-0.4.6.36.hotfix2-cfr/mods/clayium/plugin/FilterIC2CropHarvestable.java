/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.crops.ICropTile
 *  ic2.core.crop.BlockCrop
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.plugin;

import ic2.api.crops.ICropTile;
import ic2.core.crop.BlockCrop;
import java.util.List;
import mods.clayium.item.ISpecialFilter;
import mods.clayium.util.UtilBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FilterIC2CropHarvestable
implements ISpecialFilter {
    @Override
    public boolean filterMatch(NBTTagCompound filterTag, ItemStack itemstack, boolean lastResult) {
        return false;
    }

    @Override
    public boolean filterMatch(NBTTagCompound filterTag, World world, int x, int y, int z, boolean lastResult) {
        TileEntity te;
        if (lastResult) {
            return true;
        }
        if (world == null) {
            return false;
        }
        Block block = world.func_147439_a(x, y, z);
        if (block instanceof BlockCrop && (te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z)) instanceof ICropTile && ((ICropTile)te).getCrop() != null) {
            return !((ICropTile)te).getCrop().canGrow((ICropTile)te);
        }
        return false;
    }

    @Override
    public void addTooltip(NBTTagCompound filterTag, List list, int indent) {
    }

    @Override
    public void openGui(ItemStack itemstack, World world, EntityPlayer player) {
    }
}


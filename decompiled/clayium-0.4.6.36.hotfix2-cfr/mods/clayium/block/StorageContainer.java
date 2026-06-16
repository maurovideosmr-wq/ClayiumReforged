/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import mods.clayium.block.ClayContainer;
import mods.clayium.block.ITieredBlock;
import mods.clayium.block.tile.TileGeneric;
import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.gui.TextureExtra;
import mods.clayium.util.UtilBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StorageContainer
extends ClayContainer
implements ITieredBlock {
    @SideOnly(value=Side.CLIENT)
    public IIcon[] FrontIcons;
    public IIcon[] TopIcons;
    public IIcon[] SideIcons;

    public StorageContainer(Material material, String iconstr) {
        super(material, TileStorageContainer.class, iconstr, 1);
        this.guiId = 15;
        this.func_149672_a(Block.field_149777_j);
        this.func_149711_c(2.0f);
        this.func_149752_b(5.0f);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.FrontIcons = new IIcon[2];
        this.TopIcons = new IIcon[2];
        this.SideIcons = new IIcon[2];
        this.RightIcon = this.SideIcons[0] = new TextureExtra("clayium:storagecontainerside_", this.machineIconStr, "clayium:storagecontainerside").register(par1IconRegister);
        this.LeftIcon = this.SideIcons[0];
        this.BackIcon = this.SideIcons[0];
        this.FrontIcon = this.FrontIcons[0] = new TextureExtra("clayium:storagecontainer_", this.machineIconStr, "clayium:storagecontainer").register(par1IconRegister);
        this.DownIcon = this.TopIcons[0] = new TextureExtra("clayium:storagecontainertop_", this.machineIconStr, "clayium:storagecontainertop").register(par1IconRegister);
        this.UpIcon = this.TopIcons[0];
        this.SideIcons[1] = new TextureExtra("clayium:storagecontainerside_1", this.machineIconStr, "clayium:storagecontainerside", "clayium:storagecontainerex").register(par1IconRegister);
        this.FrontIcons[1] = new TextureExtra("clayium:storagecontainer_1", this.machineIconStr, "clayium:storagecontainer", "clayium:storagecontainerex").register(par1IconRegister);
        this.TopIcons[1] = new TextureExtra("clayium:storagecontainertop_1", this.machineIconStr, "clayium:storagecontainertop", "clayium:storagecontainerex").register(par1IconRegister);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import");
        this.registerExtractIcons(par1IconRegister, "export");
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return 6;
    }

    @Override
    public int getTier(IBlockAccess world, int x, int y, int z) {
        return 6;
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        NBTTagCompound tag;
        ArrayList<String> res = new ArrayList<String>();
        if (itemStack.func_77942_o() && (tag = itemStack.func_77978_p()).func_74764_b("TileEntityNBTTag")) {
            ItemStack item = null;
            NBTTagCompound tetag = (NBTTagCompound)tag.func_74781_a("TileEntityNBTTag");
            NBTTagList tagList = tetag.func_150295_c("Items", 10);
            for (int i = 0; i < tagList.func_74745_c(); ++i) {
                NBTTagCompound tagCompound1 = tagList.func_150305_b(i);
                byte byte0 = tagCompound1.func_74771_c("Slot");
                if (byte0 != 0) continue;
                item = ItemStack.func_77949_a((NBTTagCompound)tagCompound1);
            }
            int size = tetag.func_74762_e("ItemStackSize");
            int max = tetag.func_74762_e("MaxStorageSize");
            if (item == null) {
                size = 0;
                for (int i = 0; i < tagList.func_74745_c(); ++i) {
                    NBTTagCompound tagCompound1 = tagList.func_150305_b(i);
                    byte byte0 = tagCompound1.func_74771_c("Slot");
                    if (byte0 != 1) continue;
                    item = ItemStack.func_77949_a((NBTTagCompound)tagCompound1);
                }
            }
            if (item != null) {
                res.add(item.func_82833_r());
                res.add("" + size + "/" + max);
            }
        }
        return res;
    }

    public static ItemStack expandStorage(ItemStack itemStack, int storageSize) {
        if (itemStack != null && itemStack.func_77973_b() instanceof ItemBlock && ((ItemBlock)itemStack.func_77973_b()).field_150939_a instanceof StorageContainer) {
            NBTTagCompound tag = itemStack.func_77942_o() ? itemStack.func_77978_p() : new NBTTagCompound();
            NBTTagCompound tetag = tag.func_74764_b("TileEntityNBTTag") ? (NBTTagCompound)tag.func_74781_a("TileEntityNBTTag") : new NBTTagCompound();
            itemStack.func_77964_b(StorageContainer.storageSize2damage(storageSize) * 16);
            tetag.func_74768_a("MaxStorageSize", storageSize);
            tag.func_74782_a("TileEntityNBTTag", (NBTBase)tetag);
            itemStack.func_77982_d(tag);
        }
        return itemStack;
    }

    public static int getStorageSize(ItemStack itemStack) {
        if (itemStack == null || !(itemStack.func_77973_b() instanceof ItemBlock) || !(((ItemBlock)itemStack.func_77973_b()).field_150939_a instanceof StorageContainer)) {
            return 0;
        }
        NBTTagCompound tetag = TileGeneric.getTileEntityTag(itemStack);
        return tetag != null && tetag.func_74764_b("MaxStorageSize") ? tetag.func_74762_e("MaxStorageSize") : 65536;
    }

    public static int storageSize2damage(int storageSize) {
        int i = 0;
        while ((storageSize >>= 1) > 0) {
            ++i;
        }
        return i;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity te = UtilBuilder.safeGetTileEntity(world, x, y, z);
        if (te instanceof TileStorageContainer) {
            return this.func_149691_a(side, this.getFront(world, x, y, z) + StorageContainer.storageSize2damage(((TileStorageContainer)te).maxStorageSize) * 16);
        }
        return super.func_149673_e(world, x, y, z, side);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public IIcon getOverlayIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity te = UtilBuilder.safeGetTileEntity(world, x, y, z);
        if (te instanceof TileStorageContainer) {
            return this.getOverlayIcon(side, this.getFront(world, x, y, z) + StorageContainer.storageSize2damage(((TileStorageContainer)te).maxStorageSize) * 16);
        }
        return super.getOverlayIcon(world, x, y, z, side);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
        int i = meta / 16 < 30 ? 0 : 1;
        this.LeftIcon = this.RightIcon = this.SideIcons[i];
        this.BackIcon = this.RightIcon;
        this.FrontIcon = this.FrontIcons[i];
        this.UpIcon = this.DownIcon = this.TopIcons[i];
        return super.func_149691_a(side, meta % 16);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149666_a(Item item, CreativeTabs creativeTab, List list) {
        for (int i : new int[]{65536, Integer.MAX_VALUE}) {
            ItemStack itemStack = new ItemStack(item, 1);
            itemStack = StorageContainer.expandStorage(itemStack, i);
            list.add(itemStack);
        }
    }

    public int func_149643_k(World world, int x, int y, int z) {
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te instanceof TileStorageContainer) {
            return StorageContainer.storageSize2damage(((TileStorageContainer)te).maxStorageSize);
        }
        return super.func_149643_k(world, x, y, z);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        Item item = this.func_149694_d(world, x, y, z);
        ItemStack ret = new ItemStack(item, 1);
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te instanceof TileStorageContainer) {
            return StorageContainer.expandStorage(ret, ((TileStorageContainer)te).maxStorageSize);
        }
        return super.getPickBlock(target, world, x, y, z, player);
    }
}


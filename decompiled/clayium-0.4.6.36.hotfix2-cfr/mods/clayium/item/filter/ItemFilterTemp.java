/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.StatCollector
 *  net.minecraft.world.World
 */
package mods.clayium.item.filter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.item.ItemTiered;
import mods.clayium.item.filter.IItemFilter;
import mods.clayium.item.filter.IItemWithFilterSize;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilItemStack;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class ItemFilterTemp
extends ItemTiered
implements IItemFilter {
    public static final int maxFilterSize = 100;
    public static volatile IIcon iiconCopy = null;

    ItemFilterTemp() {
        this.func_77625_d(1);
    }

    public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        if (this.isCopy(p_77659_1_) && p_77659_3_.func_70093_af()) {
            if (!p_77659_2_.field_72995_K) {
                p_77659_3_.func_145747_a((IChatComponent)new ChatComponentText("Cleared the copy flag."));
            }
            return this.clearCopyFlag(p_77659_1_);
        }
        this.openGui(p_77659_1_, p_77659_2_, p_77659_3_);
        return super.func_77659_a(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
        super.func_77624_a(itemstack, player, list, flag);
        this.addTooltip(itemstack.field_77990_d, list, 0);
    }

    @Override
    public void addFilterInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
        this.addTooltip(itemstack.field_77990_d, list, 0);
    }

    public String func_77653_i(ItemStack itemstack) {
        String name = this.func_77657_g(itemstack);
        if (this.isCopy(itemstack)) {
            name = name + ".copy";
        }
        return StatCollector.func_74838_a((String)("" + name + ".name")).trim();
    }

    @Override
    public boolean filterMatch(NBTTagCompound filterTag, ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean filterMatch(NBTTagCompound filterTag, World world, int x, int y, int z) {
        return this.filterMatch(filterTag, UtilBuilder.getItemBlock(world, x, y, z));
    }

    @Override
    public void addTooltip(NBTTagCompound filterTag, List list, int indent) {
    }

    @Override
    public int getFilterSize(NBTTagCompound filterTag) {
        return 1;
    }

    public void openGui(ItemStack itemstack, World world, EntityPlayer player) {
    }

    @Override
    public void checkFilterSize(ItemStack filter, EntityPlayer player, World world) {
        IItemWithFilterSize filteritem;
        if (filter != null && filter.func_77973_b() instanceof IItemWithFilterSize && (filteritem = (IItemWithFilterSize)filter.func_77973_b()).getFilterSize(filter.func_77978_p()) >= 100) {
            filter.func_77982_d(new NBTTagCompound());
            if (!world.field_72995_K) {
                player.func_145747_a((IChatComponent)new ChatComponentText("The filter has broken! The filter size is too large!"));
            }
        }
    }

    @Override
    public boolean isCopy(ItemStack filter) {
        return filter.func_77960_j() == 1;
    }

    @Override
    public ItemStack setCopyFlag(ItemStack filter) {
        filter.func_77964_b(1);
        return filter;
    }

    @Override
    public ItemStack clearCopyFlag(ItemStack filter) {
        filter.func_77964_b(0);
        return filter;
    }

    public static boolean match(ItemStack filter, ItemStack itemstack) {
        if (ItemFilterTemp.isFilter(filter)) {
            return ((IItemFilter)filter.func_77973_b()).filterMatch(filter.func_77978_p(), itemstack);
        }
        return false;
    }

    public static boolean match(ItemStack filter, World world, int x, int y, int z) {
        if (ItemFilterTemp.isFilter(filter)) {
            return ((IItemFilter)filter.func_77973_b()).filterMatch(filter.func_77978_p(), world, x, y, z);
        }
        return false;
    }

    public static boolean matchBetweenItemstacks(ItemStack filter, ItemStack itemstack, boolean fuzzy) {
        if (filter == null) {
            return itemstack == null;
        }
        if (!fuzzy) {
            return itemstack != null && UtilItemStack.areTypeEqual(filter, itemstack);
        }
        if (itemstack == null) {
            return false;
        }
        if (UtilItemStack.areItemDamageEqualOrDamageable(filter, itemstack)) {
            return true;
        }
        return UtilItemStack.haveSameOD(filter, itemstack);
    }

    public static boolean isFilter(ItemStack filter) {
        return filter != null && filter.func_77973_b() instanceof IItemFilter;
    }

    @SideOnly(value=Side.CLIENT)
    public synchronized void func_94581_a(IIconRegister iicon) {
        super.func_94581_a(iicon);
        if (iiconCopy == null) {
            iiconCopy = iicon.func_94245_a("clayium:filtercopy");
        }
    }

    public int getRenderPasses(int meta) {
        return meta == 1 ? 2 : 1;
    }

    public boolean func_77623_v() {
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon func_77618_c(int meta, int pass) {
        return pass == 0 ? super.func_77617_a(meta) : iiconCopy;
    }
}


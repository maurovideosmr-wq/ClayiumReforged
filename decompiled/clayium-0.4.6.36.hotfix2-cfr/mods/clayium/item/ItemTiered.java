/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumChatFormatting
 */
package mods.clayium.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.item.ITieredItem;
import mods.clayium.util.UtilLocale;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemTiered
extends Item
implements ITieredItem {
    protected int baseTier = -1;

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
        List<String> alist;
        if (this.getTier(itemstack) >= 0) {
            list.add(ItemTiered.getTieredToolTip(this.getTier(itemstack)));
        }
        if ((alist = UtilLocale.localizeTooltip(this.func_77667_c(itemstack) + ".tooltip")) != null) {
            list.addAll(alist);
        }
    }

    public static String getTieredToolTip(int tier) {
        return EnumChatFormatting.WHITE + "Tier " + tier + EnumChatFormatting.RESET;
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

    public ItemTiered setBaseTier(int tier) {
        this.baseTier = tier;
        return this;
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return this.baseTier;
    }
}


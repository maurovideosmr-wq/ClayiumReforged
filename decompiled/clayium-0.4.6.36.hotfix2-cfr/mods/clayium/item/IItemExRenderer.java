/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 */
package mods.clayium.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public interface IItemExRenderer {
    @SideOnly(value=Side.CLIENT)
    public void preRenderItem(IItemRenderer.ItemRenderType var1, ItemStack var2, Object ... var3);

    @SideOnly(value=Side.CLIENT)
    public void postRenderItem(IItemRenderer.ItemRenderType var1, ItemStack var2, Object ... var3);

    @SideOnly(value=Side.CLIENT)
    public void preRenderItemPass(IItemRenderer.ItemRenderType var1, ItemStack var2, int var3, Object ... var4);

    @SideOnly(value=Side.CLIENT)
    public void postRenderItemPass(IItemRenderer.ItemRenderType var1, ItemStack var2, int var3, Object ... var4);
}


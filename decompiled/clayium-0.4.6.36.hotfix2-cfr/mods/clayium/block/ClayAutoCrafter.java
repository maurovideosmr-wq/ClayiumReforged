/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.IIconRegister
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.clayium.block.ClayNoRecipeMachines;
import mods.clayium.block.tile.TileAutoCrafter;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ClayAutoCrafter
extends ClayNoRecipeMachines {
    public ClayAutoCrafter(int tier) {
        super("", "", tier, TileAutoCrafter.class, 1);
        this.guiId = 17;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.UpOverlayIcon = par1IconRegister.func_94245_a("clayium:autocraftertop");
        this.RightOverlayIcon = this.LeftOverlayIcon = par1IconRegister.func_94245_a("clayium:autocrafterside");
        this.BackOverlayIcon = this.LeftOverlayIcon;
        this.FrontOverlayIcon = this.LeftOverlayIcon;
    }
}


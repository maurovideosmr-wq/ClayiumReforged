/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.util.IIcon
 */
package mods.clayium.gui;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IMultipleRenderIcons {
    public int getRenderPasses();

    public IIcon getIconFromPass(int var1);

    public int getColorFromPass(int var1);

    public void registerIcons(IIconRegister var1);
}


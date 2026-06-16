/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.util.IIcon
 */
package mods.clayium.gui;

import mods.clayium.gui.IMultipleRenderIcons;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GenericMaterialIcon
implements IMultipleRenderIcons {
    int[] colors;
    IIcon[] iicons;
    String iconString;

    public GenericMaterialIcon(String iconString, int r1, int g1, int b1, int r2, int g2, int b2, int r3, int g3, int b3) {
        this.iconString = iconString;
        this.colors = new int[]{GenericMaterialIcon.rgb2int(r1, g1, b1), GenericMaterialIcon.rgb2int(r2, g2, b2), GenericMaterialIcon.rgb2int(r3, g3, b3)};
    }

    public GenericMaterialIcon(String str, int color1, int color2, int color3) {
        this.colors = new int[]{color1, color2, color3};
    }

    @Override
    public int getRenderPasses() {
        return 3;
    }

    @Override
    public IIcon getIconFromPass(int pass) {
        return this.iicons[pass];
    }

    @Override
    public int getColorFromPass(int pass) {
        return this.colors[pass];
    }

    public static int rgb2int(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }

    @Override
    public void registerIcons(IIconRegister iiconRegister) {
        this.iicons = new IIcon[3];
        this.iicons[0] = iiconRegister.func_94245_a("clayium:" + this.iconString + "_base");
        this.iicons[1] = iiconRegister.func_94245_a("clayium:" + this.iconString + "_dark");
        this.iicons[2] = iiconRegister.func_94245_a("clayium:" + this.iconString + "_light");
    }
}


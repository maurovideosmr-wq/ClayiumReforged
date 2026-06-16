/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.AxisAlignedBB
 */
package mods.clayium.block.tile;

import net.minecraft.util.AxisAlignedBB;

public interface IAxisAlignedBBContainer {
    public AxisAlignedBB getAxisAlignedBB();

    public void setAxisAlignedBB(AxisAlignedBB var1);

    public boolean hasAxisAlignedBB();

    public int getBoxAppearance();
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.AxisAlignedBB
 */
package mods.clayium.block.tile;

import net.minecraft.util.AxisAlignedBB;

public interface IAxisAlignedBBProvider {
    public AxisAlignedBB getAxisAlignedBB();

    public boolean hasAxisAlignedBB();

    public void setAxisAlignedBBToMachine();
}


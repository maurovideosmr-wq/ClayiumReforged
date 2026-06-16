/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block;

import mods.clayium.block.tile.TileClayContainer;
import net.minecraft.world.IBlockAccess;

public interface IClayContainerModifier {
    public void modifyClayContainer(IBlockAccess var1, int var2, int var3, int var4, TileClayContainer var5);
}


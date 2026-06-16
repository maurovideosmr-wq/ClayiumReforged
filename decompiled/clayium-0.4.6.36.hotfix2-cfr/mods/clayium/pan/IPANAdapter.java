/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package mods.clayium.pan;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IPANAdapter {
    public ItemStack[] getPatternItems();

    public ItemStack[] getSubItems();

    public World getConnectedWorld();

    public int getConnectedXCoord();

    public int getConnectedYCoord();

    public int getConnectedZCoord();
}


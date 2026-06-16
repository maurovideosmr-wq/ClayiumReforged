/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.IInventory
 */
package mods.clayium.block.tile;

import net.minecraft.inventory.IInventory;

public interface INormalInventory
extends IInventory {
    public int getInventoryX();

    public int getInventoryY();

    public int getInventoryP();

    public int getInventoryStart();
}


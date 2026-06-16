/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 */
package mods.clayium.block.tile;

import net.minecraft.world.World;

public interface ISynchronizedInterface {
    public boolean setCoreBlockCoord(int var1, int var2, int var3);

    public boolean setCoreBlockDimension(int var1);

    public int getCoreBlockXCoord();

    public int getCoreBlockYCoord();

    public int getCoreBlockZCoord();

    public World getCoreWorld();

    public boolean isSynced();

    public boolean acceptCoordChanger();
}


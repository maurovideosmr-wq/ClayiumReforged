/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ForgeChunkManager$Ticket
 */
package mods.clayium.block;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

public interface IClayChunkLoader {
    public boolean isPassive(World var1, int var2, int var3, int var4);

    public boolean hasTicket();

    public ForgeChunkManager.Ticket requestTicket();

    public void releaseTicket();

    public void appendTicket(ForgeChunkManager.Ticket var1);

    public void forceChunk();
}


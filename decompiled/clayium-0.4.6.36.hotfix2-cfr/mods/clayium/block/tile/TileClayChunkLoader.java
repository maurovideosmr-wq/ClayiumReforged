/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.ChunkCoordIntPair
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ForgeChunkManager
 *  net.minecraftforge.common.ForgeChunkManager$Ticket
 *  net.minecraftforge.common.ForgeChunkManager$Type
 */
package mods.clayium.block.tile;

import mods.clayium.block.IClayChunkLoader;
import mods.clayium.block.tile.TileGeneric;
import mods.clayium.core.ClayiumCore;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

public class TileClayChunkLoader
extends TileGeneric
implements IClayChunkLoader {
    public static boolean chunkLoaderLog = false;
    private ForgeChunkManager.Ticket ticket;

    @Override
    public boolean isPassive(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public void func_145845_h() {
        if (!this.hasTicket()) {
            this.appendTicket(this.requestTicket());
            this.forceChunk();
        }
        super.func_145845_h();
    }

    @Override
    public ForgeChunkManager.Ticket requestTicket() {
        ForgeChunkManager.Ticket ticket = ForgeChunkManager.requestTicket((Object)ClayiumCore.INSTANCE, (World)this.field_145850_b, (ForgeChunkManager.Type)ForgeChunkManager.Type.NORMAL);
        if (ticket != null) {
            ticket.getModData().func_74768_a("chunkLoaderX", this.field_145851_c);
            ticket.getModData().func_74768_a("chunkLoaderY", this.field_145848_d);
            ticket.getModData().func_74768_a("chunkLoaderZ", this.field_145849_e);
        } else {
            ClayiumCore.logger.warn("ChunkLoader " + this.field_145851_c + " " + this.field_145848_d + " " + this.field_145849_e + " failed to request a new ticket. " + "There are too many Clayium's Chunk Loaders in the world.");
        }
        return ticket;
    }

    @Override
    public boolean hasTicket() {
        if (this.ticket == null && chunkLoaderLog) {
            ClayiumCore.logger.info("ChunkLoader " + this.field_145851_c + " " + this.field_145848_d + " " + this.field_145849_e + " has no ticket.");
        }
        return this.ticket != null;
    }

    @Override
    public void releaseTicket() {
        if (chunkLoaderLog) {
            ClayiumCore.logger.info("ChunkLoader " + this.field_145851_c + " " + this.field_145848_d + " " + this.field_145849_e + " released its ticket.");
        }
        if (this.hasTicket()) {
            ForgeChunkManager.releaseTicket((ForgeChunkManager.Ticket)this.ticket);
        }
    }

    @Override
    public void appendTicket(ForgeChunkManager.Ticket ticket) {
        if (chunkLoaderLog) {
            ClayiumCore.logger.info("ChunkLoader " + this.field_145851_c + " " + this.field_145848_d + " " + this.field_145849_e + " was appended a new ticket.");
        }
        if (ticket != null) {
            if (this.ticket == null) {
                this.ticket = ticket;
            } else if (this.ticket != ticket) {
                if (chunkLoaderLog) {
                    ClayiumCore.logger.info("ChunkLoader " + this.field_145851_c + " " + this.field_145848_d + " " + this.field_145849_e + " released the old ticket.");
                }
                ForgeChunkManager.releaseTicket((ForgeChunkManager.Ticket)this.ticket);
                this.ticket = ticket;
            }
        }
    }

    @Override
    public void forceChunk() {
        if (chunkLoaderLog) {
            ClayiumCore.logger.info("ChunkLoader " + this.field_145851_c + " " + this.field_145848_d + " " + this.field_145849_e + " tried to force the chunk.");
        }
        if (this.hasTicket()) {
            int size = 1;
            for (int chunkX = (this.field_145851_c >> 4) - size; chunkX <= (this.field_145851_c >> 4) + size; ++chunkX) {
                for (int chunkZ = (this.field_145849_e >> 4) - size; chunkZ <= (this.field_145849_e >> 4) + size; ++chunkZ) {
                    if (chunkLoaderLog) {
                        ClayiumCore.logger.info("ChunkLoader " + this.field_145851_c + " " + this.field_145848_d + " " + this.field_145849_e + " forced the chunk [" + chunkX + "] [" + chunkZ + "].");
                    }
                    ForgeChunkManager.forceChunk((ForgeChunkManager.Ticket)this.ticket, (ChunkCoordIntPair)new ChunkCoordIntPair(chunkX, chunkZ));
                }
            }
        }
    }
}


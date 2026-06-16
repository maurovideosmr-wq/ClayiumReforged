/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ForgeChunkManager
 *  net.minecraftforge.common.ForgeChunkManager$OrderedLoadingCallback
 *  net.minecraftforge.common.ForgeChunkManager$Ticket
 */
package mods.clayium.network;

import java.util.ArrayList;
import java.util.List;
import mods.clayium.block.IClayChunkLoader;
import mods.clayium.block.tile.TileClayChunkLoader;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilBuilder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

public class ClayChunkLoaderCallback
implements ForgeChunkManager.OrderedLoadingCallback {
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        for (ForgeChunkManager.Ticket ticket : tickets) {
            int z;
            int y;
            int x = ticket.getModData().func_74762_e("chunkLoaderX");
            TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y = ticket.getModData().func_74762_e("chunkLoaderY"), z = ticket.getModData().func_74762_e("chunkLoaderZ"));
            if (te instanceof IClayChunkLoader) {
                IClayChunkLoader chunkloader = (IClayChunkLoader)te;
                if (chunkloader.hasTicket()) {
                    chunkloader.releaseTicket();
                }
                chunkloader.appendTicket(ticket);
                chunkloader.forceChunk();
                if (!TileClayChunkLoader.chunkLoaderLog) continue;
                ClayiumCore.logger.info("Activating the chunkLoader " + x + " " + y + " " + z);
                continue;
            }
            ForgeChunkManager.releaseTicket((ForgeChunkManager.Ticket)ticket);
        }
    }

    public List<ForgeChunkManager.Ticket> ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world, int maxTicketCount) {
        ArrayList<ForgeChunkManager.Ticket> res = new ArrayList<ForgeChunkManager.Ticket>();
        for (ForgeChunkManager.Ticket ticket : tickets) {
            IClayChunkLoader chunkloader;
            int z;
            int y;
            int x = ticket.getModData().func_74762_e("chunkLoaderX");
            TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y = ticket.getModData().func_74762_e("chunkLoaderY"), z = ticket.getModData().func_74762_e("chunkLoaderZ"));
            if (!(te instanceof IClayChunkLoader) || (chunkloader = (IClayChunkLoader)te).isPassive(world, x, y, z)) continue;
            res.add(ticket);
        }
        return res;
    }
}


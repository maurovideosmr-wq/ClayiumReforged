/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.world.World
 */
package mods.clayium.core;

import mods.clayium.item.ClaySteelPickaxe;
import mods.clayium.util.UtilAdvancedTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class ClayiumCommonProxy {
    public void registerTileEntity() {
    }

    public void registerRenderer() {
    }

    public int getRenderID() {
        return -1;
    }

    public World getClientWorld() {
        return null;
    }

    public void LoadNEI() {
    }

    public ClaySteelPickaxe newClaySteelPickaxe() {
        return new ClaySteelPickaxe();
    }

    public int getHittingSide(EntityPlayer player) {
        Integer s = UtilAdvancedTools.sideList.get(player);
        return s == null || s < 0 || s >= 6 ? -1 : s;
    }

    public void updateHittingSide(EntityPlayer player) {
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public boolean renderAsPipingMode() {
        return false;
    }

    public void clientPlayerTick(EntityPlayer player) {
    }

    public void overclockPlayer(int delay) {
    }

    public void updateFlightStatus(int mode) {
    }

    public float hookBlockReachDistance(float distance) {
        return distance;
    }

    public boolean isCreative(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            return ((EntityPlayerMP)player).field_71134_c.func_73083_d();
        }
        return false;
    }
}


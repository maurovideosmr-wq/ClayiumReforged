/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package mods.clayium.block.tile;

import net.minecraft.entity.player.EntityPlayer;

public interface IGeneralInterface {
    public void markForStrongUpdate();

    public void markForWeakUpdate();

    public void setSyncFlag();

    public void setInstantSyncFlag();

    public void setRenderSyncFlag();

    public void pushButton(EntityPlayer var1, int var2);
}


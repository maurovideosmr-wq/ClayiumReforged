/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.util.MathHelper
 */
package mods.clayium.item.gadget;

import mods.clayium.core.ClayiumCore;
import mods.clayium.item.gadget.GadgetOrdinal;
import mods.clayium.util.UtilPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;

public class GadgetLongArm
extends GadgetOrdinal {
    public static float defaultDist = 5.0f;

    public GadgetLongArm() {
        super("LongArm0", "LongArm1", "LongArm2");
    }

    @Override
    public void update(int itemIndex, Entity entity, boolean isRemote) {
        if (entity instanceof EntityPlayer) {
            Integer c = (Integer)UtilPlayer.getPlayerInstantData((EntityPlayer)entity, "GadgetLongArm");
            int i = -1;
            if (c != null) {
                i = c;
            }
            UtilPlayer.setPlayerInstantData((EntityPlayer)entity, "GadgetLongArm", itemIndex);
            if (!isRemote && entity instanceof EntityPlayerMP && i != itemIndex) {
                ((EntityPlayerMP)entity).field_71134_c.setBlockReachDistance((double)GadgetLongArm.hookBlockReachDistance(defaultDist, (EntityPlayer)entity));
            }
        }
    }

    public static float hookBlockReachDistance(float distance) {
        return ClayiumCore.proxy.hookBlockReachDistance(distance);
    }

    public static float hookBlockReachDistance(float dist, EntityPlayer player) {
        Integer c = (Integer)UtilPlayer.getPlayerInstantData(player, "GadgetLongArm");
        int i = -1;
        if (c != null) {
            i = c;
        }
        float d = 0.0f;
        switch (i) {
            case 0: {
                d = 3.0f;
                break;
            }
            case 1: {
                d = 7.0f;
                break;
            }
            case 2: {
                d = 20.0f;
            }
        }
        return d + dist;
    }

    public static float hookBlockReachDistanceSq(float distSq, EntityPlayer player) {
        float ret = GadgetLongArm.hookBlockReachDistance(MathHelper.func_76129_c((float)distSq), player);
        return ret * ret;
    }
}


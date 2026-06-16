/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package mods.clayium.item.gadget;

import mods.clayium.core.ClayiumCore;
import mods.clayium.item.gadget.GadgetOrdinal;
import net.minecraft.entity.Entity;

public class GadgetOverclocker
extends GadgetOrdinal {
    public GadgetOverclocker() {
        super("AntimatterOverclock", "PureAntimatterOverclock", "OECOverclock", "OPAOverclock");
    }

    @Override
    public void update(int itemIndex, Entity entity, boolean isRemote) {
        if (itemIndex >= 0 && isRemote && ClayiumCore.proxy.getClientPlayer() == entity) {
            ClayiumCore.proxy.overclockPlayer(3 - itemIndex);
        }
    }
}


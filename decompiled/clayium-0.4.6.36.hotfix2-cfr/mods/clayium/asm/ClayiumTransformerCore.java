/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.EventBus
 *  cpw.mods.fml.common.DummyModContainer
 *  cpw.mods.fml.common.LoadController
 *  cpw.mods.fml.common.ModMetadata
 */
package mods.clayium.asm;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import java.util.ArrayList;

public class ClayiumTransformerCore
extends DummyModContainer {
    public ClayiumTransformerCore() {
        super(new ModMetadata());
        ModMetadata meta = super.getMetadata();
        meta.modId = "clayiumtransformer";
        meta.name = "Clayium Transformer";
        meta.version = "0.4.1";
        meta.authorList = new ArrayList();
        meta.authorList.add("deb_rk");
    }

    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register((Object)this);
        return true;
    }
}


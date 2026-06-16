/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 */
package mods.clayium.pan;

import mods.clayium.pan.IPANAdapter;
import mods.clayium.pan.IPANConversion;
import net.minecraft.world.World;

public interface IPANAdapterConversionFactory {
    public boolean match(World var1, int var2, int var3, int var4);

    public IPANConversion getConversion(IPANAdapter var1);
}


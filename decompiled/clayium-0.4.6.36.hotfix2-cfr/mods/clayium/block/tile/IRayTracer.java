/*
 * Decompiled with CFR 0.152.
 */
package mods.clayium.block.tile;

import mods.clayium.entity.RayTraceMemory;

public interface IRayTracer {
    public void setRayTraceMemory(RayTraceMemory var1);

    public boolean acceptRayTraceMemory(RayTraceMemory var1);

    public RayTraceMemory getRayTraceMemory();
}


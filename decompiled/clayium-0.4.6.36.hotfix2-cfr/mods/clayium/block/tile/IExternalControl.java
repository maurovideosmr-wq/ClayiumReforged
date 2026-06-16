/*
 * Decompiled with CFR 0.152.
 */
package mods.clayium.block.tile;

public interface IExternalControl {
    public void doWorkOnce();

    public void startWork();

    public void stopWork();

    public boolean isScheduled();

    public boolean isDoingWork();
}


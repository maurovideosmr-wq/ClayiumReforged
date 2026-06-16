/*
 * Decompiled with CFR 0.152.
 */
package mods.clayium.block.laser;

import mods.clayium.block.laser.ClayLaser;
import mods.clayium.util.UtilDirection;

public interface IClayLaserManager {
    public ClayLaser getClayLaser();

    public UtilDirection getDirection();

    public int getLaserLength();

    public int[] getTargetCoord();

    public boolean hasTarget();

    public boolean isIrradiating();
}


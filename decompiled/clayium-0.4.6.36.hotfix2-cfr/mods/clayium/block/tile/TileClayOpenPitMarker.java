/*
 * Decompiled with CFR 0.152.
 */
package mods.clayium.block.tile;

import mods.clayium.block.tile.TileClayMarker;

public class TileClayOpenPitMarker
extends TileClayMarker {
    @Override
    public void activate() {
        super.activate();
        if (this.aabb != null) {
            this.aabb.field_72338_b = 1.0;
        }
    }

    public static class TileClayPrismMarker
    extends TileClayMarker {
        @Override
        public void activate() {
            super.activate();
            if (this.aabb != null) {
                this.aabb.field_72338_b = 1.0;
                this.aabb.field_72337_e = 255.0;
            }
        }
    }

    public static class TileClayGroundLevelingMarker
    extends TileClayMarker {
        @Override
        public void activate() {
            super.activate();
            if (this.aabb != null) {
                this.aabb.field_72337_e = 255.0;
            }
        }
    }
}


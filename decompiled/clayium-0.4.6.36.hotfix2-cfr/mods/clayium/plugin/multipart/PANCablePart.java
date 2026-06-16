/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.lib.raytracer.IndexedCuboid6
 *  codechicken.lib.vec.Cuboid6
 *  codechicken.multipart.minecraft.McMetaPart
 *  net.minecraft.block.Block
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 */
package mods.clayium.plugin.multipart;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.minecraft.McMetaPart;
import java.util.ArrayList;
import mods.clayium.block.CBlocks;
import mods.clayium.block.PANCable;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class PANCablePart
extends McMetaPart {
    protected static Cuboid6 bound = null;
    protected static String type = "clayium|PanCable";

    public Cuboid6 getBounds() {
        if (bound == null) {
            bound = new Cuboid6(0.5 - (double)PANCable.pipeWidth, 0.5 - (double)PANCable.pipeWidth, 0.5 - (double)PANCable.pipeWidth, 0.5 + (double)PANCable.pipeWidth, 0.5 + (double)PANCable.pipeWidth, 0.5 + (double)PANCable.pipeWidth);
        }
        return bound;
    }

    public Block getBlock() {
        return CBlocks.blockPANCable;
    }

    public String getType() {
        return type;
    }

    public Iterable<Cuboid6> getCollisionBoxes() {
        ArrayList aabbs = new ArrayList();
        this.getBlock().func_149743_a(this.world(), this.x(), this.y(), this.z(), TileEntity.INFINITE_EXTENT_AABB, aabbs, null);
        ArrayList<Cuboid6> ret = new ArrayList<Cuboid6>();
        for (AxisAlignedBB aabb : aabbs) {
            ret.add(new Cuboid6(aabb.func_72317_d((double)(-this.x()), (double)(-this.y()), (double)(-this.z()))));
        }
        return ret;
    }

    public Iterable<IndexedCuboid6> getSubParts() {
        ArrayList aabbs = new ArrayList();
        this.getBlock().func_149743_a(this.world(), this.x(), this.y(), this.z(), TileEntity.INFINITE_EXTENT_AABB, aabbs, null);
        ArrayList<IndexedCuboid6> ret = new ArrayList<IndexedCuboid6>();
        for (AxisAlignedBB aabb : aabbs) {
            ret.add(new IndexedCuboid6((Object)0, new Cuboid6(aabb.func_72317_d((double)(-this.x()), (double)(-this.y()), (double)(-this.z())))));
        }
        return ret;
    }
}


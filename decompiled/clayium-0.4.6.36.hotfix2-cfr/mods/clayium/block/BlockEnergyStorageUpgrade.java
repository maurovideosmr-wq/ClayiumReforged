/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.world.IBlockAccess
 */
package mods.clayium.block;

import mods.clayium.block.BlockDamaged;
import mods.clayium.block.IClayContainerModifier;
import mods.clayium.block.tile.TileClayContainer;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockEnergyStorageUpgrade
extends BlockDamaged
implements IClayContainerModifier {
    public BlockEnergyStorageUpgrade(Material material) {
        super(material);
    }

    public BlockEnergyStorageUpgrade() {
        this(Material.field_151576_e);
    }

    public BlockDamaged addAdditionalEnergyStorage(int size) {
        return this.putInfo("AdditionalEnergyStorage", size);
    }

    public int getAdditionalEnergyStorage(String blockname) {
        Object obj = this.getInfo(blockname, "AdditionalEnergyStorage");
        return obj instanceof Integer ? (Integer)obj : 0;
    }

    @Override
    public void modifyClayContainer(IBlockAccess world, int x, int y, int z, TileClayContainer tile) {
        tile.clayEnergyStorageSize += this.getAdditionalEnergyStorage(this.getBlockName(world, x, y, z));
        if (tile.clayEnergyStorageSize > 64) {
            tile.clayEnergyStorageSize = 64;
        }
    }
}


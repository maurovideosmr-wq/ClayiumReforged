/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.block.IPANConductor;
import mods.clayium.block.ITieredBlock;
import mods.clayium.core.ClayiumCore;
import mods.clayium.pan.UtilPAN;
import mods.clayium.util.UtilDirection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PANCable
extends Block
implements IPANConductor,
ITieredBlock {
    public static float pipeWidth = 0.125f;
    private static UtilDirection tracingDirection = null;

    public PANCable() {
        super(Material.field_151592_s);
        this.func_149672_a(field_149778_k);
        this.func_149711_c(0.2f).func_149752_b(0.2f);
    }

    public int func_149645_b() {
        return ClayiumCore.panCableRenderId;
    }

    public boolean func_149686_d() {
        return false;
    }

    public boolean func_149662_c() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public int func_149701_w() {
        return 1;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister p_149651_1_) {
        this.field_149761_L = p_149651_1_.func_94245_a("clayium:pancable");
    }

    public MovingObjectPosition func_149731_a(World world, int x, int y, int z, Vec3 start, Vec3 end) {
        UtilDirection[] directions;
        UtilDirection mindirection = null;
        MovingObjectPosition res = null;
        double o = pipeWidth;
        this.func_149676_a((float)(0.5 - o), (float)(0.5 - o), (float)(0.5 - o), (float)(0.5 + o), (float)(0.5 + o), (float)(0.5 + o));
        res = super.func_149731_a(world, x, y, z, start, end);
        for (UtilDirection direction : directions = new UtilDirection[]{UtilDirection.NORTH, UtilDirection.SOUTH, UtilDirection.EAST, UtilDirection.WEST, UtilDirection.UP, UtilDirection.DOWN}) {
            if (!this.checkPipeConnection((IBlockAccess)world, x, y, z, direction)) continue;
            this.func_149676_a((float)(0.5 - o + (direction.offsetX == 1 ? o * 2.0 : 0.0) - (direction.offsetX == -1 ? 0.5 - o : 0.0)), (float)(0.5 - o + (direction.offsetY == 1 ? o * 2.0 : 0.0) - (direction.offsetY == -1 ? 0.5 - o : 0.0)), (float)(0.5 - o + (direction.offsetZ == 1 ? o * 2.0 : 0.0) - (direction.offsetZ == -1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetX == -1 ? o * 2.0 : 0.0) + (direction.offsetX == 1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetY == -1 ? o * 2.0 : 0.0) + (direction.offsetY == 1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetZ == -1 ? o * 2.0 : 0.0) + (direction.offsetZ == 1 ? 0.5 - o : 0.0)));
            MovingObjectPosition pos = super.func_149731_a(world, x, y, z, start, end);
            if (pos == null || res != null && !(pos.field_72307_f.func_72438_d(start) < res.field_72307_f.func_72438_d(start))) continue;
            res = pos;
            mindirection = direction;
        }
        this.func_149676_a((float)(0.5 - o), (float)(0.5 - o), (float)(0.5 - o), (float)(0.5 + o), (float)(0.5 + o), (float)(0.5 + o));
        tracingDirection = mindirection;
        return res;
    }

    public boolean checkPipeConnection(IBlockAccess world, int x, int y, int z, UtilDirection direction) {
        return UtilPAN.isPANConductor(world, x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB func_149633_g(World world, int x, int y, int z) {
        double o = pipeWidth;
        if (tracingDirection == null) {
            this.func_149676_a((float)(0.5 - o), (float)(0.5 - o), (float)(0.5 - o), (float)(0.5 + o), (float)(0.5 + o), (float)(0.5 + o));
        } else {
            UtilDirection direction = tracingDirection;
            this.func_149676_a((float)(0.5 - o + (direction.offsetX == 1 ? o * 2.0 : 0.0) - (direction.offsetX == -1 ? 0.5 - o : 0.0)), (float)(0.5 - o + (direction.offsetY == 1 ? o * 2.0 : 0.0) - (direction.offsetY == -1 ? 0.5 - o : 0.0)), (float)(0.5 - o + (direction.offsetZ == 1 ? o * 2.0 : 0.0) - (direction.offsetZ == -1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetX == -1 ? o * 2.0 : 0.0) + (direction.offsetX == 1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetY == -1 ? o * 2.0 : 0.0) + (direction.offsetY == 1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetZ == -1 ? o * 2.0 : 0.0) + (direction.offsetZ == 1 ? 0.5 - o : 0.0)));
        }
        return super.func_149633_g(world, x, y, z);
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getNormalSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return super.func_149633_g(world, x, y, z);
    }

    public AxisAlignedBB func_149668_a(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        this.func_149719_a((IBlockAccess)p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.func_149668_a(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    public void func_149743_a(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity p_149743_7_) {
        UtilDirection[] directions;
        double o = pipeWidth;
        AxisAlignedBB axisalignedbb1 = AxisAlignedBB.func_72330_a((double)((double)x + 0.5 - o), (double)((double)y + 0.5 - o), (double)((double)z + 0.5 - o), (double)((double)x + 0.5 + o), (double)((double)y + 0.5 + o), (double)((double)z + 0.5 + o));
        if (axisalignedbb1 != null && aabb.func_72326_a(axisalignedbb1)) {
            list.add(axisalignedbb1);
        }
        for (UtilDirection direction : directions = new UtilDirection[]{UtilDirection.NORTH, UtilDirection.SOUTH, UtilDirection.EAST, UtilDirection.WEST, UtilDirection.UP, UtilDirection.DOWN}) {
            if (!this.checkPipeConnection((IBlockAccess)world, x, y, z, direction) || (axisalignedbb1 = AxisAlignedBB.func_72330_a((double)((double)x + 0.5 - o + (direction.offsetX == 1 ? o * 2.0 : 0.0) - (direction.offsetX == -1 ? 0.5 - o : 0.0)), (double)((double)y + 0.5 - o + (direction.offsetY == 1 ? o * 2.0 : 0.0) - (direction.offsetY == -1 ? 0.5 - o : 0.0)), (double)((double)z + 0.5 - o + (direction.offsetZ == 1 ? o * 2.0 : 0.0) - (direction.offsetZ == -1 ? 0.5 - o : 0.0)), (double)((double)x + 0.5 + o - (direction.offsetX == -1 ? o * 2.0 : 0.0) + (direction.offsetX == 1 ? 0.5 - o : 0.0)), (double)((double)y + 0.5 + o - (direction.offsetY == -1 ? o * 2.0 : 0.0) + (direction.offsetY == 1 ? 0.5 - o : 0.0)), (double)((double)z + 0.5 + o - (direction.offsetZ == -1 ? o * 2.0 : 0.0) + (direction.offsetZ == 1 ? 0.5 - o : 0.0)))) == null || !aabb.func_72326_a(axisalignedbb1)) continue;
            list.add(axisalignedbb1);
        }
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return 11;
    }

    @Override
    public int getTier(IBlockAccess world, int x, int y, int z) {
        return 11;
    }
}


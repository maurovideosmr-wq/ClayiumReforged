/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import mods.clayium.block.ISpecialToolTip;
import mods.clayium.block.ITieredBlock;
import mods.clayium.block.tile.TileQuartzCrucible;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.CMaterials;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class QuartzCrucible
extends BlockContainer
implements ITieredBlock,
ISpecialToolTip {
    public QuartzCrucible() {
        super(Material.field_151592_s);
        this.func_149672_a(Block.field_149778_k);
        this.func_149711_c(0.2f);
        this.func_149752_b(0.2f);
    }

    public void func_149743_a(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
        this.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 0.0675f, 1.0f);
        super.func_149743_a(world, x, y, z, aabb, list, entity);
        float f = 0.0675f;
        this.func_149676_a(0.0f, 0.0f, 0.0f, f, 0.75f, 1.0f);
        super.func_149743_a(world, x, y, z, aabb, list, entity);
        this.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, f);
        super.func_149743_a(world, x, y, z, aabb, list, entity);
        this.func_149676_a(1.0f - f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        super.func_149743_a(world, x, y, z, aabb, list, entity);
        this.func_149676_a(0.0f, 0.0f, 1.0f - f, 1.0f, 0.75f, 1.0f);
        super.func_149743_a(world, x, y, z, aabb, list, entity);
        this.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.func_149683_g();
    }

    public boolean func_149662_c() {
        return false;
    }

    public int func_149645_b() {
        return ClayiumCore.quartzCrucibleRenderId;
    }

    public boolean func_149686_d() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister p_149651_1_) {
        this.field_149761_L = Blocks.field_150371_ca.func_149691_a(0, 0);
    }

    public void func_149670_a(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityItem) {
            ItemStack itemStack = ((EntityItem)entity).func_92059_d();
            ItemStack ingot = CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT);
            ItemStack string = new ItemStack(Items.field_151007_F);
            TileQuartzCrucible tile = (TileQuartzCrucible)UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
            if (entity.field_70163_u - (double)y < (double)0.2f) {
                if (UtilItemStack.areTypeEqual(itemStack, ingot) && tile.putIngot() && --itemStack.field_77994_a <= 0) {
                    entity.func_70106_y();
                }
                if (UtilItemStack.areTypeEqual(itemStack, string) && tile.consumeString() && --itemStack.field_77994_a <= 0) {
                    entity.func_70106_y();
                }
            }
        }
    }

    @Override
    public int getTier(ItemStack itemstack) {
        return 5;
    }

    public TileEntity func_149915_a(World world, int par2) {
        return new TileQuartzCrucible();
    }

    @Override
    public int getTier(IBlockAccess world, int x, int y, int z) {
        return 5;
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        return UtilLocale.localizeTooltip(this.func_149739_a() + ".tooltip");
    }
}


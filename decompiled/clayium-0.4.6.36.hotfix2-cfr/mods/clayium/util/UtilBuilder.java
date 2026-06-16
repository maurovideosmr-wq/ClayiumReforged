/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.ForgeDirection
 *  net.minecraftforge.event.ForgeEventFactory
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidBlock
 */
package mods.clayium.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import mods.clayium.block.ClayContainer;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.ItemCapsule;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.UtilPlayer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

public class UtilBuilder {
    private static Method createStackedBlock = null;

    public static ItemStack[] harvestBlock(World theWorld, int xx, int yy, int zz, boolean dropXP, boolean canSilkHarvest, int fortune, boolean collectFluid) {
        Block block = theWorld.func_147439_a(xx, yy, zz);
        int l = theWorld.func_72805_g(xx, yy, zz);
        ItemStack[] itemToDrop = UtilBuilder.getDropsFromBlock(theWorld, xx, yy, zz, canSilkHarvest, fortune, collectFluid);
        UtilBuilder.destroyBlock(theWorld, xx, yy, zz, dropXP, canSilkHarvest, fortune);
        return itemToDrop;
    }

    public static ItemStack[] harvestBlock(World theWorld, int xx, int yy, int zz, boolean dropXP, boolean canSilkHarvest, int fortune) {
        return UtilBuilder.harvestBlock(theWorld, xx, yy, zz, dropXP, canSilkHarvest, fortune, true);
    }

    public static void destroyBlock(World theWorld, int xx, int yy, int zz, boolean dropXP, boolean canSilkHarvest, int fortune) {
        Block block = theWorld.func_147439_a(xx, yy, zz);
        int l = theWorld.func_72805_g(xx, yy, zz);
        if (!theWorld.field_72995_K) {
            theWorld.func_72889_a(null, 2001, xx, yy, zz, Block.func_149682_b((Block)block) + (theWorld.func_72805_g(xx, yy, zz) << 12));
        }
        theWorld.func_147468_f(xx, yy, zz);
        if (dropXP) {
            block.func_149657_c(theWorld, xx, yy, zz, block.getExpDrop((IBlockAccess)theWorld, l, fortune));
        }
    }

    public static ItemStack getFluidCapsule(World theWorld, int xx, int yy, int zz) {
        return ItemCapsule.getItemCapsuleFromFluidStack(UtilBuilder.getFluidStack(theWorld, xx, yy, zz));
    }

    public static FluidStack getFluidStack(World theWorld, int xx, int yy, int zz) {
        Block block = theWorld.func_147439_a(xx, yy, zz);
        if (block == Blocks.field_150355_j || block == Blocks.field_150358_i) {
            return theWorld.func_72805_g(xx, yy, zz) == 0 ? new FluidStack(FluidRegistry.WATER, 1000) : null;
        }
        if (block == Blocks.field_150353_l || block == Blocks.field_150356_k) {
            return theWorld.func_72805_g(xx, yy, zz) == 0 ? new FluidStack(FluidRegistry.LAVA, 1000) : null;
        }
        Fluid fluid = FluidRegistry.lookupFluidForBlock((Block)block);
        if (fluid != null) {
            if (block instanceof IFluidBlock) {
                if (!((IFluidBlock)block).canDrain(theWorld, xx, yy, zz)) {
                    return null;
                }
                return ((IFluidBlock)block).drain(theWorld, xx, yy, zz, false);
            }
            return theWorld.func_72805_g(xx, yy, zz) == 0 ? new FluidStack(fluid, 1000) : null;
        }
        return null;
    }

    public static ItemStack createStackedBlock(Block block, int metadata) {
        if (createStackedBlock == null) {
            Class<?> classBlock = null;
            try {
                classBlock = Class.forName("net.minecraft.block.Block");
            }
            catch (ClassNotFoundException e2) {
                try {
                    classBlock = Class.forName("net.minecraft.aji");
                }
                catch (ClassNotFoundException e) {
                    ClayiumCore.logger.catching((Throwable)e);
                }
            }
            if (classBlock != null) {
                try {
                    createStackedBlock = classBlock.getDeclaredMethod("createStackedBlock", Integer.TYPE);
                }
                catch (NoSuchMethodException e) {
                    try {
                        createStackedBlock = classBlock.getDeclaredMethod("func_149644_j", Integer.TYPE);
                    }
                    catch (NoSuchMethodException e1) {
                        try {
                            createStackedBlock = classBlock.getDeclaredMethod("j", Integer.TYPE);
                        }
                        catch (NoSuchMethodException e2) {
                            ClayiumCore.logger.catching((Throwable)e2);
                        }
                        catch (SecurityException e2) {
                            ClayiumCore.logger.catching((Throwable)e2);
                        }
                    }
                    catch (SecurityException e1) {
                        ClayiumCore.logger.catching((Throwable)e1);
                    }
                }
                catch (SecurityException e) {
                    ClayiumCore.logger.catching((Throwable)e);
                }
            }
            if (createStackedBlock != null) {
                createStackedBlock.setAccessible(true);
            }
        }
        ItemStack itemstack = null;
        if (createStackedBlock != null) {
            Object obj = null;
            try {
                obj = createStackedBlock.invoke((Object)block, metadata);
            }
            catch (Exception e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            if (obj instanceof ItemStack) {
                itemstack = (ItemStack)obj;
            }
        }
        return itemstack;
    }

    public static ItemStack[] getDropsFromBlock(World theWorld, int xx, int yy, int zz, boolean canSilkHarvest, int fortune, boolean collectFluid) {
        ItemStack fluidCapsule;
        Block block = theWorld.func_147439_a(xx, yy, zz);
        if (block == Blocks.field_150350_a) {
            return new ItemStack[0];
        }
        int l = theWorld.func_72805_g(xx, yy, zz);
        ArrayList<ItemStack> itemToDrop = new ArrayList<ItemStack>();
        if (collectFluid && (fluidCapsule = UtilBuilder.getFluidCapsule(theWorld, xx, yy, zz)) != null) {
            itemToDrop.add(fluidCapsule);
            return itemToDrop.toArray(new ItemStack[0]);
        }
        if (canSilkHarvest && block.canSilkHarvest(theWorld, UtilPlayer.getFakePlayer(null), xx, yy, zz, l)) {
            ItemStack itemstack = UtilBuilder.createStackedBlock(block, l);
            if (itemstack != null) {
                itemToDrop.add(itemstack);
            }
        } else {
            float g = 1.0f;
            if (!theWorld.field_72995_K && !theWorld.restoringBlockSnapshots) {
                ArrayList items = block.getDrops(theWorld, xx, yy, zz, l, fortune);
                g = ForgeEventFactory.fireBlockHarvesting((ArrayList)items, (World)theWorld, (Block)block, (int)xx, (int)yy, (int)zz, (int)l, (int)fortune, (float)g, (boolean)false, null);
                for (ItemStack item : items) {
                    if (!(theWorld.field_73012_v.nextFloat() <= g)) continue;
                    itemToDrop.add(item);
                }
            }
        }
        return itemToDrop.toArray(new ItemStack[0]);
    }

    public static ItemStack[] getDropsFromBlock(World theWorld, int xx, int yy, int zz, boolean canSilkHarvest, int fortune) {
        return UtilBuilder.getDropsFromBlock(theWorld, xx, yy, zz, canSilkHarvest, fortune, true);
    }

    public static ItemStack getItemBlock(World theWorld, int xx, int yy, int zz) {
        ItemStack capsule = UtilBuilder.getFluidCapsule(theWorld, xx, yy, zz);
        if (capsule != null) {
            return capsule;
        }
        ItemStack silkitem = UtilBuilder.getRawItemBlock(theWorld, xx, yy, zz);
        ArrayList dropitems = theWorld.func_147439_a(xx, yy, zz).getDrops(theWorld, xx, yy, zz, theWorld.func_72805_g(xx, yy, zz), 0);
        for (ItemStack dropitem : dropitems) {
            if (!UtilItemStack.areItemDamageEqual(silkitem, dropitem)) continue;
            ItemStack res = dropitem.func_77946_l();
            res.field_77994_a = 1;
            return res;
        }
        for (ItemStack dropitem : dropitems) {
            if (!UtilItemStack.areItemEqual(silkitem, dropitem)) continue;
            ItemStack res = dropitem.func_77946_l();
            res.field_77994_a = 1;
            return res;
        }
        return silkitem;
    }

    public static ItemStack getRawItemBlock(World theWorld, int xx, int yy, int zz) {
        Block block = theWorld.func_147439_a(xx, yy, zz);
        int j = 0;
        Item item = Item.func_150898_a((Block)block);
        if (item != null && item.func_77614_k()) {
            j = block.func_149643_k(theWorld, xx, yy, zz);
        }
        return new ItemStack(item, 1, j);
    }

    public static void dropItems(World theWorld, int xx, int yy, int zz, ItemStack[] itemToDrop) {
        for (ItemStack item : itemToDrop) {
            if (theWorld.field_72995_K || !theWorld.func_82736_K().func_82766_b("doTileDrops") || theWorld.restoringBlockSnapshots) continue;
            float f = 0.7f;
            double d0 = (double)(theWorld.field_73012_v.nextFloat() * f) + (double)(1.0f - f) * 0.5;
            double d1 = (double)(theWorld.field_73012_v.nextFloat() * f) + (double)(1.0f - f) * 0.5;
            double d2 = (double)(theWorld.field_73012_v.nextFloat() * f) + (double)(1.0f - f) * 0.5;
            EntityItem entityitem = new EntityItem(theWorld, (double)xx + d0, (double)yy + d1, (double)zz + d2, item);
            entityitem.field_145804_b = 10;
            theWorld.func_72838_d((Entity)entityitem);
        }
    }

    public static void harvestBlockAndDropItems(World theWorld, int xx, int yy, int zz, boolean dropXP, boolean canSilkHarvest, int fortune, boolean collectFluid) {
        UtilBuilder.dropItems(theWorld, xx, yy, zz, UtilBuilder.harvestBlock(theWorld, xx, yy, zz, dropXP, canSilkHarvest, fortune, collectFluid));
    }

    public static void harvestBlockAndDropItems(World theWorld, int xx, int yy, int zz, boolean dropXP, boolean canSilkHarvest, int fortune) {
        UtilBuilder.harvestBlockAndDropItems(theWorld, xx, yy, zz, dropXP, canSilkHarvest, fortune, true);
    }

    public static boolean placeBlockByRightClick(Block block, int meta, ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int num) {
        int[] coord = UtilBuilder.coordTransformOnPlaceBlock(world, x, y, z, side);
        x = coord[0];
        y = coord[1];
        z = coord[2];
        side = coord[3];
        if (itemstack.field_77994_a == 0) {
            return false;
        }
        if (!player.func_82247_a(x, y, z, side, itemstack)) {
            return false;
        }
        if (y == 255 && block.func_149688_o().func_76220_a()) {
            return false;
        }
        if (world.func_147472_a(block, x, y, z, false, side, (Entity)player, itemstack)) {
            int j1 = block.func_149660_a(world, x, y, z, side, hitX, hitY, hitZ, meta);
            if (UtilBuilder.placeBlockByPlayer(block, itemstack, player, world, x, y, z, side, hitX, hitY, hitZ, j1)) {
                UtilBuilder.postBlockPlace(block, world, itemstack, x, y, z, num);
            }
            return true;
        }
        return false;
    }

    public static int[] coordTransformOnPlaceBlock(World world, int x, int y, int z, int side) {
        Block b = world.func_147439_a(x, y, z);
        if (b == Blocks.field_150431_aC && (world.func_72805_g(x, y, z) & 7) < 1) {
            side = 1;
        } else if (b != Blocks.field_150395_bd && b != Blocks.field_150329_H && b != Blocks.field_150330_I && !b.isReplaceable((IBlockAccess)world, x, y, z)) {
            if (side == 0) {
                --y;
            }
            if (side == 1) {
                ++y;
            }
            if (side == 2) {
                --z;
            }
            if (side == 3) {
                ++z;
            }
            if (side == 4) {
                --x;
            }
            if (side == 5) {
                // empty if block
            }
        }
        return new int[]{++x, y, z, side};
    }

    public static boolean placeBlockByPlayer(Block block, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (!world.func_147465_d(x, y, z, block, metadata, 3)) {
            return false;
        }
        if (world.func_147439_a(x, y, z) == block) {
            block.func_149689_a(world, x, y, z, (EntityLivingBase)player, stack);
            block.func_149714_e(world, x, y, z, metadata);
        }
        return true;
    }

    public static boolean placeBlockByItemBlock(ItemStack itemstack, World world, int x, int y, int z) {
        return UtilBuilder.placeBlockByItemBlock(itemstack, world, x, y, z, 1, 0.5f, 0.5f, 0.5f);
    }

    public static boolean placeBlockByItemBlock(ItemStack itemstack, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (itemstack == null || !(itemstack.func_77973_b() instanceof ItemBlock)) {
            return false;
        }
        ItemBlock itemBlock = (ItemBlock)itemstack.func_77973_b();
        int[] coord = UtilBuilder.coordTransformOnPlaceBlock(world, x, y, z, side);
        x = coord[0];
        y = coord[1];
        z = coord[2];
        side = coord[3];
        if (itemstack.field_77994_a == 0) {
            return false;
        }
        if (y == 255 && itemBlock.field_150939_a.func_149688_o().func_76220_a()) {
            return false;
        }
        int i1 = itemBlock.func_77647_b(itemstack.func_77960_j());
        int j1 = itemBlock.field_150939_a.func_149660_a(world, x, y, z, side, hitX, hitY, hitZ, i1);
        if (UtilBuilder.placeBlockAt(itemBlock.field_150939_a, itemstack, world, x, y, z, j1, true)) {
            UtilBuilder.postBlockPlace(itemBlock.field_150939_a, world, itemstack, x, y, z, 1);
        }
        return true;
    }

    public static boolean placeBlockAt(Block block, ItemStack stack, World world, int x, int y, int z, int metadata, boolean useFakePlayer) {
        if (!world.func_147465_d(x, y, z, block, metadata, 3)) {
            return false;
        }
        if (world.func_147439_a(x, y, z) == block) {
            if (useFakePlayer) {
                block.func_149689_a(world, x, y, z, (EntityLivingBase)UtilPlayer.getFakePlayer(null, world, x, y, z), stack);
            }
            block.func_149714_e(world, x, y, z, metadata);
        }
        return true;
    }

    public static boolean placeBlockAt(Block block, World world, int x, int y, int z, int metadata) {
        return UtilBuilder.placeBlockAt(block, null, world, x, y, z, metadata, false);
    }

    public static boolean rotateBlockByWrench(World world, int x, int y, int z, int side) {
        Block block = world.func_147439_a(x, y, z);
        if (block instanceof ClayContainer) {
            return false;
        }
        ForgeDirection direction = ForgeDirection.getOrientation((int)side);
        ForgeDirection[] axes = block.getValidRotations(world, x, y, z);
        if (axes == null || axes.length == 0) {
            return false;
        }
        ForgeDirection axis = axes[0];
        for (ForgeDirection axis1 : block.getValidRotations(world, x, y, z)) {
            if (axis1 != direction) continue;
            axis = axis1;
        }
        return block.rotateBlock(world, x, y, z, axis);
    }

    public static void postBlockPlace(Block block, World world, ItemStack item, int x, int y, int z, int num) {
        world.func_72908_a((double)((float)x + 0.5f), (double)((float)y + 0.5f), (double)((float)z + 0.5f), block.field_149762_H.func_150496_b(), (block.field_149762_H.func_150497_c() + 1.0f) / 2.0f, block.field_149762_H.func_150494_d() * 0.8f);
        item.field_77994_a -= num;
    }

    public static TileEntity safeGetTileEntity(IBlockAccess world, int x, int y, int z) {
        return y >= 0 && y < 256 && x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000 ? world.func_147438_o(x, y, z) : null;
    }
}


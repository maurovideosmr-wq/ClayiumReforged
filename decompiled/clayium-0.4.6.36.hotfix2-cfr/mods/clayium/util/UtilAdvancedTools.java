/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 *  net.minecraftforge.event.entity.player.PlayerEvent$BreakSpeed
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$Action
 */
package mods.clayium.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.IAdvancedTool;
import mods.clayium.network.ClaySteelPickaxePacket;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilPlayer;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class UtilAdvancedTools {
    private static boolean firstCall = true;
    public static HashMap<EntityPlayer, Integer> sideList = new HashMap();
    public static HashMap<EntityPlayer, HashMap<String, Integer>> forceList = new HashMap();
    public static UtilAdvancedTools INSTANCE = new UtilAdvancedTools();

    public static int onBlockDestroyed(ItemStack itemstack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
        int damage = 0;
        if (!world.field_72995_K && entity instanceof EntityPlayer) {
            for (Vec3 coord : UtilAdvancedTools.getHarvestedCoordListInSafe(itemstack, world, x, y, z, (EntityPlayer)entity)) {
                if ((int)coord.field_72450_a == x && (int)coord.field_72448_b == y && (int)coord.field_72449_c == z) continue;
                UtilBuilder.harvestBlockAndDropItems(world, (int)coord.field_72450_a, (int)coord.field_72448_b, (int)coord.field_72449_c, true, world.func_147439_a((int)coord.field_72450_a, (int)coord.field_72448_b, (int)coord.field_72449_c).canSilkHarvest(world, (EntityPlayer)entity, (int)coord.field_72450_a, (int)coord.field_72448_b, (int)coord.field_72449_c, world.func_72805_g((int)coord.field_72450_a, (int)coord.field_72448_b, (int)coord.field_72449_c)) && EnchantmentHelper.func_77502_d((EntityLivingBase)((EntityPlayer)entity)), EnchantmentHelper.func_77517_e((EntityLivingBase)((EntityPlayer)entity)));
                ++damage;
            }
        }
        if (world.field_72995_K && entity == UtilPlayer.getClientPlayer()) {
            ClayiumCore.packetDispatcher.sendToServer((IMessage)new ClaySteelPickaxePacket(world, x, y, z));
        }
        return damage;
    }

    @SubscribeEvent
    public void playerInteractEventSubscriber(PlayerInteractEvent event) {
        ItemStack item = event.entityPlayer.func_71045_bC();
        if (item == null || !(item.func_77973_b() instanceof IAdvancedTool) || event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            // empty if block
        }
    }

    public void playerTick(EntityPlayer player) {
        ClayiumCore.proxy.updateHittingSide(player);
    }

    @SubscribeEvent
    public void breakSpeedSubscriber(PlayerEvent.BreakSpeed event) {
        ItemStack item = event.entityPlayer.func_71045_bC();
        World world = event.entityPlayer.func_130014_f_();
        if (item != null && item.func_77973_b() instanceof IAdvancedTool) {
            if (!world.field_72995_K && forceList.containsKey(event.entityPlayer)) {
                HashMap<String, Integer> map = forceList.get(event.entityPlayer);
                forceList.remove(event.entityPlayer);
                if (map.get("x") == event.x && map.get("y") == event.y && map.get("z") == event.z && map.get("d") == event.entityPlayer.field_70170_p.field_73011_w.field_76574_g) {
                    event.newSpeed = Float.POSITIVE_INFINITY;
                    return;
                }
            }
            if (firstCall) {
                ClayiumCore.proxy.updateHittingSide(event.entityPlayer);
                firstCall = false;
                float hardness = 0.0f;
                for (Vec3 coord : UtilAdvancedTools.getHarvestedCoordListInSafe(item, world, event.x, event.y, event.z, event.entityPlayer)) {
                    if (world.func_147439_a((int)coord.field_72450_a, (int)coord.field_72448_b, (int)coord.field_72449_c) == Blocks.field_150350_a) continue;
                    float speed = world.func_147439_a((int)coord.field_72450_a, (int)coord.field_72448_b, (int)coord.field_72449_c).func_149737_a(event.entityPlayer, world, event.x, event.y, event.z) * 30.0f;
                    hardness += speed == 0.0f ? Float.POSITIVE_INFINITY : 1.0f / speed;
                    if (world.func_147439_a((int)coord.field_72450_a, (int)coord.field_72448_b, (int)coord.field_72449_c) != Blocks.field_150357_h) continue;
                    hardness = Float.POSITIVE_INFINITY;
                }
                float f = event.newSpeed = hardness == 0.0f ? Float.POSITIVE_INFINITY : 1.0f / hardness;
                if (event.newSpeed >= 24.0f && world.field_72995_K && event.entityPlayer == UtilPlayer.getClientPlayer()) {
                    ClayiumCore.packetDispatcher.sendToServer((IMessage)new ClaySteelPickaxePacket(event.entityPlayer.field_70170_p, event.x, event.y, event.z));
                }
                firstCall = true;
            }
            if (ClayiumCore.proxy.getHittingSide(event.entityPlayer) == -1) {
                event.newSpeed = 0.0f;
            }
        }
    }

    public static List<Vec3> getHarvestedCoordListInSafe(ItemStack itemstack, World world, int x, int y, int z, EntityPlayer player) {
        List<Vec3> list = new ArrayList<Vec3>();
        int side = ClayiumCore.proxy.getHittingSide(player);
        if (side == -1 || !(itemstack.func_77973_b() instanceof IAdvancedTool)) {
            list.add(Vec3.func_72443_a((double)x, (double)y, (double)z));
        } else {
            list = UtilAdvancedTools.getHarvestedCoordList(itemstack, world, x, y, z, player, side);
        }
        return list;
    }

    public static List<Vec3> getHarvestedCoordList(ItemStack itemstack, World world, int x, int y, int z, EntityPlayer player, int side) {
        if (!(itemstack.func_77973_b() instanceof IAdvancedTool)) {
            return null;
        }
        Vec3[] ev = UtilAdvancedTools.getEigenVectors(player, side);
        return ((IAdvancedTool)itemstack.func_77973_b()).getHarvestCoord().getHarvestedCoordList(itemstack, x, y, z, ev[0], ev[1], ev[2]);
    }

    public static Vec3[] getEigenVectorsInSafe(EntityPlayer player) {
        return ClayiumCore.proxy.getHittingSide(player) == -1 ? null : UtilAdvancedTools.getEigenVectors(player, ClayiumCore.proxy.getHittingSide(player));
    }

    public static Vec3[] getEigenVectors(EntityPlayer player, int side) {
        UtilDirection xxVector = UtilDirection.EAST;
        UtilDirection yyVector = UtilDirection.UP;
        UtilDirection zzVector = UtilDirection.SOUTH;
        UtilDirection clickedSide = UtilDirection.getOrientation(side);
        if (clickedSide != UtilDirection.UP && clickedSide != UtilDirection.DOWN) {
            zzVector = clickedSide;
            xxVector = zzVector.getSideOfDirection(UtilDirection.LEFTSIDE);
        } else {
            zzVector = UtilDirection.UP;
            float f1 = MathHelper.func_76134_b((float)(-player.field_70177_z * ((float)Math.PI / 180) - (float)Math.PI));
            float f2 = MathHelper.func_76126_a((float)(-player.field_70177_z * ((float)Math.PI / 180) - (float)Math.PI));
            if ((double)f1 >= Math.sqrt(0.5)) {
                yyVector = UtilDirection.NORTH;
            }
            if ((double)f1 <= -Math.sqrt(0.5)) {
                yyVector = UtilDirection.SOUTH;
            }
            if ((double)f2 >= Math.sqrt(0.5)) {
                yyVector = UtilDirection.WEST;
            }
            if ((double)f2 <= -Math.sqrt(0.5)) {
                yyVector = UtilDirection.EAST;
            }
            xxVector = yyVector.getSideOfDirection(UtilDirection.RIGHTSIDE);
            if (clickedSide == UtilDirection.DOWN) {
                yyVector = yyVector.getOpposite();
                zzVector = zzVector.getOpposite();
            }
        }
        return new Vec3[]{xxVector.toVec3(), yyVector.toVec3(), zzVector.toVec3()};
    }

    public static Vec3[] getInverse(Vec3[] v) {
        double d = v[0].field_72450_a * v[1].field_72448_b * v[2].field_72449_c + v[1].field_72450_a * v[2].field_72448_b * v[0].field_72449_c + v[2].field_72450_a * v[0].field_72448_b * v[1].field_72449_c - v[0].field_72450_a * v[2].field_72448_b * v[1].field_72449_c - v[1].field_72450_a * v[0].field_72448_b * v[2].field_72449_c - v[2].field_72450_a * v[1].field_72448_b * v[0].field_72449_c;
        if (d == 0.0) {
            return null;
        }
        return new Vec3[]{Vec3.func_72443_a((double)((v[1].field_72448_b * v[2].field_72449_c - v[2].field_72448_b * v[1].field_72449_c) / d), (double)((v[2].field_72448_b * v[0].field_72449_c - v[0].field_72448_b * v[2].field_72449_c) / d), (double)((v[0].field_72448_b * v[1].field_72449_c - v[1].field_72448_b * v[0].field_72449_c) / d)), Vec3.func_72443_a((double)((v[1].field_72449_c * v[2].field_72450_a - v[2].field_72449_c * v[1].field_72450_a) / d), (double)((v[2].field_72449_c * v[0].field_72450_a - v[0].field_72449_c * v[2].field_72450_a) / d), (double)((v[0].field_72449_c * v[1].field_72450_a - v[1].field_72449_c * v[0].field_72450_a) / d)), Vec3.func_72443_a((double)((v[1].field_72450_a * v[2].field_72448_b - v[2].field_72450_a * v[1].field_72448_b) / d), (double)((v[2].field_72450_a * v[0].field_72448_b - v[0].field_72450_a * v[2].field_72448_b) / d), (double)((v[0].field_72450_a * v[1].field_72448_b - v[1].field_72450_a * v[0].field_72448_b) / d))};
    }
}


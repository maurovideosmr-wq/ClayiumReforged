/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  cpw.mods.fml.client.registry.ClientRegistry
 *  cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
 *  cpw.mods.fml.client.registry.RenderingRegistry
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderSnowball
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovementInput
 *  net.minecraft.world.World
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.MinecraftForgeClient
 */
package mods.clayium.core;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.lang.reflect.Field;
import java.util.Map;
import mods.clayium.block.CBlocks;
import mods.clayium.block.tile.TileAreaActivator;
import mods.clayium.block.tile.TileAreaCollector;
import mods.clayium.block.tile.TileAreaMiner;
import mods.clayium.block.tile.TileCAReactor;
import mods.clayium.block.tile.TileClayContainerInterface;
import mods.clayium.block.tile.TileClayEnergyLaser;
import mods.clayium.block.tile.TileClayLaserInterface;
import mods.clayium.block.tile.TileClayMarker;
import mods.clayium.block.tile.TileClayOpenPitMarker;
import mods.clayium.block.tile.TileLaserReflector;
import mods.clayium.block.tile.TileMetalChest;
import mods.clayium.block.tile.TileRedstoneInterface;
import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.block.tile.TileVacuumContainer;
import mods.clayium.core.ClayiumCommonProxy;
import mods.clayium.core.ClayiumCore;
import mods.clayium.entity.EntityClayBall;
import mods.clayium.entity.EntityTeleportBall;
import mods.clayium.item.CItems;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import mods.clayium.item.ClaySteelPickaxe;
import mods.clayium.item.ClaySteelPickaxeClient;
import mods.clayium.item.ItemCapsule;
import mods.clayium.item.ItemDamaged;
import mods.clayium.item.gadget.GadgetLongArm;
import mods.clayium.misc.RenderFluidTab;
import mods.clayium.misc.TileEntityFluidTabRenderer;
import mods.clayium.misc.TileFluidTab;
import mods.clayium.network.ClaySteelPickaxePacket;
import mods.clayium.plugin.nei.LoadNEIConfig;
import mods.clayium.render.block.RenderClayContainer;
import mods.clayium.render.block.RenderLaserReflector;
import mods.clayium.render.block.RenderMetalChest;
import mods.clayium.render.block.RenderPANCable;
import mods.clayium.render.block.RenderQuartzCrucible;
import mods.clayium.render.item.ItemDamagedRenderer;
import mods.clayium.render.tile.AreaMachineRenderer;
import mods.clayium.render.tile.CAReactorRenderer;
import mods.clayium.render.tile.ClayLaserRenderer;
import mods.clayium.render.tile.ClayMarkerRenderer;
import mods.clayium.render.tile.InterfaceRenderer;
import mods.clayium.render.tile.MetalChestRenderer;
import mods.clayium.render.tile.StorageContainerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClayiumClientProxy
extends ClayiumCommonProxy {
    Field blockHitDelay = null;
    Field rightClickDelayTimer = null;
    float mode1velocity = 0.7f;
    float mode2acceleration = 0.9f;
    float mode2division = 1.1f;

    @Override
    public void registerTileEntity() {
        if (!ClayiumCore.cfgUtilityMode) {
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderFluidTab());
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderClayContainer());
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderQuartzCrucible());
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderLaserReflector());
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderPANCable());
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderMetalChest());
            ClientRegistry.bindTileEntitySpecialRenderer(TileFluidTab.class, (TileEntitySpecialRenderer)new TileEntityFluidTabRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileClayEnergyLaser.class, (TileEntitySpecialRenderer)new ClayLaserRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileLaserReflector.class, (TileEntitySpecialRenderer)new ClayLaserRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileStorageContainer.class, (TileEntitySpecialRenderer)new StorageContainerRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileVacuumContainer.class, (TileEntitySpecialRenderer)new StorageContainerRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileClayMarker.class, (TileEntitySpecialRenderer)new ClayMarkerRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileClayOpenPitMarker.class, (TileEntitySpecialRenderer)new ClayMarkerRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileAreaMiner.class, (TileEntitySpecialRenderer)new AreaMachineRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileAreaCollector.class, (TileEntitySpecialRenderer)new AreaMachineRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileAreaActivator.class, (TileEntitySpecialRenderer)new AreaMachineRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileCAReactor.class, (TileEntitySpecialRenderer)new CAReactorRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileMetalChest.class, (TileEntitySpecialRenderer)MetalChestRenderer.INSTANCE);
            ClientRegistry.bindTileEntitySpecialRenderer(TileClayContainerInterface.class, (TileEntitySpecialRenderer)new InterfaceRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileClayLaserInterface.class, (TileEntitySpecialRenderer)new InterfaceRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer(TileRedstoneInterface.class, (TileEntitySpecialRenderer)new InterfaceRenderer());
        } else {
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderClayContainer());
        }
    }

    @Override
    public void registerRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityClayBall.class, (Render)new RenderSnowball(Items.field_151119_aD));
        RenderingRegistry.registerEntityRenderingHandler(EntityTeleportBall.class, (Render)new RenderSnowball(Items.field_151079_bi));
        ItemDamagedRenderer renderer = new ItemDamagedRenderer();
        for (Map.Entry<CMaterial, ItemDamaged> entry : CMaterials.materialMap.entrySet()) {
            MinecraftForgeClient.registerItemRenderer((Item)entry.getValue(), (IItemRenderer)renderer);
        }
        for (Map.Entry<Object, ItemDamaged> entry : CMaterials.shapeMap.entrySet()) {
            MinecraftForgeClient.registerItemRenderer((Item)entry.getValue(), (IItemRenderer)renderer);
        }
        if (ClayiumCore.cfgEnableFluidCapsule) {
            for (ItemCapsule item : CItems.itemsCapsule) {
                MinecraftForgeClient.registerItemRenderer((Item)item, (IItemRenderer)renderer);
            }
        }
    }

    @Override
    public int getRenderID() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().field_71441_e;
    }

    @Override
    public void LoadNEI() {
        if (ClayiumCore.IntegrationID.NEI.loaded()) {
            try {
                LoadNEIConfig.load();
            }
            catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }

    @Override
    public ClaySteelPickaxe newClaySteelPickaxe() {
        return new ClaySteelPickaxeClient();
    }

    @Override
    public int getHittingSide(EntityPlayer player) {
        if (!player.func_130014_f_().field_72995_K || player != Minecraft.func_71410_x().field_71439_g) {
            return super.getHittingSide(player);
        }
        return this.sideHit();
    }

    @Override
    public void updateHittingSide(EntityPlayer player) {
        if (player.field_70170_p.field_72995_K && this.getClientPlayer() == player) {
            ClayiumCore.packetDispatcher.sendToServer((IMessage)new ClaySteelPickaxePacket(this.sideHit()));
        }
    }

    private int sideHit() {
        return Minecraft.func_71410_x().field_71451_h.func_70614_a((double)9999.0, (float)0.0f).field_72310_e;
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.func_71410_x().field_71439_g;
    }

    @Override
    public boolean renderAsPipingMode() {
        ItemStack item;
        EntityPlayer player = this.getClientPlayer();
        return player != null && (item = player.func_71045_bC()) != null && item.func_77973_b() == CItems.itemClayPipingTools;
    }

    @Override
    public void clientPlayerTick(EntityPlayer player) {
        boolean flag = false;
        int s = 5;
        block6: for (int i = 0; i < 9; ++i) {
            ItemStack item = player.field_71071_by.field_70462_a[i];
            if (item == null || !(item.func_77973_b() instanceof ItemBlock) || ((ItemBlock)item.func_77973_b()).field_150939_a != CBlocks.blockOverclocker) continue;
            flag |= true;
            switch (item.func_77960_j()) {
                case 10: {
                    s = Math.min(s, 3);
                    continue block6;
                }
                case 11: {
                    s = Math.min(s, 2);
                    continue block6;
                }
                case 12: {
                    s = Math.min(s, 1);
                    continue block6;
                }
                case 13: {
                    s = Math.min(s, 0);
                }
            }
        }
        if (flag) {
            this.overclockPlayer(s);
        }
    }

    @Override
    public void overclockPlayer(int delay) {
        if (this.blockHitDelay == null) {
            Class<?> classPlayerController = null;
            try {
                classPlayerController = Class.forName("net.minecraft.client.multiplayer.PlayerControllerMP");
            }
            catch (ClassNotFoundException e2) {
                try {
                    classPlayerController = Class.forName("net.minecraft.bje");
                }
                catch (ClassNotFoundException e) {
                    ClayiumCore.logger.catching((Throwable)e);
                }
            }
            if (classPlayerController != null) {
                try {
                    this.blockHitDelay = classPlayerController.getDeclaredField("field_78781_i");
                }
                catch (NoSuchFieldException e) {
                    ClayiumCore.logger.info(e.getMessage());
                }
                catch (SecurityException e) {
                    ClayiumCore.logger.catching((Throwable)e);
                }
                if (this.blockHitDelay == null) {
                    try {
                        this.blockHitDelay = classPlayerController.getDeclaredField("blockHitDelay");
                    }
                    catch (NoSuchFieldException e) {
                        ClayiumCore.logger.info(e.getMessage());
                    }
                    catch (SecurityException e) {
                        ClayiumCore.logger.catching((Throwable)e);
                    }
                }
                if (this.blockHitDelay == null) {
                    try {
                        this.blockHitDelay = classPlayerController.getDeclaredField("i");
                    }
                    catch (NoSuchFieldException e) {
                        ClayiumCore.logger.info(e.getMessage());
                    }
                    catch (SecurityException e) {
                        ClayiumCore.logger.catching((Throwable)e);
                    }
                }
                if (this.blockHitDelay != null) {
                    this.blockHitDelay.setAccessible(true);
                }
            }
        }
        if (this.rightClickDelayTimer == null) {
            Class<?> classMinecraft = null;
            try {
                classMinecraft = Class.forName("net.minecraft.client.Minecraft");
            }
            catch (ClassNotFoundException e2) {
                try {
                    classMinecraft = Class.forName("net.minecraft.bao");
                }
                catch (ClassNotFoundException e) {
                    ClayiumCore.logger.catching((Throwable)e);
                }
            }
            if (classMinecraft != null) {
                try {
                    this.rightClickDelayTimer = classMinecraft.getDeclaredField("field_71467_ac");
                }
                catch (NoSuchFieldException e) {
                    ClayiumCore.logger.info(e.getMessage());
                }
                catch (SecurityException e) {
                    ClayiumCore.logger.catching((Throwable)e);
                }
                if (this.rightClickDelayTimer == null) {
                    try {
                        this.rightClickDelayTimer = classMinecraft.getDeclaredField("rightClickDelayTimer");
                    }
                    catch (NoSuchFieldException e) {
                        ClayiumCore.logger.info(e.getMessage());
                    }
                    catch (SecurityException e) {
                        ClayiumCore.logger.catching((Throwable)e);
                    }
                }
                if (this.rightClickDelayTimer == null) {
                    try {
                        this.rightClickDelayTimer = classMinecraft.getDeclaredField("ad");
                    }
                    catch (NoSuchFieldException e) {
                        ClayiumCore.logger.info(e.getMessage());
                    }
                    catch (SecurityException e) {
                        ClayiumCore.logger.catching((Throwable)e);
                    }
                }
                if (this.rightClickDelayTimer != null) {
                    this.rightClickDelayTimer.setAccessible(true);
                }
            }
        }
        if (this.blockHitDelay != null) {
            try {
                int i = this.blockHitDelay.getInt(Minecraft.func_71410_x().field_71442_b);
                if (i >= delay) {
                    this.blockHitDelay.setInt(Minecraft.func_71410_x().field_71442_b, delay);
                }
            }
            catch (IllegalArgumentException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalAccessException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
        }
        if (this.rightClickDelayTimer != null) {
            try {
                int i = this.rightClickDelayTimer.getInt(Minecraft.func_71410_x());
                if (i >= delay + 1) {
                    this.rightClickDelayTimer.setInt(Minecraft.func_71410_x(), delay + 1);
                }
            }
            catch (IllegalArgumentException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalAccessException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
        }
    }

    @Override
    public void updateFlightStatus(int mode) {
        EntityClientPlayerMP player = (EntityClientPlayerMP)this.getClientPlayer();
        if (mode != -1 && mode >= 1 && player.field_71075_bZ.field_75100_b) {
            MovementInput mi = player.field_71158_b;
            GameSettings settings = Minecraft.func_71410_x().field_71474_y;
            float sin = MathHelper.func_76126_a((float)(player.field_70177_z * (float)Math.PI / 180.0f));
            float cos = MathHelper.func_76134_b((float)(player.field_70177_z * (float)Math.PI / 180.0f));
            mi.field_78900_b = (float)(player.field_70179_y * (double)cos - player.field_70159_w * (double)sin);
            mi.field_78902_a = (float)(player.field_70179_y * (double)sin + player.field_70159_w * (double)cos);
            if (mi.field_78899_d) {
                if (mode >= 2) {
                    player.field_70181_x -= (double)this.mode2acceleration;
                    player.field_70181_x /= (double)this.mode2division;
                } else {
                    player.field_70181_x = -this.mode1velocity;
                }
            }
            if (mi.field_78901_c) {
                if (mode >= 2) {
                    player.field_70181_x += (double)this.mode2acceleration;
                    player.field_70181_x /= (double)this.mode2division;
                } else {
                    player.field_70181_x = this.mode1velocity;
                }
            }
            if (mi.field_78901_c == mi.field_78899_d) {
                player.field_70181_x = 0.0;
            }
            if (settings.field_74351_w.func_151470_d()) {
                if (mode >= 2) {
                    mi.field_78900_b += this.mode2acceleration;
                    mi.field_78900_b /= this.mode2division;
                } else {
                    mi.field_78900_b = this.mode1velocity;
                }
            }
            if (settings.field_74368_y.func_151470_d()) {
                if (mode >= 2) {
                    mi.field_78900_b -= this.mode2acceleration;
                    mi.field_78900_b /= this.mode2division;
                } else {
                    mi.field_78900_b = -this.mode1velocity;
                }
            }
            if (!settings.field_74351_w.func_151470_d() && !settings.field_74368_y.func_151470_d()) {
                mi.field_78900_b = 0.0f;
            }
            if (settings.field_74370_x.func_151470_d()) {
                if (mode >= 2) {
                    mi.field_78902_a += this.mode2acceleration;
                    mi.field_78902_a /= this.mode2division;
                } else {
                    mi.field_78902_a = this.mode1velocity;
                }
            }
            if (settings.field_74366_z.func_151470_d()) {
                if (mode >= 2) {
                    mi.field_78902_a -= this.mode2acceleration;
                    mi.field_78902_a /= this.mode2division;
                } else {
                    mi.field_78902_a = -this.mode1velocity;
                }
            }
            if (!settings.field_74370_x.func_151470_d() && !settings.field_74366_z.func_151470_d()) {
                mi.field_78902_a = 0.0f;
            }
            player.field_70159_w = mi.field_78902_a * cos - mi.field_78900_b * sin;
            player.field_70179_y = mi.field_78900_b * cos + mi.field_78902_a * sin;
        }
    }

    @Override
    public float hookBlockReachDistance(float distance) {
        return GadgetLongArm.hookBlockReachDistance(distance, this.getClientPlayer());
    }

    @Override
    public boolean isCreative(EntityPlayer player) {
        if (player == this.getClientPlayer()) {
            return Minecraft.func_71410_x().field_71442_b.func_78758_h();
        }
        return super.isCreative(player);
    }
}


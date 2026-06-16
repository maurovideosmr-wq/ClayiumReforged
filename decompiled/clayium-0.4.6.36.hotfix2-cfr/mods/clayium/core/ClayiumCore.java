/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.FMLCommonHandler
 *  cpw.mods.fml.common.IWorldGenerator
 *  cpw.mods.fml.common.Loader
 *  cpw.mods.fml.common.Mod
 *  cpw.mods.fml.common.Mod$EventHandler
 *  cpw.mods.fml.common.Mod$Instance
 *  cpw.mods.fml.common.SidedProxy
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPostInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.network.IGuiHandler
 *  cpw.mods.fml.common.network.NetworkRegistry
 *  cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
 *  cpw.mods.fml.common.registry.EntityRegistry
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraftforge.common.ForgeChunkManager
 *  net.minecraftforge.common.ForgeChunkManager$LoadingCallback
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.config.Configuration
 *  net.minecraftforge.fluids.FluidContainerRegistry
 *  net.minecraftforge.fluids.FluidContainerRegistry$FluidContainerData
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package mods.clayium.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import mods.clayium.block.BlockDamaged;
import mods.clayium.block.CBlocks;
import mods.clayium.block.ClayChunkLoader;
import mods.clayium.block.MetalBlock;
import mods.clayium.block.itemblock.ItemBlockCompressedClay;
import mods.clayium.block.itemblock.ItemBlockTiered;
import mods.clayium.block.tile.TileClayChunkLoader;
import mods.clayium.block.tile.TilePANAdapter;
import mods.clayium.core.ClayiumCommonProxy;
import mods.clayium.entity.EntityClayBall;
import mods.clayium.entity.EntityTeleportBall;
import mods.clayium.gui.CreativeTab;
import mods.clayium.gui.GuiHandler;
import mods.clayium.item.CItems;
import mods.clayium.item.CMaterials;
import mods.clayium.item.ClaySteelShovel;
import mods.clayium.item.ItemCapsule;
import mods.clayium.item.gadget.GadgetRepeatedlyAttack;
import mods.clayium.network.ClayChunkLoaderCallback;
import mods.clayium.network.ClaySteelPickaxePacket;
import mods.clayium.network.ClaySteelPickaxePacketHandler;
import mods.clayium.network.GuiButtonPacket;
import mods.clayium.network.GuiButtonPacketHandler;
import mods.clayium.network.GuiTextFieldPacket;
import mods.clayium.network.GuiTextFieldPacketHandler;
import mods.clayium.network.KeyInputEventPacket;
import mods.clayium.network.KeyInputEventPacketHandler;
import mods.clayium.network.MouseClickEventPacket;
import mods.clayium.network.MouseClickEventPacketHandler;
import mods.clayium.network.PANCoreListPacket;
import mods.clayium.network.PANCoreListPacketHandler;
import mods.clayium.pan.PANACFactoryClayMachines;
import mods.clayium.pan.PANACFactoryCraftingTable;
import mods.clayium.pan.PANACFactoryFurnace;
import mods.clayium.plugin.LoadIC2Plugin;
import mods.clayium.plugin.UtilGT;
import mods.clayium.plugin.minetweaker.MineTweakerRecipeHandler;
import mods.clayium.plugin.multipart.RegisterMultipart;
import mods.clayium.plugin.multipart.UtilMultipart;
import mods.clayium.util.UtilAdvancedTools;
import mods.clayium.util.UtilFluid;
import mods.clayium.util.UtilKeyInput;
import mods.clayium.util.UtilPlayer;
import mods.clayium.util.UtilTier;
import mods.clayium.util.crafting.CRecipes;
import mods.clayium.worldgen.ClayOreGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidContainerRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid="clayium", name="Clayium", version="0.4.6.36.hotfix2", dependencies="required-after:Forge@[10.13.4.1448,);after:ForgeMultipart;after:appliedenergistics2;after:Thaumcraft;after:BambooMod;after:ThermalFoundation;after:TConstruct;after:ThermalExpansion;after:BigReactors;after:Botania;after:HardcoreEnderExpansion;after:mod_ecru_MapleTree;after:IC2;after:Forestry;after:EnderIO;after:ExtraUtilities;after:factorization;after:GalacticraftCore;after:GalacticraftMars;after:Railcraft;after:TwilightForest;after:gregtech;after:Metallurgy;after:PneumaticCraft;after:ProjRed|Exploration;after:DCsAppleMilk;after:SextiarySector;after:TofuCraft;after:Mekanism", useMetadata=true)
public class ClayiumCore {
    public static final String modid = "clayium";
    public static final String modname = "Clayium";
    @Mod.Instance(value="clayium")
    public static ClayiumCore INSTANCE;
    @SidedProxy(clientSide="mods.clayium.core.ClayiumClientProxy", serverSide="mods.clayium.core.ClayiumCommonProxy")
    public static ClayiumCommonProxy proxy;
    public static int fluidTabRenderId;
    public static int clayContainerRenderId;
    public static int quartzCrucibleRenderId;
    public static int laserReflectorRenderId;
    public static int panCableRenderId;
    public static int metalChestRenderId;
    public static int EntityIdClayBall;
    public static int EntityIdTeleportBall;
    public static final int GuiIdClayWorkTable = 0;
    public static final int GuiIdClayMachines = 1;
    public static final int GuiIdClayAssembler = 2;
    public static final int GuiIdClayCentrifuge = 3;
    public static final int GuiIdClayChemicalReactor = 4;
    public static final int GuiIdAutoClayCondenser = 5;
    public static final int GuiIdChemicalMetalSeparator = 6;
    public static final int GuiIdClayBlastFurnace = 7;
    public static final int GuiIdClayReactor = 8;
    public static final int GuiIdCAReactor = 9;
    public static final int GuiIdClayWaterWheel = 10;
    public static final int GuiIdNormalInventory = 11;
    public static final int GuiIdSolarClayFabricator = 12;
    public static final int GuiIdClayEnergyLaser = 13;
    public static final int GuiIdClayDistributor = 14;
    public static final int GuiIdStorageContainer = 15;
    public static final int GuiIdAreaMiner = 16;
    public static final int GuiIdAutoCrafter = 17;
    public static final int GuiIdCACollector = 18;
    public static final int GuiIdAutoTrader = 19;
    public static final int GuiIdItemFilterWhitelist = 20;
    public static final int GuiIdItemFilterString = 21;
    public static final int GuiIdVacuumContainer = 22;
    public static final int GuiIdGadgetHolder = 23;
    public static final int GuiIdAreaActivator = 24;
    public static final int GuiIdClayCraftingTable = 30;
    public static final int GuiIdMultitrackBuffer = 31;
    public static final int GuiIdPANAdapter = 40;
    public static final int GuiIdPANCore = 41;
    public static final int GuiIdRFGenerator = 90;
    public static final int GuiIdClayInterface = 99;
    public static final CreativeTabs creativeTabClayium;
    public static CreativeTabs creativeTabClayiumCapsule;
    public static final SimpleNetworkWrapper packetDispatcher;
    public static Logger logger;
    public static Configuration configrationDefault;
    public static boolean cfgUtilityMode;
    public static boolean cfgHardcoreAluminium;
    public static boolean cfgHardcoreOsmium;
    public static double cfgProgressionRate;
    public static boolean cfgEnableInjectorRecipeOfInterface;
    public static boolean cfgInverseClayLaserRSCondition;
    public static int cfgClaySteelPickaxeRange;
    public static int cfgPacketSendingRate;
    public static boolean cfgEnableInstantSync;
    public static boolean cfgEnableFluidCapsule;
    public static int cfgFluidCapsuleCreativeTabMode;
    public static boolean cfgVerboseLoggingForFluidIDLoader;
    public static int cfgRenderingRate;
    public static boolean cfgCAReactorGlittering;
    public static int cfgLaserQuality;
    public static boolean cfgEnableAlternativeTileEntityName;
    public static String[] cfgRFGenerator;
    public static boolean cfgEnableRFGenerator;
    public static Map<IntegrationID, Boolean> cfgModIntegration;
    private EntityPlayer playerEntity;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        UtilFluid.loadMapsFromConfig(new Configuration(new File(event.getModConfigurationDirectory(), "clayiumFluidContainer.cfg")));
        MinecraftForge.EVENT_BUS.register((Object)UtilKeyInput.INSTANCE);
        FMLCommonHandler.instance().bus().register((Object)UtilKeyInput.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)UtilPlayer.INSTANCE);
        FMLCommonHandler.instance().bus().register((Object)UtilPlayer.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)new GadgetRepeatedlyAttack());
        if (!cfgUtilityMode) {
            UtilTier.setTierManagers();
        }
        configrationDefault = new Configuration(event.getSuggestedConfigurationFile());
        try {
            configrationDefault.load();
            cfgUtilityMode = configrationDefault.getBoolean("UtilityMode", "mode", false, "");
            cfgHardcoreAluminium = configrationDefault.getBoolean("HardcoreAluminium", "mode", false, "");
            cfgHardcoreOsmium = configrationDefault.getBoolean("HardcoreOsmium", "mode", false, "");
            cfgProgressionRate = configrationDefault.getFloat("ProgressionRate", "mode", 1.0f, 0.001f, 9999.0f, "");
            cfgEnableInjectorRecipeOfInterface = configrationDefault.getBoolean("EnableInjectorRecipeOfInterface", "mode", false, "This recipe makes it much easier to construct multi-block machines.");
            cfgClaySteelPickaxeRange = configrationDefault.getInt("ClaySteelPickaxeRange", "misc", 2, 0, 64, "");
            cfgPacketSendingRate = configrationDefault.getInt("PacketSendingRate", "misc", 20, 1, 9999, "");
            cfgEnableInstantSync = configrationDefault.getBoolean("EnableInstantSync", "misc", true, "");
            cfgInverseClayLaserRSCondition = configrationDefault.getBoolean("InvertClayLaserRSCondition", "misc", false, "");
            cfgEnableFluidCapsule = configrationDefault.getBoolean("EnableFluidCapsule", "misc", true, "");
            cfgFluidCapsuleCreativeTabMode = configrationDefault.getInt("FluidCapsuleCreativeTabMode", "misc", 1, 0, 2, "This setting is also valid for NEI.  0: Disable  1: 1000mB only  2: Display All");
            cfgVerboseLoggingForFluidIDLoader = configrationDefault.getBoolean("VerboseLoggingForFluidIDLoader", "misc", false, "");
            cfgEnableAlternativeTileEntityName = configrationDefault.getBoolean("EnableAlternativeTileEntityName", "misc", true, "Disable this if a tile entity id of this mod conflicts with one of other mod.");
            cfgRenderingRate = configrationDefault.getInt("RenderingRate", "render", 200, 1, 9999, "");
            cfgCAReactorGlittering = configrationDefault.getBoolean("CAReactorGlittering", "render", true, "");
            cfgLaserQuality = configrationDefault.getInt("LaserQuality", "render", 8, 1, 32, "");
            EntityIdClayBall = configrationDefault.getInt("ClayBall", "entityid", EntityRegistry.findGlobalUniqueEntityId(), 0, 65535, "");
            EntityIdTeleportBall = configrationDefault.getInt("TeleportBall", "entityid", EntityRegistry.findGlobalUniqueEntityId(), 0, 65535, "");
            for (IntegrationID integrationID : IntegrationID.values()) {
                cfgModIntegration.put(integrationID, configrationDefault.getBoolean(integrationID.configId, "integration", true, ""));
            }
            ClayOreGenerator.loadConfig(configrationDefault);
            if (!cfgUtilityMode) {
                UtilTier.loadConfig(configrationDefault);
            }
            cfgEnableRFGenerator = configrationDefault.getBoolean("EnableRFGenerator", "misc", false, "This setting enables assembler recipes for RF Converters.");
            cfgRFGenerator = new String[]{"BasicRFGenerator;rfgenerator:4:0.001:10:10:10000:1", "AdvancedRFGenerator;rfgenerator:5:0.01:30:30:30000:1", "PrecisionRFGenerator;rfgenerator:6:0.1:90:90:90000:1", "ClaySteelRFGenerator;rfgenerator:7:1:270:270:270000:1", "ClayiumRFGenerator;rfgenerator:8:10:810:810:810000:1", "UltimateRFGenerator;rfgenerator:9:100:2430:2430:2430000:1", "AntimatterRFGenerator;rfgenerator:10:1000:7290:7290:7290000:1", "PureAntimatterRFGenerator;rfgenerator:11:10000:21870:21870:21870000:1", "OECRFGenerator;rfgenerator:12:100000:65610:65610:65610000:1", "OPARFGenerator;rfgenerator:13:1000000:196830:196830:196830000:1"};
            cfgRFGenerator = configrationDefault.getStringList("RFGenerator", "misc", cfgRFGenerator, "BlockName;IconName:Tier:CEConsumptionPerTick:RFProductionPerTick:RFOutputPerTick:RFStorageSize:OverclockExponent");
            logger.info("Loaded RF Generator Settings.");
            for (String string : cfgRFGenerator) {
                logger.info(string);
            }
        }
        finally {
            configrationDefault.save();
        }
        ForgeChunkManager.setForcedChunkLoadingCallback((Object)INSTANCE, (ForgeChunkManager.LoadingCallback)new ClayChunkLoaderCallback());
        if (!cfgUtilityMode) {
            CRecipes.initRecipes();
            CBlocks.registerBlocks();
            CItems.registerItems();
            if (IntegrationID.MULTI_PART.loaded()) {
                new RegisterMultipart().init();
            }
        } else {
            CBlocks.blockCompressedClay = new BlockDamaged(Material.field_151571_B, 13).func_149658_d("clayium:compressedclay").func_149663_c("blockCompressedClay").func_149647_a(creativeTabClayium).func_149711_c(1.0f).func_149752_b(1.0f).func_149672_a(Block.field_149767_g);
            CBlocks.blockCompressedClay.setHarvestLevel("shovel", 0);
            GameRegistry.registerBlock((Block)CBlocks.blockCompressedClay, ItemBlockCompressedClay.class, (String)"blockCompressedClay");
            CBlocks.blockClayChunkLoader = new ClayChunkLoader(6).func_149663_c("blockClayChunkLoader").func_149647_a(creativeTabClayium);
            GameRegistry.registerBlock((Block)CBlocks.blockClayChunkLoader, ItemBlockTiered.class, (String)"blockClayChunkLoader");
            GameRegistry.registerTileEntity(TileClayChunkLoader.class, (String)"tileClayChunkLoader");
            CItems.itemClaySteelPickaxe = proxy.newClaySteelPickaxe();
            GameRegistry.registerItem((Item)CItems.itemClaySteelPickaxe, (String)"itemClaySteelPickaxe");
            CItems.itemClaySteelShovel = new ClaySteelShovel();
            GameRegistry.registerItem((Item)CItems.itemClaySteelShovel, (String)"itemClaySteelShovel");
            MinecraftForge.EVENT_BUS.register((Object)UtilAdvancedTools.INSTANCE);
        }
        CMaterials.registerMaterials();
        fluidTabRenderId = proxy.getRenderID();
        clayContainerRenderId = proxy.getRenderID();
        quartzCrucibleRenderId = proxy.getRenderID();
        laserReflectorRenderId = proxy.getRenderID();
        panCableRenderId = proxy.getRenderID();
        metalChestRenderId = proxy.getRenderID();
        proxy.registerTileEntity();
        proxy.registerRenderer();
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)INSTANCE, (IGuiHandler)new GuiHandler());
        packetDispatcher.registerMessage(GuiButtonPacketHandler.class, GuiButtonPacket.class, 0, Side.SERVER);
        packetDispatcher.registerMessage(ClaySteelPickaxePacketHandler.class, ClaySteelPickaxePacket.class, 1, Side.SERVER);
        packetDispatcher.registerMessage(MouseClickEventPacketHandler.class, MouseClickEventPacket.class, 2, Side.SERVER);
        packetDispatcher.registerMessage(KeyInputEventPacketHandler.class, KeyInputEventPacket.class, 5, Side.SERVER);
        packetDispatcher.registerMessage(GuiTextFieldPacketHandler.class, GuiTextFieldPacket.class, 3, Side.SERVER);
        packetDispatcher.registerMessage(GuiTextFieldPacketHandler.class, GuiTextFieldPacket.class, 4, Side.CLIENT);
        packetDispatcher.registerMessage(PANCoreListPacketHandler.class, PANCoreListPacket.class, 7, Side.CLIENT);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CRecipes.registerRecipes();
        EntityRegistry.registerModEntity(EntityClayBall.class, (String)"clayBall", (int)EntityIdClayBall, (Object)this, (int)128, (int)1, (boolean)true);
        EntityRegistry.registerModEntity(EntityTeleportBall.class, (String)"teleportBall", (int)EntityIdTeleportBall, (Object)this, (int)128, (int)1, (boolean)true);
        if (!cfgUtilityMode) {
            GameRegistry.registerWorldGenerator((IWorldGenerator)new ClayOreGenerator(), (int)0);
        }
        TilePANAdapter.addConversionFactory(new PANACFactoryCraftingTable());
        TilePANAdapter.addConversionFactory(new PANACFactoryFurnace());
        TilePANAdapter.addConversionFactory(new PANACFactoryClayMachines());
        if (IntegrationID.MULTI_PART.loaded()) {
            BlockDamaged[] blocks0 = new BlockDamaged[]{(BlockDamaged)CBlocks.blockClayOre, (BlockDamaged)CBlocks.blockCompressedClay, (BlockDamaged)CBlocks.blockRawClayMachineHull, (BlockDamaged)CBlocks.blockMachineHull, CBlocks.blockOthersHull, CBlocks.blockMaterial, CBlocks.blockSiliconeColored};
            BlockDamaged[][] blockDamagedArrayArray = new BlockDamaged[][]{blocks0, MetalBlock.metalBlockMap.values().toArray(new BlockDamaged[0])};
            int n = blockDamagedArrayArray.length;
            for (int i = 0; i < n; ++i) {
                BlockDamaged[] blocks;
                for (BlockDamaged block : blocks = blockDamagedArrayArray[i]) {
                    for (Integer i2 : block.getAvailableMetadata()) {
                        UtilMultipart.registerMicroBlock(block, i2);
                    }
                }
            }
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.LoadNEI();
        if (IntegrationID.IC2.loaded()) {
            LoadIC2Plugin.loadRecipes();
        }
        if (IntegrationID.MINE_TWEAKER.loaded()) {
            MineTweakerRecipeHandler.load();
        }
        if (cfgEnableFluidCapsule) {
            for (ItemCapsule itemCapsule : CItems.itemsCapsule) {
                itemCapsule.registerFluidContainer(cfgFluidCapsuleCreativeTabMode >= 2 || cfgFluidCapsuleCreativeTabMode >= 1 && itemCapsule.getCapacity() == 1000);
            }
            GameRegistry.addRecipe((IRecipe)new ItemCapsule.RecipeCapsulePackaging());
            GameRegistry.addRecipe((IRecipe)new ItemCapsule.RecipeCapsuleUnpackaging());
            for (ItemCapsule itemCapsule : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                for (ItemCapsule capsule : ItemCapsule.getAllCapsules()) {
                    if (capsule.getCapacity() != ((FluidContainerRegistry.FluidContainerData)itemCapsule).fluid.amount || ((FluidContainerRegistry.FluidContainerData)itemCapsule).filledContainer.func_77973_b() instanceof ItemCapsule) continue;
                    try {
                        if (((FluidContainerRegistry.FluidContainerData)itemCapsule).emptyContainer != null) {
                            CRecipes.recipeFluidTransferMachine.addRecipe(new ItemStack[]{((FluidContainerRegistry.FluidContainerData)itemCapsule).emptyContainer.func_77946_l(), new ItemStack((Item)capsule, 1, UtilFluid.getFluidID(((FluidContainerRegistry.FluidContainerData)itemCapsule).fluid))}, 0, 5, new ItemStack[]{((FluidContainerRegistry.FluidContainerData)itemCapsule).filledContainer.func_77946_l()}, CRecipes.e(5), 1L);
                            CRecipes.recipeFluidTransferMachine.addRecipe(new ItemStack[]{((FluidContainerRegistry.FluidContainerData)itemCapsule).filledContainer.func_77946_l()}, 0, 5, new ItemStack[]{((FluidContainerRegistry.FluidContainerData)itemCapsule).emptyContainer.func_77946_l(), new ItemStack((Item)capsule, 1, UtilFluid.getFluidID(((FluidContainerRegistry.FluidContainerData)itemCapsule).fluid))}, CRecipes.e(5), 1L);
                            continue;
                        }
                        CRecipes.recipeFluidTransferMachine.addRecipe(new ItemStack[]{((FluidContainerRegistry.FluidContainerData)itemCapsule).filledContainer.func_77946_l()}, 0, 5, new ItemStack[]{new ItemStack((Item)capsule, 1, UtilFluid.getFluidID(((FluidContainerRegistry.FluidContainerData)itemCapsule).fluid))}, CRecipes.e(5), 1L);
                    }
                    catch (NullPointerException e) {
                        logger.error("An exception was thrown while registering fluid recipes.  Container : " + ((FluidContainerRegistry.FluidContainerData)itemCapsule).filledContainer + " Fluid : " + ((FluidContainerRegistry.FluidContainerData)itemCapsule).fluid.getUnlocalizedName());
                        e.printStackTrace();
                    }
                }
            }
        }
        UtilGT.addItemToBlackListGTUnification(CItems.itemMisc.get("AdvancedCircuit"));
        UtilGT.addItemToBlackListGTUnification(CItems.itemMisc.get("PrecisionCircuit"));
        UtilGT.addItemToBlackListGTUnification(CItems.itemMisc.get("IntegratedCircuit"));
        UtilGT.addItemToBlackListGTUnification(CItems.itemMisc.get("ClayCore"));
    }

    public static double multiplyProgressionRateD(double a) {
        return a * cfgProgressionRate;
    }

    public static float multiplyProgressionRateF(float a) {
        return (float)((double)a * cfgProgressionRate);
    }

    public static int multiplyProgressionRateI(int a) {
        int r = (int)((double)a * cfgProgressionRate);
        return r != 0 ? r : (a < 0 ? -1 : (a > 0 ? 1 : 0));
    }

    public static long multiplyProgressionRateL(long a) {
        long r = (long)((double)a * cfgProgressionRate);
        return r != 0L ? r : (a < 0L ? -1L : (a > 0L ? 1L : 0L));
    }

    public static int multiplyProgressionRateStackSize(int a) {
        int r = ClayiumCore.multiplyProgressionRateI(a);
        return r > 64 ? 64 : r;
    }

    public static double divideByProgressionRateD(double a) {
        return a / cfgProgressionRate;
    }

    public static float divideByProgressionRateF(float a) {
        return (float)((double)a / cfgProgressionRate);
    }

    public static int divideByProgressionRateI(int a) {
        int r = (int)((double)a / cfgProgressionRate);
        return r != 0 ? r : (a < 0 ? -1 : (a > 0 ? 1 : 0));
    }

    public static long divideByProgressionRateL(long a) {
        long r = (long)((double)a / cfgProgressionRate);
        return r != 0L ? r : (a < 0L ? -1L : (a > 0L ? 1L : 0L));
    }

    public static int divideByProgressionRateStackSize(int a) {
        int r = ClayiumCore.divideByProgressionRateI(a);
        return r > 64 ? 64 : r;
    }

    public static boolean debug() {
        ItemStack item = new ItemStack(Items.field_151034_e, 1, 0);
        item.field_77994_a = 20;
        String s = "for compiling test";
        return true;
    }

    public void test() {
        Entity entity = null;
        boolean flag = this.playerEntity.func_70685_l(entity);
        double d0 = 36.0;
        if (!flag) {
            d0 = 9.0;
        }
    }

    public void test2() {
        Entity entity = null;
        boolean flag = this.playerEntity.func_70685_l(entity);
        double d0 = ClayiumCore.method(36.0f, this.playerEntity);
        if (!flag) {
            d0 = ClayiumCore.method(9.0f, this.playerEntity);
        }
    }

    public static float method(float f, EntityPlayer player) {
        return f;
    }

    static {
        creativeTabClayium = new CreativeTab(modname);
        packetDispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(modid);
        logger = LogManager.getLogger((String)modid);
        configrationDefault = null;
        cfgUtilityMode = false;
        cfgHardcoreAluminium = false;
        cfgHardcoreOsmium = false;
        cfgProgressionRate = 1.0;
        cfgEnableInjectorRecipeOfInterface = false;
        cfgInverseClayLaserRSCondition = false;
        cfgClaySteelPickaxeRange = 2;
        cfgPacketSendingRate = 20;
        cfgEnableInstantSync = true;
        cfgEnableFluidCapsule = true;
        cfgFluidCapsuleCreativeTabMode = 1;
        cfgVerboseLoggingForFluidIDLoader = false;
        cfgRenderingRate = 200;
        cfgCAReactorGlittering = true;
        cfgLaserQuality = 8;
        cfgEnableAlternativeTileEntityName = true;
        cfgRFGenerator = new String[0];
        cfgEnableRFGenerator = false;
        cfgModIntegration = new HashMap<IntegrationID, Boolean>();
        MinecraftForge.EVENT_BUS.register((Object)UtilFluid.INSTANCE);
    }

    public static enum IntegrationID {
        NEI("NotEnoughItems", "NotEnoughItems"),
        MINE_TWEAKER("MineTweaker3", "MineTweaker3"),
        MULTI_PART("ForgeMultipart", "ForgeMultipart"),
        IC2("IC2", "IndustrialCraft2"),
        EIO("EnderIO", "EnderIO"),
        TF("ThermalFoundation", "ThermalFoundation"),
        FFM("Forestry", "Forestry"),
        AE2("appliedenergistics2", "AppliedEnergistics2"),
        TIC("TConstruct", "TinkersConstruct"),
        PR_EX("ProjRed|Exploration", "ProjectRedExploration"),
        MEK("Mekanism", "Mekanism"),
        BR("BigReactors", "BigReactors"),
        GC("GalacticraftCore", "Galacticraft"),
        FZ("factorization", "Factorization"),
        GT("gregtech", "GregTech"),
        MET("Metallurgy", "Metallurgy"),
        SS2("SextiarySector", "SextiarySector"),
        MAPLE("mod_ecru_MapleTree", "MapleTree"),
        TOFU("TofuCraft", "TofuCraft"),
        MISC("Misc");

        public final boolean isMod;
        public final String modId;
        public final String configId;

        private IntegrationID(String modId, String configId) {
            this.isMod = true;
            this.modId = modId;
            this.configId = configId;
        }

        private IntegrationID(String configId) {
            this.isMod = false;
            this.modId = "";
            this.configId = configId;
        }

        public boolean enabled() {
            Boolean b = cfgModIntegration.get((Object)this);
            return b == null ? false : b;
        }

        public boolean loaded() {
            return this.enabled() && this.isMod && Loader.isModLoaded((String)this.modId);
        }
    }
}


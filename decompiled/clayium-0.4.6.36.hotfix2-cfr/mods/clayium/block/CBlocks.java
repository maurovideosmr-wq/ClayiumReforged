/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraftforge.oredict.OreDictionary
 */
package mods.clayium.block;

import cpw.mods.fml.common.registry.GameRegistry;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mods.clayium.block.AreaActivator;
import mods.clayium.block.AreaCollector;
import mods.clayium.block.AreaMiner;
import mods.clayium.block.AutoTrader;
import mods.clayium.block.BlockDamaged;
import mods.clayium.block.BlockEnergyStorageUpgrade;
import mods.clayium.block.BlockOverclocker;
import mods.clayium.block.BlockResonator;
import mods.clayium.block.BlockSiliconeColored;
import mods.clayium.block.CAReactor;
import mods.clayium.block.ClayAssembler;
import mods.clayium.block.ClayAutoCrafter;
import mods.clayium.block.ClayBlastFurnace;
import mods.clayium.block.ClayBuffer;
import mods.clayium.block.ClayChemicalReactor;
import mods.clayium.block.ClayChunkLoader;
import mods.clayium.block.ClayContainerTiered;
import mods.clayium.block.ClayCraftingTable;
import mods.clayium.block.ClayDistributor;
import mods.clayium.block.ClayEnergyLaser;
import mods.clayium.block.ClayFabricator;
import mods.clayium.block.ClayInterface;
import mods.clayium.block.ClayLaserInterface;
import mods.clayium.block.ClayMachines;
import mods.clayium.block.ClayMarker;
import mods.clayium.block.ClayNoRecipeMachines;
import mods.clayium.block.ClayOre;
import mods.clayium.block.ClayRFGenerator;
import mods.clayium.block.ClayReactor;
import mods.clayium.block.ClayTreeLeaf;
import mods.clayium.block.ClayTreeLog;
import mods.clayium.block.ClayTreeSapling;
import mods.clayium.block.ClayWorkTable;
import mods.clayium.block.CobblestoneGenerator;
import mods.clayium.block.DenseClay;
import mods.clayium.block.FluidTranslator;
import mods.clayium.block.LaserReflector;
import mods.clayium.block.MachineHull;
import mods.clayium.block.MetalChest;
import mods.clayium.block.MultitrackBuffer;
import mods.clayium.block.PANAdapter;
import mods.clayium.block.PANCable;
import mods.clayium.block.PANCore;
import mods.clayium.block.PANDuplicator;
import mods.clayium.block.QuartzCrucible;
import mods.clayium.block.RedstoneInterface;
import mods.clayium.block.SaltExtractor;
import mods.clayium.block.SolarClayFabricator;
import mods.clayium.block.StorageContainer;
import mods.clayium.block.VacuumContainer;
import mods.clayium.block.WaterWheel;
import mods.clayium.block.itemblock.ItemBlockCompressedClay;
import mods.clayium.block.itemblock.ItemBlockDamaged;
import mods.clayium.block.itemblock.ItemBlockTiered;
import mods.clayium.block.tile.TileAreaActivator;
import mods.clayium.block.tile.TileAreaCollector;
import mods.clayium.block.tile.TileAreaMiner;
import mods.clayium.block.tile.TileAutoClayCondenser;
import mods.clayium.block.tile.TileAutoCrafter;
import mods.clayium.block.tile.TileAutoTrader;
import mods.clayium.block.tile.TileCACollector;
import mods.clayium.block.tile.TileCACondenser;
import mods.clayium.block.tile.TileCAInjector;
import mods.clayium.block.tile.TileCAReactor;
import mods.clayium.block.tile.TileChemicalMetalSeparator;
import mods.clayium.block.tile.TileClayAssembler;
import mods.clayium.block.tile.TileClayBlastFurnace;
import mods.clayium.block.tile.TileClayBuffer;
import mods.clayium.block.tile.TileClayCentrifuge;
import mods.clayium.block.tile.TileClayChemicalReactor;
import mods.clayium.block.tile.TileClayChunkLoader;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.block.tile.TileClayContainerInterface;
import mods.clayium.block.tile.TileClayCraftingTable;
import mods.clayium.block.tile.TileClayDistributor;
import mods.clayium.block.tile.TileClayEnergyLaser;
import mods.clayium.block.tile.TileClayFabricator;
import mods.clayium.block.tile.TileClayLaserInterface;
import mods.clayium.block.tile.TileClayMachines;
import mods.clayium.block.tile.TileClayMarker;
import mods.clayium.block.tile.TileClayOpenPitMarker;
import mods.clayium.block.tile.TileClayRFGenerator;
import mods.clayium.block.tile.TileClayReactor;
import mods.clayium.block.tile.TileClayWorkTable;
import mods.clayium.block.tile.TileCobblestoneGenerator;
import mods.clayium.block.tile.TileCreativeEnergySource;
import mods.clayium.block.tile.TileFluidTranslator;
import mods.clayium.block.tile.TileLaserReflector;
import mods.clayium.block.tile.TileMetalChest;
import mods.clayium.block.tile.TileMultitrackBuffer;
import mods.clayium.block.tile.TilePANAdapter;
import mods.clayium.block.tile.TilePANCore;
import mods.clayium.block.tile.TilePANDuplicator;
import mods.clayium.block.tile.TileQuartzCrucible;
import mods.clayium.block.tile.TileRedstoneInterface;
import mods.clayium.block.tile.TileSaltExtractor;
import mods.clayium.block.tile.TileSolarClayFabricator;
import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.block.tile.TileVacuumContainer;
import mods.clayium.block.tile.TileWaterWheel;
import mods.clayium.core.ClayiumCore;
import mods.clayium.misc.TileFluidTab;
import mods.clayium.util.UtilLocale;
import mods.clayium.util.UtilTier;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public class CBlocks {
    public static Block blockClayWorkTable;
    public static Block blockClayCraftingTable;
    public static Block blockDenseClay;
    public static Block blockCompressedClay;
    public static Block blockRawClayMachineHull;
    public static Block blockMachineHull;
    public static BlockDamaged blockOthersHull;
    public static BlockDamaged blockMaterial;
    public static BlockDamaged blockSiliconeColored;
    public static Block[] blocksBendingMachine;
    public static Block[] blocksWireDrawingMachine;
    public static Block[] blocksPipeDrawingMachine;
    public static Block[] blocksCuttingMachine;
    public static Block[] blocksLathe;
    public static Block[] blocksCobblestoneGenerator;
    public static Block blockElementalMillingMachine;
    public static Block[] blocksCondenser;
    public static Block[] blocksGrinder;
    public static Block[] blocksDecomposer;
    public static Block[] blocksMillingMachine;
    public static Block blockClayWaterWheel;
    public static Block blockDenseClayWaterWheel;
    public static Block blockEnergeticClayCondenser;
    public static Block blockEnergeticClayCondenserMK2;
    public static Block[] blocksAssembler;
    public static Block[] blocksInscriber;
    public static Block[] blocksCentrifuge;
    public static Block[] blocksSmelter;
    public static Block[] blocksChemicalReactor;
    public static Block[] blocksBuffer;
    public static Block[] blocksMultitrackBuffer;
    public static Block[] blocksSaltExtractor;
    public static Block blockAutoClayCondenser;
    public static Block blockAutoClayCondenserMK2;
    public static Block blockQuartzCrucible;
    public static Block blockSolarClayFabricatorMK1;
    public static Block blockSolarClayFabricatorMK2;
    public static Block blockLithiumSolarClayFabricator;
    public static Block blockClayFabricatorMK1;
    public static Block blockClayFabricatorMK2;
    public static Block blockClayFabricatorMK3;
    public static Block[] blocksAlloySmelter;
    public static Block[] blocksAutoCrafter;
    public static Block blockChemicalMetalSeparator;
    public static Block blockClayBlastFurnace;
    public static Block[] blocksClayInterface;
    public static Block[] blocksRedstoneInterface;
    public static Block[] blocksClayLaserInterface;
    public static Block[] blocksElectrolysisReactor;
    public static Block[] blocksClayEnergyLaser;
    public static Block[] blocksDistributor;
    public static Block blockLaserReflector;
    public static Block blockClayReactor;
    public static Block[] blocksTransformer;
    public static Block[] blocksCACondenser;
    public static Block[] blocksCAInjector;
    public static Block blockCACollector;
    public static BlockDamaged blockResonator;
    public static BlockDamaged blockEnergyStorageUpgrade;
    public static BlockDamaged blockOverclocker;
    public static BlockDamaged blockCAReactorCoil;
    public static BlockDamaged blockCAReactorHull;
    public static Block[] blocksCAReactorCore;
    public static Block blockEnergeticClayDecomposer;
    public static Block blockCreativeCESource;
    public static Block blockClayOre;
    public static Block blockClayTreeSapling;
    public static Block blockClayTreeLeaf;
    public static Block blockClayTreeLog;
    public static Block blockClayChunkLoader;
    public static Block blockStorageContainer;
    public static Block blockVacuumContainer;
    public static Block blockAutoTrader;
    public static Block blockClayMarker;
    public static Block blockClayOpenPitMarker;
    public static Block blockClayGroundLevelingMarker;
    public static Block blockClayPrismMarker;
    public static Block blockAreaCollector;
    public static Block blockMiner;
    public static Block blockAreaMiner;
    public static Block blockAdvancedAreaMiner;
    public static Block blockAreaReplacer;
    public static Block blockActivator;
    public static Block blockAreaActivator;
    public static Block blockMetalChest;
    public static Block[] blocksFluidTranslator;
    public static Block[] blocksFluidTransferMachine;
    public static Block blockPANCore;
    public static Block[] blocksPANAdapter;
    public static Block[] blocksPANDuplicator;
    public static Block blockPANCable;
    public static Map<String, Block> blocksRFGenerator;
    public static final String[] tierPrefix;

    public static void registerBlocks() {
        int i;
        blockClayOre = new ClayOre().func_149663_c("blockClayOre").func_149658_d("clayium:clayore");
        GameRegistry.registerBlock((Block)blockClayOre, ItemBlockDamaged.class, (String)"blockClayOre");
        OreDictionary.registerOre((String)"oreClay", (ItemStack)new ItemStack(blockClayOre, 1, 0));
        OreDictionary.registerOre((String)"oreDenseClay", (ItemStack)new ItemStack(blockClayOre, 1, 1));
        OreDictionary.registerOre((String)"oreLargeDenseClay", (ItemStack)new ItemStack(blockClayOre, 1, 2));
        blockDenseClay = new DenseClay();
        blockCompressedClay = new BlockDamaged(Material.field_151571_B, 13).func_149658_d("clayium:compressedclay").func_149663_c("blockCompressedClay").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(1.0f).func_149752_b(1.0f).func_149672_a(Block.field_149767_g);
        blockCompressedClay.setHarvestLevel("shovel", 0);
        GameRegistry.registerBlock((Block)blockCompressedClay, ItemBlockCompressedClay.class, (String)"blockCompressedClay");
        blockRawClayMachineHull = new BlockDamaged(Material.field_151571_B, 1).func_149658_d("clayium:rawclaymachinehull").func_149663_c("blockRawClayMachineHull").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(1.0f).func_149752_b(1.0f).func_149672_a(Block.field_149767_g);
        blockRawClayMachineHull.setHarvestLevel("shovel", 0);
        ((BlockDamaged)blockRawClayMachineHull).setTier(1);
        GameRegistry.registerBlock((Block)blockRawClayMachineHull, ItemBlockDamaged.class, (String)"blockRawClayMachineHull");
        blockMachineHull = new MachineHull(13);
        GameRegistry.registerBlock((Block)blockMachineHull, ItemBlockDamaged.class, (String)"blockMachineHull");
        blockOthersHull = new BlockDamaged(Material.field_151573_f);
        blockOthersHull.func_149658_d("clayium:othershull").func_149663_c("blockOthersHull").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(2.0f).func_149752_b(2.0f).func_149672_a(Block.field_149777_j);
        blockOthersHull.addBlockList("az91d", 0).setTier(6).setSubBlockName("blockAZ91DHull").setIconName("clayium:az91dhull");
        blockOthersHull.addBlockList("zk60a", 1).setTier(6).setSubBlockName("blockZK60AHull").setIconName("clayium:zk60ahull");
        GameRegistry.registerBlock((Block)blockOthersHull, ItemBlockDamaged.class, (String)"blockOthersHull");
        blockClayWorkTable = new ClayWorkTable().func_149658_d("clayium:clayworktable").func_149663_c("blockClayWorkTable").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(2.0f).func_149752_b(2.0f);
        GameRegistry.registerBlock((Block)blockClayWorkTable, ItemBlockTiered.class, (String)"blockClayWorkTable");
        CBlocks.registerTileEntity(TileClayWorkTable.class, "ClayWorkTable");
        blockClayCraftingTable = new ClayCraftingTable(0).func_149663_c("blockClayCraftingTable").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayCraftingTable, ItemBlockTiered.class, (String)"blockClayCraftingTable");
        CBlocks.registerTileEntity(TileClayCraftingTable.class, "ClayCraftingTable");
        CBlocks.registerTileEntity(TileClayMachines.class, "ClayMachines");
        blockElementalMillingMachine = new ClayMachines("MillingMachine", "clayium:millingmachine", 1).func_149663_c("blockElementalMillingMachine").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockElementalMillingMachine, ItemBlockTiered.class, (String)"blockElementalMillingMachine");
        blockEnergeticClayCondenser = new ClayMachines("ECCondenser", "clayium:eccondenser", 3).func_149663_c("blockEnergeticClayCondenser").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockEnergeticClayCondenser, ItemBlockTiered.class, (String)"blockEnergeticClayCondenser");
        blockEnergeticClayCondenserMK2 = new ClayMachines("ECCondenser", "clayium:eccondenser", 4).func_149663_c("blockEnergeticClayCondenserMK2").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockEnergeticClayCondenserMK2, ItemBlockTiered.class, (String)"blockEnergeticClayCondenserMK2");
        blocksBendingMachine = CBlocks.registerTieredMachines("BendingMachine", "bendingmachine", "BendingMachine", new int[]{1, 2, 3, 4, 5, 6, 7, 9});
        blocksWireDrawingMachine = CBlocks.registerTieredMachines("WireDrawingMachine", "wiredrawingmachine", "WireDrawingMachine", new int[]{1, 2, 3, 4});
        blocksPipeDrawingMachine = CBlocks.registerTieredMachines("PipeDrawingMachine", "pipedrawingmachine", "PipeDrawingMachine", new int[]{1, 2, 3, 4});
        blocksCuttingMachine = CBlocks.registerTieredMachines("CuttingMachine", "cuttingmachine", "CuttingMachine", new int[]{1, 2, 3, 4});
        blocksLathe = CBlocks.registerTieredMachines("Lathe", "lathe", "Lathe", new int[]{1, 2, 3, 4});
        blocksCobblestoneGenerator = new Block[16];
        for (i = 1; i <= 7; ++i) {
            CBlocks.blocksCobblestoneGenerator[i] = new CobblestoneGenerator(i);
            blocksCobblestoneGenerator[i].func_149663_c("block" + tierPrefix[i] + "CobblestoneGenerator").func_149647_a(ClayiumCore.creativeTabClayium);
            GameRegistry.registerBlock((Block)blocksCobblestoneGenerator[i], ItemBlockTiered.class, (String)("block" + tierPrefix[i] + "CobblestoneGenerator"));
        }
        CBlocks.registerTileEntity(TileCobblestoneGenerator.class, "CobblestoneGenerator");
        blocksCondenser = CBlocks.registerTieredMachines("Condenser", "condenser", "Condenser", new int[]{2, 3, 4, 5, 10});
        UtilTier.TierManager.applyMachineTierManager(blocksCondenser, UtilTier.tierGeneric);
        blocksGrinder = CBlocks.registerTieredMachines("Grinder", "grinder", "Grinder", new int[]{2, 3, 4, 5, 6, 10});
        UtilTier.TierManager.applyMachineTierManager(blocksGrinder, UtilTier.tierGeneric);
        blocksDecomposer = CBlocks.registerTieredMachines("Decomposer", "decomposer", "Decomposer", new int[]{2, 3, 4});
        blocksMillingMachine = CBlocks.registerTieredMachines("MillingMachine", "millingmachine", "MillingMachine", new int[]{3, 4});
        blocksAssembler = CBlocks.registerTieredMachines("Assembler", "assembler", "Assembler", new int[]{3, 4, 6, 10}, ClayAssembler.class, ItemBlockTiered.class);
        CBlocks.registerTileEntity(TileClayAssembler.class, "ClayAssembler");
        blocksInscriber = CBlocks.registerTieredMachines("Inscriber", "inscriber", "Inscriber", new int[]{3, 4}, ClayAssembler.class, ItemBlockTiered.class);
        blocksCentrifuge = CBlocks.registerTieredMachines("Centrifuge", "centrifuge", "Centrifuge", new int[]{3, 4, 5, 6}, TileClayCentrifuge.class, 3);
        CBlocks.registerTileEntity(TileClayCentrifuge.class, "ClayCentrifuge");
        UtilTier.TierManager.applyMachineTierManager(blocksCentrifuge, UtilTier.tierGeneric);
        blocksSmelter = CBlocks.registerTieredMachines("Smelter", "smelter", "Smelter", new int[]{4, 5, 6, 7, 8, 9});
        UtilTier.TierManager.applyMachineTierManager(blocksSmelter, UtilTier.tierSmelter);
        blocksBuffer = CBlocks.registerTieredContainers("Buffer", new int[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, ClayBuffer.class);
        CBlocks.registerTileEntity(TileClayBuffer.class, "ClayBuffer");
        blocksMultitrackBuffer = CBlocks.registerTieredContainers("MultitrackBuffer", new int[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, MultitrackBuffer.class);
        CBlocks.registerTileEntity(TileMultitrackBuffer.class, "MultitrackBuffer");
        blockCreativeCESource = new ClayNoRecipeMachines("", "", "clayium:creativeenergy", 13, TileCreativeEnergySource.class, 11, 2).func_149663_c("blockCreativeCESource").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockCreativeCESource, ItemBlockTiered.class, (String)"blockCreativeCESource");
        CBlocks.registerTileEntity(TileCreativeEnergySource.class, "CreativeCESource");
        blocksChemicalReactor = CBlocks.registerTieredMachines("ChemicalReactor", "chemicalreactor", "ChemicalReactor", new int[]{4, 5, 8}, ClayChemicalReactor.class, ItemBlockTiered.class);
        CBlocks.registerTileEntity(TileClayChemicalReactor.class, "ClayChemicalReactor");
        blocksSaltExtractor = CBlocks.registerTieredContainers("SaltExtractor", new int[]{4, 5, 6, 7}, SaltExtractor.class);
        CBlocks.registerTileEntity(TileSaltExtractor.class, "SaltExtractor");
        blockAutoClayCondenser = new ClayMachines("clayium:autoclaycondenser", 5, TileAutoClayCondenser.class, 5).func_149663_c("blockAutoClayCondenser").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockAutoClayCondenser, ItemBlockTiered.class, (String)"blockAutoClayCondenser");
        blockAutoClayCondenserMK2 = new ClayMachines("clayium:autoclaycondenser", 7, TileAutoClayCondenser.class, 5).func_149663_c("blockAutoClayCondenserMK2").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockAutoClayCondenserMK2, ItemBlockTiered.class, (String)"blockAdvancedAutoClayCondenser");
        CBlocks.registerTileEntity(TileAutoClayCondenser.class, "AutoClayCondenser");
        blockQuartzCrucible = new QuartzCrucible();
        blockQuartzCrucible.func_149663_c("blockQuartzCrucible").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockQuartzCrucible, ItemBlockTiered.class, (String)"blockQuartzCrucible");
        CBlocks.registerTileEntity(TileQuartzCrucible.class, "QuartzCrucible");
        blockSolarClayFabricatorMK1 = new SolarClayFabricator(null, 5).func_149663_c("blockSolarClayFabricatorMK1").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockSolarClayFabricatorMK1, ItemBlockTiered.class, (String)"blockSolarClayFabricatorMK1");
        blockSolarClayFabricatorMK2 = new SolarClayFabricator(null, 6).func_149663_c("blockSolarClayFabricatorMK2").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockSolarClayFabricatorMK2, ItemBlockTiered.class, (String)"blockSolarClayFabricatorMK2");
        blockLithiumSolarClayFabricator = new SolarClayFabricator(null, 7).func_149663_c("blockLithiumSolarClayFabricator").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockLithiumSolarClayFabricator, ItemBlockTiered.class, (String)"blockLithiumSolarClayFabricator");
        CBlocks.registerTileEntity(TileSolarClayFabricator.class, "SolarClayFabricator");
        blockClayFabricatorMK1 = new ClayFabricator(8).func_149663_c("blockClayFabricatorMK1").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayFabricatorMK1, ItemBlockTiered.class, (String)"blockClayFabricatorMK1");
        blockClayFabricatorMK2 = new ClayFabricator(9).func_149663_c("blockClayFabricatorMK2").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayFabricatorMK2, ItemBlockTiered.class, (String)"blockClayFabricatorMK2");
        blockClayFabricatorMK3 = new ClayFabricator(13).func_149663_c("blockClayFabricatorMK3").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayFabricatorMK3, ItemBlockTiered.class, (String)"blockClayFabricatorMK3");
        CBlocks.registerTileEntity(TileClayFabricator.class, "ClayFabricator");
        blockChemicalMetalSeparator = new ClayMachines("clayium:chemicalmetalseparator", 6, TileChemicalMetalSeparator.class, 6).func_149663_c("blockChemicalMetalSeparator").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockChemicalMetalSeparator, ItemBlockTiered.class, (String)"blockChemicalMetalSeparator");
        CBlocks.registerTileEntity(TileChemicalMetalSeparator.class, "ChemicalMetalSeparator");
        blocksAlloySmelter = CBlocks.registerTieredMachines("AlloySmelter", "alloysmelter", "AlloySmelter", new int[]{6}, ClayAssembler.class, ItemBlockTiered.class);
        blockClayBlastFurnace = new ClayBlastFurnace("BlastFurnace", "clayium:blastfurnace", 6).func_149663_c("blockClayBlastFurnace").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayBlastFurnace, ItemBlockTiered.class, (String)"blockClayBlastFurnace");
        CBlocks.registerTileEntity(TileClayBlastFurnace.class, "ClayBlastFurnace");
        blocksClayInterface = CBlocks.registerTieredContainers("ClayInterface", new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13}, ClayInterface.class);
        CBlocks.registerTileEntity(TileClayContainerInterface.class, "ClayInterface");
        blocksRedstoneInterface = CBlocks.registerTieredContainers("RedstoneInterface", new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13}, RedstoneInterface.class);
        CBlocks.registerTileEntity(TileRedstoneInterface.class, "RedstoneInterface");
        blocksElectrolysisReactor = CBlocks.registerTieredMachines("ElectrolysisReactor", "electrolysisreactor", "ElectrolysisReactor", new int[]{6, 7, 8, 9});
        blocksClayLaserInterface = CBlocks.registerTieredContainers("LaserClayInterface", new int[]{7, 8, 9, 10, 11, 12, 13}, ClayLaserInterface.class);
        CBlocks.registerTileEntity(TileClayLaserInterface.class, "ClayLaserInterface");
        blocksDistributor = CBlocks.registerTieredContainers("Distributor", new int[]{7, 8, 9}, ClayDistributor.class);
        CBlocks.registerTileEntity(TileClayDistributor.class, "ClayDistributor");
        blocksAutoCrafter = CBlocks.registerTieredContainers("AutoCrafter", new int[]{5, 6, 7, 8, 9}, ClayAutoCrafter.class);
        CBlocks.registerTileEntity(TileAutoCrafter.class, "AutoCrafter");
        blockClayReactor = new ClayReactor("Reactor", "clayium:reactor", 7).func_149663_c("blockClayReactor").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayReactor, ItemBlockTiered.class, (String)"blockClayReactor");
        CBlocks.registerTileEntity(TileClayReactor.class, "ClayReactor");
        blocksTransformer = CBlocks.registerTieredMachines("MatterTransformer", "transformer", "Transformer", new int[]{7, 8, 9, 10, 11, 12});
        blocksCACondenser = CBlocks.registerTieredMachines("CACondenser", "cacondenser", "CACondenser", new int[]{9, 10, 11}, TileCACondenser.class);
        UtilTier.TierManager.applyMachineTierManager(blocksCACondenser, UtilTier.tierCACondenser);
        CBlocks.registerTileEntity(TileCACondenser.class, "CACondenser");
        blocksCAInjector = CBlocks.registerTieredMachines("CAInjector", "cainjector", "CAInjector", new int[]{9, 10, 11, 12, 13}, TileCAInjector.class, ClayAssembler.class, ItemBlockTiered.class);
        CBlocks.registerTileEntity(TileCAInjector.class, "CAInjector");
        blockCACollector = new ClayNoRecipeMachines(null, "clayium:cacollector", 10, TileCACollector.class, 18, 1).func_149663_c("blockCACollector").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockCACollector, ItemBlockTiered.class, (String)"blockCACollector");
        CBlocks.registerTileEntity(TileCACollector.class, "CACollector");
        blockResonator = new BlockResonator(Material.field_151573_f);
        blockResonator.func_149658_d("clayium:resonator").func_149663_c("blockResonator").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(2.0f).func_149752_b(2.0f).func_149672_a(Block.field_149777_j);
        blockResonator.addBlockList("antimatter", 10).setTier(10);
        ((BlockResonator)blockResonator).addResonance(1.08);
        blockResonator.addBlockList("pureantimatter", 11).setTier(11);
        ((BlockResonator)blockResonator).addResonance(1.1);
        blockResonator.addBlockList("oec", 12).setTier(12);
        ((BlockResonator)blockResonator).addResonance(2.0);
        blockResonator.addBlockList("opa", 13).setTier(13);
        ((BlockResonator)blockResonator).addResonance(20.0);
        GameRegistry.registerBlock((Block)blockResonator, ItemBlockDamaged.class, (String)"blockResonator");
        blockOverclocker = new BlockOverclocker(Material.field_151573_f);
        blockOverclocker.func_149658_d("clayium:overclocker").func_149663_c("blockOverclocker").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(2.0f).func_149752_b(2.0f).func_149672_a(Block.field_149777_j);
        blockOverclocker.addBlockList("antimatter", 10).setTier(10);
        ((BlockOverclocker)blockOverclocker).addOverclockFactor(1.5);
        blockOverclocker.addBlockList("pureantimatter", 11).setTier(11);
        ((BlockOverclocker)blockOverclocker).addOverclockFactor(2.3);
        blockOverclocker.addBlockList("oec", 12).setTier(12);
        ((BlockOverclocker)blockOverclocker).addOverclockFactor(3.5);
        blockOverclocker.addBlockList("opa", 13).setTier(13);
        ((BlockOverclocker)blockOverclocker).addOverclockFactor(5.0);
        GameRegistry.registerBlock((Block)blockOverclocker, ItemBlockDamaged.class, (String)"blockOverclocker");
        blockEnergyStorageUpgrade = new BlockEnergyStorageUpgrade(Material.field_151573_f);
        blockEnergyStorageUpgrade.func_149658_d("clayium:estorageupgrade").func_149663_c("blockEnergyStorageUpgrade").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(2.0f).func_149752_b(2.0f).func_149672_a(Block.field_149777_j);
        blockEnergyStorageUpgrade.addBlockList("antimatter", 10).setTier(10);
        ((BlockEnergyStorageUpgrade)blockEnergyStorageUpgrade).addAdditionalEnergyStorage(1);
        blockEnergyStorageUpgrade.addBlockList("pureantimatter", 11).setTier(11);
        ((BlockEnergyStorageUpgrade)blockEnergyStorageUpgrade).addAdditionalEnergyStorage(3);
        blockEnergyStorageUpgrade.addBlockList("oec", 12).setTier(12);
        ((BlockEnergyStorageUpgrade)blockEnergyStorageUpgrade).addAdditionalEnergyStorage(7);
        blockEnergyStorageUpgrade.addBlockList("opa", 13).setTier(13);
        ((BlockEnergyStorageUpgrade)blockEnergyStorageUpgrade).addAdditionalEnergyStorage(63);
        GameRegistry.registerBlock((Block)blockEnergyStorageUpgrade, ItemBlockDamaged.class, (String)"blockEnergyStorageUpgrade");
        blockCAReactorCoil = new BlockDamaged(Material.field_151573_f){

            @Override
            public List getTooltip(ItemStack itemStack) {
                List<String> ret = UtilLocale.localizeTooltip("tooltip.CAReactorCoil");
                ret.addAll(super.getTooltip(itemStack));
                return ret;
            }
        };
        blockCAReactorCoil.func_149658_d("clayium:careactorcoil").func_149663_c("blockCAReactorCoil").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(8.0f).func_149752_b(5.0f).func_149672_a(Block.field_149777_j);
        blockCAReactorCoil.addBlockList("antimatter", 10).setTier(10);
        blockCAReactorCoil.addBlockList("pureantimatter", 11).setTier(11);
        blockCAReactorCoil.addBlockList("oec", 12).setTier(12);
        blockCAReactorCoil.addBlockList("opa", 13).setTier(13);
        GameRegistry.registerBlock((Block)blockCAReactorCoil, ItemBlockDamaged.class, (String)"blockCAReactorCoil");
        blockCAReactorHull = new BlockDamaged(Material.field_151573_f, 10);
        blockCAReactorHull.func_149658_d("clayium:careactorhull").func_149663_c("blockCAReactorHull").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(4.0f).func_149752_b(25.0f).func_149672_a(Block.field_149777_j);
        blockCAReactorHull.setTier("0", 10);
        blockCAReactorHull.setTier("1", 11);
        blockCAReactorHull.setTier("2", 11);
        blockCAReactorHull.setTier("3", 11);
        blockCAReactorHull.setTier("4", 11);
        blockCAReactorHull.setTier("5", 12);
        blockCAReactorHull.setTier("6", 12);
        blockCAReactorHull.setTier("7", 12);
        blockCAReactorHull.setTier("8", 12);
        blockCAReactorHull.setTier("9", 13);
        GameRegistry.registerBlock((Block)blockCAReactorHull, ItemBlockDamaged.class, (String)"blockCAReactorHull");
        blocksCAReactorCore = CBlocks.registerTieredMachines("CAReactor", "careactorcore", "CAReactorCore", new int[]{10, 11, 12, 13}, TileCAReactor.class, 9, CAReactor.class, ItemBlockTiered.class);
        CBlocks.registerTileEntity(TileCAReactor.class, "CAReactor");
        blockEnergeticClayDecomposer = new ClayMachines("ECDecomposer", "clayium:ecdecomposer", 13).func_149663_c("blockEnergeticClayDecomposer").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockEnergeticClayDecomposer, ItemBlockTiered.class, (String)"blockEnergeticClayDecomposer");
        blockStorageContainer = new StorageContainer(Material.field_151573_f, "clayium:az91dhull").func_149663_c("blockStorageContainer").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockStorageContainer, ItemBlockTiered.class, (String)"blockStorageContainer");
        CBlocks.registerTileEntity(TileStorageContainer.class, "StorageContainer");
        blockVacuumContainer = new VacuumContainer(Material.field_151573_f, "clayium:az91dhull").func_149663_c("blockVacuumContainer").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockVacuumContainer, ItemBlockTiered.class, (String)"blockVacuumContainer");
        CBlocks.registerTileEntity(TileVacuumContainer.class, "VacuumContainer");
        blockAutoTrader = new AutoTrader(8).func_149663_c("blockAutoTrader").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockAutoTrader, ItemBlockTiered.class, (String)"blockAutoTrader");
        CBlocks.registerTileEntity(TileAutoTrader.class, "AutoTrader");
        blockClayMarker = new ClayMarker(7, Blocks.field_150435_aG, TileClayMarker.class).func_149663_c("blockClayMarker").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayMarker, ItemBlockTiered.class, (String)"blockClayMarker");
        CBlocks.registerTileEntity(TileClayMarker.class, "ClayMarker");
        blockClayOpenPitMarker = new ClayMarker(8, blockCompressedClay, TileClayOpenPitMarker.class).func_149663_c("blockClayOpenPitMarker").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayOpenPitMarker, ItemBlockTiered.class, (String)"blockClayOpenPitMarker");
        CBlocks.registerTileEntity(TileClayOpenPitMarker.class, "ClayOpenPitMarker");
        blockClayGroundLevelingMarker = new ClayMarker(8, blockCompressedClay, 1, TileClayOpenPitMarker.TileClayGroundLevelingMarker.class).func_149663_c("blockClayGroundLevelingMarker").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayGroundLevelingMarker, ItemBlockTiered.class, (String)"blockClayGroundLevelingMarker");
        CBlocks.registerTileEntity(TileClayOpenPitMarker.TileClayGroundLevelingMarker.class, "ClayGroundLevelingMarker");
        blockClayPrismMarker = new ClayMarker(8, blockCompressedClay, 2, TileClayOpenPitMarker.TileClayPrismMarker.class).func_149663_c("blockClayPrismMarker").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayPrismMarker, ItemBlockTiered.class, (String)"blockClayPrismMarker");
        CBlocks.registerTileEntity(TileClayOpenPitMarker.TileClayPrismMarker.class, "ClayPrismMarker");
        blockMiner = new AreaMiner(6, "clayium:areaminer").func_149663_c("blockMiner").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockMiner, ItemBlockTiered.class, (String)"blockMiner");
        CBlocks.registerTileEntity(TileAreaMiner.class, "AreaMiner");
        blockAreaCollector = new AreaCollector(7).func_149663_c("blockAreaCollector").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockAreaCollector, ItemBlockTiered.class, (String)"blockAreaCollector");
        CBlocks.registerTileEntity(TileAreaCollector.class, "AreaCollector");
        blockAreaMiner = new AreaMiner(8, "clayium:areaminer").func_149663_c("blockAreaMiner").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockAreaMiner, ItemBlockTiered.class, (String)"blockAreaMiner");
        blockAdvancedAreaMiner = new AreaMiner(9, "clayium:advareaminer").func_149663_c("blockAdvancedAreaMiner").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockAdvancedAreaMiner, ItemBlockTiered.class, (String)"blockAdvancedAreaMiner");
        blockAreaReplacer = new AreaMiner(10, "clayium:areareplacer").func_149663_c("blockAreaReplacer").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockAreaReplacer, ItemBlockTiered.class, (String)"blockAreaReplacer");
        blockActivator = new AreaActivator(6).func_149663_c("blockActivator").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockActivator, ItemBlockTiered.class, (String)"blockActivator");
        blockAreaActivator = new AreaActivator(8).func_149663_c("blockAreaActivator").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockAreaActivator, ItemBlockTiered.class, (String)"blockAreaActivator");
        CBlocks.registerTileEntity(TileAreaActivator.class, "AreaActivator");
        blockClayWaterWheel = new WaterWheel("Clay Water Wheel", "clayium:waterwheel", 1).func_149663_c("blockClayWaterWheel").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayWaterWheel, ItemBlockTiered.class, (String)"blockClayWaterWheel");
        blockDenseClayWaterWheel = new WaterWheel("Dense Clay Water Wheel", "clayium:waterwheel", 2).func_149663_c("blockDenseClayWaterWheel").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockDenseClayWaterWheel, ItemBlockTiered.class, (String)"blockDenseClayWaterWheel");
        CBlocks.registerTileEntity(TileWaterWheel.class, "WaterWheel");
        blocksClayEnergyLaser = CBlocks.registerTieredContainers("ClayEnergyLaser", new int[]{7, 8, 9, 10}, ClayEnergyLaser.class);
        CBlocks.registerTileEntity(TileClayEnergyLaser.class, "ClayEnergyLaser");
        blockLaserReflector = new LaserReflector().func_149663_c("blockLaserReflector").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(1.0f).func_149752_b(1.0f);
        GameRegistry.registerBlock((Block)blockLaserReflector, ItemBlockTiered.class, (String)"blockLaserReflector");
        CBlocks.registerTileEntity(TileLaserReflector.class, "LaserReflector");
        blockClayTreeSapling = new ClayTreeSapling().func_149663_c("blockClayTreeSapling").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(0.0f).func_149672_a(Block.field_149779_h).func_149663_c("blockClayTreeSapling").func_149658_d("clayium:sapling_claytree");
        GameRegistry.registerBlock((Block)blockClayTreeSapling, ItemBlockTiered.class, (String)"blockClayTreeSapling");
        blockClayTreeLeaf = new ClayTreeLeaf().func_149663_c("blockClayTreeLeaf").func_149658_d("clayium:leaves");
        GameRegistry.registerBlock((Block)blockClayTreeLeaf, ItemBlockTiered.class, (String)"blockClayTreeLeaf");
        blockClayTreeLog = new ClayTreeLog().func_149663_c("blockClayTreeLog").func_149658_d("clayium:log");
        GameRegistry.registerBlock((Block)blockClayTreeLog, ItemBlockTiered.class, (String)"blockClayTreeLog");
        OreDictionary.registerOre((String)"logWood", (Block)blockClayTreeLog);
        OreDictionary.registerOre((String)"treeLeaves", (Block)blockClayTreeLeaf);
        OreDictionary.registerOre((String)"treeSapling", (Block)blockClayTreeSapling);
        OreDictionary.registerOre((String)"saplingTree", (Block)blockClayTreeSapling);
        blockClayChunkLoader = new ClayChunkLoader(6).func_149663_c("blockClayChunkLoader").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockClayChunkLoader, ItemBlockTiered.class, (String)"blockClayChunkLoader");
        CBlocks.registerTileEntity(TileClayChunkLoader.class, "ClayChunkLoader");
        blockPANCore = new PANCore().func_149663_c("blockPANCore").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockPANCore, ItemBlockTiered.class, (String)"blockPANCore");
        CBlocks.registerTileEntity(TilePANCore.class, "PANCore");
        blocksPANAdapter = CBlocks.registerTieredContainers("PANAdapter", new int[]{10, 11, 12, 13}, PANAdapter.class);
        CBlocks.registerTileEntity(TilePANAdapter.class, "PANAdapter");
        blocksPANDuplicator = CBlocks.registerTieredContainers("PANDuplicator", new int[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, PANDuplicator.class);
        for (i = 4; i <= 13; ++i) {
            ((PANDuplicator)CBlocks.blocksPANDuplicator[i]).multConsumingEnergy = (float)Math.pow(10.0, i - 5);
        }
        CBlocks.registerTileEntity(TilePANDuplicator.class, "PANDuplicator");
        blockPANCable = new PANCable().func_149663_c("blockPANCable").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockPANCable, ItemBlockTiered.class, (String)"blockPANCable");
        blockMetalChest = new MetalChest().func_149663_c("blockMetalChest").func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockMetalChest, ItemBlockDamaged.class, (String)"blockMetalChest");
        CBlocks.registerTileEntity(TileMetalChest.class, "MetalChest");
        if (ClayiumCore.cfgEnableFluidCapsule) {
            blocksFluidTranslator = CBlocks.registerTieredContainers("FluidTranslator", new int[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13}, FluidTranslator.class);
            CBlocks.registerTileEntity(TileFluidTranslator.class, "FluidTranslator");
            blocksFluidTransferMachine = CBlocks.registerTieredMachines("FluidTransferMachine", "fluidtransfermachine", "FluidTransferMachine", new int[]{5}, ClayChemicalReactor.class, ItemBlockTiered.class);
        }
        blockMaterial = new BlockDamaged(Material.field_151573_f);
        blockMaterial.func_149663_c("blockMaterial").func_149647_a(ClayiumCore.creativeTabClayium).func_149711_c(2.0f).func_149752_b(2.0f).func_149672_a(Block.field_149777_j);
        blockMaterial.addBlockList("ImpureSilicon", 0).setTier(5).setSubBlockName("blockImpureSilicon").setIconName("clayium:impuresilicon");
        blockMaterial.addBlockList("Silicone", 1).setTier(5).setSubBlockName("blockSilicone").setIconName("clayium:silicone");
        blockMaterial.addBlockList("Silicon", 2).setTier(5).setSubBlockName("blockSilicon").setIconName("clayium:silicon");
        blockMaterial.addBlockList("Aluminium", 3).setTier(6).setSubBlockName("blockAluminium").setIconName("clayium:aluminium");
        blockMaterial.addBlockList("ClaySteel", 4).setTier(7).setSubBlockName("blockClaySteel").setIconName("clayium:claysteel");
        blockMaterial.addBlockList("Clayium", 5).setTier(8).setSubBlockName("blockClayium").setIconName("clayium:clayium");
        blockMaterial.addBlockList("UltimateAlloy", 6).setTier(9).setSubBlockName("blockUltimateAlloy").setIconName("clayium:ultimatealloy");
        blockMaterial.addBlockList("Antimatter", 7).setTier(10).setSubBlockName("blockAntimatter").setIconName("clayium:antimatter");
        blockMaterial.addBlockList("PureAntimatter", 8).setTier(11).setSubBlockName("blockPureAntimatter").setIconName("clayium:pureantimatter");
        blockMaterial.addBlockList("OctupleEnergeticClay", 9).setTier(12).setSubBlockName("blockOctupleEnergeticClay").setIconName("clayium:oec");
        blockMaterial.addBlockList("OctuplePureAntimatter", 10).setTier(13).setSubBlockName("blockOctuplePureAntimatter").setIconName("clayium:opa");
        GameRegistry.registerBlock((Block)blockMaterial, ItemBlockDamaged.class, (String)"blockMaterial");
        blockSiliconeColored = new BlockSiliconeColored();
        blockSiliconeColored.func_149647_a(ClayiumCore.creativeTabClayium);
        GameRegistry.registerBlock((Block)blockSiliconeColored, ItemBlockDamaged.class, (String)"blockSiliconeColored");
        CBlocks.registerTileEntity(TileFluidTab.class, "FluidTab");
        blocksRFGenerator = new HashMap<String, Block>();
        Map<String, Map<String, Object>> configMap = ClayRFGenerator.getConfigMap();
        if (configMap != null) {
            for (Map.Entry<String, Map<String, Object>> entry : configMap.entrySet()) {
                String blockName = entry.getKey();
                Map<String, Object> config = entry.getValue();
                Object obj = config.get("IconName");
                if (!(obj instanceof String)) continue;
                String iconName = (String)obj;
                obj = config.get("Tier");
                if (!(obj instanceof Integer)) continue;
                int tier = (Integer)obj;
                Block block = new ClayRFGenerator(blockName, iconName, tier).func_149663_c("block" + blockName).func_149711_c(2.0f).func_149752_b(2.0f).func_149647_a(ClayiumCore.creativeTabClayium);
                GameRegistry.registerBlock((Block)block, ItemBlockTiered.class, (String)("block" + blockName));
                blocksRFGenerator.put(blockName, block);
            }
        }
        CBlocks.registerTileEntity(TileClayRFGenerator.class, "RFGenerator");
    }

    public static Block[] registerTieredContainers(String blockName, int[] tiers, Class<? extends ClayContainerTiered> containerClass, Class<? extends ItemBlockTiered> itemBlockClass) {
        Block[] res = new Block[tierPrefix.length];
        for (int i = 0; i < tiers.length; ++i) {
            if (tiers[i] < 0 || tiers[i] >= tierPrefix.length) continue;
            try {
                res[tiers[i]] = (Block)containerClass.getConstructor(Integer.TYPE).newInstance(tiers[i]);
            }
            catch (InstantiationException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalAccessException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalArgumentException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (InvocationTargetException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (NoSuchMethodException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (SecurityException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            res[tiers[i]].func_149663_c("block" + tierPrefix[tiers[i]] + blockName).func_149647_a(ClayiumCore.creativeTabClayium);
            GameRegistry.registerBlock((Block)res[tiers[i]], itemBlockClass, (String)("block" + tierPrefix[tiers[i]] + blockName));
        }
        return res;
    }

    public static Block[] registerTieredContainers(String blockName, int[] tiers, Class<? extends ClayContainerTiered> containerClass) {
        return CBlocks.registerTieredContainers(blockName, tiers, containerClass, ItemBlockTiered.class);
    }

    public static Block[] registerTieredMachines(String recipeId, String icon, String blockName, int[] tiers, Class<? extends ClayMachines> machineClass, Class<? extends ItemBlockTiered> itemBlockClass) {
        Block[] res = new Block[tierPrefix.length];
        for (int i = 0; i < tiers.length; ++i) {
            if (tiers[i] < 0 || tiers[i] >= tierPrefix.length) continue;
            try {
                res[tiers[i]] = (Block)machineClass.getConstructor(String.class, String.class, Integer.TYPE).newInstance(recipeId, "clayium:" + icon, tiers[i]);
            }
            catch (InstantiationException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalAccessException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalArgumentException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (InvocationTargetException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (NoSuchMethodException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (SecurityException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            res[tiers[i]].func_149663_c("block" + tierPrefix[tiers[i]] + blockName).func_149647_a(ClayiumCore.creativeTabClayium);
            GameRegistry.registerBlock((Block)res[tiers[i]], itemBlockClass, (String)("block" + tierPrefix[tiers[i]] + blockName));
        }
        return res;
    }

    public static Block[] registerTieredMachines(String recipeId, String icon, String blockName, int[] tiers) {
        return CBlocks.registerTieredMachines(recipeId, icon, blockName, tiers, ClayMachines.class, ItemBlockTiered.class);
    }

    public static Block[] registerTieredMachines(String recipeId, String icon, String blockName, int[] tiers, Class<? extends TileClayContainer> tileEntityClass, Class<? extends ClayMachines> machineClass, Class<? extends ItemBlockTiered> itemBlockClass) {
        Block[] res = new Block[tierPrefix.length];
        for (int i = 0; i < tiers.length; ++i) {
            if (tiers[i] < 0 || tiers[i] >= tierPrefix.length) continue;
            try {
                res[tiers[i]] = (Block)machineClass.getConstructor(String.class, String.class, Integer.TYPE, Class.class).newInstance(recipeId, "clayium:" + icon, tiers[i], tileEntityClass);
            }
            catch (InstantiationException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalAccessException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalArgumentException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (InvocationTargetException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (NoSuchMethodException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (SecurityException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            res[tiers[i]].func_149663_c("block" + tierPrefix[tiers[i]] + blockName).func_149647_a(ClayiumCore.creativeTabClayium);
            GameRegistry.registerBlock((Block)res[tiers[i]], itemBlockClass, (String)("block" + tierPrefix[tiers[i]] + blockName));
        }
        return res;
    }

    public static Block[] registerTieredMachines(String recipeId, String icon, String blockName, int[] tiers, Class<? extends TileClayContainer> tileEntityClass) {
        return CBlocks.registerTieredMachines(recipeId, icon, blockName, tiers, tileEntityClass, ClayMachines.class, ItemBlockTiered.class);
    }

    public static Block[] registerTieredMachines(String recipeId, String icon, String blockName, int[] tiers, Class<? extends TileClayContainer> tileEntityClass, int guiId, Class<? extends ClayMachines> machineClass, Class<? extends ItemBlockTiered> itemBlockClass) {
        Block[] res = new Block[tierPrefix.length];
        for (int i = 0; i < tiers.length; ++i) {
            if (tiers[i] < 0 || tiers[i] >= tierPrefix.length) continue;
            try {
                res[tiers[i]] = (Block)machineClass.getConstructor(String.class, String.class, Integer.TYPE, Class.class, Integer.TYPE).newInstance(recipeId, "clayium:" + icon, tiers[i], tileEntityClass, guiId);
            }
            catch (InstantiationException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalAccessException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (IllegalArgumentException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (InvocationTargetException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (NoSuchMethodException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            catch (SecurityException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
            res[tiers[i]].func_149663_c("block" + tierPrefix[tiers[i]] + blockName).func_149647_a(ClayiumCore.creativeTabClayium);
            GameRegistry.registerBlock((Block)res[tiers[i]], itemBlockClass, (String)("block" + tierPrefix[tiers[i]] + blockName));
        }
        return res;
    }

    public static Block[] registerTieredMachines(String recipeId, String icon, String blockName, int[] tiers, Class<? extends TileClayContainer> tileEntityClass, int guiId) {
        return CBlocks.registerTieredMachines(recipeId, icon, blockName, tiers, tileEntityClass, guiId, ClayMachines.class, ItemBlockTiered.class);
    }

    public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
        if (ClayiumCore.cfgEnableAlternativeTileEntityName) {
            GameRegistry.registerTileEntityWithAlternatives(tileEntityClass, (String)("clayiumTile" + id), (String[])new String[]{"Tile" + id, "tile" + id});
        } else {
            GameRegistry.registerTileEntityWithAlternatives(tileEntityClass, (String)("clayiumTile" + id), (String[])new String[0]);
        }
    }

    static {
        tierPrefix = new String[]{"", "Clay", "DenseClay", "Simple", "Basic", "Advanced", "Precision", "ClaySteel", "Clayium", "Ultimate", "Antimatter", "PureAntimatter", "OEC", "OPA"};
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.oredict.OreDictionary
 */
package mods.clayium.item;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mods.clayium.block.CBlocks;
import mods.clayium.block.MetalBlock;
import mods.clayium.block.MetalChest;
import mods.clayium.core.ClayiumCore;
import mods.clayium.gui.GenericMaterialIcon;
import mods.clayium.gui.IMultipleRenderIcons;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CShape;
import mods.clayium.item.ItemDamaged;
import mods.clayium.util.crafting.OreDictionaryStack;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CMaterials {
    public static Map<CMaterial, Map<CShape, ItemStack>> materialShapeMap = new HashMap<CMaterial, Map<CShape, ItemStack>>();
    public static Map<CMaterial, ItemDamaged> materialMap = new HashMap<CMaterial, ItemDamaged>();
    public static Map<CShape, ItemDamaged> shapeMap = new HashMap<CShape, ItemDamaged>();
    public static Map<Integer, CMaterial> materialList = new HashMap<Integer, CMaterial>();
    public static ItemDamaged itemClayParts;
    public static ItemDamaged itemDenseClayParts;
    public static CMaterial CLAY;
    public static CMaterial DENSE_CLAY;
    public static CMaterial IND_CLAY;
    public static CMaterial ADVIND_CLAY;
    public static CMaterial ENG_CLAY;
    public static CMaterial EXC_CLAY;
    public static CMaterial ORG_CLAY;
    public static CMaterial IRON;
    public static CMaterial GOLD;
    public static CMaterial ALUMINIUM;
    public static CMaterial SILICON;
    public static CMaterial MAGNESIUM;
    public static CMaterial SODIUM;
    public static CMaterial LITHIUM;
    public static CMaterial ZIRCONIUM;
    public static CMaterial ZINC;
    public static CMaterial MAIN_ALUMINIUM;
    public static CMaterial MANGANESE;
    public static CMaterial CALCIUM;
    public static CMaterial POTASSIUM;
    public static CMaterial NICKEL;
    public static CMaterial BERYLLIUM;
    public static CMaterial LEAD;
    public static CMaterial HAFNIUM;
    public static CMaterial CHROME;
    public static CMaterial TITANIUM;
    public static CMaterial STRONTIUM;
    public static CMaterial BARIUM;
    public static CMaterial COPPER;
    public static CMaterial IMPURE_ALUMINIUM;
    public static CMaterial IMPURE_SILICON;
    public static CMaterial IMPURE_MAGNESIUM;
    public static CMaterial IMPURE_SODIUM;
    public static CMaterial IMPURE_LITHIUM;
    public static CMaterial IMPURE_ZIRCONIUM;
    public static CMaterial IMPURE_ZINC;
    public static CMaterial IMPURE_MANGANESE;
    public static CMaterial IMPURE_CALCIUM;
    public static CMaterial IMPURE_POTASSIUM;
    public static CMaterial IMPURE_NICKEL;
    public static CMaterial IMPURE_IRON;
    public static CMaterial IMPURE_BERYLLIUM;
    public static CMaterial IMPURE_LEAD;
    public static CMaterial IMPURE_HAFNIUM;
    public static CMaterial IMPURE_CHROME;
    public static CMaterial IMPURE_TITANIUM;
    public static CMaterial IMPURE_STRONTIUM;
    public static CMaterial IMPURE_BARIUM;
    public static CMaterial IMPURE_COPPER;
    public static CMaterial IMPURE_REDSTONE;
    public static CMaterial IMPURE_GLOWSTONE;
    public static CMaterial MAIN_OSMIUM;
    public static CMaterial IMPURE_OSMIUM;
    public static CMaterial SALT;
    public static CMaterial CAL_CLAY;
    public static CMaterial CALCIUM_CHLORIDE;
    public static CMaterial SODIUM_CARBONATE;
    public static CMaterial QUARTZ;
    public static CMaterial SILICONE;
    public static CMaterial CLAY_STEEL;
    public static CMaterial CLAYIUM;
    public static CMaterial IMPURE_ULTIMATE_ALLOY;
    public static CMaterial ULTIMATE_ALLOY;
    public static CMaterial ANTIMATTER;
    public static CMaterial PURE_ANTIMATTER;
    public static CMaterial[] COMPRESSED_PURE_ANTIMATTER;
    public static CMaterial OCTUPLE_CLAY;
    public static CMaterial OCTUPLE_PURE_ANTIMATTER;
    public static CMaterial ZINCALMINIUM_ALLOY;
    public static CMaterial AZ91D_ALLOY;
    public static CMaterial ZINCONIUM_ALLOY;
    public static CMaterial ZK60A_ALLOY;
    public static CMaterial RUBIDIUM;
    public static CMaterial CAESIUM;
    public static CMaterial FRANCIUM;
    public static CMaterial RADIUM;
    public static CMaterial ACTINIUM;
    public static CMaterial THORIUM;
    public static CMaterial PROTACTINIUM;
    public static CMaterial URANIUM;
    public static CMaterial NEPTUNIUM;
    public static CMaterial PLUTONIUM;
    public static CMaterial AMERICIUM;
    public static CMaterial CURIUM;
    public static CMaterial LANTHANUM;
    public static CMaterial CERIUM;
    public static CMaterial PRASEODYMIUM;
    public static CMaterial NEODYMIUM;
    public static CMaterial PROMETHIUM;
    public static CMaterial SAMARIUM;
    public static CMaterial EUROPIUM;
    public static CMaterial VANADIUM;
    public static CMaterial COBALT;
    public static CMaterial PALLADIUM;
    public static CMaterial SILVER;
    public static CMaterial PLATINUM;
    public static CMaterial IRIDIUM;
    public static CMaterial OSMIUM;
    public static CMaterial RHENIUM;
    public static CMaterial TANTALUM;
    public static CMaterial TUNGSTEN;
    public static CMaterial MOLYBDENUM;
    public static CMaterial TIN;
    public static CMaterial ANTIMONY;
    public static CMaterial BISMUTH;
    public static CMaterial BRONZE;
    public static CMaterial BRASS;
    public static CMaterial ELECTRUM;
    public static CMaterial INVAR;
    public static CMaterial STEEL;
    public static CMaterial OBSIDIAN;
    public static CMaterial REDSTONE;
    public static CMaterial GLOWSTONE;
    public static CMaterial ENDER_PEARL;
    public static CMaterial COAL;
    public static CMaterial CHARCOAL;
    public static CMaterial LAPIS;
    public static CMaterial DIAMOND;
    public static CMaterial EMERALD;
    public static CMaterial STONE;
    public static CMaterial CARBON;
    public static CMaterial PHOSPHORUS;
    public static CMaterial SULFUR;
    public static CMaterial PLASTIC;
    public static CMaterial CINNABAR;
    public static CMaterial SALTPETER;
    public static CMaterial RUBY;
    public static CMaterial SAPPHIRE;
    public static CMaterial PERIDOT;
    public static CMaterial AMBER;
    public static CMaterial AMETHYST;
    public static CMaterial REDSTONE_ALLOY;
    public static CMaterial CONDUCTIVE_IRON;
    public static CMaterial ENERGETIC_ALLOY;
    public static CMaterial ELECTRICAL_STEEL;
    public static CMaterial DARK_STEEL;
    public static CMaterial PHASED_IRON;
    public static CMaterial PHASED_GOLD;
    public static CMaterial SOULARIUM;
    public static CMaterial SIGNALUM;
    public static CMaterial LUMIUM;
    public static CMaterial ENDERIUM;
    public static CMaterial ELECTRUM_FLUX;
    public static CMaterial CRYSTAL_FLUX;
    public static CMaterial APATITE;
    public static CMaterial CERTUS_QUARTZ;
    public static CMaterial FLUIX;
    public static CMaterial ARDITE;
    public static CMaterial ALUMINUM_BRASS;
    public static CMaterial PIG_IRON;
    public static CMaterial ALUMITE;
    public static CMaterial MANYULLYN;
    public static CMaterial FAIRY;
    public static CMaterial POKEFENNIUM;
    public static CMaterial RED_AURUM;
    public static CMaterial DRULLOY;
    public static CMaterial RED_ALLOY;
    public static CMaterial ELECTROTINE;
    public static CMaterial ELECTROTINE_ALLOY;
    public static CMaterial REFINED_GLOWSTONE;
    public static CMaterial REFINED_OBSIDIAN;
    public static CMaterial UNSTABLE;
    public static CMaterial HSLA;
    public static CMaterial GRAPHITE;
    public static CMaterial YELLORIUM;
    public static CMaterial CYANITE;
    public static CMaterial BLUTONIUM;
    public static CMaterial LUDICRITE;
    public static CMaterial METEORIC_IRON;
    public static CMaterial DESH;
    public static CMaterial IRON_COMPRESSED;
    public static CMaterial FZ_DARK_IRON;
    public static CMaterial THAUMIUM;
    public static CMaterial VOID;
    public static CMaterial MANASTEEL;
    public static CMaterial TERRASTEEL;
    public static CMaterial ELVEN_ELEMENTIUM;
    public static CMaterial TOPAZ;
    public static CMaterial MALACHITE;
    public static CMaterial TANZANITE;
    public static CMaterial HEE_ENDIUM;
    public static CMaterial DILITHIUM;
    public static CMaterial FORCICIUM;
    public static CMaterial GALLIUM;
    public static CMaterial YTTRIUM;
    public static CMaterial NIOBIUM;
    public static CMaterial URANIUM_235;
    public static CMaterial PLUTONIUM_241;
    public static CMaterial NAQUADAH;
    public static CMaterial NAQUADAH_ENRICHED;
    public static CMaterial NAQUADRIA;
    public static CMaterial NEUTRONIUM;
    public static CMaterial NIKOLITE;
    public static CMaterial QUARTZITE;
    public static CMaterial MONAZITE;
    public static CMaterial NITER;
    public static CMaterial TUNGSTEN_STEEL;
    public static CMaterial CUPRONICKEL;
    public static CMaterial NICHROME;
    public static CMaterial KANTHAL;
    public static CMaterial STAINLESS_STEEL;
    public static CMaterial COBALT_BRASS;
    public static CMaterial MAGNALIUM;
    public static CMaterial SOLDERING_ALLOY;
    public static CMaterial BATTERY_ALLOY;
    public static CMaterial VANADIUM_GALLIUM;
    public static CMaterial YTTRIUM_BARIUM_CUPRATE;
    public static CMaterial NIOBIUM_TITANIUM;
    public static CMaterial ULTIMET;
    public static CMaterial TIN_ALLOY;
    public static CMaterial BLUE_ALLOY;
    public static CMaterial WROUGHT_IRON;
    public static CMaterial ANNEALED_COPPER;
    public static CMaterial IRON_MAGNETIC;
    public static CMaterial STEEL_MAGNETIC;
    public static CMaterial NEODYMIUM_MAGNETIC;
    public static CMaterial LIGNITE;
    public static CMaterial LAZURITE;
    public static CMaterial SODALITE;
    public static CMaterial GREEN_SAPPHIRE;
    public static CMaterial GARNET_RED;
    public static CMaterial GARNET_YELLOW;
    public static CMaterial OPAL;
    public static CMaterial JASPER;
    public static CMaterial BLUE_TOPAZ;
    public static CMaterial FORCE;
    public static CMaterial FORCILLIUM;
    public static CMaterial GLASS;
    public static CMaterial PROMETHEUM;
    public static CMaterial DEEP_IRON;
    public static CMaterial INFUSCOLIUM;
    public static CMaterial OURECLASE;
    public static CMaterial AREDRITE;
    public static CMaterial ASTRAL_SILVER;
    public static CMaterial CARMOT;
    public static CMaterial MITHRIL;
    public static CMaterial RUBRACIUM;
    public static CMaterial ORICHALCUM;
    public static CMaterial ADAMANTINE;
    public static CMaterial ATLARUS;
    public static CMaterial IGNATIUS;
    public static CMaterial SHADOW_IRON;
    public static CMaterial LEMURITE;
    public static CMaterial MIDASIUM;
    public static CMaterial VYROXERES;
    public static CMaterial CERUCLASE;
    public static CMaterial ALDUORITE;
    public static CMaterial KALENDRITE;
    public static CMaterial VULCANITE;
    public static CMaterial SANGUINITE;
    public static CMaterial EXIMITE;
    public static CMaterial MEUTOITE;
    public static CMaterial HEPATIZON;
    public static CMaterial DAMASCUS_STEEL;
    public static CMaterial ANGMALLEN;
    public static CMaterial BLACK_STEEL;
    public static CMaterial QUICKSILVER;
    public static CMaterial HADEROTH;
    public static CMaterial CELENEGIL;
    public static CMaterial TARTARITE;
    public static CMaterial SHADOW_STEEL;
    public static CMaterial INOLASHITE;
    public static CMaterial AMORDRINE;
    public static CMaterial DESICHALKOS;
    public static CMaterial NINJA;
    public static CMaterial YELLOWSTONE;
    public static CMaterial BLUESTONE;
    public static CMaterial ALUMINIUM_OD;
    public static ItemDamaged itemPlates;
    public static ItemDamaged itemLargePlates;
    public static ItemDamaged itemDusts;
    public static ItemDamaged itemIngots;
    public static ItemDamaged itemGems;
    public static CShape PLATE;
    public static CShape STICK;
    public static CShape SHORT_STICK;
    public static CShape RING;
    public static CShape SMALL_RING;
    public static CShape GEAR;
    public static CShape BLADE;
    public static CShape NEEDLE;
    public static CShape DISC;
    public static CShape SMALL_DISC;
    public static CShape CYLINDER;
    public static CShape PIPE;
    public static CShape LARGE_BALL;
    public static CShape LARGE_PLATE;
    public static CShape GRINDING_HEAD;
    public static CShape BEARING;
    public static CShape SPINDLE;
    public static CShape CUTTING_HEAD;
    public static CShape WATER_WHEEL;
    public static CShape BLOCK;
    public static CShape BALL;
    public static CShape DUST;
    public static CShape INGOT;
    public static CShape GEM;
    public static CShape CRYSTAL;
    public static CMaterial currentMaterial;
    public static CShape currentShape;

    public static void registerMaterials() {
        if (!ClayiumCore.cfgUtilityMode) {
            CMaterial[] impuremetals;
            CMaterial[] metals;
            itemClayParts = CMaterials.createItem("itemClayParts");
            itemDenseClayParts = CMaterials.createItem("itemDenseClayParts");
            CLAY = CMaterials.registerMaterial("Clay", "clay", 512, itemClayParts);
            DENSE_CLAY = CMaterials.registerMaterial("DenseClay", "denseclay", 513, itemDenseClayParts);
            IND_CLAY = CMaterials.registerMaterial("IndustrialClay", "indclay", 515);
            ADVIND_CLAY = CMaterials.registerMaterial("AdvancedIndustrialClay", "advindclay", 516);
            ENG_CLAY = CMaterials.registerMaterial("EnergizedClay", "engclay", 768);
            IMPURE_SILICON = CMaterials.registerMaterial("ImpureSilicon", "impuresilicon", 142);
            IMPURE_SILICON.setColor(151, 143, 152, 0).setColor(83, 55, 100, 1).setColor(169, 165, 165, 2);
            SILICON = CMaterials.registerMaterial("Silicon", "silicon", 14);
            SILICON.setColor(40, 28, 40).setColor(255, 255, 255, 2);
            IRON = CMaterials.registerMaterial("Iron", "iron", 26);
            IRON.setColor(216, 216, 216, 0).setColor(53, 53, 53, 1).setColor(255, 255, 255, 2);
            GOLD = CMaterials.registerMaterial("Gold", "gold", 79);
            GOLD.setColor(255, 255, 10, 0).setColor(60, 60, 0, 1).setColor(255, 255, 255, 2);
            ALUMINIUM = CMaterials.registerMaterial("Aluminium", "alminium", "Aluminum", 13);
            ALUMINIUM.setColor(190, 200, 202).setColor(255, 255, 255, 2);
            MAGNESIUM = CMaterials.registerMaterial("Magnesium", "magnesium", 12);
            MAGNESIUM.setColor(150, 210, 150).setColor(120, 120, 120, 1);
            SODIUM = CMaterials.registerMaterial("Sodium", "sodium", 11);
            SODIUM.setColor(170, 170, 222).setColor(120, 120, 120, 1);
            LITHIUM = CMaterials.registerMaterial("Lithium", "lithium", 3);
            LITHIUM.setColor(210, 210, 150).setColor(120, 120, 120, 1);
            ZIRCONIUM = CMaterials.registerMaterial("Zirconium", "zirconium", 40);
            ZIRCONIUM.setColor(190, 170, 122).setColor(120, 120, 120, 1);
            ZINC = CMaterials.registerMaterial("Zinc", "zinc", 30);
            ZINC.setColor(230, 170, 170).setColor(120, 120, 120, 1);
            MANGANESE = CMaterials.registerMaterial("Manganese", "manganese", 25);
            MANGANESE.setColor(190, 240, 240);
            CALCIUM = CMaterials.registerMaterial("Calcium", "calcium", 20);
            CALCIUM.setColor(240, 240, 240);
            POTASSIUM = CMaterials.registerMaterial("Potassium", "potassium", 19);
            POTASSIUM.setColor(240, 240, 190);
            NICKEL = CMaterials.registerMaterial("Nickel", "nickel", 28);
            NICKEL.setColor(210, 210, 240, 0);
            BERYLLIUM = CMaterials.registerMaterial("Beryllium", "beryllium", 4);
            BERYLLIUM.setColor(210, 240, 210);
            LEAD = CMaterials.registerMaterial("Lead", "lead", 82);
            LEAD.setColor(190, 240, 210);
            HAFNIUM = CMaterials.registerMaterial("Hafnium", "hafnium", 72);
            HAFNIUM.setColor(240, 210, 170);
            CHROME = CMaterials.registerMaterial("Chrome", "chrome", 24);
            CHROME.setColor(240, 210, 210);
            TITANIUM = CMaterials.registerMaterial("Titanium", "titanium", 22);
            TITANIUM.setColor(210, 240, 240);
            STRONTIUM = CMaterials.registerMaterial("Strontium", "strontium", 38);
            STRONTIUM.setColor(210, 170, 242);
            BARIUM = CMaterials.registerMaterial("Barium", "barium", 56);
            BARIUM.setColor(150, 80, 120).setColor(120, 20, 80, 1);
            COPPER = CMaterials.registerMaterial("Copper", "copper", 29);
            COPPER.setColor(160, 90, 10).setColor(255, 255, 255, 2);
            ZINCALMINIUM_ALLOY = CMaterials.registerMaterial("Zincalminium", "zincalminium", 1344);
            ZINCALMINIUM_ALLOY.setColor(240, 190, 220).setColor(160, 0, 0, 1);
            AZ91D_ALLOY = CMaterials.registerMaterial("AZ91D", "az91d", 1312);
            AZ91D_ALLOY.setColor(130, 140, 135).setColor(255, 255, 255, 2).setColor(10, 40, 10, 1);
            ZINCONIUM_ALLOY = CMaterials.registerMaterial("Zinconium", "zinconium", 1345);
            ZINCONIUM_ALLOY.setColor(230, 170, 140).setColor(120, 0, 0, 1);
            ZK60A_ALLOY = CMaterials.registerMaterial("ZK60A", "zk60a", 1313);
            ZK60A_ALLOY.setColor(75, 85, 80).setColor(255, 255, 255, 2).setColor(10, 40, 10, 1);
            IMPURE_ALUMINIUM = CMaterials.registerMaterial("ImpureAluminium", "alminium", 141);
            IMPURE_ALUMINIUM.setColor(190, 200, 202).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_MAGNESIUM = CMaterials.registerMaterial("ImpureMagnesium", "impuremagnesium", 140);
            IMPURE_MAGNESIUM.setColor(150, 220, 150).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_SODIUM = CMaterials.registerMaterial("ImpureSodium", "impuresodium", 139);
            IMPURE_SODIUM.setColor(170, 170, 230).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_LITHIUM = CMaterials.registerMaterial("ImpureLithium", "impurelithium", 131);
            IMPURE_LITHIUM.setColor(220, 220, 150).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_ZIRCONIUM = CMaterials.registerMaterial("ImpureZirconium", "impurezirconium", 168);
            IMPURE_ZIRCONIUM.setColor(190, 170, 122).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_ZINC = CMaterials.registerMaterial("ImpureZinc", "impurezinc", 158);
            IMPURE_ZINC.setColor(230, 170, 170).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_MANGANESE = CMaterials.registerMaterial("ImpureManganese", "impuremanganese", 153);
            IMPURE_MANGANESE.setColor(190, 240, 240).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_CALCIUM = CMaterials.registerMaterial("ImpureCalcium", "impurecalcium", 148);
            IMPURE_CALCIUM.setColor(240, 240, 240).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_POTASSIUM = CMaterials.registerMaterial("ImpurePotassium", "impurepotassium", 147);
            IMPURE_POTASSIUM.setColor(240, 240, 190).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_NICKEL = CMaterials.registerMaterial("ImpureNickel", "impurenickel", 156);
            IMPURE_NICKEL.setColor(210, 210, 240, 0).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_IRON = CMaterials.registerMaterial("ImpureIron", "impureiron", 154);
            IMPURE_IRON.setColor(216, 216, 216, 0).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_BERYLLIUM = CMaterials.registerMaterial("ImpureBeryllium", "impureberyllium", 132);
            IMPURE_BERYLLIUM.setColor(210, 240, 210).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_LEAD = CMaterials.registerMaterial("ImpureLead", "impurelead", 210);
            IMPURE_LEAD.setColor(190, 240, 210).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_HAFNIUM = CMaterials.registerMaterial("ImpureHafnium", "impurehafnium", 200);
            IMPURE_HAFNIUM.setColor(240, 210, 170).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_CHROME = CMaterials.registerMaterial("ImpureChrome", "impurechrome", 152);
            IMPURE_CHROME.setColor(240, 210, 210).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_TITANIUM = CMaterials.registerMaterial("ImpureTitanium", "impuretitanium", 150);
            IMPURE_TITANIUM.setColor(210, 240, 240).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_STRONTIUM = CMaterials.registerMaterial("ImpureStrontium", "impurestrontium", 166);
            IMPURE_STRONTIUM.setColor(210, 170, 242).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_BARIUM = CMaterials.registerMaterial("ImpureBarium", "impurebarium", 184);
            IMPURE_BARIUM.setColor(150, 80, 120).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_COPPER = CMaterials.registerMaterial("ImpureCopper", "impurecopper", 157);
            IMPURE_COPPER.setColor(160, 90, 10).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            IMPURE_OSMIUM = CMaterials.registerMaterial("ImpureOsmium", 204).setColor(70, 70, 150).setColor(120, 120, 60, 1).setColor(220, 220, 220, 2);
            SALT = CMaterials.registerMaterial("Salt", "salt", 1024);
            CAL_CLAY = CMaterials.registerMaterial("CalcareousClay", "calclay", 769);
            CALCIUM_CHLORIDE = CMaterials.registerMaterial("CalciumChloride", "calciumchloride", 1025);
            SODIUM_CARBONATE = CMaterials.registerMaterial("SodiumCarbonate", "sodiumcarbonate", 1026);
            QUARTZ = CMaterials.registerMaterial("Quartz", "quartz", 1027);
            SILICONE = CMaterials.registerMaterial("Silicone", "silicone", 1028);
            CMaterials.SILICONE.setColor((int)180, (int)180, (int)180, (int)240, (int)240, (int)240).hardness = 0.2f;
            EXC_CLAY = CMaterials.registerMaterial("ExcitedClay", "excclay", 770);
            CLAY_STEEL = CMaterials.registerMaterial("ClaySteel", "claysteel", 256);
            CMaterials.CLAY_STEEL.setColor((int)136, (int)144, (int)173).setColor((int)255, (int)255, (int)255, (int)2).hardness = 3.0f;
            CLAYIUM = CMaterials.registerMaterial("Clayium", "clayium", 257);
            CMaterials.CLAYIUM.setColor((int)90, (int)240, (int)210).setColor((int)63, (int)72, (int)85, (int)1).setColor((int)255, (int)205, (int)200, (int)2).hardness = 6.0f;
            ULTIMATE_ALLOY = CMaterials.registerMaterial("UltimateAlloy", "ultimatealloy", 258);
            CMaterials.ULTIMATE_ALLOY.setColor((int)85, (int)205, (int)85).setColor((int)245, (int)160, (int)255, (int)2).hardness = 9.0f;
            ORG_CLAY = CMaterials.registerMaterial("OrganicClay", "orgclay", 771);
            ORG_CLAY.setColor(136, 144, 173).setColor(106, 44, 43, 1).setColor(146, 164, 183, 2);
            ANTIMATTER = CMaterials.registerMaterial("Antimatter", 800);
            ANTIMATTER.setColor(0, 0, 235).setColor(0, 0, 0, 1).setColor(255, 255, 255, 2);
            PURE_ANTIMATTER = CMaterials.registerMaterial("PureAntimatter", 801);
            PURE_ANTIMATTER.setColor(255, 50, 255).setColor(0, 0, 0, 1).setColor(255, 255, 255, 2);
            COMPRESSED_PURE_ANTIMATTER = new CMaterial[9];
            CMaterials.COMPRESSED_PURE_ANTIMATTER[0] = PURE_ANTIMATTER;
            for (int i = 1; i <= 8; ++i) {
                CMaterials.COMPRESSED_PURE_ANTIMATTER[i] = i != 8 ? CMaterials.registerMaterial("PureAntimatter" + i, 801 + i) : CMaterials.registerMaterial("OctuplePureAntimatter", 801 + i);
                double r = (double)i / 8.0;
                double l = 1.0 - (r < 0.5 ? r : 1.0 - r) * 1.5;
                COMPRESSED_PURE_ANTIMATTER[i].setColor((int)(l * (255.0 * (1.0 - r) + 150.0 * r)), (int)(l * (50.0 * (1.0 - r) + 0.0 * r)), (int)(l * (255.0 * (1.0 - r) + 0.0 * r))).setColor((int)(200.0 * r), (int)(200.0 * r), 0, 1).setColor(255, 255, 255, 2);
            }
            OCTUPLE_CLAY = CMaterials.registerMaterial("OctupleEnergeticClay", 525);
            OCTUPLE_CLAY.setColor(255, 255, 0).setColor(140, 140, 140, 1).setColor(255, 255, 255, 2);
            OCTUPLE_PURE_ANTIMATTER = COMPRESSED_PURE_ANTIMATTER[8];
            IMPURE_ULTIMATE_ALLOY = CMaterials.registerMaterial("ImpureUltimateAlloy", "impureultimatealloy", 386);
            CMaterials.IMPURE_ULTIMATE_ALLOY.setColor((int)85, (int)205, (int)85).setColor((int)245, (int)160, (int)255, (int)2).setColor((int)245, (int)255, (int)255, (int)1).hardness = 9.0f;
            IMPURE_REDSTONE = CMaterials.registerMaterial("ImpureRedstone", "impureredstone", 897);
            IMPURE_REDSTONE.setColor(151, 70, 70);
            IMPURE_GLOWSTONE = CMaterials.registerMaterial("ImpureGlowstone", "impureglowstone", 898);
            IMPURE_GLOWSTONE.setColor(151, 151, 70);
            RUBIDIUM = CMaterials.registerMaterial("Rubidium", 37).setColor(245, 245, 245).setColor(235, 0, 0, 1);
            CAESIUM = CMaterials.registerMaterial("Caesium", 55).setColor(245, 245, 245).setColor(150, 150, 0, 1);
            FRANCIUM = CMaterials.registerMaterial("Francium", 87).setColor(245, 245, 245).setColor(0, 235, 0, 1);
            RADIUM = CMaterials.registerMaterial("Radium", 88).setColor(245, 245, 245).setColor(0, 150, 150, 1);
            ACTINIUM = CMaterials.registerMaterial("Actinium", 89).setColor(245, 245, 245).setColor(0, 0, 235, 1);
            THORIUM = CMaterials.registerMaterial("Thorium", 90).setColor(50, 50, 50).setColor(200, 50, 50, 2);
            PROTACTINIUM = CMaterials.registerMaterial("Protactinium", 91).setColor(50, 50, 50).setColor(50, 50, 100, 2);
            URANIUM = CMaterials.registerMaterial("Uranium", 92).setColor(50, 255, 50).setColor(50, 155, 50, 1).setColor(50, 255, 50, 2);
            NEPTUNIUM = CMaterials.registerMaterial("Neptunium", 93).setColor(50, 50, 255).setColor(50, 50, 155, 1).setColor(50, 50, 255, 2);
            PLUTONIUM = CMaterials.registerMaterial("Plutonium", 94).setColor(255, 50, 50).setColor(155, 50, 50, 1).setColor(255, 50, 50, 2);
            AMERICIUM = CMaterials.registerMaterial("Americium", 95).setColor(235, 235, 235).setColor(155, 155, 155, 1).setColor(235, 235, 235, 2);
            CURIUM = CMaterials.registerMaterial("Curium", 96).setColor(255, 255, 255).setColor(155, 155, 155, 1).setColor(244, 244, 244, 2);
            LANTHANUM = CMaterials.registerMaterial("Lanthanum", 57).setColor(145, 145, 145).setColor(235, 0, 0, 1);
            CERIUM = CMaterials.registerMaterial("Cerium", 58).setColor(145, 145, 145).setColor(150, 150, 0, 1);
            PRASEODYMIUM = CMaterials.registerMaterial("Praseodymium", 59).setColor(145, 145, 145).setColor(0, 235, 0, 1);
            NEODYMIUM = CMaterials.registerMaterial("Neodymium", 60).setColor(145, 145, 145).setColor(0, 150, 150, 1);
            PROMETHIUM = CMaterials.registerMaterial("Promethium", 61).setColor(145, 145, 145).setColor(0, 0, 235, 1);
            SAMARIUM = CMaterials.registerMaterial("Samarium", 62).setColor(145, 145, 145).setColor(150, 0, 150, 1);
            EUROPIUM = CMaterials.registerMaterial("Europium", 63).setColor(145, 145, 145).setColor(55, 55, 55, 1).setColor(145, 145, 145, 2);
            VANADIUM = CMaterials.registerMaterial("Vanadium", 23).setColor(60, 120, 120);
            COBALT = CMaterials.registerMaterial("Cobalt", 27).setColor(30, 30, 230);
            PALLADIUM = CMaterials.registerMaterial("Palladium", 46).setColor(151, 70, 70);
            SILVER = CMaterials.registerMaterial("Silver", 47).setColor(230, 230, 245).setColor(120, 120, 140, 1).setColor(255, 255, 255, 2);
            PLATINUM = CMaterials.registerMaterial("Platinum", 78).setColor(245, 245, 230).setColor(140, 140, 120, 1).setColor(255, 255, 255, 2);
            IRIDIUM = CMaterials.registerMaterial("Iridium", 77).setColor(240, 240, 240).setColor(210, 210, 210, 1).setColor(235, 235, 235, 2);
            OSMIUM = CMaterials.registerMaterial("Osmium", 76).setColor(70, 70, 150);
            RHENIUM = CMaterials.registerMaterial("Rhenium", 75).setColor(70, 70, 150).setColor(50, 50, 90, 2);
            TANTALUM = CMaterials.registerMaterial("Tantalum", 73).setColor(240, 210, 170).setColor(240, 210, 150, 2);
            TUNGSTEN = CMaterials.registerMaterial("Tungsten", 74).setColor(30, 30, 30);
            MOLYBDENUM = CMaterials.registerMaterial("Molybdenum", 42).setColor(130, 160, 130);
            TIN = CMaterials.registerMaterial("Tin", 50).setColor(230, 230, 240).setColor(0, 0, 0, 1).setColor(255, 255, 255, 2);
            ANTIMONY = CMaterials.registerMaterial("Antimony", 51).setColor(70, 70, 70);
            BISMUTH = CMaterials.registerMaterial("Bismuth", 83).setColor(70, 120, 70);
            BRONZE = CMaterials.registerMaterial("Bronze", 1280).setColor(250, 150, 40).setColor(0, 0, 0, 1).setColor(255, 255, 255, 2);
            BRASS = CMaterials.registerMaterial("Brass", 1281).setColor(190, 170, 20).setColor(0, 0, 0, 1).setColor(255, 255, 255, 2);
            ELECTRUM = CMaterials.registerMaterial("Electrum", 1283).setColor(230, 230, 155).setColor(120, 120, 70, 1).setColor(255, 255, 255, 2);
            INVAR = CMaterials.registerMaterial("Invar", 1284).setColor(170, 170, 80).setColor(140, 140, 70, 1).setColor(180, 180, 80, 2);
            STEEL = CMaterials.registerMaterial("Steel", 1536).setColor(90, 90, 110).setColor(0, 0, 0, 1).setColor(255, 255, 255, 2);
            OBSIDIAN = CMaterials.registerMaterial("Obsidian", 896);
            REDSTONE = CMaterials.registerMaterial("Redstone", 899);
            GLOWSTONE = CMaterials.registerMaterial("Glowstone", 900);
            ENDER_PEARL = CMaterials.registerMaterial("EnderPearl", 901);
            COAL = CMaterials.registerMaterial("Coal", 1792).setColor(20, 20, 20).setColor(50, 50, 80, 2);
            CHARCOAL = CMaterials.registerMaterial("Charcoal", 1793).setColor(20, 20, 20).setColor(80, 50, 50, 2);
            LAPIS = CMaterials.registerMaterial("Lapis", 1824).setColor(60, 100, 190).setColor(10, 43, 122, 1).setColor(90, 130, 226, 2);
            DIAMOND = CMaterials.registerMaterial("Diamond", 1856);
            EMERALD = CMaterials.registerMaterial("Emerald", 1857);
            STONE = CMaterials.registerMaterial("Stone", 2048);
            CARBON = CMaterials.registerMaterial("Carbon", 6).setColor(10, 10, 10).setColor(30, 30, 30, 2);
            PHOSPHORUS = CMaterials.registerMaterial("Phosphorus", 15).setColor(155, 155, 0, 205, 205, 50);
            SULFUR = CMaterials.registerMaterial("Sulfur", 16).setColor(205, 205, 0, 255, 255, 0);
            PLASTIC = CMaterials.registerMaterial("Plastic", 1032);
            CINNABAR = CMaterials.registerMaterial("Cinnabar", 1040);
            SALTPETER = CMaterials.registerMaterial("Saltpeter", 1041).setColor(190, 200, 210, 255, 240, 230);
            RUBY = CMaterials.registerMaterial("Ruby", 1858);
            SAPPHIRE = CMaterials.registerMaterial("Sapphire", 1859);
            PERIDOT = CMaterials.registerMaterial("Peridot", 1860);
            AMBER = CMaterials.registerMaterial("Amber", 1861);
            AMETHYST = CMaterials.registerMaterial("Amethyst", 1862);
            REDSTONE_ALLOY = CMaterials.registerMaterial("RedstoneAlloy", 1408);
            CONDUCTIVE_IRON = CMaterials.registerMaterial("ConductiveIron", 1409);
            ENERGETIC_ALLOY = CMaterials.registerMaterial("EnergeticAlloy", 1410);
            ELECTRICAL_STEEL = CMaterials.registerMaterial("ElectricalSteel", 1411);
            DARK_STEEL = CMaterials.registerMaterial("DarkSteel", 1412);
            PHASED_IRON = CMaterials.registerMaterial("PhasedIron", 1413);
            PHASED_GOLD = CMaterials.registerMaterial("PhasedGold", 1414);
            SOULARIUM = CMaterials.registerMaterial("Soularium", 1415);
            SIGNALUM = CMaterials.registerMaterial("Signalum", 1424);
            LUMIUM = CMaterials.registerMaterial("Lumium", 1425);
            ENDERIUM = CMaterials.registerMaterial("Enderium", 1426);
            ELECTRUM_FLUX = CMaterials.registerMaterial("ElectrumFlux", 1428);
            CRYSTAL_FLUX = CMaterials.registerMaterial("CrystalFlux", 2016);
            APATITE = CMaterials.registerMaterial("Apatite", 1044);
            CERTUS_QUARTZ = CMaterials.registerMaterial("CertusQuartz", 1048);
            FLUIX = CMaterials.registerMaterial("Fluix", 1049);
            ARDITE = CMaterials.registerMaterial("Ardite", 320);
            ALUMINUM_BRASS = CMaterials.registerMaterial("AluminumBrass", 1432);
            PIG_IRON = CMaterials.registerMaterial("PigIron", 1433);
            ALUMITE = CMaterials.registerMaterial("Alumite", 1434);
            MANYULLYN = CMaterials.registerMaterial("Manyullyn", 1435);
            FAIRY = CMaterials.registerMaterial("Fairy", 1440);
            POKEFENNIUM = CMaterials.registerMaterial("Pokefennium", 1441);
            RED_AURUM = CMaterials.registerMaterial("Red_aurum", 1442);
            DRULLOY = CMaterials.registerMaterial("Drulloy", 1443);
            RED_ALLOY = CMaterials.registerMaterial("RedAlloy", 1448);
            ELECTROTINE = CMaterials.registerMaterial("Electrotine", 1060);
            ELECTROTINE_ALLOY = CMaterials.registerMaterial("ElectrotineAlloy", 1449);
            REFINED_GLOWSTONE = CMaterials.registerMaterial("RefinedGlowstone", 1600);
            REFINED_OBSIDIAN = CMaterials.registerMaterial("RefinedObsidian", 1601);
            UNSTABLE = CMaterials.registerMaterial("Unstable", 1728);
            HSLA = CMaterials.registerMaterial("HSLA", 1608);
            YELLORIUM = CMaterials.registerMaterial("Yellorium", 324);
            CYANITE = CMaterials.registerMaterial("Cyanite", 325);
            BLUTONIUM = CMaterials.registerMaterial("Blutonium", 326);
            LUDICRITE = CMaterials.registerMaterial("Ludicrite", 327);
            GRAPHITE = CMaterials.registerMaterial("Graphite", 1056);
            METEORIC_IRON = CMaterials.registerMaterial("MeteoricIron", 336);
            DESH = CMaterials.registerMaterial("Desh", 337);
            IRON_COMPRESSED = CMaterials.registerMaterial("IronCompressed", 1604);
            FZ_DARK_IRON = CMaterials.registerMaterial("FzDarkIron", 332);
            THAUMIUM = CMaterials.registerMaterial("Thaumium", 1664);
            VOID = CMaterials.registerMaterial("Void", 1665);
            MANASTEEL = CMaterials.registerMaterial("Manasteel", 1668);
            TERRASTEEL = CMaterials.registerMaterial("Terrasteel", 1669);
            ELVEN_ELEMENTIUM = CMaterials.registerMaterial("ElvenElementium", 1670);
            TOPAZ = CMaterials.registerMaterial("Manasteel", 1863);
            MALACHITE = CMaterials.registerMaterial("Malachite", 1864);
            TANZANITE = CMaterials.registerMaterial("Tanzanite", 1865);
            HEE_ENDIUM = CMaterials.registerMaterial("HeeEndium", 1696);
            DILITHIUM = CMaterials.registerMaterial("Dilithium", 1880);
            FORCICIUM = CMaterials.registerMaterial("Forcicium", 1884);
            GALLIUM = CMaterials.registerMaterial("Gallium", 31);
            YTTRIUM = CMaterials.registerMaterial("Yttrium", 39);
            NIOBIUM = CMaterials.registerMaterial("Niobium", 41);
            URANIUM_235 = CMaterials.registerMaterial("Uranium235", 288);
            PLUTONIUM_241 = CMaterials.registerMaterial("Plutonium241", 289);
            NAQUADAH = CMaterials.registerMaterial("Naquadah", 296);
            NAQUADAH_ENRICHED = CMaterials.registerMaterial("NaquadahEnriched", 297);
            NAQUADRIA = CMaterials.registerMaterial("Naquadria", 298);
            NEUTRONIUM = CMaterials.registerMaterial("Neutronium", 299);
            NIKOLITE = CMaterials.registerMaterial("Nikolite", 1064);
            QUARTZITE = CMaterials.registerMaterial("Quartzite", 1088);
            MONAZITE = CMaterials.registerMaterial("Monazite", 1089);
            NITER = CMaterials.registerMaterial("Niter", 1090);
            TUNGSTEN_STEEL = CMaterials.registerMaterial("TungstenSteel", 1472);
            CUPRONICKEL = CMaterials.registerMaterial("Cupronickel", 1473);
            NICHROME = CMaterials.registerMaterial("Nichrome", 1474);
            KANTHAL = CMaterials.registerMaterial("Kanthal", 1475);
            STAINLESS_STEEL = CMaterials.registerMaterial("StainlessSteel", 1476);
            COBALT_BRASS = CMaterials.registerMaterial("CobaltBrass", 1477);
            MAGNALIUM = CMaterials.registerMaterial("Magnalium", 1478);
            SOLDERING_ALLOY = CMaterials.registerMaterial("SolderingAlloy", 1479);
            BATTERY_ALLOY = CMaterials.registerMaterial("BatteryAlloy", 1480);
            VANADIUM_GALLIUM = CMaterials.registerMaterial("VanadiumGallium", 1481);
            YTTRIUM_BARIUM_CUPRATE = CMaterials.registerMaterial("YttriumBariumCuprate", 1482);
            NIOBIUM_TITANIUM = CMaterials.registerMaterial("NiobiumTitanium", 1483);
            ULTIMET = CMaterials.registerMaterial("Ultimet", 1484);
            TIN_ALLOY = CMaterials.registerMaterial("TinAlloy", 1485);
            BLUE_ALLOY = CMaterials.registerMaterial("BlueAlloy", 1486);
            WROUGHT_IRON = CMaterials.registerMaterial("WroughtIron", 1552);
            ANNEALED_COPPER = CMaterials.registerMaterial("AnnealedCopper", 1553);
            IRON_MAGNETIC = CMaterials.registerMaterial("IronMagnetic", 1568);
            STEEL_MAGNETIC = CMaterials.registerMaterial("SteelMagnetic", 1569);
            NEODYMIUM_MAGNETIC = CMaterials.registerMaterial("NeodymiumMagnetic", 1570);
            LIGNITE = CMaterials.registerMaterial("Lignite", 1808);
            LAZURITE = CMaterials.registerMaterial("Lazurite", 1840);
            SODALITE = CMaterials.registerMaterial("Sodalite", 1841);
            GREEN_SAPPHIRE = CMaterials.registerMaterial("GreenSaphire", 1888);
            GARNET_RED = CMaterials.registerMaterial("GarnetRed", 1892);
            GARNET_YELLOW = CMaterials.registerMaterial("GarnetYellow", 1893);
            OPAL = CMaterials.registerMaterial("Opal", 1896);
            JASPER = CMaterials.registerMaterial("Jasper", 1897);
            BLUE_TOPAZ = CMaterials.registerMaterial("BlueTopaz", 1920);
            FORCE = CMaterials.registerMaterial("Force", 1984);
            FORCILLIUM = CMaterials.registerMaterial("Forcillium", 1988);
            GLASS = CMaterials.registerMaterial("Glass", 2176);
            PROMETHEUM = CMaterials.registerMaterial("Prometheum", 352);
            DEEP_IRON = CMaterials.registerMaterial("DeepIron", 353);
            INFUSCOLIUM = CMaterials.registerMaterial("Infuscolium", 354);
            OURECLASE = CMaterials.registerMaterial("Oureclase", 355);
            AREDRITE = CMaterials.registerMaterial("Aredrite", 356);
            ASTRAL_SILVER = CMaterials.registerMaterial("AstralSilver", 357);
            CARMOT = CMaterials.registerMaterial("Carmot", 358);
            MITHRIL = CMaterials.registerMaterial("Mithril", 359);
            RUBRACIUM = CMaterials.registerMaterial("Rubracium", 360);
            ORICHALCUM = CMaterials.registerMaterial("Orichalcum", 361);
            ADAMANTINE = CMaterials.registerMaterial("Adamantine", 362);
            ATLARUS = CMaterials.registerMaterial("Atlarus", 363);
            IGNATIUS = CMaterials.registerMaterial("Ignatius", 368);
            SHADOW_IRON = CMaterials.registerMaterial("ShadowIron", 369);
            LEMURITE = CMaterials.registerMaterial("Lemurite", 370);
            MIDASIUM = CMaterials.registerMaterial("Midasium", 371);
            VYROXERES = CMaterials.registerMaterial("Vyroxeres", 372);
            CERUCLASE = CMaterials.registerMaterial("Ceruclase", 373);
            ALDUORITE = CMaterials.registerMaterial("Alduorite", 374);
            KALENDRITE = CMaterials.registerMaterial("Kalendrite", 375);
            VULCANITE = CMaterials.registerMaterial("Vulcanite", 376);
            SANGUINITE = CMaterials.registerMaterial("Sanguinite", 377);
            EXIMITE = CMaterials.registerMaterial("Eximite", 380);
            MEUTOITE = CMaterials.registerMaterial("Meutoite", 381);
            HEPATIZON = CMaterials.registerMaterial("Hepatizon", 1504);
            DAMASCUS_STEEL = CMaterials.registerMaterial("DamascusSteel", 1505);
            ANGMALLEN = CMaterials.registerMaterial("Angmallen", 1506);
            BLACK_STEEL = CMaterials.registerMaterial("BlackSteel", 1508);
            QUICKSILVER = CMaterials.registerMaterial("QuickSilver", 1509);
            HADEROTH = CMaterials.registerMaterial("Haderoth", 1510);
            CELENEGIL = CMaterials.registerMaterial("Celenegil", 1511);
            TARTARITE = CMaterials.registerMaterial("Tartarite", 1512);
            SHADOW_STEEL = CMaterials.registerMaterial("ShadowSteel", 1516);
            INOLASHITE = CMaterials.registerMaterial("Inolashite", 1517);
            AMORDRINE = CMaterials.registerMaterial("Amordrine", 1518);
            DESICHALKOS = CMaterials.registerMaterial("Desichalkos", 1519);
            NINJA = CMaterials.registerMaterial("Ninja", 1468);
            YELLOWSTONE = CMaterials.registerMaterial("Yellowstone", 1068);
            BLUESTONE = CMaterials.registerMaterial("Bluestone", 1069);
            ALUMINIUM_OD = CMaterials.registerMaterial("Aluminium", 4096);
            itemPlates = CMaterials.createItem("itemPlates");
            itemLargePlates = CMaterials.createItem("itemLargePlates");
            itemDusts = CMaterials.createItem("itemDusts");
            itemIngots = CMaterials.createItem("itemIngots");
            itemGems = CMaterials.createItem("itemGems");
            PLATE = CMaterials.registerShape("Plate", "plate", 0, itemPlates);
            STICK = CMaterials.registerShape("Stick", "stick", 1);
            SHORT_STICK = CMaterials.registerShape("ShortStick", "shortstick", 2);
            RING = CMaterials.registerShape("Ring", "ring", 3);
            SMALL_RING = CMaterials.registerShape("SmallRing", "smallring", 4);
            GEAR = CMaterials.registerShape("Gear", "gear", 5);
            BLADE = CMaterials.registerShape("Blade", "blade", 6);
            NEEDLE = CMaterials.registerShape("Needle", "needle", 7);
            DISC = CMaterials.registerShape("Disc", "disc", 16);
            SMALL_DISC = CMaterials.registerShape("SmallDisc", "smalldisc", 17);
            CYLINDER = CMaterials.registerShape("Cylinder", "cylinder", 18);
            PIPE = CMaterials.registerShape("Pipe", "pipe", 19);
            LARGE_BALL = CMaterials.registerShape("LargeBall", "largeball", 32);
            LARGE_PLATE = CMaterials.registerShape("LargePlate", "largeplate", 33, itemLargePlates);
            GRINDING_HEAD = CMaterials.registerShape("GrindingHead", "grindinghead", 64);
            BEARING = CMaterials.registerShape("Bearing", "bearing", 65);
            SPINDLE = CMaterials.registerShape("Spindle", "spindle", 66);
            CUTTING_HEAD = CMaterials.registerShape("CuttingHead", "cuttinghead", 67);
            WATER_WHEEL = CMaterials.registerShape("WaterWheel", "waterwheel", 68);
            BLOCK = CMaterials.registerShape("Block", "block", 1024);
            BALL = CMaterials.registerShape("Ball", "ball", 8);
            DUST = CMaterials.registerShape("Dust", "dust", 128, itemDusts);
            INGOT = CMaterials.registerShape("Ingot", "ingot", 129, itemIngots);
            GEM = CMaterials.registerShape("Gem", "gem", 130, itemGems);
            CRYSTAL = CMaterials.registerShape("Crystal", "crystal", 131);
            CMaterials.add(CLAY, PLATE);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, STICK);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, SHORT_STICK, "shortclaystick");
            CMaterials.setTier(1);
            CMaterials.add(CLAY, RING);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, SMALL_RING, "smallclayring");
            CMaterials.setTier(1);
            CMaterials.add(CLAY, GEAR);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, BLADE);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, NEEDLE);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, DISC);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, SMALL_DISC, "smallclaydisc");
            CMaterials.setTier(1);
            CMaterials.add(CLAY, CYLINDER);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, PIPE);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, LARGE_BALL, "largeclayball");
            CMaterials.setTier(1);
            CMaterials.add(CLAY, LARGE_PLATE, "largeclayplate");
            CMaterials.setTier(1);
            CMaterials.add(CLAY, GRINDING_HEAD);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, BEARING);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, SPINDLE);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, CUTTING_HEAD);
            CMaterials.setTier(1);
            CMaterials.add(CLAY, WATER_WHEEL);
            CMaterials.setTier(1);
            CMaterials.registerMaterialShape(CLAY, BLOCK, new ItemStack(Blocks.field_150435_aG));
            CMaterials.registerMaterialShape(CLAY, BALL, new ItemStack(Items.field_151119_aD));
            CMaterials.add(DENSE_CLAY, PLATE);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, STICK);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, SHORT_STICK, "shortdenseclaystick");
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, RING);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, SMALL_RING, "smalldenseclayring");
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, GEAR);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, BLADE);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, NEEDLE);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, DISC);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, SMALL_DISC, "smalldenseclaydisc");
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, CYLINDER);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, PIPE);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, LARGE_PLATE, "largedenseclayplate");
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, GRINDING_HEAD);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, BEARING);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, SPINDLE);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, CUTTING_HEAD);
            CMaterials.setTier(2);
            CMaterials.add(DENSE_CLAY, WATER_WHEEL);
            CMaterials.setTier(2);
            CMaterials.registerMaterialShape(DENSE_CLAY, BLOCK, new ItemStack(CBlocks.blockCompressedClay, 1, 0));
            CMaterials.registerMaterialShape(DENSE_CLAY, BALL, new ItemStack(Items.field_151119_aD));
            CMaterials.add(IND_CLAY, PLATE);
            CMaterials.setTier(3);
            CMaterials.add(IND_CLAY, LARGE_PLATE);
            CMaterials.setTier(3);
            CMaterials.registerMaterialShape(IND_CLAY, BLOCK, new ItemStack(CBlocks.blockCompressedClay, 1, 2));
            CMaterials.add(ADVIND_CLAY, PLATE);
            CMaterials.setTier(4);
            CMaterials.add(ADVIND_CLAY, LARGE_PLATE);
            CMaterials.setTier(4);
            CMaterials.registerMaterialShape(ADVIND_CLAY, BLOCK, new ItemStack(CBlocks.blockCompressedClay, 1, 3));
            CMaterials.add(CLAY, DUST);
            CMaterials.setTier(1);
            CMaterials.add(DENSE_CLAY, DUST);
            CMaterials.setTier(2);
            CMaterials.addOD(IND_CLAY, DUST, false, true);
            CMaterials.setTier(3);
            CMaterials.addOD(ADVIND_CLAY, DUST, false, true);
            CMaterials.setTier(4);
            CMaterials.addOD(ENG_CLAY, DUST, false, true);
            CMaterials.setTier(3);
            CMaterials.addOD(EXC_CLAY, DUST, false, true);
            CMaterials.setTier(7);
            CMaterials.addOD(SALT, DUST, false, true);
            CMaterials.setTier(4);
            CMaterials.add(CAL_CLAY, DUST);
            CMaterials.setTier(4);
            CMaterials.add(CALCIUM_CHLORIDE, DUST);
            CMaterials.setTier(4);
            CMaterials.add(SODIUM_CARBONATE, DUST);
            CMaterials.setTier(4);
            CMaterials.addOD(QUARTZ, DUST, false, true);
            CMaterials.setTier(4);
            CMaterials.addOD(IMPURE_SILICON, DUST, true, true);
            CMaterials.setTier(5);
            CMaterials.addOD(IMPURE_SILICON, INGOT, false, true);
            CMaterials.setTier(5);
            CMaterials.addOD(IMPURE_SILICON, PLATE, false, true);
            CMaterials.setTier(5);
            CMaterials.add(IMPURE_SILICON, LARGE_PLATE);
            CMaterials.setTier(5);
            CMaterials.addOD(SILICONE, DUST, true, true);
            CMaterials.setTier(5);
            CMaterials.addOD(SILICONE, INGOT, true, true);
            CMaterials.setTier(5);
            CMaterials.addOD(SILICONE, PLATE, true, true);
            CMaterials.setTier(5);
            CMaterials.add(SILICONE, LARGE_PLATE, true);
            CMaterials.setTier(5);
            CMaterials.addOD(SILICON, DUST, true, true);
            CMaterials.setTier(5);
            CMaterials.addOD(SILICON, INGOT, true, true);
            CMaterials.setTier(5);
            CMaterials.addOD(SILICON, PLATE, true, true);
            CMaterials.setTier(5);
            CMaterials.add(SILICON, LARGE_PLATE, true);
            CMaterials.setTier(5);
            CMaterials.addOD(ALUMINIUM, DUST, true, true);
            CMaterials.setTier(6);
            CMaterials.addOD(ALUMINIUM, INGOT, true, true);
            CMaterials.setTier(6);
            CMaterials.addOD(ALUMINIUM, PLATE, true, true);
            CMaterials.setTier(6);
            CMaterials.add(ALUMINIUM, LARGE_PLATE, true);
            CMaterials.setTier(6);
            boolean cfg = ClayiumCore.cfgHardcoreAluminium;
            if (cfg) {
                CMaterials.setTier(IMPURE_ALUMINIUM, DUST, 6);
            }
            CMaterials.add(IMPURE_ALUMINIUM, INGOT, true, cfg);
            CMaterials.setTier(6);
            CMaterials.add(IMPURE_ALUMINIUM, PLATE, true, cfg);
            CMaterials.setTier(6);
            CMaterials.add(IMPURE_ALUMINIUM, LARGE_PLATE, true, cfg);
            CMaterials.setTier(6);
            MAIN_ALUMINIUM = cfg ? IMPURE_ALUMINIUM : ALUMINIUM;
            CMaterials.addOD(CLAY_STEEL, DUST, true, true);
            CMaterials.setTier(7);
            CMaterials.addOD(CLAY_STEEL, INGOT, true, true);
            CMaterials.setTier(7);
            CMaterials.addOD(CLAY_STEEL, PLATE, true, true);
            CMaterials.setTier(7);
            CMaterials.add(CLAY_STEEL, LARGE_PLATE, true);
            CMaterials.setTier(7);
            CMaterials.addOD(CLAYIUM, DUST, true, true);
            CMaterials.setTier(8);
            CMaterials.addOD(CLAYIUM, INGOT, true, true);
            CMaterials.setTier(8);
            CMaterials.addOD(CLAYIUM, PLATE, true, true);
            CMaterials.setTier(8);
            CMaterials.add(CLAYIUM, LARGE_PLATE, true);
            CMaterials.setTier(8);
            CMaterials.addOD(ULTIMATE_ALLOY, DUST, true, true);
            CMaterials.setTier(9);
            CMaterials.addOD(ULTIMATE_ALLOY, INGOT, true, true);
            CMaterials.setTier(9);
            CMaterials.addOD(ULTIMATE_ALLOY, PLATE, true, true);
            CMaterials.setTier(9);
            CMaterials.add(ULTIMATE_ALLOY, LARGE_PLATE, true);
            CMaterials.setTier(9);
            CMaterials.addOD(ANTIMATTER, DUST, true, true);
            CMaterials.setTier(10);
            CMaterials.addOD(ANTIMATTER, GEM, "matter", true, true);
            CMaterials.setTier(10);
            CMaterials.addOD(ANTIMATTER, PLATE, true, true);
            CMaterials.setTier(10);
            CMaterials.add(ANTIMATTER, LARGE_PLATE, true);
            CMaterials.setTier(10);
            CMaterials.addOD(PURE_ANTIMATTER, DUST, true, true);
            CMaterials.setTier(11);
            CMaterials.addOD(PURE_ANTIMATTER, GEM, "matter", true, true);
            CMaterials.setTier(11);
            CMaterials.addOD(PURE_ANTIMATTER, PLATE, true, true);
            CMaterials.setTier(11);
            CMaterials.add(PURE_ANTIMATTER, LARGE_PLATE, true);
            CMaterials.setTier(11);
            CMaterials.addOD(COMPRESSED_PURE_ANTIMATTER[1], GEM, "matter", true, true);
            CMaterials.setTier(11);
            CMaterials.addOD(COMPRESSED_PURE_ANTIMATTER[2], GEM, "matter2", true, true);
            CMaterials.setTier(11);
            CMaterials.addOD(COMPRESSED_PURE_ANTIMATTER[3], GEM, "matter2", true, true);
            CMaterials.setTier(11);
            CMaterials.addOD(COMPRESSED_PURE_ANTIMATTER[4], GEM, "matter3", true, true);
            CMaterials.setTier(12);
            CMaterials.addOD(COMPRESSED_PURE_ANTIMATTER[5], GEM, "matter3", true, true);
            CMaterials.setTier(12);
            CMaterials.addOD(COMPRESSED_PURE_ANTIMATTER[6], GEM, "matter4", true, true);
            CMaterials.setTier(12);
            CMaterials.addOD(COMPRESSED_PURE_ANTIMATTER[7], GEM, "matter4", true, true);
            CMaterials.setTier(12);
            CMaterials.add(OCTUPLE_CLAY, DUST, true);
            CMaterials.setTier(12);
            CMaterials.add(OCTUPLE_CLAY, PLATE, true);
            CMaterials.setTier(12);
            CMaterials.add(OCTUPLE_CLAY, LARGE_PLATE, true);
            CMaterials.setTier(12);
            CMaterials.registerMaterialShape(OCTUPLE_CLAY, BLOCK, new ItemStack(CBlocks.blockCompressedClay, 1, 12));
            CMaterials.addOD(OCTUPLE_PURE_ANTIMATTER, DUST, true, true);
            CMaterials.setTier(13);
            CMaterials.addOD(OCTUPLE_PURE_ANTIMATTER, GEM, "matter5", true, true);
            CMaterials.setTier(13);
            CMaterials.addOD(OCTUPLE_PURE_ANTIMATTER, PLATE, true, true);
            CMaterials.setTier(13);
            CMaterials.add(OCTUPLE_PURE_ANTIMATTER, LARGE_PLATE, true);
            CMaterials.setTier(13);
            for (CMaterial material : new CMaterial[]{IMPURE_SILICON, SILICONE, SILICON, ALUMINIUM, CLAY_STEEL, CLAYIUM, ULTIMATE_ALLOY, ANTIMATTER, PURE_ANTIMATTER, OCTUPLE_PURE_ANTIMATTER}) {
                CMaterials.registerMaterialShape(material, BLOCK, CBlocks.blockMaterial.get(material.name));
                CMaterials.addItemToOD(material, BLOCK);
            }
            String[] dyes = new String[]{"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White"};
            for (int i = 0; i < 16; ++i) {
                OreDictionary.registerOre((String)CMaterials.getODName(SILICONE, BLOCK), (ItemStack)new ItemStack((Block)CBlocks.blockSiliconeColored, 1, i));
                OreDictionary.registerOre((String)(CMaterials.getODName(SILICONE, BLOCK) + dyes[15 - i]), (ItemStack)new ItemStack((Block)CBlocks.blockSiliconeColored, 1, i));
            }
            CMaterials.addOD(ORG_CLAY, DUST, true, true);
            CMaterials.add(IMPURE_REDSTONE, DUST, true);
            CMaterials.add(IMPURE_GLOWSTONE, DUST, true);
            CMaterials.add(IMPURE_ULTIMATE_ALLOY, INGOT, true);
            CMaterials.setTier(8);
            for (CMaterial metal : metals = new CMaterial[]{MAGNESIUM, SODIUM, LITHIUM, ZIRCONIUM, ZINC, MANGANESE, CALCIUM, POTASSIUM, NICKEL, BERYLLIUM, LEAD, HAFNIUM, CHROME, TITANIUM, STRONTIUM, BARIUM, COPPER, IRON, GOLD, BRONZE, BRASS, ELECTRUM, INVAR, STEEL}) {
                CMaterials.addOD(metal, DUST, true, false);
                CMaterials.addOD(metal, INGOT, true, false);
            }
            for (CMaterial metal : impuremetals = new CMaterial[]{IMPURE_ALUMINIUM, IMPURE_MAGNESIUM, IMPURE_SODIUM, IMPURE_LITHIUM, IMPURE_ZIRCONIUM, IMPURE_ZINC, IMPURE_MANGANESE, IMPURE_CALCIUM, IMPURE_POTASSIUM, IMPURE_NICKEL, IMPURE_IRON, IMPURE_BERYLLIUM, IMPURE_LEAD, IMPURE_HAFNIUM, IMPURE_CHROME, IMPURE_TITANIUM, IMPURE_STRONTIUM, IMPURE_BARIUM, IMPURE_COPPER}) {
                CMaterials.add(metal, DUST, true);
            }
            metals = new CMaterial[]{ZINCALMINIUM_ALLOY, ZINCONIUM_ALLOY};
            for (CMaterial metal : metals) {
                CMaterials.addOD(metal, DUST, true, true);
                CMaterials.setTier(6);
                CMaterials.addOD(metal, INGOT, true, true);
                CMaterials.setTier(6);
            }
            metals = new CMaterial[]{AZ91D_ALLOY, ZK60A_ALLOY};
            for (CMaterial metal : metals) {
                CMaterials.addOD(metal, DUST, true, true);
                CMaterials.setTier(6);
                CMaterials.addOD(metal, INGOT, true, true);
                CMaterials.setTier(6);
                CMaterials.addOD(metal, PLATE, true, true);
                CMaterials.setTier(6);
                CMaterials.add(metal, LARGE_PLATE, true);
                CMaterials.setTier(6);
            }
            CMaterials.addOD(CARBON, DUST, true, false);
            CMaterials.addOD(PHOSPHORUS, DUST, true, false);
            CMaterials.addOD(SULFUR, DUST, true, false);
            CMaterials.addOD(LAPIS, DUST, true, false);
            CMaterials.addOD(COAL, DUST, true, false);
            CMaterials.addOD(CHARCOAL, DUST, true, false);
            CMaterials.addOD(SALTPETER, DUST, true, false);
            metals = new CMaterial[]{RUBIDIUM, CAESIUM, FRANCIUM, RADIUM, ACTINIUM, THORIUM, PROTACTINIUM, URANIUM, NEPTUNIUM, PLUTONIUM, AMERICIUM, CURIUM, LANTHANUM, CERIUM, PRASEODYMIUM, NEODYMIUM, PROMETHIUM, SAMARIUM, EUROPIUM, VANADIUM, COBALT, PALLADIUM, SILVER, PLATINUM, IRIDIUM, OSMIUM, RHENIUM, TANTALUM, TUNGSTEN, MOLYBDENUM, TIN, ANTIMONY, BISMUTH};
            for (CMaterial metal : metals) {
                CMaterials.addOD(metal, INGOT, true, false);
            }
            cfg = ClayiumCore.cfgHardcoreOsmium;
            CMaterials.addOD(IMPURE_OSMIUM, INGOT, true, true, cfg ? 0 : -1);
            MAIN_OSMIUM = cfg ? IMPURE_OSMIUM : OSMIUM;
            OreDictionary.registerOre((String)"itemSilicon", (ItemStack)CMaterials.get(SILICON, PLATE));
            OreDictionary.registerOre((String)"itemSalt", (ItemStack)CMaterials.get(SALT, DUST));
            OreDictionary.registerOre((String)"condimentSalt", (ItemStack)CMaterials.get(SALT, DUST));
            OreDictionary.registerOre((String)"ingotAluminium", (ItemStack)CMaterials.get(ALUMINIUM, INGOT));
            OreDictionary.registerOre((String)"dustAluminium", (ItemStack)CMaterials.get(ALUMINIUM, DUST));
            OreDictionary.registerOre((String)"plateAluminium", (ItemStack)CMaterials.get(ALUMINIUM, PLATE));
            OreDictionary.registerOre((String)"blockAluminium", (ItemStack)CMaterials.get(ALUMINIUM, BLOCK));
            MetalChest.registerMaterials();
            MetalBlock.registerMaterials();
        } else {
            itemIngots = CMaterials.createItem("itemIngots");
            CLAY_STEEL = CMaterials.registerMaterial("ClaySteel", "claysteel", 256);
            CMaterials.CLAY_STEEL.setColor((int)136, (int)144, (int)173).setColor((int)255, (int)255, (int)255, (int)2).hardness = 3.0f;
            INGOT = CMaterials.registerShape("Ingot", "ingot", 129, itemIngots);
            CMaterials.addOD(CLAY_STEEL, INGOT, true, true);
        }
    }

    public static CMaterial registerMaterial(String materialName, String materialIconName, String materialOreDictionaryName, int materialMeta, ItemDamaged itemMap) {
        CMaterial material = new CMaterial(materialName, materialIconName, materialOreDictionaryName, materialMeta);
        materialMap.put(material, itemMap);
        materialList.put(materialMeta, material);
        return material;
    }

    public static CMaterial registerMaterial(String materialName, String materialIconName, int materialMeta, ItemDamaged itemMap) {
        CMaterial material = new CMaterial(materialName, materialIconName, materialMeta);
        materialMap.put(material, itemMap);
        materialList.put(materialMeta, material);
        return material;
    }

    public static CShape registerShape(String shapeName, String shapeIconName, String shapeOreDictionaryName, int shapeMeta, ItemDamaged itemMap) {
        CShape shape = new CShape(shapeName, shapeIconName, shapeOreDictionaryName, shapeMeta);
        shapeMap.put(shape, itemMap);
        return shape;
    }

    public static CShape registerShape(String shapeName, String shapeIconName, int shapeMeta, ItemDamaged itemMap) {
        CShape shape = new CShape(shapeName, shapeIconName, shapeMeta);
        shapeMap.put(shape, itemMap);
        return shape;
    }

    public static void registerMaterialShape(CMaterial material, CShape shape, ItemStack itemStack) {
        if (!materialShapeMap.containsKey(material)) {
            materialShapeMap.put(material, new HashMap());
        }
        materialShapeMap.get(material).put(shape, itemStack);
    }

    public static CMaterial registerMaterial(String materialName, String materialIconName, String materialOreDictionaryName, int materialMeta) {
        CMaterial material = new CMaterial(materialName, materialIconName, materialOreDictionaryName, materialMeta);
        materialList.put(materialMeta, material);
        return material;
    }

    public static CMaterial registerMaterial(String materialName, int materialMeta) {
        return CMaterials.registerMaterial(materialName, materialName.toLowerCase(), materialMeta);
    }

    public static CMaterial registerMaterial(String materialName, String materialIconName, int materialMeta) {
        CMaterial material = new CMaterial(materialName, materialIconName, materialMeta);
        materialList.put(materialMeta, material);
        return material;
    }

    public static CShape registerShape(String shapeName, String shapeIconName, String shapeOreDictionaryName, int shapeMeta) {
        CShape shape = new CShape(shapeName, shapeIconName, shapeOreDictionaryName, shapeMeta);
        return shape;
    }

    public static CShape registerShape(String shapeName, String shapeIconName, int shapeMeta) {
        CShape shape = new CShape(shapeName, shapeIconName, shapeMeta);
        return shape;
    }

    public static boolean add(CMaterial material, CShape shape) {
        return CMaterials.add(material, shape, material.iname + shape.iname);
    }

    public static boolean add(CMaterial material, CShape shape, String iconstr) {
        return CMaterials.add(material, shape, iconstr, true);
    }

    public static boolean add(CMaterial material, CShape shape, String iconstr, boolean display) {
        currentMaterial = material;
        currentShape = shape;
        if (materialShapeMap.containsKey(material) && materialShapeMap.get(material).containsKey(shape)) {
            ClayiumCore.logger.error("The CMaterials item already exits  [" + material.name + "] [" + shape.name + "]");
            return false;
        }
        if (materialMap.containsKey(material)) {
            materialMap.get(material).addItemList(shape.name, shape.meta, iconstr, display);
            return true;
        }
        if (shapeMap.containsKey(shape)) {
            shapeMap.get(shape).addItemList(material.name, material.meta, iconstr, display);
            return true;
        }
        ClayiumCore.logger.error("Can't add the CMaterials item  [" + material.name + "] [" + shape.name + "]");
        return false;
    }

    public static boolean addOD(CMaterial material, CShape shape, String iconstr, boolean registerOreDictionary) {
        boolean res = false;
        if (registerOreDictionary) {
            res = CMaterials.add(material, shape, iconstr);
            CMaterials.addItemToOD(material, shape);
        } else {
            ArrayList list = OreDictionary.getOres((String)CMaterials.getODName(material, shape));
            if (list != null && list.size() > 0) {
                CMaterials.registerMaterialShape(material, shape, (ItemStack)list.get(0));
            } else {
                return CMaterials.addOD(material, shape, iconstr, true);
            }
        }
        return res;
    }

    public static boolean add(CMaterial material, CShape shape, IMultipleRenderIcons icon) {
        return CMaterials.add(material, shape, icon, true);
    }

    public static boolean add(CMaterial material, CShape shape, IMultipleRenderIcons icon, boolean display) {
        currentMaterial = material;
        currentShape = shape;
        if (materialShapeMap.containsKey(material) && materialShapeMap.get(material).containsKey(shape)) {
            ClayiumCore.logger.error("The CMaterials item already exits  [" + material.name + "] [" + shape.name + "]");
            return false;
        }
        if (materialMap.containsKey(material)) {
            materialMap.get(material).addItemList(shape.name, shape.meta, icon, display);
            return true;
        }
        if (shapeMap.containsKey(shape)) {
            shapeMap.get(shape).addItemList(material.name, material.meta, icon, display);
            return true;
        }
        ClayiumCore.logger.error("Can't add the CMaterials item  [" + material.name + "] [" + shape.name + "]");
        return false;
    }

    public static boolean add(CMaterial material, CShape shape, boolean materialicon) {
        return CMaterials.add(material, shape, materialicon, true);
    }

    public static boolean add(CMaterial material, CShape shape, boolean materialicon, boolean display) {
        return CMaterials.add(material, shape, shape.iname, materialicon, display);
    }

    public static boolean add(CMaterial material, CShape shape, String shapeiconstr, boolean materialicon, boolean display) {
        return materialicon ? CMaterials.add(material, shape, new GenericMaterialIcon(shapeiconstr, material.colors[0][0], material.colors[0][1], material.colors[0][2], material.colors[1][0], material.colors[1][1], material.colors[1][2], material.colors[2][0], material.colors[2][1], material.colors[2][2]), display) : CMaterials.add(material, shape, material.iname + shapeiconstr, display);
    }

    public static boolean addOD(CMaterial material, CShape shape, boolean materialicon, boolean registerOreDictionary) {
        return CMaterials.addOD(material, shape, materialicon, registerOreDictionary, 0);
    }

    public static boolean addOD(CMaterial material, CShape shape, boolean materialicon, boolean registerOreDictionary, int displayFlag) {
        boolean res = false;
        if (registerOreDictionary) {
            res = CMaterials.add(material, shape, materialicon, displayFlag >= 0);
            CMaterials.addItemToOD(material, shape);
        } else {
            ArrayList list = OreDictionary.getOres((String)CMaterials.getODName(material, shape));
            if (list != null && list.size() > 0) {
                res = CMaterials.add(material, shape, materialicon, displayFlag > 0);
                CMaterials.addItemToOD(material, shape);
                CMaterials.registerMaterialShape(material, shape, (ItemStack)list.get(0));
            } else {
                return CMaterials.addOD(material, shape, materialicon, true, displayFlag);
            }
        }
        return res;
    }

    public static boolean addOD(CMaterial material, CShape shape, String shapeiconstr, boolean materialicon, boolean registerOreDictionary) {
        return CMaterials.addOD(material, shape, shapeiconstr, materialicon, registerOreDictionary, 0);
    }

    public static boolean addOD(CMaterial material, CShape shape, String shapeiconstr, boolean materialicon, boolean registerOreDictionary, int displayFlag) {
        boolean res = false;
        if (registerOreDictionary) {
            res = CMaterials.add(material, shape, shapeiconstr, materialicon, displayFlag >= 0);
            CMaterials.addItemToOD(material, shape);
        } else {
            ArrayList list = OreDictionary.getOres((String)CMaterials.getODName(material, shape));
            if (list != null && list.size() > 0) {
                res = CMaterials.add(material, shape, shapeiconstr, materialicon, displayFlag > 0);
                CMaterials.addItemToOD(material, shape);
                CMaterials.registerMaterialShape(material, shape, (ItemStack)list.get(0));
            } else {
                return CMaterials.addOD(material, shape, shapeiconstr, materialicon, true, displayFlag);
            }
        }
        return res;
    }

    public static void addItemToOD(CMaterial material, CShape shape) {
        OreDictionary.registerOre((String)CMaterials.getODName(material, shape), (ItemStack)CMaterials.get(material, shape));
    }

    public static boolean setTier(CMaterial material, CShape shape, int tier) {
        if (materialShapeMap.containsKey(material) && materialShapeMap.get(material).containsKey(shape)) {
            ClayiumCore.logger.info("Can(t apply the tier to " + material.name + " " + shape.name);
            return false;
        }
        if (materialMap.containsKey(material)) {
            materialMap.get(material).setTier(shape.name, tier);
            return true;
        }
        if (shapeMap.containsKey(shape)) {
            shapeMap.get(shape).setTier(material.name, tier);
            return true;
        }
        ClayiumCore.logger.error("Can't get the CMaterials item  [" + material.name + "] [" + shape.name + "]");
        return false;
    }

    public static boolean setTier(int tier) {
        return CMaterials.setTier(currentMaterial, currentShape, tier);
    }

    public static ItemStack get(CMaterial material, CShape shape, int stackSize) {
        if (materialShapeMap.containsKey(material) && materialShapeMap.get(material).containsKey(shape)) {
            ItemStack itemStack = materialShapeMap.get(material).get(shape).func_77946_l();
            itemStack.field_77994_a = stackSize;
            return itemStack;
        }
        if (materialMap.containsKey(material) && materialMap.get(material).containsKey(shape.name)) {
            return materialMap.get(material).getItemStack(shape.name, stackSize);
        }
        if (shapeMap.containsKey(shape) && shapeMap.get(shape).containsKey(material.name)) {
            return shapeMap.get(shape).getItemStack(material.name, stackSize);
        }
        ClayiumCore.logger.error("Can't get the CMaterials item  [" + material.name + "] [" + shape.name + "]");
        return null;
    }

    public static ItemStack get(CMaterial material, CShape shape) {
        return CMaterials.get(material, shape, 1);
    }

    public static boolean exist(CMaterial material, CShape shape) {
        if (materialShapeMap.containsKey(material) && materialShapeMap.get(material).containsKey(shape)) {
            return true;
        }
        if (materialMap.containsKey(material) && materialMap.get(material).containsKey(shape.name)) {
            return true;
        }
        return shapeMap.containsKey(shape) && shapeMap.get(shape).containsKey(material.name);
    }

    public static CMaterial getMaterialFromId(int id) {
        return materialList.get(id);
    }

    public static OreDictionaryStack getOD(CMaterial material, CShape shape, int stackSize) {
        return new OreDictionaryStack(CMaterials.getODName(material, shape), stackSize);
    }

    public static OreDictionaryStack getOD(CMaterial material, CShape shape) {
        return CMaterials.getOD(material, shape, 1);
    }

    public static String getODName(CMaterial material, CShape shape) {
        return shape.oreDictionaryName + material.oreDictionaryName;
    }

    public static ItemStack getODExist(String oreName, int stackSize) {
        ArrayList oreList = OreDictionary.getOres((String)oreName);
        if (oreList != null && oreList.size() > 0) {
            ItemStack res = ((ItemStack)oreList.get(0)).func_77946_l();
            res.field_77994_a = stackSize;
            return res;
        }
        return null;
    }

    public static ItemStack getODExist(String oreName) {
        return CMaterials.getODExist(oreName, 1);
    }

    public static ItemStack getODExist(CMaterial material, CShape shape, int stackSize) {
        return CMaterials.getODExist(CMaterials.getODName(material, shape), stackSize);
    }

    public static ItemStack getODExist(CMaterial material, CShape shape) {
        return CMaterials.getODExist(material, shape, 1);
    }

    public static boolean existOD(String oreName) {
        return CMaterials.getODExist(oreName) != null;
    }

    public static boolean existOD(CMaterial material, CShape shape) {
        return CMaterials.getODExist(material, shape) != null;
    }

    public static ItemDamaged createItem(String unlocalizedName, CreativeTabs creativeTab, String itemId) {
        ItemDamaged itemDamaged = new ItemDamaged();
        itemDamaged.func_77637_a(creativeTab);
        itemDamaged.func_77655_b(unlocalizedName);
        itemDamaged.func_77625_d(64);
        GameRegistry.registerItem((Item)itemDamaged, (String)itemId);
        return itemDamaged;
    }

    public static ItemDamaged createItem(String unlocalizedName, CreativeTabs creativeTab) {
        return CMaterials.createItem(unlocalizedName, creativeTab, unlocalizedName);
    }

    public static ItemDamaged createItem(String unlocalizedName) {
        return CMaterials.createItem(unlocalizedName, ClayiumCore.creativeTabClayium);
    }
}


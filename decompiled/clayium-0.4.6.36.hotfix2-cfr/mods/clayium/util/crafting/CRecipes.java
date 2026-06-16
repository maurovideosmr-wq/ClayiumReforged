/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockColored
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemDye
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.item.crafting.ShapelessRecipes
 *  net.minecraftforge.oredict.OreDictionary
 *  net.minecraftforge.oredict.ShapedOreRecipe
 *  net.minecraftforge.oredict.ShapelessOreRecipe
 */
package mods.clayium.util.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mods.clayium.block.CBlocks;
import mods.clayium.block.ClayRFGenerator;
import mods.clayium.block.MetalChest;
import mods.clayium.block.StorageContainer;
import mods.clayium.block.tile.TileChemicalMetalSeparator;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.CItems;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import mods.clayium.item.CShape;
import mods.clayium.item.ItemCapsule;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.crafting.OreDictionaryStack;
import mods.clayium.util.crafting.Recipes;
import mods.clayium.util.crafting.ShapelessRecipesWithNBT;
import mods.clayium.util.crafting.SimpleMachinesRecipes;
import mods.clayium.util.crafting.SmeltingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CRecipes {
    public static SimpleMachinesRecipes recipeBendingMachine;
    public static SimpleMachinesRecipes recipeWireDrawingMachine;
    public static SimpleMachinesRecipes recipePipeDrawingMachine;
    public static SimpleMachinesRecipes recipeCuttingMachine;
    public static SimpleMachinesRecipes recipeLathe;
    public static SimpleMachinesRecipes recipeMillingMachine;
    public static SimpleMachinesRecipes recipeCondenser;
    public static SimpleMachinesRecipes recipeGrinder;
    public static SimpleMachinesRecipes recipeDecomposer;
    public static Recipes recipeAssembler;
    public static Recipes recipeInscriber;
    public static Recipes recipeCentrifuge;
    public static SimpleMachinesRecipes recipeEnergeticClayCondenser;
    public static Recipes recipeChemicalReactor;
    public static Recipes recipeAlloySmelter;
    public static Recipes recipeBlastFurnace;
    public static SimpleMachinesRecipes recipeElectrolysisReactor;
    public static Recipes recipeReactor;
    public static SimpleMachinesRecipes recipeTransformer;
    public static SimpleMachinesRecipes recipeCACondenser;
    public static Recipes recipeCAInjector;
    public static SimpleMachinesRecipes recipeCAReactor;
    public static SimpleMachinesRecipes recipeSmelter;
    public static SimpleMachinesRecipes recipeEnergeticClayDecomposer;
    public static Recipes recipeFluidTransferMachine;
    private static CMaterial[] cmats;
    private static CMaterial[] mats;
    private static ItemStack[] circuits;
    private static ItemStack[] machines;

    public static ItemStack i(Block block) {
        return new ItemStack(block);
    }

    public static ItemStack i(Block block, int stacksize) {
        return new ItemStack(block, stacksize);
    }

    public static ItemStack i(Block block, int stacksize, int meta) {
        return new ItemStack(block, stacksize, meta);
    }

    public static ItemStack i(Item item) {
        return new ItemStack(item);
    }

    public static ItemStack i(Item item, int stacksize) {
        return new ItemStack(item, stacksize);
    }

    public static ItemStack i(Item item, int stacksize, int meta) {
        return new ItemStack(item, stacksize, meta);
    }

    public static ItemStack s(ItemStack itemstack, int stacksize) {
        ItemStack res = itemstack.func_77946_l();
        res.field_77994_a = stacksize;
        return res;
    }

    public static Object[] oo(Object ... a) {
        return a;
    }

    public static ItemStack[] ii(ItemStack ... a) {
        return a;
    }

    public static List<ItemStack> li(ItemStack ... a) {
        return new ArrayList<ItemStack>(Arrays.asList(CRecipes.ii(a)));
    }

    public static long e(int tier) {
        return CRecipes.e(1.0, tier);
    }

    public static long e(double factor, int tier) {
        return (long)(factor * 100.0 * Math.pow(10.0, tier - 4));
    }

    public static void registerODChainRecipes(SimpleMachinesRecipes recipe, CMaterial[] materials, CShape shape, int[] stackSizes, int[] tiers, int time) {
        int matPos = 0;
        long energy = 0L;
        for (int i = 1; i < materials.length; ++i) {
            int tmp;
            energy += CRecipes.e(tiers[i]);
            if (!CMaterials.existOD(materials[i], shape)) continue;
            int x = stackSizes[matPos];
            int y = stackSizes[i];
            if (x < y) {
                tmp = y;
                y = x;
                x = tmp;
            }
            while ((tmp = x % y) != 0) {
                x = y;
                y = tmp;
            }
            recipe.addRecipe(CMaterials.getOD(materials[matPos], shape, stackSizes[matPos] / y), tiers[i], CMaterials.getODExist(materials[i], shape, stackSizes[i] / y), energy, time);
            matPos = i;
            energy = 0L;
        }
    }

    public static void registerODChainRecipes(SimpleMachinesRecipes recipe, CMaterial[] materials, CShape shape, int[] tiers, int time) {
        int[] stackSizes = new int[materials.length];
        for (int i = 0; i < stackSizes.length; ++i) {
            stackSizes[i] = 1;
        }
        CRecipes.registerODChainRecipes(recipe, materials, shape, stackSizes, tiers, time);
    }

    public static void registerStackChainRecipes(SimpleMachinesRecipes recipe, ItemStack[] itemstacks, int[] stackSizes, int[] tiers, int time) {
        int matPos = 0;
        long energy = 0L;
        for (int i = 1; i < itemstacks.length; ++i) {
            int tmp;
            energy += CRecipes.e(tiers[i]);
            if (itemstacks[i] == null) continue;
            int x = stackSizes[matPos];
            int y = stackSizes[i];
            if (x < y) {
                tmp = y;
                y = x;
                x = tmp;
            }
            while ((tmp = x % y) != 0) {
                x = y;
                y = tmp;
            }
            recipe.addRecipe(CRecipes.s(itemstacks[matPos], stackSizes[matPos] / y), tiers[i], CRecipes.s(itemstacks[i], stackSizes[i] / y), energy, time);
            matPos = i;
            energy = 0L;
        }
    }

    public static void registerStackChainRecipes(SimpleMachinesRecipes recipe, ItemStack[] itemstacks, int[] tiers, int time) {
        int[] stackSizes = new int[itemstacks.length];
        for (int i = 0; i < stackSizes.length; ++i) {
            stackSizes[i] = 1;
        }
        CRecipes.registerStackChainRecipes(recipe, itemstacks, stackSizes, tiers, time);
    }

    public static void register1to1Recipe(Recipes recipe, Object material, int tier, ItemStack result, long time) {
        if (result != null) {
            recipe.addRecipe(CRecipes.oo(material), 0, tier, CRecipes.ii(result), CRecipes.e(tier), time, true);
        }
    }

    public static void register2to1Recipe(Recipes recipe, Object material1, Object material2, int tier, ItemStack result, long time) {
        if (result != null) {
            recipe.addRecipe(CRecipes.oo(material1, material2), 0, tier, CRecipes.ii(result), CRecipes.e(tier), time, true);
        }
    }

    public static void initRecipes() {
        recipeBendingMachine = new SimpleMachinesRecipes("BendingMachine");
        recipeWireDrawingMachine = new SimpleMachinesRecipes("WireDrawingMachine");
        recipePipeDrawingMachine = new SimpleMachinesRecipes("PipeDrawingMachine");
        recipeCuttingMachine = new SimpleMachinesRecipes("CuttingMachine");
        recipeLathe = new SimpleMachinesRecipes("Lathe");
        recipeMillingMachine = new SimpleMachinesRecipes("MillingMachine");
        recipeCondenser = new SimpleMachinesRecipes("Condenser");
        recipeGrinder = new SimpleMachinesRecipes("Grinder");
        recipeDecomposer = new SimpleMachinesRecipes("Decomposer");
        recipeEnergeticClayCondenser = new SimpleMachinesRecipes("ECCondenser");
        recipeAssembler = new Recipes("Assembler");
        recipeInscriber = new Recipes("Inscriber");
        recipeCentrifuge = new Recipes("Centrifuge");
        recipeSmelter = new SmeltingRecipe("Smelter");
        recipeChemicalReactor = new Recipes("ChemicalReactor");
        recipeAlloySmelter = new Recipes("AlloySmelter");
        recipeBlastFurnace = new Recipes("BlastFurnace");
        recipeElectrolysisReactor = new SimpleMachinesRecipes("ElectrolysisReactor");
        recipeReactor = new Recipes("Reactor");
        recipeTransformer = new SimpleMachinesRecipes("MatterTransformer");
        recipeCACondenser = new SimpleMachinesRecipes("CACondenser");
        recipeCAInjector = new SimpleMachinesRecipes("CAInjector");
        recipeCAReactor = new SimpleMachinesRecipes("CAReactor");
        recipeEnergeticClayDecomposer = new SimpleMachinesRecipes("ECDecomposer");
        recipeFluidTransferMachine = new Recipes("FluidTransferMachine");
    }

    public static void registerRecipes() {
        if (!ClayiumCore.cfgUtilityMode) {
            cmats = new CMaterial[]{CMaterials.CLAY, CMaterials.CLAY, CMaterials.DENSE_CLAY, CMaterials.DENSE_CLAY, CMaterials.DENSE_CLAY};
            mats = new CMaterial[]{CMaterials.CLAY, CMaterials.CLAY, CMaterials.DENSE_CLAY, CMaterials.IND_CLAY, CMaterials.ADVIND_CLAY, CMaterials.IMPURE_SILICON, CMaterials.MAIN_ALUMINIUM, CMaterials.CLAY_STEEL, CMaterials.CLAYIUM, CMaterials.ULTIMATE_ALLOY, CMaterials.ANTIMATTER, CMaterials.PURE_ANTIMATTER, CMaterials.OCTUPLE_CLAY, CMaterials.OCTUPLE_PURE_ANTIMATTER};
            circuits = new ItemStack[]{CItems.itemMisc.get("ClayCircuit"), CItems.itemMisc.get("ClayCircuit"), CItems.itemMisc.get("ClayCircuit"), CItems.itemMisc.get("SimpleCircuit"), CItems.itemMisc.get("BasicCircuit"), CItems.itemMisc.get("AdvancedCircuit"), CItems.itemMisc.get("PrecisionCircuit"), CItems.itemMisc.get("IntegratedCircuit"), CItems.itemMisc.get("ClayCore"), CItems.itemMisc.get("ClayBrain"), CItems.itemMisc.get("ClaySpirit"), CItems.itemMisc.get("ClaySoul"), CItems.itemMisc.get("ClayAnima"), CItems.itemMisc.get("ClayPsyche")};
            machines = new ItemStack[16];
            CRecipes.machines[0] = new ItemStack(CBlocks.blockRawClayMachineHull, 1, 0);
            for (int i = 1; i < 16; ++i) {
                CRecipes.machines[i] = new ItemStack(CBlocks.blockMachineHull, 1, i - 1);
            }
            CRecipes.registerMainMaterialRecipes();
            CRecipes.registerHullRecipes();
            CRecipes.registerMachineRecipes();
            CRecipes.registerMaterialRecipes();
            CRecipes.registerToolRecipes();
        } else {
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockCompressedClay, 1, 0), (Object[])new Object[]{"###", "###", "###", Character.valueOf('#'), Blocks.field_150435_aG});
            GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(Blocks.field_150435_aG, 9), (Object[])new Object[]{CRecipes.i(CBlocks.blockCompressedClay, 1, 0)});
            for (int i = 0; i <= 11; ++i) {
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockCompressedClay, 1, i + 1), (Object[])new Object[]{"###", "###", "###", Character.valueOf('#'), CRecipes.i(CBlocks.blockCompressedClay, 1, i)});
                GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CBlocks.blockCompressedClay, 9, i), (Object[])new Object[]{CRecipes.i(CBlocks.blockCompressedClay, 1, i + 1)});
            }
            GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY_STEEL, CMaterials.INGOT), (Object[])new Object[]{CRecipes.i(CBlocks.blockCompressedClay, 1, 0), CRecipes.i(CBlocks.blockCompressedClay, 1, 0), CRecipes.i(Items.field_151042_j)});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockClayChunkLoader), (Object[])new Object[]{"###", "# #", "###", Character.valueOf('#'), CMaterials.get(CMaterials.CLAY_STEEL, CMaterials.INGOT)});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CItems.itemClaySteelPickaxe), (Object[])new Object[]{"###", " | ", " | ", Character.valueOf('#'), CMaterials.get(CMaterials.CLAY_STEEL, CMaterials.INGOT), Character.valueOf('|'), Items.field_151055_y});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CItems.itemClaySteelShovel), (Object[])new Object[]{"#", "|", "|", Character.valueOf('#'), CMaterials.get(CMaterials.CLAY_STEEL, CMaterials.INGOT), Character.valueOf('|'), Items.field_151055_y});
        }
    }

    public static void registerToolRecipes() {
        GameRegistry.addRecipe((ItemStack)CItems.itemRawClayCraftingTools.get("RollingPin"), (Object[])new Object[]{"-=-", Character.valueOf('-'), CMaterials.get(CMaterials.CLAY, CMaterials.SHORT_STICK), Character.valueOf('='), CMaterials.get(CMaterials.CLAY, CMaterials.CYLINDER)});
        GameRegistry.addRecipe((ItemStack)CItems.itemRawClayCraftingTools.get("Spatula"), (Object[])new Object[]{"-=", Character.valueOf('-'), CMaterials.get(CMaterials.CLAY, CMaterials.SHORT_STICK), Character.valueOf('='), CMaterials.get(CMaterials.CLAY, CMaterials.BLADE)});
        GameRegistry.addSmelting((ItemStack)CItems.itemRawClayCraftingTools.get("RollingPin"), (ItemStack)CRecipes.i(CItems.itemClayRollingPin), (float)0.1f);
        GameRegistry.addSmelting((ItemStack)CItems.itemRawClayCraftingTools.get("Slicer"), (ItemStack)CRecipes.i(CItems.itemClaySlicer), (float)0.1f);
        GameRegistry.addSmelting((ItemStack)CItems.itemRawClayCraftingTools.get("Spatula"), (ItemStack)CRecipes.i(CItems.itemClaySpatula), (float)0.1f);
        recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(CItems.itemClayRollingPin, 1, Short.MAX_VALUE), CRecipes.i(CItems.itemClaySlicer, 1, Short.MAX_VALUE)), 0, 6, CRecipes.ii(CItems.itemClayPipingTools.get("IO")), CRecipes.e(6), 20L);
        recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(CItems.itemClaySpatula, 1, Short.MAX_VALUE), CRecipes.i(CItems.itemClayWrench)), 0, 6, CRecipes.ii(CItems.itemClayPipingTools.get("Piping")), CRecipes.e(6), 20L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemClayPipingTools.get("IO"), CRecipes.s(circuits[6], 2)), 0, 6, CRecipes.ii(CItems.itemClayPipingTools.get("Memory")), CRecipes.e(6), 20L);
        recipeAssembler.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SPINDLE), CRecipes.s(circuits[6], 2)), 0, 6, CRecipes.ii(CRecipes.i(CItems.itemDirectionMemory)), CRecipes.e(6), 20L);
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CItems.itemClayShovel), (Object[])new Object[]{"#", "|", "|", Character.valueOf('#'), CMaterials.get(CMaterials.CLAY, CMaterials.PLATE), Character.valueOf('|'), CMaterials.get(CMaterials.CLAY, CMaterials.STICK)});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CItems.itemClayPickaxe), (Object[])new Object[]{"###", " | ", " | ", Character.valueOf('#'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE), Character.valueOf('|'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK)});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CItems.itemClayWrench), (Object[])new Object[]{"# #", " o ", " | ", Character.valueOf('#'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLADE), Character.valueOf('|'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK), Character.valueOf('o'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SPINDLE)});
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(CRecipes.i(CItems.itemClaySteelPickaxe), new Object[]{"###", " | ", " | ", Character.valueOf('#'), CMaterials.getODName(CMaterials.CLAY_STEEL, CMaterials.INGOT), Character.valueOf('|'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK)}));
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(CRecipes.i(CItems.itemClaySteelShovel), new Object[]{"#", "|", "|", Character.valueOf('#'), CMaterials.getODName(CMaterials.CLAY_STEEL, CMaterials.INGOT), Character.valueOf('|'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK)}));
        recipeAssembler.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.AZ91D_ALLOY, CMaterials.PLATE, 3), CItems.itemMisc.get("SynchronousParts", 2)), 0, 6, CRecipes.ii(CRecipes.i(CItems.itemSynchronizer)), CRecipes.e(6), 20L);
        recipeAssembler.addRecipe(CRecipes.oo(circuits[5], CMaterials.get(CMaterials.IND_CLAY, CMaterials.PLATE, 3)), 0, 4, CRecipes.ii(CRecipes.i(CItems.itemFilterWhitelist)), 8L, 20L);
        recipeAssembler.addRecipe(CRecipes.oo(circuits[6], CMaterials.get(CMaterials.IND_CLAY, CMaterials.PLATE, 3)), 0, 4, CRecipes.ii(CRecipes.i(CItems.itemFilterItemName)), 8L, 20L);
        recipeAssembler.addRecipe(CRecipes.oo(circuits[7], CMaterials.get(CMaterials.IND_CLAY, CMaterials.PLATE, 3)), 0, 4, CRecipes.ii(CRecipes.i(CItems.itemFilterFuzzy)), 8L, 20L);
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterWhitelist), (Object[])new Object[]{CRecipes.i(CItems.itemFilterBlacklist)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterBlacklist), (Object[])new Object[]{CRecipes.i(CItems.itemFilterWhitelist)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterUnlocalizedName), (Object[])new Object[]{CRecipes.i(CItems.itemFilterItemName)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterOreDict), (Object[])new Object[]{CRecipes.i(CItems.itemFilterUnlocalizedName)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterUniqueId), (Object[])new Object[]{CRecipes.i(CItems.itemFilterOreDict)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterModId), (Object[])new Object[]{CRecipes.i(CItems.itemFilterUniqueId)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterItemDamage), (Object[])new Object[]{CRecipes.i(CItems.itemFilterModId)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterItemName), (Object[])new Object[]{CRecipes.i(CItems.itemFilterItemDamage)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterFuzzy), (Object[])new Object[]{CRecipes.i(CItems.itemFilterFuzzy)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterDuplicator), (Object[])new Object[]{CRecipes.i(CItems.itemFilterWhitelist), CRecipes.i(CItems.itemFilterItemName), CRecipes.i(CItems.itemFilterFuzzy)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterBlockMetadata), (Object[])new Object[]{CRecipes.i(CItems.itemFilterItemDamage), CRecipes.i(Blocks.field_150435_aG)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CItems.itemFilterItemDamage), (Object[])new Object[]{CRecipes.i(CItems.itemFilterBlockMetadata), CRecipes.i(Blocks.field_150435_aG)});
        if (ClayiumCore.cfgEnableFluidCapsule) {
            ItemCapsule.registerCompressionRecipe(new ItemCapsule[]{CItems.itemsCapsule[4], CItems.itemsCapsule[3], CItems.itemsCapsule[2], CItems.itemsCapsule[1], CItems.itemsCapsule[0]}, new int[]{5, 5, 5, 8});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CItems.itemsCapsule[0]), (Object[])new Object[]{" # ", "# #", " # ", Character.valueOf('#'), CRecipes.i(CBlocks.blockCompressedClay, 1, 0)});
        }
        recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(Items.field_151116_aA, 4), CMaterials.get(CMaterials.AZ91D_ALLOY, CMaterials.PLATE, 8)), 0, 4, CRecipes.ii(CRecipes.i(CItems.itemGadgetHolder)), CRecipes.e(6), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.IND_CLAY, CMaterials.PLATE, 8), CMaterials.get(CMaterials.AZ91D_ALLOY, CMaterials.PLATE, 4)), 0, 4, CRecipes.ii(CItems.itemGadget.get("Blank")), CRecipes.e(6), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Blank"), CBlocks.blockOverclocker.get("antimatter")), 0, 10, CRecipes.ii(CItems.itemGadget.get("AntimatterOverclock")), CRecipes.e(10), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("AntimatterOverclock"), CBlocks.blockOverclocker.get("pureantimatter")), 0, 10, CRecipes.ii(CItems.itemGadget.get("PureAntimatterOverclock")), CRecipes.e(11), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("PureAntimatterOverclock"), CBlocks.blockOverclocker.get("oec")), 0, 10, CRecipes.ii(CItems.itemGadget.get("OECOverclock")), CRecipes.e(12), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("OECOverclock"), CBlocks.blockOverclocker.get("opa")), 0, 10, CRecipes.ii(CItems.itemGadget.get("OPAOverclock")), CRecipes.e(13), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Blank"), CRecipes.s(circuits[12], 16)), 0, 10, CRecipes.ii(CItems.itemGadget.get("Flight0")), CRecipes.e(12), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Flight0"), CRecipes.s(circuits[13], 16)), 0, 10, CRecipes.ii(CItems.itemGadget.get("Flight1")), CRecipes.e(13), 1200L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Flight1"), CBlocks.blockOverclocker.get("opa", 16)), 0, 10, CRecipes.ii(CItems.itemGadget.get("Flight2")), CRecipes.e(14), 12000L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Blank"), CRecipes.s(circuits[6], 4)), 0, 4, CRecipes.ii(CItems.itemGadget.get("Health0")), CRecipes.e(6), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Health0"), CRecipes.s(circuits[10], 4)), 0, 4, CRecipes.ii(CItems.itemGadget.get("Health1")), CRecipes.e(10), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Health1"), CRecipes.s(circuits[12], 4)), 0, 10, CRecipes.ii(CItems.itemGadget.get("Health2")), CRecipes.e(12), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Blank"), CRecipes.s(circuits[7], 2)), 0, 4, CRecipes.ii(CItems.itemGadget.get("AutoEat0")), CRecipes.e(7), 120L);
        GameRegistry.addRecipe((IRecipe)new ShapelessRecipesWithNBT(CItems.itemGadget.get("AutoEat0"), CItems.itemGadget.get("AutoEat1")));
        GameRegistry.addRecipe((IRecipe)new ShapelessRecipesWithNBT(CItems.itemGadget.get("AutoEat1"), CItems.itemGadget.get("AutoEat0")));
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("AntimatterOverclock"), CRecipes.s(circuits[10], 4)), 0, 10, CRecipes.ii(CItems.itemGadget.get("RepeatedlyAttack")), CRecipes.e(10), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.AZ91D_ALLOY, CMaterials.INGOT, 16), circuits[6]), 0, 4, CRecipes.ii(CItems.itemMisc.get("Manipulator1")), CRecipes.e(4), 20L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemMisc.get("Manipulator1", 16), CRecipes.s(circuits[8], 6)), 0, 4, CRecipes.ii(CItems.itemMisc.get("Manipulator2")), CRecipes.e(8), 20L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemMisc.get("Manipulator2", 64), CRecipes.s(circuits[12], 6)), 0, 10, CRecipes.ii(CItems.itemMisc.get("Manipulator3")), CRecipes.e(12), 20L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("Blank"), CItems.itemMisc.get("Manipulator1")), 0, 4, CRecipes.ii(CItems.itemGadget.get("LongArm0")), CRecipes.e(4), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("LongArm0"), CItems.itemMisc.get("Manipulator2")), 0, 4, CRecipes.ii(CItems.itemGadget.get("LongArm1")), CRecipes.e(8), 120L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemGadget.get("LongArm1"), CItems.itemMisc.get("Manipulator3")), 0, 10, CRecipes.ii(CItems.itemGadget.get("LongArm2")), CRecipes.e(12), 120L);
    }

    public static void registerMainMaterialRecipes() {
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockCompressedClay, 1, 0), (Object[])new Object[]{"###", "###", "###", Character.valueOf('#'), Blocks.field_150435_aG});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(Blocks.field_150435_aG, 9), (Object[])new Object[]{CRecipes.i(CBlocks.blockCompressedClay, 1, 0)});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockCompressedClay, 1, 1), (Object[])new Object[]{"###", "###", "###", Character.valueOf('#'), CRecipes.i(CBlocks.blockCompressedClay, 1, 0)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CBlocks.blockCompressedClay, 9, 0), (Object[])new Object[]{CRecipes.i(CBlocks.blockCompressedClay, 1, 1)});
        recipeCondenser.addRecipe(CRecipes.i(Blocks.field_150435_aG, 9), CRecipes.i(CBlocks.blockCompressedClay, 1, 0), 1L, 4L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 0), CRecipes.i(CBlocks.blockCompressedClay, 1, 1), 1L, 4L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 1), CRecipes.i(CBlocks.blockCompressedClay, 1, 2), 10L, 4L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 2), CRecipes.i(CBlocks.blockCompressedClay, 1, 3), 100L, 4L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 3), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 4), 100L, 16L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 4), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 5), 1000L, 16L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 5), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 6), 10000L, 13L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 6), 5, CRecipes.i(CBlocks.blockCompressedClay, 1, 7), 100000L, 10L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 7), 5, CRecipes.i(CBlocks.blockCompressedClay, 1, 8), 1000000L, 8L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 8), 5, CRecipes.i(CBlocks.blockCompressedClay, 1, 9), 10000000L, 6L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 9), 5, CRecipes.i(CBlocks.blockCompressedClay, 1, 10), 100000000L, 4L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 10), 5, CRecipes.i(CBlocks.blockCompressedClay, 1, 11), 1000000000L, 3L);
        recipeCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 11), 5, CRecipes.i(CBlocks.blockCompressedClay, 1, 12), 1000000000L, 25L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 3), 3, CRecipes.i(CBlocks.blockCompressedClay, 1, 4), 1L, 16L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 4), 3, CRecipes.i(CBlocks.blockCompressedClay, 1, 5), 10L, 32L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 5), 3, CRecipes.i(CBlocks.blockCompressedClay, 1, 6), 100L, 64L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 6), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 7), 1000L, 64L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 7), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 8), 10000L, 64L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 8), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 9), 100000L, 64L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 9), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 10), 1000000L, 64L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 10), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 11), 10000000L, 64L);
        recipeEnergeticClayCondenser.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 9, 11), 4, CRecipes.i(CBlocks.blockCompressedClay, 1, 12), 10000000L, 64L);
        recipeDecomposer.addRecipe(CRecipes.i(Blocks.field_150435_aG), CMaterials.get(CMaterials.CLAY, CMaterials.BALL, 4), 1L, 3L);
        recipeDecomposer.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 1, 0), CRecipes.i(Blocks.field_150435_aG, 9), 1L, 3L);
        recipeDecomposer.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 1, 1), CRecipes.i(CBlocks.blockCompressedClay, 9, 0), 1L, 3L);
        recipeDecomposer.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 1, 2), CRecipes.i(CBlocks.blockCompressedClay, 9, 1), 1L, 10L);
        recipeDecomposer.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 1, 3), CRecipes.i(CBlocks.blockCompressedClay, 9, 2), 1L, 20L);
        recipeEnergeticClayDecomposer.addRecipe(CRecipes.i(Blocks.field_150435_aG), 13, CMaterials.get(CMaterials.CLAY, CMaterials.BALL, 4), 1L, 0L);
        recipeEnergeticClayDecomposer.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 1, 0), 13, CRecipes.i(Blocks.field_150435_aG, 9), 1L, 0L);
        for (int i = 0; i < 12; ++i) {
            recipeEnergeticClayDecomposer.addRecipe(CRecipes.i(CBlocks.blockCompressedClay, 1, i + 1), 13, CRecipes.i(CBlocks.blockCompressedClay, 9, i), 1L, 0L);
        }
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_BALL), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.BALL)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.SHORT_STICK, 2), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.STICK)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.SMALL_RING), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.SHORT_STICK)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.SHORT_STICK), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.SMALL_RING)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.RING), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.CYLINDER)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.PIPE), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.PLATE)});
        GameRegistry.addRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_PLATE), (Object[])new Object[]{"###", "###", "###", Character.valueOf('#'), CMaterials.get(CMaterials.CLAY, CMaterials.PLATE)});
        for (CMaterial material : new CMaterial[]{CMaterials.CLAY, CMaterials.DENSE_CLAY}) {
            GameRegistry.addRecipe((ItemStack)CMaterials.get(material, CMaterials.GEAR), (Object[])new Object[]{"iii", "ioi", "iii", Character.valueOf('i'), CMaterials.get(material, CMaterials.SHORT_STICK), Character.valueOf('o'), CMaterials.get(material, CMaterials.SMALL_RING)});
            GameRegistry.addRecipe((ItemStack)CMaterials.get(material, CMaterials.CUTTING_HEAD), (Object[])new Object[]{"iii", "ioi", "iii", Character.valueOf('i'), CMaterials.get(material, CMaterials.BLADE), Character.valueOf('o'), CMaterials.get(material, CMaterials.RING)});
            GameRegistry.addRecipe((ItemStack)CMaterials.get(material, CMaterials.BEARING), (Object[])new Object[]{"iii", "ioi", "iii", Character.valueOf('i'), CMaterials.get(material, CMaterials.BALL), Character.valueOf('o'), CMaterials.get(material, CMaterials.RING)});
            GameRegistry.addRecipe((ItemStack)CMaterials.get(material, CMaterials.SPINDLE), (Object[])new Object[]{"0#0", "ioO", "0#0", Character.valueOf('i'), CMaterials.get(material, CMaterials.STICK), Character.valueOf('o'), CMaterials.get(material, CMaterials.BEARING), Character.valueOf('O'), CMaterials.get(material, CMaterials.RING), Character.valueOf('#'), CMaterials.get(material, CMaterials.PLATE), Character.valueOf('0'), CMaterials.get(material, CMaterials.SMALL_RING)});
            GameRegistry.addRecipe((ItemStack)CMaterials.get(material, CMaterials.GRINDING_HEAD), (Object[])new Object[]{"iii", "ioi", "iii", Character.valueOf('i'), CMaterials.get(material, CMaterials.NEEDLE), Character.valueOf('o'), CMaterials.get(material, CMaterials.RING)});
            GameRegistry.addRecipe((ItemStack)CMaterials.get(material, CMaterials.WATER_WHEEL), (Object[])new Object[]{"###", "#o#", "###", Character.valueOf('#'), CMaterials.get(material, CMaterials.PLATE), Character.valueOf('o'), CMaterials.get(material, CMaterials.RING)});
        }
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.SMALL_DISC), ClayiumCore.divideByProgressionRateI(3));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_BALL), CMaterials.get(CMaterials.CLAY, CMaterials.DISC), ClayiumCore.divideByProgressionRateI(2));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.DISC), CItems.itemRawClayCraftingTools.getItemStack("Slicer"), ClayiumCore.divideByProgressionRateI(1));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.BLOCK), CMaterials.get(CMaterials.CLAY, CMaterials.PLATE), ClayiumCore.divideByProgressionRateI(1));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.PLATE, 4), CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_PLATE), ClayiumCore.divideByProgressionRateI(4));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.CLAY, CMaterials.BLADE, 2), ClayiumCore.divideByProgressionRateI(4));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE), ClayiumCore.divideByProgressionRateI(4));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE, 4), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.LARGE_PLATE), ClayiumCore.divideByProgressionRateI(8));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLADE, 2), ClayiumCore.divideByProgressionRateI(8));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.IND_CLAY, CMaterials.BLOCK), 2, CMaterials.get(CMaterials.IND_CLAY, CMaterials.PLATE), 2L, ClayiumCore.divideByProgressionRateI(4));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.IND_CLAY, CMaterials.PLATE, 4), 2, CMaterials.get(CMaterials.IND_CLAY, CMaterials.LARGE_PLATE), 2L, ClayiumCore.divideByProgressionRateI(8));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.BLOCK), 2, CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.PLATE), 4L, ClayiumCore.divideByProgressionRateI(4));
        recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.PLATE, 4), 2, CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.LARGE_PLATE), 4L, ClayiumCore.divideByProgressionRateI(8));
        recipeWireDrawingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.STICK), ClayiumCore.divideByProgressionRateI(1));
        recipeWireDrawingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.CLAY, CMaterials.STICK, 8), ClayiumCore.divideByProgressionRateI(3));
        recipeWireDrawingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.PIPE), CMaterials.get(CMaterials.CLAY, CMaterials.STICK, 4), ClayiumCore.divideByProgressionRateI(2));
        recipeWireDrawingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.SMALL_DISC), CMaterials.get(CMaterials.CLAY, CMaterials.STICK), ClayiumCore.divideByProgressionRateI(1));
        recipeWireDrawingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK, 8), ClayiumCore.divideByProgressionRateI(6));
        recipeWireDrawingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PIPE), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK, 4), ClayiumCore.divideByProgressionRateI(4));
        recipeWireDrawingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SMALL_DISC), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK), ClayiumCore.divideByProgressionRateI(2));
        recipePipeDrawingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.CLAY, CMaterials.PIPE, 2), ClayiumCore.divideByProgressionRateI(3));
        recipePipeDrawingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PIPE, 2), ClayiumCore.divideByProgressionRateI(6));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_BALL), CMaterials.get(CMaterials.CLAY, CMaterials.DISC), ClayiumCore.divideByProgressionRateI(2));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.CLAY, CMaterials.SMALL_DISC, 8), ClayiumCore.divideByProgressionRateI(2));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.CLAY, CMaterials.DISC, 2), ClayiumCore.divideByProgressionRateI(3));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.PLATE), CMaterials.get(CMaterials.CLAY, CMaterials.SMALL_DISC, 4), ClayiumCore.divideByProgressionRateI(3));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.STICK), CMaterials.get(CMaterials.CLAY, CMaterials.SHORT_STICK, 2), ClayiumCore.divideByProgressionRateI(1));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SMALL_DISC, 8), ClayiumCore.divideByProgressionRateI(4));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DISC, 2), ClayiumCore.divideByProgressionRateI(6));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SMALL_DISC, 4), ClayiumCore.divideByProgressionRateI(6));
        recipeCuttingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SHORT_STICK, 2), ClayiumCore.divideByProgressionRateI(2));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.BALL), CMaterials.get(CMaterials.CLAY, CMaterials.SHORT_STICK), ClayiumCore.divideByProgressionRateI(1));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_BALL), CMaterials.get(CMaterials.CLAY, CMaterials.CYLINDER), ClayiumCore.divideByProgressionRateI(4));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.CLAY, CMaterials.NEEDLE), ClayiumCore.divideByProgressionRateI(3));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.NEEDLE), CMaterials.get(CMaterials.CLAY, CMaterials.STICK, 6), ClayiumCore.divideByProgressionRateI(3));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.DISC), CMaterials.get(CMaterials.CLAY, CMaterials.RING), ClayiumCore.divideByProgressionRateI(2));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.SMALL_DISC), CMaterials.get(CMaterials.CLAY, CMaterials.SMALL_RING), ClayiumCore.divideByProgressionRateI(1));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK, 2), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CYLINDER), ClayiumCore.divideByProgressionRateI(4));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CYLINDER), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.NEEDLE), ClayiumCore.divideByProgressionRateI(6));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.NEEDLE), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK, 6), ClayiumCore.divideByProgressionRateI(6));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DISC), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.RING), ClayiumCore.divideByProgressionRateI(4));
        recipeLathe.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SMALL_DISC), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SMALL_RING), ClayiumCore.divideByProgressionRateI(2));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.STICK, 5)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.GEAR)), 10L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.SHORT_STICK, 9)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.GEAR)), 10L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.CLAY, CMaterials.BALL, 8)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.SPINDLE)), 10L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.CLAY, CMaterials.BLOCK, 8)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.GRINDING_HEAD)), 10L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.CLAY, CMaterials.PLATE, 8)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.CUTTING_HEAD)), 10L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK, 5)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.GEAR)), 10L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SHORT_STICK, 9)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.GEAR)), 10L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.CLAY, CMaterials.BALL, 8)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SPINDLE)), 100L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK, 8)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.GRINDING_HEAD)), 100L, ClayiumCore.divideByProgressionRateI(20));
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE, 8)), 0, 3, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CUTTING_HEAD)), 100L, ClayiumCore.divideByProgressionRateI(20));
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.BALL, 3), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.RING)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.BALL, 3), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.GEAR)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.BALL, 3), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.BLADE)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.CLAY, CMaterials.BALL, 5), (Object[])new Object[]{CMaterials.get(CMaterials.CLAY, CMaterials.NEEDLE)});
        for (CMaterial material : new CMaterial[]{CMaterials.CLAY, CMaterials.DENSE_CLAY}) {
            int i = material == CMaterials.CLAY ? 1 : 4;
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.PLATE), CMaterials.get(material, CMaterials.DUST), 1L, 3 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.STICK, 4), CMaterials.get(material, CMaterials.DUST), 1L, 3 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.SHORT_STICK, 8), CMaterials.get(material, CMaterials.DUST), 1L, 3 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.RING, 4), CMaterials.get(material, CMaterials.DUST, 5), 1L, 15 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.SMALL_RING, 8), CMaterials.get(material, CMaterials.DUST), 1L, 3 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.GEAR, 8), CMaterials.get(material, CMaterials.DUST, 9), 1L, 27 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.BLADE), CMaterials.get(material, CMaterials.DUST), 1L, 3 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.NEEDLE), CMaterials.get(material, CMaterials.DUST, 2), 1L, 6 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.DISC, 2), CMaterials.get(material, CMaterials.DUST, 3), 1L, 9 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.SMALL_DISC, 4), CMaterials.get(material, CMaterials.DUST), 1L, 3 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.CYLINDER), CMaterials.get(material, CMaterials.DUST, 2), 1L, 6 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.PIPE), CMaterials.get(material, CMaterials.DUST), 1L, 3 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.LARGE_PLATE), CMaterials.get(material, CMaterials.DUST, 4), 1L, 12 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.GRINDING_HEAD), CMaterials.get(material, CMaterials.DUST, 16), 1L, 48 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.BEARING, 4), CMaterials.get(material, CMaterials.DUST, 5), 1L, 15 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.SPINDLE), CMaterials.get(material, CMaterials.DUST, 4), 1L, 12 * i);
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.CUTTING_HEAD), CMaterials.get(material, CMaterials.DUST, 9), 1L, 27 * i);
        }
        recipeDecomposer.addRecipe(CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST), CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 3), 1L, ClayiumCore.divideByProgressionRateI(60));
        recipeDecomposer.addRecipe(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST), 4, CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 28), 1000L, ClayiumCore.divideByProgressionRateI(60));
        recipeCentrifuge.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.DUST, 9)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DUST)), 4L, ClayiumCore.divideByProgressionRateI(20));
        recipeCentrifuge.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DUST, 2)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.CLAY, CMaterials.DUST, 9), CMaterials.get(CMaterials.CAL_CLAY, CMaterials.DUST, 1)), 4L, ClayiumCore.divideByProgressionRateI(20));
        recipeCentrifuge.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST, 2)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 12), CMaterials.get(CMaterials.CLAY, CMaterials.DUST, 8), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DUST, 8), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST)), 4L, ClayiumCore.divideByProgressionRateI(20));
        recipeCentrifuge.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST, 2)), 0, 4, CRecipes.ii(CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 64), CMaterials.get(CMaterials.CLAY, CMaterials.DUST, 64), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DUST, 64), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST, 12)), 10000L, ClayiumCore.divideByProgressionRateI(12));
        recipeChemicalReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.SALT, CMaterials.DUST, 2), CMaterials.get(CMaterials.CAL_CLAY, CMaterials.DUST)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.CALCIUM_CHLORIDE, CMaterials.DUST), CMaterials.get(CMaterials.SODIUM_CARBONATE, CMaterials.DUST)), CRecipes.e(5), 120L);
        recipeChemicalReactor.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.SODIUM_CARBONATE, CMaterials.DUST), CMaterials.get(CMaterials.CLAY, CMaterials.DUST)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.QUARTZ, CMaterials.DUST)), CRecipes.e(4), 120L);
        recipeChemicalReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.QUARTZ, CMaterials.DUST), CRecipes.i(Items.field_151044_h)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT)), CRecipes.e(4), 120L);
        recipeChemicalReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.QUARTZ, CMaterials.DUST), CRecipes.i(Items.field_151044_h, 1, 1)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT)), CRecipes.e(4), 120L);
        recipeChemicalReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.SALT, CMaterials.DUST), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST)), 0, 8, CRecipes.ii(CMaterials.get(CMaterials.QUARTZ, CMaterials.DUST), CMaterials.get(CMaterials.CALCIUM_CHLORIDE, CMaterials.DUST)), CRecipes.e(10.0, 8), 1L);
        recipeChemicalReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.QUARTZ, CMaterials.DUST), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST)), 0, 8, CRecipes.ii(CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT)), CRecipes.e(10.0, 8), 1L);
        recipeBlastFurnace.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST), CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT)), 0, 7, CRecipes.ii(CMaterials.get(CMaterials.SILICON, CMaterials.INGOT)), CRecipes.e(7), 100L);
        recipeChemicalReactor.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DUST)), 0, 5, CRecipes.ii(CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.DUST), CMaterials.get(CMaterials.MAIN_ALUMINIUM, CMaterials.DUST)), CRecipes.e(5), 30L);
        recipeBlastFurnace.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST, 2), CMaterials.get(CMaterials.IMPURE_MANGANESE, CMaterials.DUST)), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.CLAY_STEEL, CMaterials.INGOT, 2)), CRecipes.e(5.0, 6), 200L);
        recipeBlastFurnace.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST), CMaterials.get(CMaterials.IMPURE_MANGANESE, CMaterials.DUST)), 0, 7, CRecipes.ii(CMaterials.get(CMaterials.CLAY_STEEL, CMaterials.INGOT)), CRecipes.e(5.0, 7), 5L);
        recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST), CMaterials.getOD(CMaterials.MANGANESE, CMaterials.DUST)), 0, 8, CRecipes.ii(CMaterials.get(CMaterials.CLAY_STEEL, CMaterials.INGOT)), CRecipes.e(5.0, 8), 1L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST, 8), CMaterials.getOD(CMaterials.LITHIUM, CMaterials.DUST, 4)), 0, 7, CRecipes.ii(CMaterials.get(CMaterials.CLAYIUM, CMaterials.DUST, 8)), CRecipes.e(10.0, 7), 50000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST, 8), CMaterials.getOD(CMaterials.HAFNIUM, CMaterials.DUST)), 0, 7, CRecipes.ii(CMaterials.get(CMaterials.CLAYIUM, CMaterials.DUST, 8)), CRecipes.e(10.0, 7), 500000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST, 8), CMaterials.getOD(CMaterials.BARIUM, CMaterials.DUST)), 0, 7, CRecipes.ii(CMaterials.get(CMaterials.CLAYIUM, CMaterials.DUST, 8)), CRecipes.e(3.0, 7), 5000000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST, 8), CMaterials.getOD(CMaterials.STRONTIUM, CMaterials.DUST)), 0, 7, CRecipes.ii(CMaterials.get(CMaterials.CLAYIUM, CMaterials.DUST, 8)), CRecipes.e(7), 50000000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST), CMaterials.get(CMaterials.IMPURE_ULTIMATE_ALLOY, CMaterials.INGOT)), 0, 8, CRecipes.ii(CMaterials.get(CMaterials.ULTIMATE_ALLOY, CMaterials.INGOT)), CRecipes.e(10.0, 8), 1000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 8), CMaterials.getOD(CMaterials.LITHIUM, CMaterials.DUST)), 0, 7, CRecipes.ii(CMaterials.get(CMaterials.EXC_CLAY, CMaterials.DUST, 4)), CRecipes.e(7), 2000000L);
        recipeGrinder.addRecipe(CRecipes.i(CBlocks.blockClayTreeLog), 5, CMaterials.get(CMaterials.ORG_CLAY, CMaterials.DUST), CRecipes.e(5), 200L);
        recipeReactor.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.ORG_CLAY, CMaterials.DUST), CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST)), 0, 10, CRecipes.ii(CMaterials.get(CMaterials.ORG_CLAY, CMaterials.DUST, 2)), CRecipes.e(10), 1000000000000L);
        recipeReactor.addRecipe(CRecipes.ii(CItems.itemMisc.get("ClaySoul"), CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST)), 0, 11, CRecipes.ii(CMaterials.get(CMaterials.ORG_CLAY, CMaterials.DUST, 2)), CRecipes.e(11), 100000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.CLAYIUM, CMaterials.INGOT)), 0, 9, CRecipes.ii(CItems.itemMisc.get("AntimatterSeed")), CRecipes.e(9), ClayiumCore.divideByProgressionRateL(200000000000000L));
        recipeCACondenser.addRecipe(CItems.itemMisc.get("AntimatterSeed"), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), CRecipes.e(2.5, 9), ClayiumCore.divideByProgressionRateL(2000L));
        recipeCAInjector.addRecipe(CRecipes.ii(machines[10], CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 8)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blockCACollector)), CRecipes.e(2.0, 10), 4000L);
        recipeCAReactor.addRecipe(CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.get(CMaterials.PURE_ANTIMATTER, CMaterials.GEM), CRecipes.e(0.1, 10), ClayiumCore.divideByProgressionRateI(300));
        for (int i = 0; i < 8; ++i) {
            recipeCondenser.addRecipe(CMaterials.get(CMaterials.COMPRESSED_PURE_ANTIMATTER[i], CMaterials.GEM, 9), 10, CMaterials.get(CMaterials.COMPRESSED_PURE_ANTIMATTER[i + 1], CMaterials.GEM), CRecipes.e(9), 6L);
            GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.COMPRESSED_PURE_ANTIMATTER[i], CMaterials.GEM, 9), (Object[])new Object[]{CMaterials.get(CMaterials.COMPRESSED_PURE_ANTIMATTER[i + 1], CMaterials.GEM)});
        }
    }

    public static void registerHullRecipes() {
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockRawClayMachineHull, 1, 0), (Object[])new Object[]{"###", "#o#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.CLAY, CMaterials.LARGE_PLATE), Character.valueOf('o'), CMaterials.get(CMaterials.CLAY, CMaterials.GEAR)});
        GameRegistry.addSmelting((ItemStack)CRecipes.i(CBlocks.blockRawClayMachineHull, 1, 0), (ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 0), (float)0.1f);
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 1), (Object[])new Object[]{"###", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("ClayCircuit")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 2), (Object[])new Object[]{"###", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.IND_CLAY, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("SimpleCircuit")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 3), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("BasicCircuit"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 4), (Object[])new Object[]{"#E#", "*C*", "#*#", Character.valueOf('#'), CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.LARGE_PLATE), Character.valueOf('*'), CMaterials.get(CMaterials.SILICONE, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("AdvancedCircuit"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 5), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.MAIN_ALUMINIUM, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("PrecisionCircuit"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 6), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.CLAY_STEEL, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("IntegratedCircuit"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 7), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.CLAYIUM, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("ClayCore"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 8), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.ULTIMATE_ALLOY, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("ClayBrain"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 9), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("ClaySpirit"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 10), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.PURE_ANTIMATTER, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("ClaySoul"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 11), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.OCTUPLE_CLAY, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("ClayAnima"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockMachineHull, 1, 12), (Object[])new Object[]{"#E#", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.OCTUPLE_PURE_ANTIMATTER, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("ClayPsyche"), Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.AZ91D_ALLOY, CMaterials.LARGE_PLATE), circuits[6]), 0, 4, CRecipes.ii(CBlocks.blockOthersHull.get("az91d")), CRecipes.e(6), 120L);
        GameRegistry.addRecipe((ItemStack)CBlocks.blockOthersHull.get("zk60a"), (Object[])new Object[]{"###", "#C#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.ZK60A_ALLOY, CMaterials.LARGE_PLATE), Character.valueOf('C'), CItems.itemMisc.get("PrecisionCircuit")});
        CMaterial[] reactorHullMats = new CMaterial[]{CMaterials.RUBIDIUM, CMaterials.CERIUM, CMaterials.TANTALUM, CMaterials.PRASEODYMIUM, CMaterials.PROTACTINIUM, CMaterials.NEPTUNIUM, CMaterials.PROMETHIUM, CMaterials.SAMARIUM, CMaterials.CURIUM, CMaterials.EUROPIUM};
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(CRecipes.i(CBlocks.blockCAReactorHull, 1, 0), CRecipes.oo("#I#", "#H#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), Character.valueOf('H'), machines[10], Character.valueOf('I'), CMaterials.getODName(reactorHullMats[0], CMaterials.INGOT))));
        for (int i = 0; i <= 8; ++i) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(CRecipes.i(CBlocks.blockCAReactorHull, 1, i + 1), CRecipes.oo("#I#", "#H#", "###", Character.valueOf('#'), CMaterials.get(CMaterials.COMPRESSED_PURE_ANTIMATTER[i], CMaterials.GEM), Character.valueOf('H'), CRecipes.i(CBlocks.blockCAReactorHull, 1, i), Character.valueOf('I'), CMaterials.getODName(reactorHullMats[i + 1], CMaterials.INGOT))));
        }
        GameRegistry.addRecipe((ItemStack)CItems.itemMisc.get("ClayCircuit"), (Object[])new Object[]{"-*-", "o#o", "-*-", Character.valueOf('-'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.STICK), Character.valueOf('*'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.GEAR), Character.valueOf('o'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.RING), Character.valueOf('#'), CItems.itemMisc.get("ClayCircuitBoard")});
        GameRegistry.addRecipe((ItemStack)CItems.itemMisc.get("SimpleCircuit"), (Object[])new Object[]{"---", "-#-", "---", Character.valueOf('-'), CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST), Character.valueOf('#'), CItems.itemMisc.get("ClayCircuitBoard")});
        recipeMillingMachine.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE), CItems.itemMisc.get("ClayCircuitBoard"), 32L);
        recipeMillingMachine.addRecipe(CMaterials.get(CMaterials.IND_CLAY, CMaterials.PLATE), CItems.itemMisc.get("ClayCircuitBoard"), 1L);
        recipeMillingMachine.addRecipe(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.PLATE), 3, CItems.itemMisc.get("CEEBoard"), 2L, 32L);
        recipeAssembler.addRecipe(CRecipes.ii(CItems.itemMisc.get("CEECircuit"), CMaterials.get(CMaterials.IND_CLAY, CMaterials.PLATE, 3)), 0, 0, CRecipes.ii(CItems.itemMisc.get("CEE")), 8L, 20L);
        recipeInscriber.addRecipe(CRecipes.ii(CItems.itemMisc.get("CEEBoard"), CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 32)), 0, 0, CRecipes.ii(CItems.itemMisc.get("CEECircuit")), 2L, 20L);
        recipeInscriber.addRecipe(CRecipes.ii(CItems.itemMisc.get("ClayCircuitBoard"), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DUST, 6)), 0, 0, CRecipes.ii(CItems.itemMisc.get("ClayCircuit")), 2L, 20L);
        recipeInscriber.addRecipe(CRecipes.ii(CItems.itemMisc.get("ClayCircuitBoard"), CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 32)), 0, 0, CRecipes.ii(CItems.itemMisc.get("BasicCircuit")), 2L, 20L);
        recipeInscriber.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.PLATE), CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 32)), 0, 0, CRecipes.ii(CItems.itemMisc.get("AdvancedCircuit")), 100L, 120L);
        recipeInscriber.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.SILICON, CMaterials.PLATE), CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 32)), 0, 0, CRecipes.ii(CItems.itemMisc.get("PrecisionCircuit")), 1000L, 120L);
        recipeAssembler.addRecipe(CRecipes.ii(CItems.itemMisc.get("PrecisionCircuit"), CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 32)), 0, 6, CRecipes.ii(CItems.itemMisc.get("IntegratedCircuit")), 10000L, 1200L);
        recipeAssembler.addRecipe(CRecipes.oo(CItems.itemMisc.get("CEE"), CItems.itemMisc.get("IntegratedCircuit")), 0, 6, CRecipes.ii(CItems.itemMisc.get("LaserParts")), CRecipes.e(6), 20L);
        recipeAssembler.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.BERYLLIUM, CMaterials.DUST, 8), CItems.itemMisc.get("IntegratedCircuit")), 0, 6, CRecipes.ii(CItems.itemMisc.get("SynchronousParts")), CRecipes.e(6), 432000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.PURE_ANTIMATTER, CMaterials.GEM, 8), CItems.itemMisc.get("IntegratedCircuit")), 0, 11, CRecipes.ii(CItems.itemMisc.get("TeleportationParts")), CRecipes.e(11), 10000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CItems.itemMisc.get("IntegratedCircuit", 6), CMaterials.get(CMaterials.EXC_CLAY, CMaterials.DUST)), 0, 7, CRecipes.ii(CItems.itemMisc.get("ClayCore")), CRecipes.e(10.0, 7), 8000000L);
        recipeReactor.addRecipe(CRecipes.oo(CItems.itemMisc.get("ClayCore", 6), CMaterials.get(CMaterials.EXC_CLAY, CMaterials.DUST, 12)), 0, 8, CRecipes.ii(CItems.itemMisc.get("ClayBrain")), CRecipes.e(10.0, 8), 4000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CItems.itemMisc.get("ClayBrain", 6), CMaterials.get(CMaterials.EXC_CLAY, CMaterials.DUST, 32)), 0, 9, CRecipes.ii(CItems.itemMisc.get("ClaySpirit")), CRecipes.e(10.0, 9), 10000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CItems.itemMisc.get("ClaySpirit", 6), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 4)), 0, 10, CRecipes.ii(CItems.itemMisc.get("ClaySoul")), CRecipes.e(10.0, 10), 10000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CItems.itemMisc.get("ClaySoul", 6), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 16)), 0, 11, CRecipes.ii(CItems.itemMisc.get("ClayAnima")), CRecipes.e(30.0, 11), 100000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CItems.itemMisc.get("ClayAnima", 6), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 64)), 0, 12, CRecipes.ii(CItems.itemMisc.get("ClayPsyche")), CRecipes.e(90.0, 12), 1000000000000000L);
    }

    public static void registerMachineRecipes() {
        int i;
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockClayWorkTable), (Object[])new Object[]{"##", "##", Character.valueOf('#'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK)});
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockClayCraftingTable), (Object[])new Object[]{"###", Character.valueOf('#'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK)});
        GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK, 3), (Object[])new Object[]{CRecipes.i(CBlocks.blockClayCraftingTable)});
        for (i = 1; i <= 4; ++i) {
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksBendingMachine[i]), (Object[])new Object[]{"o-*", "P#P", "o-*", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('-'), CMaterials.get(cmats[i], CMaterials.CYLINDER), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('P'), CMaterials.get(cmats[i], CMaterials.PLATE)});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksWireDrawingMachine[i]), (Object[])new Object[]{"*o*", "=#=", "*o*", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('='), CMaterials.get(cmats[i], CMaterials.PIPE)});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksPipeDrawingMachine[i]), (Object[])new Object[]{"*o*", "-#=", "*o*", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('-'), CMaterials.get(cmats[i], CMaterials.CYLINDER), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('='), CMaterials.get(cmats[i], CMaterials.PIPE)});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksCuttingMachine[i]), (Object[])new Object[]{"P*P", "o#|", "P*P", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('P'), CMaterials.get(cmats[i], CMaterials.PLATE), Character.valueOf('|'), CMaterials.get(cmats[i], CMaterials.CUTTING_HEAD)});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksLathe[i]), (Object[])new Object[]{"P*P", "-#o", "P*P", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('P'), CMaterials.get(cmats[i], CMaterials.PLATE), Character.valueOf('-'), CMaterials.get(cmats[i], CMaterials.STICK)});
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksCobblestoneGenerator[i]), (Object[])new Object[]{" * ", "=#=", " * ", Character.valueOf('#'), machines[i], Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('='), CMaterials.get(cmats[i], CMaterials.PIPE)});
            if (i == 1) {
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockElementalMillingMachine), (Object[])new Object[]{"P0P", "o#o", "P*P", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.GEAR), Character.valueOf('P'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE), Character.valueOf('0'), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CUTTING_HEAD)});
                GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CBlocks.blockClayWaterWheel), (Object[])new Object[]{machines[i], CMaterials.get(mats[i - 1], CMaterials.WATER_WHEEL)});
            }
            if (i >= 2) {
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksGrinder[i]), (Object[])new Object[]{"P0P", "o#o", "P*P", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('P'), CMaterials.get(cmats[i], CMaterials.PLATE), Character.valueOf('0'), CMaterials.get(cmats[i], CMaterials.GRINDING_HEAD)});
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksDecomposer[i]), (Object[])new Object[]{"*o*", "C#C", "*=*", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('='), CMaterials.get(cmats[i], CMaterials.PIPE), Character.valueOf('C'), circuits[i]});
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksCondenser[i]), (Object[])new Object[]{"*P*", "P#P", "*P*", Character.valueOf('#'), machines[i], Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('P'), CMaterials.get(cmats[i], CMaterials.PLATE)});
            }
            if (i == 2) {
                GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CBlocks.blockDenseClayWaterWheel), (Object[])new Object[]{machines[i], CMaterials.get(cmats[i], CMaterials.WATER_WHEEL)});
            }
            if (i >= 3) {
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksCentrifuge[i]), (Object[])new Object[]{"*o*", "o#o", "*o*", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR)});
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksInscriber[i]), (Object[])new Object[]{"*o*", "C#C", "*C*", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('C'), circuits[i]});
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksAssembler[i]), (Object[])new Object[]{"*C*", "o#o", "*C*", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('C'), circuits[i]});
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blocksMillingMachine[i]), (Object[])new Object[]{"P0P", "o#o", "P*P", Character.valueOf('#'), machines[i], Character.valueOf('o'), CMaterials.get(cmats[i], CMaterials.SPINDLE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('P'), CMaterials.get(cmats[i], CMaterials.PLATE), Character.valueOf('0'), CMaterials.get(cmats[i], CMaterials.CUTTING_HEAD)});
            }
            if (i == 3) {
                GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockEnergeticClayCondenser), (Object[])new Object[]{"P*P", "E#E", "PCP", Character.valueOf('#'), machines[i], Character.valueOf('P'), CMaterials.get(cmats[i], CMaterials.PLATE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('C'), circuits[i + 1], Character.valueOf('E'), CItems.itemMisc.get("CEE")});
            }
            if (i != 4) continue;
            GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockEnergeticClayCondenserMK2), (Object[])new Object[]{"P*P", "E#E", "PCP", Character.valueOf('#'), machines[i], Character.valueOf('P'), CMaterials.get(cmats[i], CMaterials.PLATE), Character.valueOf('*'), CMaterials.get(cmats[i], CMaterials.GEAR), Character.valueOf('C'), circuits[i + 1], Character.valueOf('E'), CItems.itemMisc.get("CEE")});
        }
        for (i = 1; i <= 13; ++i) {
            if (i == 1) {
                recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CUTTING_HEAD)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockElementalMillingMachine)), CRecipes.e(i), 120L);
            }
            if (i >= 1) {
                if (i <= 3) {
                    recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(mats[i], CMaterials.LARGE_PLATE), circuits[3]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksCobblestoneGenerator[i])), CRecipes.e(i), 40L);
                }
                if (i <= 4) {
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE, 3)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksBendingMachine[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PIPE, 2)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksWireDrawingMachine[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CYLINDER, 2)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksPipeDrawingMachine[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.CLAY, CMaterials.CUTTING_HEAD)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksCuttingMachine[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.CLAY, CMaterials.SPINDLE)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksLathe[i])), CRecipes.e(i), 120L);
                }
            }
            if (i >= 2) {
                if (i <= 3) {
                    recipeAssembler.addRecipe(CRecipes.oo(machines[i], CMaterials.get(mats[i], CMaterials.LARGE_PLATE)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksCondenser[i])), CRecipes.e(i), 120L);
                }
                if (i <= 4) {
                    recipeAssembler.addRecipe(CRecipes.oo(machines[i], CMaterials.get(CMaterials.CLAY, CMaterials.GEAR, 4)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksDecomposer[i])), CRecipes.e(i), 120L);
                }
                if (i <= 6) {
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.GRINDING_HEAD, 1)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksGrinder[i])), CRecipes.e(i), 120L);
                }
            }
            if (i == 3) {
                recipeAssembler.addRecipe(CRecipes.ii(machines[i], CItems.itemMisc.get("CEE", 2)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockEnergeticClayCondenser)), CRecipes.e(i), 120L);
            }
            if (i >= 3) {
                if (i <= 4) {
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.CUTTING_HEAD)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksMillingMachine[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.GEAR, 4)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksAssembler[i])), CRecipes.e(i), 40L);
                    recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksAssembler[i]), circuits[4]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksInscriber[i])), CRecipes.e(i), 40L);
                }
                if (i <= 6) {
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.SPINDLE, Math.max(i - 4, 1))), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksCentrifuge[i])), CRecipes.e(i), 120L);
                }
            }
            if (i == 4) {
                recipeAssembler.addRecipe(CRecipes.ii(machines[i], CItems.itemMisc.get("CEE", 2)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockEnergeticClayCondenserMK2)), CRecipes.e(i), 120L);
            }
            if (i >= 4) {
                recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(mats[i], CMaterials.PLATE), circuits[i]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksBuffer[i], 16)), CRecipes.e(i), 40L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksBuffer[i], 6), CMaterials.get(mats[i], CMaterials.LARGE_PLATE)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksMultitrackBuffer[i])), CRecipes.e(i), 40L);
                if (ClayiumCore.cfgEnableFluidCapsule) {
                    recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksBuffer[i]), CRecipes.i(CItems.itemsCapsule[0], 4)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksFluidTranslator[i])), CRecipes.e(i), 40L);
                }
                if (i <= 7) {
                    recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksBuffer[i]), circuits[3]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksCobblestoneGenerator[i])), CRecipes.e(i), 40L);
                    recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksBuffer[i]), circuits[4]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksSaltExtractor[i])), CRecipes.e(i), 40L);
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CRecipes.s(circuits[3], i - 3)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksSmelter[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.AZ91D_ALLOY, CMaterials.PLATE, 4), circuits[i]), 0, 4, CRecipes.ii(CRecipes.i(CItems.itemsClayShooter[i - 4])), CRecipes.e(i), 40L);
                }
                if (i <= 5) {
                    recipeAssembler.addRecipe(CRecipes.oo(machines[i], CRecipes.i(CBlocks.blocksBuffer[i])), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksCondenser[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CRecipes.s(circuits[4], i - 3)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksChemicalReactor[i])), CRecipes.e(i), 120L);
                }
            }
            if (i >= 5) {
                recipeAssembler.addRecipe(CRecipes.ii(machines[i], CRecipes.i(CBlocks.blocksBuffer[6])), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksClayInterface[i])), CRecipes.e(i), 40L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksClayInterface[i]), CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST, 16)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksRedstoneInterface[i])), CRecipes.e(i), 40L);
                if (i <= 7) {
                    recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.PLATE, (i - 4) * 3)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksBendingMachine[i])), CRecipes.e(i), 120L);
                }
            }
            if (i == 5) {
                recipeAssembler.addRecipe(CRecipes.oo(machines[i], CMaterials.getOD(CMaterials.SILICON, CMaterials.PLATE, 8)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockSolarClayFabricatorMK1)), CRecipes.e(i), 120L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksBuffer[i]), circuits[5]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockAutoClayCondenser)), CRecipes.e(i), 40L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksAssembler[4]), machines[i]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksAutoCrafter[i])), CRecipes.e(i), 40L);
                if (ClayiumCore.cfgEnableFluidCapsule) {
                    recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksFluidTranslator[i], 2), machines[i]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksFluidTransferMachine[i])), CRecipes.e(i), 40L);
                }
            }
            if (i >= 6 && i <= 9) {
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksChemicalReactor[5]), circuits[i]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksElectrolysisReactor[i])), CRecipes.e(i), 40L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksAutoCrafter[i - 1]), machines[i]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksAutoCrafter[i])), CRecipes.e(i), 40L);
            }
            if (i == 6) {
                recipeAssembler.addRecipe(CRecipes.oo(machines[i], CMaterials.getOD(CMaterials.SILICON, CMaterials.PLATE, 16)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockSolarClayFabricatorMK2)), CRecipes.e(i), 120L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksSmelter[i]), CRecipes.i(CBlocks.blocksClayInterface[i])), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockClayBlastFurnace)), CRecipes.e(i), 120L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksChemicalReactor[5]), CRecipes.i(CBlocks.blocksSmelter[i])), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockChemicalMetalSeparator)), CRecipes.e(i), 40L);
                recipeAssembler.addRecipe(CRecipes.ii(machines[i], CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.GEAR, 4)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksAssembler[i])), CRecipes.e(i), 40L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksAssembler[4]), circuits[i]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksAssembler[6])), CRecipes.e(i), 40L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksSmelter[i]), circuits[i]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksAlloySmelter[i])), CRecipes.e(i), 40L);
            }
            if (i == 7) {
                recipeAssembler.addRecipe(CRecipes.oo(machines[i], CMaterials.getOD(CMaterials.SILICON, CMaterials.PLATE, 16)), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockLithiumSolarClayFabricator)), CRecipes.e(i), 120L);
                recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blocksBuffer[i]), circuits[5]), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockAutoClayCondenserMK2)), CRecipes.e(i), 40L);
                recipeAssembler.addRecipe(CRecipes.oo(machines[i], CRecipes.i(CBlocks.blocksClayLaserInterface[i])), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockClayReactor)), CRecipes.e(i), 1200L);
            }
            if (i >= 7) {
                recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(CBlocks.blocksBuffer[i]), CItems.itemMisc.get("LaserParts", 1)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksClayLaserInterface[i])), CRecipes.e(i), 120L);
                if (i <= 9) {
                    recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(CBlocks.blocksBuffer[i]), machines[i]), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksDistributor[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(CBlocks.blocksBuffer[i]), circuits[7]), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksDistributor[i])), CRecipes.e(i), 120L);
                    recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(CBlocks.blockClayReactor), CRecipes.i(CBlocks.blocksElectrolysisReactor[i])), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksTransformer[i])), CRecipes.e(i), 120L);
                }
                if (i <= 10) {
                    recipeAssembler.addRecipe(CRecipes.oo(machines[i], CItems.itemMisc.get("LaserParts", 4)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksClayEnergyLaser[i])), CRecipes.e(i), 480L);
                }
            }
            if (i == 8) {
                recipeAssembler.addRecipe(CRecipes.ii(machines[i], CRecipes.i(CBlocks.blocksChemicalReactor[5])), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blocksChemicalReactor[i])), CRecipes.e(i), 480L);
            }
            if (i >= 8 && i <= 9) {
                recipeAssembler.addRecipe(CRecipes.ii(machines[i], CRecipes.i(CBlocks.blocksSmelter[i - 1], 16)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksSmelter[i])), CRecipes.e(i), 2000L);
            }
            if (i == 9) {
                recipeAssembler.addRecipe(CRecipes.oo(machines[i], CRecipes.i(CBlocks.blocksBendingMachine[7], 4)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksBendingMachine[i])), CRecipes.e(i), 480L);
            }
            if (i >= 9) {
                if (i == 9) {
                    recipeAssembler.addRecipe(CRecipes.oo(machines[i], CRecipes.i(CBlocks.blocksTransformer[i], 16)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksCACondenser[i])), CRecipes.e(i), 480L);
                    recipeAssembler.addRecipe(CRecipes.oo(machines[i], CRecipes.i(CBlocks.blockClayReactor, 16)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksCAInjector[i])), CRecipes.e(i), 480L);
                } else {
                    if (i <= 11) {
                        recipeAssembler.addRecipe(CRecipes.oo(machines[i], CRecipes.i(CBlocks.blocksTransformer[i], 16)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksCACondenser[i])), CRecipes.e(i), 480L);
                    }
                    recipeAssembler.addRecipe(CRecipes.oo(machines[i], CRecipes.i(CBlocks.blocksCAInjector[i - 1], 2)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksCAInjector[i])), CRecipes.e(i), 480L);
                }
            }
            if (i == 10) {
                recipeAssembler.addRecipe(CRecipes.ii(machines[i], CRecipes.i(CBlocks.blocksAssembler[6])), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blocksAssembler[i])), CRecipes.e(i), 40L);
            }
            if (i >= 10) {
                recipeAssembler.addRecipe(CRecipes.oo(machines[i], CRecipes.i(CBlocks.blockClayReactor, 16)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksCAReactorCore[i])), CRecipes.e(i), 120L);
                if (i <= 12) {
                    recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(CBlocks.blocksCAInjector[i]), CRecipes.i(CBlocks.blockClayReactor)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksTransformer[i])), CRecipes.e(i), 120L);
                }
            }
            if (i != 13) continue;
            recipeAssembler.addRecipe(CRecipes.oo(machines[i], CBlocks.blockCAReactorCoil.get("opa")), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blockEnergeticClayDecomposer)), CRecipes.e(i), 120L);
        }
        if (ClayiumCore.cfgEnableRFGenerator) {
            Map<String, Map<String, Object>> map = ClayRFGenerator.getConfigMap();
            HashMap blockList = new HashMap();
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                Map<String, Object> config = entry.getValue();
                if (config == null || !(config.get("Tier") instanceof Number)) continue;
                int tier = ((Number)config.get("Tier")).intValue();
                Block blockRFGenerator = CBlocks.blocksRFGenerator.get(entry.getKey());
                ArrayList<Block> list = blockList.containsKey(tier) ? (List)blockList.get(tier) : new ArrayList<Block>();
                list.add(blockRFGenerator);
                blockList.put(tier, list);
            }
            for (Map.Entry<String, Map<String, Object>> entry : blockList.entrySet()) {
                int tier = (Integer)((Object)entry.getKey());
                List list = (List)((Object)entry.getValue());
                for (int i2 = 0; i2 < list.size(); ++i2) {
                    Block block = tier >= 0 && tier < CBlocks.blocksRedstoneInterface.length && CBlocks.blocksRedstoneInterface[tier] != null ? CBlocks.blocksRedstoneInterface[tier] : CBlocks.blocksRedstoneInterface[5];
                    ItemStack prev = i2 == 0 ? machines[tier] : CRecipes.i((Block)list.get(i2 - 1));
                    recipeAssembler.addRecipe(CRecipes.oo(prev, CRecipes.i(block, 1)), 0, 4, CRecipes.ii(CRecipes.i((Block)list.get(i2))), CRecipes.e(tier), 120L);
                }
            }
        }
        recipeAssembler.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.QUARTZ, CMaterials.DUST, 16)), 0, 0, CRecipes.ii(CRecipes.i(CBlocks.blockQuartzCrucible)), 1000L, 20L);
        recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST), CMaterials.getOD(CMaterials.QUARTZ, CMaterials.DUST, 8)), 0, 7, CRecipes.ii(CRecipes.i(CBlocks.blockLaserReflector)), CRecipes.e(0.2, 7), 100L);
        recipeReactor.addRecipe(CRecipes.ii(machines[8], CRecipes.i(CBlocks.blockLithiumSolarClayFabricator)), 0, 8, CRecipes.ii(CRecipes.i(CBlocks.blockClayFabricatorMK1)), CRecipes.e(3.0, 8), 100000000L);
        recipeReactor.addRecipe(CRecipes.ii(machines[9], CRecipes.i(CBlocks.blockLithiumSolarClayFabricator)), 0, 9, CRecipes.ii(CRecipes.i(CBlocks.blockClayFabricatorMK2)), CRecipes.e(3.0, 9), 100000000000L);
        recipeReactor.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blockClayFabricatorMK2, 64), CBlocks.blockOverclocker.get("opa", 16)), 0, 13, CRecipes.ii(CRecipes.i(CBlocks.blockClayFabricatorMK3)), CRecipes.e(10.0, 13), 1000000000000000000L);
        recipeCAInjector.addRecipe(CRecipes.ii(machines[9], CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 8)), 0, 9, CRecipes.ii(CBlocks.blockResonator.get("antimatter")), CRecipes.e(2.0, 9), 4000L);
        recipeCAInjector.addRecipe(CRecipes.ii(CBlocks.blockResonator.get("antimatter", 16), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 64)), 0, 11, CRecipes.ii(CBlocks.blockResonator.get("pureantimatter")), CRecipes.e(2.0, 11), 4000L);
        recipeCAInjector.addRecipe(CRecipes.ii(CBlocks.blockResonator.get("pureantimatter", 16), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 64)), 0, 12, CRecipes.ii(CBlocks.blockResonator.get("oec")), CRecipes.e(2.0, 12), 4000L);
        recipeCAInjector.addRecipe(CRecipes.ii(CBlocks.blockResonator.get("oec", 16), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 64)), 0, 13, CRecipes.ii(CBlocks.blockResonator.get("opa")), CRecipes.e(2.0, 13), 4000L);
        recipeCAInjector.addRecipe(CRecipes.ii(CBlocks.blockOverclocker.get("antimatter"), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 8)), 0, 9, CRecipes.ii(CBlocks.blockEnergyStorageUpgrade.get("antimatter")), CRecipes.e(2.0, 9), 4000L);
        recipeCAInjector.addRecipe(CRecipes.ii(CBlocks.blockEnergyStorageUpgrade.get("antimatter", 16), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 64)), 0, 11, CRecipes.ii(CBlocks.blockEnergyStorageUpgrade.get("pureantimatter")), CRecipes.e(2.0, 11), 4000L);
        recipeCAInjector.addRecipe(CRecipes.ii(CBlocks.blockEnergyStorageUpgrade.get("pureantimatter", 16), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 64)), 0, 12, CRecipes.ii(CBlocks.blockEnergyStorageUpgrade.get("oec")), CRecipes.e(2.0, 12), 4000L);
        recipeCAInjector.addRecipe(CRecipes.ii(CBlocks.blockEnergyStorageUpgrade.get("oec", 16), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 64)), 0, 13, CRecipes.ii(CBlocks.blockEnergyStorageUpgrade.get("opa")), CRecipes.e(2.0, 13), 4000L);
        recipeReactor.addRecipe(CRecipes.oo(CRecipes.s(machines[10], 1), CBlocks.blockResonator.get("antimatter", 8)), 0, 10, CRecipes.ii(CBlocks.blockOverclocker.get("antimatter")), CRecipes.e(5.0, 10), 10000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CRecipes.s(machines[11], 4), CBlocks.blockResonator.get("pureantimatter", 16)), 0, 11, CRecipes.ii(CBlocks.blockOverclocker.get("pureantimatter")), CRecipes.e(5.0, 11), 100000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CRecipes.s(machines[12], 16), CBlocks.blockResonator.get("oec", 32)), 0, 12, CRecipes.ii(CBlocks.blockOverclocker.get("oec")), CRecipes.e(5.0, 12), 1000000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CRecipes.s(machines[13], 64), CBlocks.blockResonator.get("opa", 64)), 0, 13, CRecipes.ii(CBlocks.blockOverclocker.get("opa")), CRecipes.e(5.0, 13), 1000000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(mats[10], CMaterials.PLATE, 6), CMaterials.getOD(CMaterials.PLATINUM, CMaterials.INGOT)), 0, 10, CRecipes.ii(CBlocks.blockCAReactorCoil.get("antimatter")), CRecipes.e(10), 10000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(mats[11], CMaterials.PLATE, 6), CMaterials.getOD(CMaterials.IRIDIUM, CMaterials.INGOT, 4)), 0, 11, CRecipes.ii(CBlocks.blockCAReactorCoil.get("pureantimatter")), CRecipes.e(11), 100000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(mats[12], CMaterials.PLATE, 6), CMaterials.getOD(CMaterials.MAIN_OSMIUM, CMaterials.INGOT, 16)), 0, 12, CRecipes.ii(CBlocks.blockCAReactorCoil.get("oec")), CRecipes.e(12), 1000000000000000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(mats[13], CMaterials.PLATE, 6), CMaterials.getOD(CMaterials.RHENIUM, CMaterials.INGOT, 64)), 0, 13, CRecipes.ii(CBlocks.blockCAReactorCoil.get("opa")), CRecipes.e(13), 1000000000000000L);
        recipeAssembler.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.PURE_ANTIMATTER, CMaterials.GEM, 3), new OreDictionaryStack("blockGlass", 2)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blockPANCable, 12)), CRecipes.e(10), 2L);
        recipeReactor.addRecipe(CRecipes.oo(CRecipes.i(CBlocks.blocksPANAdapter[10], 4), circuits[11]), 0, 11, CRecipes.ii(CRecipes.i(CBlocks.blockPANCore)), CRecipes.e(11), 100000000000000L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockResonator.get("antimatter"), CRecipes.i(CBlocks.blockPANCable, 6)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksPANAdapter[10])), CRecipes.e(10), 60L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockResonator.get("pureantimatter"), CRecipes.i(CBlocks.blockPANCable, 6)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksPANAdapter[11])), CRecipes.e(11), 60L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockResonator.get("oec"), CRecipes.i(CBlocks.blockPANCable, 6)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksPANAdapter[12])), CRecipes.e(12), 60L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockResonator.get("opa"), CRecipes.i(CBlocks.blockPANCable, 6)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksPANAdapter[13])), CRecipes.e(13), 60L);
        recipeAssembler.addRecipe(CRecipes.oo(machines[4], CRecipes.i(CBlocks.blockPANCable, 4)), 0, 10, CRecipes.ii(CRecipes.i(CBlocks.blocksPANDuplicator[4])), CRecipes.e(10), 20L);
        CMaterial[] panDuplicatorMats = new CMaterial[]{CMaterials.RUBIDIUM, CMaterials.LANTHANUM, CMaterials.CAESIUM, CMaterials.FRANCIUM, CMaterials.RADIUM, CMaterials.TANTALUM, CMaterials.BISMUTH, CMaterials.ACTINIUM, CMaterials.VANADIUM};
        for (int i3 = 0; i3 <= 8; ++i3) {
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(CRecipes.i(CBlocks.blocksPANDuplicator[i3 + 5]), CRecipes.oo("#I#", "DMD", "#I#", Character.valueOf('#'), CMaterials.get(CMaterials.COMPRESSED_PURE_ANTIMATTER[i3], CMaterials.GEM), Character.valueOf('D'), CRecipes.i(CBlocks.blocksPANDuplicator[i3 + 4]), Character.valueOf('I'), CMaterials.getODName(panDuplicatorMats[i3], CMaterials.INGOT), Character.valueOf('M'), machines[i3 + 5])));
        }
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockOthersHull.get("az91d", 4), CRecipes.i(CBlocks.blocksClayInterface[5])), 0, 4, CRecipes.ii(CRecipes.s(StorageContainer.expandStorage(CRecipes.i(CBlocks.blockStorageContainer), 65536), 4)), CRecipes.e(6), 120L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockOthersHull.get("az91d", 4), CRecipes.i(CBlocks.blocksRedstoneInterface[5])), 0, 4, CRecipes.ii(CRecipes.i(CBlocks.blockVacuumContainer, 4)), CRecipes.e(6), 120L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockOthersHull.get("az91d"), CRecipes.s(circuits[8], 4)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockAutoTrader)), CRecipes.e(8), 120L);
        GameRegistry.addRecipe((IRecipe)new ShapelessRecipes(StorageContainer.expandStorage(CRecipes.i(CBlocks.blockStorageContainer), Integer.MAX_VALUE), CRecipes.li(StorageContainer.expandStorage(CRecipes.i(CBlocks.blockStorageContainer), 65536), CRecipes.s(circuits[8], 1))){

            public ItemStack func_77572_b(InventoryCrafting inv) {
                for (int i = 0; i < inv.func_70302_i_(); ++i) {
                    ItemStack item = inv.func_70301_a(i);
                    if (StorageContainer.getStorageSize(item) != 65536) continue;
                    ItemStack copy = item.func_77946_l();
                    copy.field_77994_a = 1;
                    return StorageContainer.expandStorage(copy, Integer.MAX_VALUE);
                }
                return super.func_77572_b(inv);
            }
        });
        GameRegistry.addRecipe((ItemStack)CRecipes.i(CBlocks.blockClayChunkLoader), (Object[])new Object[]{"PCP", "C#C", "PCP", Character.valueOf('#'), CBlocks.blockOthersHull.get("zk60a"), Character.valueOf('C'), circuits[6], Character.valueOf('P'), circuits[5]});
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150435_aG), circuits[7]), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockClayMarker)), CRecipes.e(7), 480L);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blockCompressedClay, 1, 0), circuits[8]), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockClayOpenPitMarker)), CRecipes.e(8), 480L);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blockCompressedClay, 1, 1), circuits[8]), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockClayGroundLevelingMarker)), CRecipes.e(8), 480L);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blockClayOpenPitMarker), CRecipes.i(CBlocks.blockClayGroundLevelingMarker)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockClayPrismMarker)), CRecipes.e(8), 480L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockOthersHull.get("zk60a"), CRecipes.s(circuits[7], 64)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockAreaCollector)), CRecipes.e(7), 6000L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockOthersHull.get("az91d"), circuits[6]), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockMiner)), CRecipes.e(6), 60L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockOthersHull.get("zk60a"), CRecipes.s(circuits[8], 64)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockAreaMiner)), CRecipes.e(8), 6000L);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blockAreaMiner), CRecipes.s(circuits[9], 64)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockAdvancedAreaMiner)), CRecipes.e(9), 6000L);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blockAdvancedAreaMiner), CRecipes.s(circuits[10], 64)), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockAreaReplacer)), CRecipes.e(10), 6000L);
        recipeAssembler.addRecipe(CRecipes.ii(CBlocks.blockOthersHull.get("az91d"), circuits[5]), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockActivator)), CRecipes.e(6), 60L);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blockAreaCollector), circuits[8]), 0, 6, CRecipes.ii(CRecipes.i(CBlocks.blockAreaActivator)), CRecipes.e(8), 6000L);
        recipeAssembler.addRecipe(CRecipes.oo(CRecipes.i(CItems.itemsClayShooter[3]), CItems.itemMisc.get("TeleportationParts")), 0, 10, CRecipes.ii(CRecipes.i(CItems.itemInstantTeleporter)), CRecipes.e(11), 3000L);
        Block[] blocksEnergeticClayCondenser = new Block[16];
        blocksEnergeticClayCondenser[3] = CBlocks.blockEnergeticClayCondenser;
        blocksEnergeticClayCondenser[4] = CBlocks.blockEnergeticClayCondenserMK2;
        blocksEnergeticClayCondenser[5] = CBlocks.blockAutoClayCondenser;
        blocksEnergeticClayCondenser[7] = CBlocks.blockAutoClayCondenserMK2;
        Block[] blocksClayFabricator = new Block[16];
        blocksClayFabricator[5] = CBlocks.blockSolarClayFabricatorMK1;
        blocksClayFabricator[6] = CBlocks.blockSolarClayFabricatorMK2;
        blocksClayFabricator[7] = CBlocks.blockLithiumSolarClayFabricator;
        blocksClayFabricator[8] = CBlocks.blockClayFabricatorMK1;
        blocksClayFabricator[9] = CBlocks.blockClayFabricatorMK2;
        Block[] blockArray = new Block[16];
        blockArray[1] = CBlocks.blockClayWaterWheel;
        blockArray[2] = CBlocks.blockDenseClayWaterWheel;
        Block[][] arrayblocklist = new Block[][]{CBlocks.blocksBendingMachine, CBlocks.blocksWireDrawingMachine, CBlocks.blocksPipeDrawingMachine, CBlocks.blocksCuttingMachine, CBlocks.blocksLathe, CBlocks.blocksCobblestoneGenerator, CBlocks.blocksCondenser, CBlocks.blocksGrinder, CBlocks.blocksDecomposer, CBlocks.blocksMillingMachine, CBlocks.blocksAssembler, CBlocks.blocksInscriber, CBlocks.blocksCentrifuge, CBlocks.blocksSaltExtractor, CBlocks.blocksSmelter, CBlocks.blocksBuffer, CBlocks.blocksChemicalReactor, CBlocks.blocksAlloySmelter, CBlocks.blocksElectrolysisReactor, CBlocks.blocksClayEnergyLaser, CBlocks.blocksDistributor, CBlocks.blocksTransformer, CBlocks.blocksAutoCrafter, CBlocks.blocksCACondenser, CBlocks.blocksCAInjector, blocksEnergeticClayCondenser, blocksClayFabricator, blockArray};
        ArrayList blocklists = new ArrayList(Arrays.asList(arrayblocklist));
        if (ClayiumCore.cfgEnableInjectorRecipeOfInterface) {
            blocklists.addAll(Arrays.asList(CBlocks.blocksClayInterface, CBlocks.blocksRedstoneInterface, CBlocks.blocksClayLaserInterface));
        }
        for (Block[] blocks : blocklists) {
            int j = -1;
            int n = 0;
            for (int i4 = 0; i4 < blocks.length; ++i4) {
                if ((n = (int)((double)n + Math.pow(1.3f, i4))) >= 64) {
                    n = 64;
                }
                if (blocks[i4] == null) continue;
                if (j != -1) {
                    recipeCAInjector.addRecipe(CRecipes.oo(CRecipes.i(blocks[j]), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, n)), 0, i4, CRecipes.ii(CRecipes.i(blocks[i4])), CRecipes.e(3.0, i4), 4000L);
                }
                j = i4;
                n = 0;
            }
        }
        Iterator<Object> iterator = MetalChest.getChestMaterialMap().keySet().iterator();
        while (iterator.hasNext()) {
            CMaterial material;
            CMaterial imaterial = material = (CMaterial)iterator.next();
            if (material == CMaterials.OSMIUM) {
                imaterial = CMaterials.MAIN_OSMIUM;
            }
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(CRecipes.i(CBlocks.blockMetalChest, 1, material.meta), CRecipes.oo("###", "#*#", "###", Character.valueOf('#'), CMaterials.getODName(imaterial, CMaterials.INGOT), Character.valueOf('*'), CRecipes.i((Block)Blocks.field_150486_ae, 1, Short.MAX_VALUE))));
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(CRecipes.i(CBlocks.blockMetalChest, 1, material.meta), CRecipes.oo("###", "#*#", "###", Character.valueOf('#'), CMaterials.getODName(imaterial, CMaterials.INGOT), Character.valueOf('*'), CRecipes.i(CBlocks.blockMetalChest, 1, Short.MAX_VALUE))));
        }
    }

    public static void registerMaterialRecipes() {
        CMaterial[][] materialmaps;
        CMaterial[] materials;
        CShape shape = CMaterials.INGOT;
        for (CMaterial material : new CMaterial[]{CMaterials.IMPURE_SILICON, CMaterials.SILICONE, CMaterials.SILICON, CMaterials.ALUMINIUM, CMaterials.CLAY_STEEL, CMaterials.CLAYIUM, CMaterials.ULTIMATE_ALLOY}) {
            GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(material, CMaterials.BLOCK), CRecipes.oo(CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape))));
            GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(material, shape, 9), CRecipes.oo(CMaterials.getODName(material, CMaterials.BLOCK))));
        }
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(CMaterials.ALUMINIUM, CMaterials.BLOCK), CRecipes.oo(CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape), CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape), CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape), CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape), CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape), CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape), CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape), CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape), CMaterials.getODName(CMaterials.ALUMINIUM_OD, shape))));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(CMaterials.ALUMINIUM, shape, 9), CRecipes.oo(CMaterials.getODName(CMaterials.ALUMINIUM_OD, CMaterials.BLOCK))));
        shape = CMaterials.GEM;
        for (CMaterial material : new CMaterial[]{CMaterials.ANTIMATTER, CMaterials.PURE_ANTIMATTER, CMaterials.OCTUPLE_PURE_ANTIMATTER}) {
            GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(material, CMaterials.BLOCK), CRecipes.oo(CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape), CMaterials.getODName(material, shape))));
            GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(material, shape, 9), CRecipes.oo(CMaterials.getODName(material, CMaterials.BLOCK))));
        }
        GameRegistry.addRecipe((ItemStack)CBlocks.blockMaterial.get("OctupleEnergeticClay"), (Object[])new Object[]{"###", "###", "###", Character.valueOf('#'), CRecipes.i(CBlocks.blockCompressedClay, 1, 12)});
        GameRegistry.addShapelessRecipe((ItemStack)CRecipes.i(CBlocks.blockCompressedClay, 9, 12), (Object[])new Object[]{CBlocks.blockMaterial.get("OctupleEnergeticClay")});
        String[] dyes = new String[]{"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White"};
        for (int i = 0; i < 16; ++i) {
            String str = ItemDye.field_150921_b[BlockColored.func_150031_c((int)i)];
            str = Character.toTitleCase(str.charAt(0)) + str.substring(1);
            GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(CRecipes.i(CBlocks.blockSiliconeColored, 8, i), CRecipes.oo("###", "#*#", "###", Character.valueOf('#'), CMaterials.getODName(CMaterials.SILICONE, CMaterials.BLOCK), Character.valueOf('*'), "dye" + dyes[15 - i])));
        }
        recipeGrinder.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.BLOCK), CMaterials.get(CMaterials.CLAY, CMaterials.DUST), 1L, 3L);
        recipeGrinder.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DUST), 1L, 6L);
        recipeGrinder.addRecipe(CMaterials.get(CMaterials.IND_CLAY, CMaterials.BLOCK), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST), 1L, 12L);
        recipeGrinder.addRecipe(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.BLOCK), CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST), 2L, 12L);
        recipeCondenser.addRecipe(CMaterials.get(CMaterials.CLAY, CMaterials.DUST), CMaterials.get(CMaterials.CLAY, CMaterials.BLOCK), 1L, 3L);
        recipeCondenser.addRecipe(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.DUST), CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK), 1L, 6L);
        for (CMaterial material : materials = new CMaterial[]{CMaterials.IMPURE_SILICON, CMaterials.SILICON, CMaterials.SILICONE, CMaterials.ALUMINIUM, CMaterials.CLAY_STEEL, CMaterials.CLAYIUM, CMaterials.ULTIMATE_ALLOY, CMaterials.AZ91D_ALLOY, CMaterials.ZK60A_ALLOY}) {
            recipeBendingMachine.addRecipe(CMaterials.getOD(material, CMaterials.INGOT), 4, CMaterials.get(material, CMaterials.PLATE), CRecipes.e(4), (int)(20.0f * material.hardness));
            recipeBendingMachine.addRecipe(CMaterials.getOD(material, CMaterials.PLATE, 4), 4, CMaterials.get(material, CMaterials.LARGE_PLATE), CRecipes.e(4), (int)(40.0f * material.hardness));
            recipeGrinder.addRecipe(CMaterials.getOD(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * material.hardness));
            recipeGrinder.addRecipe(CMaterials.getOD(material, CMaterials.PLATE), CMaterials.get(material, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * material.hardness));
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.LARGE_PLATE), CMaterials.get(material, CMaterials.DUST, 4), CRecipes.e(0.25, 4), (int)(80.0f * material.hardness));
        }
        recipeBendingMachine.addRecipe(CMaterials.getOD(CMaterials.ALUMINIUM_OD, CMaterials.INGOT), 4, CMaterials.get(CMaterials.ALUMINIUM, CMaterials.PLATE), CRecipes.e(4), (int)(20.0f * CMaterials.ALUMINIUM.hardness));
        recipeBendingMachine.addRecipe(CMaterials.getOD(CMaterials.ALUMINIUM_OD, CMaterials.PLATE, 4), 4, CMaterials.get(CMaterials.ALUMINIUM, CMaterials.LARGE_PLATE), CRecipes.e(4), (int)(40.0f * CMaterials.ALUMINIUM.hardness));
        recipeGrinder.addRecipe(CMaterials.getOD(CMaterials.ALUMINIUM_OD, CMaterials.INGOT), CMaterials.get(CMaterials.ALUMINIUM, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * CMaterials.ALUMINIUM.hardness));
        recipeGrinder.addRecipe(CMaterials.getOD(CMaterials.ALUMINIUM_OD, CMaterials.PLATE), CMaterials.get(CMaterials.ALUMINIUM, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * CMaterials.ALUMINIUM.hardness));
        if (ClayiumCore.cfgHardcoreAluminium) {
            recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.INGOT), 4, CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.PLATE), CRecipes.e(4), (int)(20.0f * CMaterials.IMPURE_ALUMINIUM.hardness));
            recipeBendingMachine.addRecipe(CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.PLATE, 4), 4, CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.LARGE_PLATE), CRecipes.e(4), (int)(40.0f * CMaterials.IMPURE_ALUMINIUM.hardness));
            recipeGrinder.addRecipe(CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.INGOT), CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * CMaterials.IMPURE_ALUMINIUM.hardness));
            recipeGrinder.addRecipe(CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.PLATE), CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * CMaterials.IMPURE_ALUMINIUM.hardness));
            recipeGrinder.addRecipe(CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.LARGE_PLATE), CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.DUST, 4), CRecipes.e(0.25, 4), (int)(80.0f * CMaterials.IMPURE_ALUMINIUM.hardness));
        }
        GameRegistry.addSmelting((ItemStack)CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT), (ItemStack)CMaterials.get(CMaterials.SILICONE, CMaterials.INGOT), (float)0.0f);
        for (CMaterial material : materials = new CMaterial[]{CMaterials.IRON, CMaterials.GOLD, CMaterials.MAGNESIUM, CMaterials.SODIUM, CMaterials.LITHIUM, CMaterials.ZIRCONIUM, CMaterials.ZINC, CMaterials.MANGANESE, CMaterials.CALCIUM, CMaterials.POTASSIUM, CMaterials.NICKEL, CMaterials.BERYLLIUM, CMaterials.LEAD, CMaterials.HAFNIUM, CMaterials.CHROME, CMaterials.TITANIUM, CMaterials.STRONTIUM, CMaterials.BARIUM, CMaterials.COPPER, CMaterials.ZINCALMINIUM_ALLOY, CMaterials.ZINCONIUM_ALLOY, CMaterials.BRONZE, CMaterials.BRASS, CMaterials.ELECTRUM, CMaterials.INVAR, CMaterials.STEEL}) {
            recipeGrinder.addRecipe(CMaterials.getOD(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * material.hardness));
            recipeGrinder.addRecipe(CMaterials.getOD(material, CMaterials.PLATE), CMaterials.get(material, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * material.hardness));
            if (!CMaterials.existOD(material, CMaterials.PLATE)) continue;
            recipeBendingMachine.addRecipe(CMaterials.getOD(material, CMaterials.INGOT), CMaterials.getODExist(material, CMaterials.PLATE), CRecipes.e(4), (int)(40.0f * material.hardness));
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.ZINCALMINIUM_ALLOY, CMaterials.ZINCONIUM_ALLOY}) {
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * material.hardness));
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.RUBIDIUM, CMaterials.CAESIUM, CMaterials.FRANCIUM, CMaterials.RADIUM, CMaterials.ACTINIUM, CMaterials.THORIUM, CMaterials.PROTACTINIUM, CMaterials.URANIUM, CMaterials.NEPTUNIUM, CMaterials.PLUTONIUM, CMaterials.AMERICIUM, CMaterials.CURIUM, CMaterials.LANTHANUM, CMaterials.CERIUM, CMaterials.PRASEODYMIUM, CMaterials.NEODYMIUM, CMaterials.PROMETHIUM, CMaterials.SAMARIUM, CMaterials.EUROPIUM, CMaterials.VANADIUM, CMaterials.COBALT, CMaterials.PALLADIUM, CMaterials.SILVER, CMaterials.PLATINUM, CMaterials.IRIDIUM, CMaterials.OSMIUM, CMaterials.RHENIUM, CMaterials.TANTALUM, CMaterials.TUNGSTEN, CMaterials.MOLYBDENUM, CMaterials.TIN, CMaterials.ANTIMONY, CMaterials.BISMUTH, CMaterials.CARBON, CMaterials.GALLIUM, CMaterials.YTTRIUM, CMaterials.NIOBIUM, CMaterials.URANIUM_235, CMaterials.PLUTONIUM_241, CMaterials.NAQUADAH, CMaterials.NAQUADAH_ENRICHED, CMaterials.NAQUADRIA, CMaterials.NEUTRONIUM, CMaterials.ARDITE, CMaterials.YELLORIUM, CMaterials.CYANITE, CMaterials.BLUTONIUM, CMaterials.LUDICRITE, CMaterials.FZ_DARK_IRON, CMaterials.METEORIC_IRON, CMaterials.DESH, CMaterials.PROMETHEUM, CMaterials.DEEP_IRON, CMaterials.INFUSCOLIUM, CMaterials.OURECLASE, CMaterials.AREDRITE, CMaterials.ASTRAL_SILVER, CMaterials.CARMOT, CMaterials.MITHRIL, CMaterials.RUBRACIUM, CMaterials.ORICHALCUM, CMaterials.ADAMANTINE, CMaterials.ATLARUS, CMaterials.IGNATIUS, CMaterials.SHADOW_IRON, CMaterials.LEMURITE, CMaterials.MIDASIUM, CMaterials.VYROXERES, CMaterials.CERUCLASE, CMaterials.ALDUORITE, CMaterials.KALENDRITE, CMaterials.VULCANITE, CMaterials.SANGUINITE, CMaterials.EXIMITE, CMaterials.MEUTOITE, CMaterials.PLASTIC, CMaterials.GRAPHITE, CMaterials.REDSTONE_ALLOY, CMaterials.CONDUCTIVE_IRON, CMaterials.ENERGETIC_ALLOY, CMaterials.ELECTRICAL_STEEL, CMaterials.DARK_STEEL, CMaterials.PHASED_IRON, CMaterials.PHASED_GOLD, CMaterials.SOULARIUM, CMaterials.SIGNALUM, CMaterials.LUMIUM, CMaterials.ENDERIUM, CMaterials.ELECTRUM_FLUX, CMaterials.ALUMINUM_BRASS, CMaterials.PIG_IRON, CMaterials.ALUMITE, CMaterials.MANYULLYN, CMaterials.FAIRY, CMaterials.POKEFENNIUM, CMaterials.RED_AURUM, CMaterials.DRULLOY, CMaterials.RED_ALLOY, CMaterials.ELECTROTINE_ALLOY, CMaterials.TUNGSTEN_STEEL, CMaterials.CUPRONICKEL, CMaterials.NICHROME, CMaterials.KANTHAL, CMaterials.STAINLESS_STEEL, CMaterials.COBALT_BRASS, CMaterials.MAGNALIUM, CMaterials.SOLDERING_ALLOY, CMaterials.BATTERY_ALLOY, CMaterials.VANADIUM_GALLIUM, CMaterials.YTTRIUM_BARIUM_CUPRATE, CMaterials.ULTIMET, CMaterials.TIN_ALLOY, CMaterials.BLUE_ALLOY, CMaterials.HEPATIZON, CMaterials.DAMASCUS_STEEL, CMaterials.ANGMALLEN, CMaterials.BLACK_STEEL, CMaterials.QUICKSILVER, CMaterials.HADEROTH, CMaterials.CELENEGIL, CMaterials.TARTARITE, CMaterials.SHADOW_STEEL, CMaterials.INOLASHITE, CMaterials.AMORDRINE, CMaterials.DESICHALKOS, CMaterials.WROUGHT_IRON, CMaterials.ANNEALED_COPPER, CMaterials.IRON_MAGNETIC, CMaterials.STEEL_MAGNETIC, CMaterials.NEODYMIUM_MAGNETIC, CMaterials.REFINED_GLOWSTONE, CMaterials.REFINED_OBSIDIAN, CMaterials.IRON_COMPRESSED, CMaterials.THAUMIUM, CMaterials.VOID, CMaterials.MANASTEEL, CMaterials.TERRASTEEL, CMaterials.ELVEN_ELEMENTIUM, CMaterials.HEE_ENDIUM, CMaterials.UNSTABLE, CMaterials.NINJA}) {
            if (CMaterials.existOD(material, CMaterials.DUST)) {
                recipeGrinder.addRecipe(CMaterials.getOD(material, CMaterials.INGOT), CMaterials.getODExist(material, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * material.hardness));
                recipeGrinder.addRecipe(CMaterials.getOD(material, CMaterials.PLATE), CMaterials.getODExist(material, CMaterials.DUST), CRecipes.e(0.25, 4), (int)(80.0f * material.hardness));
            }
            if (!CMaterials.existOD(material, CMaterials.PLATE)) continue;
            recipeBendingMachine.addRecipe(CMaterials.getOD(material, CMaterials.INGOT), CMaterials.getODExist(material, CMaterials.PLATE), CRecipes.e(4), (int)(40.0f * material.hardness));
        }
        materials = new CMaterial[]{CMaterials.ANTIMATTER, CMaterials.PURE_ANTIMATTER, CMaterials.OCTUPLE_CLAY, CMaterials.OCTUPLE_PURE_ANTIMATTER};
        CShape[] shapes = new CShape[]{CMaterials.GEM, CMaterials.GEM, CMaterials.BLOCK, CMaterials.GEM};
        for (int i = 0; i < materials.length; ++i) {
            CMaterial material = materials[i];
            shape = shapes[i];
            recipeBendingMachine.addRecipe(CMaterials.get(material, shape), 9, CMaterials.get(material, CMaterials.PLATE), CRecipes.e(9 + i), (int)(20.0f * material.hardness));
            recipeBendingMachine.addRecipe(CMaterials.get(material, CMaterials.PLATE, 4), 9, CMaterials.get(material, CMaterials.LARGE_PLATE), CRecipes.e(9 + i), (int)(40.0f * material.hardness));
            recipeGrinder.addRecipe(CMaterials.get(material, shape), 10, CMaterials.get(material, CMaterials.DUST), CRecipes.e(0.25, 9 + i), (int)(80.0f * material.hardness));
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.PLATE), 10, CMaterials.get(material, CMaterials.DUST), CRecipes.e(0.25, 9 + i), (int)(80.0f * material.hardness));
            recipeGrinder.addRecipe(CMaterials.get(material, CMaterials.LARGE_PLATE), 10, CMaterials.get(material, CMaterials.DUST, 4), CRecipes.e(0.25, 9 + i), (int)(80.0f * material.hardness));
            recipeCondenser.addRecipe(CMaterials.get(material, CMaterials.DUST), 10, CMaterials.get(material, shape), CRecipes.e(0.25, 9 + i), (int)(80.0f * material.hardness));
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.IMPURE_SILICON, CMaterials.SILICON, CMaterials.SILICONE, CMaterials.IRON, CMaterials.GOLD, CMaterials.LEAD, CMaterials.COPPER, CMaterials.BRONZE, CMaterials.BRASS, CMaterials.ELECTRUM, CMaterials.INVAR}) {
            GameRegistry.addSmelting((ItemStack)CMaterials.get(material, CMaterials.DUST), (ItemStack)CMaterials.get(material, CMaterials.INGOT), (float)0.0f);
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.ALUMINIUM, CMaterials.SODIUM, CMaterials.ZINC, CMaterials.NICKEL, CMaterials.ALUMINIUM_OD}) {
            recipeSmelter.addRecipe(CMaterials.getOD(material, CMaterials.DUST), 5, CMaterials.get(material, CMaterials.INGOT), CRecipes.e(0.5, 5), 200L);
        }
        if (ClayiumCore.cfgHardcoreAluminium) {
            recipeSmelter.addRecipe(CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.DUST), 5, CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.INGOT), CRecipes.e(0.5, 5), 200L);
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.MAGNESIUM, CMaterials.LITHIUM, CMaterials.ZIRCONIUM, CMaterials.ZINCALMINIUM_ALLOY, CMaterials.ZINCONIUM_ALLOY, CMaterials.AZ91D_ALLOY, CMaterials.ZK60A_ALLOY}) {
            recipeSmelter.addRecipe(CMaterials.getOD(material, CMaterials.DUST), 6, CMaterials.get(material, CMaterials.INGOT), CRecipes.e(0.2, 6), 400L);
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.CALCIUM, CMaterials.POTASSIUM}) {
            recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.getOD(material, CMaterials.DUST)), 0, 5, CRecipes.ii(CMaterials.get(material, CMaterials.INGOT)), CRecipes.e(5), 500L);
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.BERYLLIUM, CMaterials.HAFNIUM, CMaterials.CLAY_STEEL, CMaterials.STEEL}) {
            recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.getOD(material, CMaterials.DUST)), 0, 6, CRecipes.ii(CMaterials.get(material, CMaterials.INGOT)), CRecipes.e(6), 500L);
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.MANGANESE, CMaterials.STRONTIUM, CMaterials.BARIUM, CMaterials.CLAYIUM}) {
            recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.getOD(material, CMaterials.DUST)), 0, 7, CRecipes.ii(CMaterials.get(material, CMaterials.INGOT)), CRecipes.e(2.0, 7), 1000L);
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.TITANIUM, CMaterials.ULTIMATE_ALLOY}) {
            recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.getOD(material, CMaterials.DUST)), 0, 8, CRecipes.ii(CMaterials.get(material, CMaterials.INGOT)), CRecipes.e(4.0, 8), 2000L);
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.CHROME}) {
            recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.getOD(material, CMaterials.DUST)), 0, 9, CRecipes.ii(CMaterials.get(material, CMaterials.INGOT)), CRecipes.e(4.0, 9), 2000L);
        }
        if (ClayiumCore.cfgHardcoreOsmium) {
            recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.IMPURE_OSMIUM, CMaterials.INGOT)), 0, 11, CRecipes.ii(CMaterials.get(CMaterials.OSMIUM, CMaterials.INGOT)), CRecipes.e(4.0, 11), 100L);
        }
        for (CMaterial material : materials = new CMaterial[]{CMaterials.QUARTZ, CMaterials.LAPIS, CMaterials.DIAMOND, CMaterials.EMERALD, CMaterials.RUBY, CMaterials.SAPPHIRE, CMaterials.PERIDOT, CMaterials.AMBER, CMaterials.AMETHYST, CMaterials.APATITE, CMaterials.CRYSTAL_FLUX, CMaterials.MALACHITE, CMaterials.TANZANITE, CMaterials.TOPAZ, CMaterials.DILITHIUM, CMaterials.FORCICIUM, CMaterials.GREEN_SAPPHIRE, CMaterials.OPAL, CMaterials.JASPER, CMaterials.BLUE_TOPAZ, CMaterials.FORCILLIUM, CMaterials.MONAZITE, CMaterials.FORCE, CMaterials.QUARTZITE, CMaterials.LAZURITE, CMaterials.SODALITE, CMaterials.GARNET_RED, CMaterials.GARNET_YELLOW, CMaterials.NITER, CMaterials.PHOSPHORUS, CMaterials.LIGNITE, CMaterials.GLASS, CMaterials.IRIDIUM}) {
            if (!CMaterials.existOD(material, CMaterials.GEM)) continue;
            recipeReactor.addRecipe(CRecipes.oo(CMaterials.getOD(material, CMaterials.DUST), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST)), 0, 7, CRecipes.ii(CMaterials.getODExist(material, CMaterials.GEM)), CRecipes.e(7), 10000L);
        }
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.COAL, CMaterials.DUST), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST)), 0, 7, CRecipes.ii(CRecipes.i(Items.field_151044_h)), CRecipes.e(7), 10000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.CHARCOAL, CMaterials.DUST), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST)), 0, 7, CRecipes.ii(CRecipes.i(Items.field_151044_h, 1, 1)), CRecipes.e(7), 10000L);
        for (CMaterial material : materials) {
            if (!CMaterials.existOD(material, CMaterials.DUST)) continue;
            recipeGrinder.addRecipe(CMaterials.getOD(material, CMaterials.GEM), 5, CMaterials.getODExist(material, CMaterials.DUST), CRecipes.e(0.25, 5), (int)(80.0f * material.hardness));
        }
        recipeGrinder.addRecipe(CRecipes.i(Items.field_151044_h), 5, CMaterials.get(CMaterials.COAL, CMaterials.DUST), CRecipes.e(0.25, 5), (int)(80.0f * CMaterials.COAL.hardness));
        recipeGrinder.addRecipe(CRecipes.i(Items.field_151044_h, 1, 1), 5, CMaterials.get(CMaterials.CHARCOAL, CMaterials.DUST), CRecipes.e(0.25, 5), (int)(80.0f * CMaterials.CHARCOAL.hardness));
        for (CMaterial material : materials = new CMaterial[]{CMaterials.CINNABAR, CMaterials.CERTUS_QUARTZ, CMaterials.FLUIX}) {
            if (!CMaterials.existOD(material, CMaterials.CRYSTAL)) continue;
            recipeReactor.addRecipe(CRecipes.oo(CMaterials.getOD(material, CMaterials.DUST), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST)), 0, 7, CRecipes.ii(CMaterials.getODExist(material, CMaterials.CRYSTAL)), CRecipes.e(7), 10000L);
        }
        for (CMaterial material : materials) {
            if (!CMaterials.existOD(material, CMaterials.DUST)) continue;
            recipeGrinder.addRecipe(CMaterials.getOD(material, CMaterials.CRYSTAL), 5, CMaterials.getODExist(material, CMaterials.DUST), CRecipes.e(0.25, 5), (int)(80.0f * material.hardness));
        }
        recipeGrinder.addRecipe("oreClay", CItems.itemCompressedClayShard.get("1", ClayiumCore.multiplyProgressionRateStackSize(2)), 1L, ClayiumCore.divideByProgressionRateI(3));
        recipeGrinder.addRecipe("oreDenseClay", CItems.itemCompressedClayShard.get("2", ClayiumCore.multiplyProgressionRateStackSize(3)), 1L, ClayiumCore.divideByProgressionRateI(6));
        recipeGrinder.addRecipe("oreLargeDenseClay", CItems.itemCompressedClayShard.get("3", ClayiumCore.multiplyProgressionRateStackSize(5)), 1L, ClayiumCore.divideByProgressionRateI(9));
        recipeCondenser.addRecipe(CItems.itemCompressedClayShard.get("1", 4), CRecipes.i(CBlocks.blockCompressedClay, 1, 1), 1L, ClayiumCore.divideByProgressionRateI(3));
        recipeCondenser.addRecipe(CItems.itemCompressedClayShard.get("2", 4), CRecipes.i(CBlocks.blockCompressedClay, 1, 2), 1L, ClayiumCore.divideByProgressionRateI(6));
        recipeCondenser.addRecipe(CItems.itemCompressedClayShard.get("3", 4), CRecipes.i(CBlocks.blockCompressedClay, 1, 3), 1L, ClayiumCore.divideByProgressionRateI(9));
        Object oreList = null;
        materials = new CMaterial[]{CMaterials.SILICON, CMaterials.ALUMINIUM, CMaterials.ALUMINIUM_OD, CMaterials.COAL, CMaterials.IRON, CMaterials.GOLD, CMaterials.MAGNESIUM, CMaterials.SODIUM, CMaterials.LITHIUM, CMaterials.ZIRCONIUM, CMaterials.ZINC, CMaterials.MANGANESE, CMaterials.CALCIUM, CMaterials.POTASSIUM, CMaterials.NICKEL, CMaterials.BERYLLIUM, CMaterials.LEAD, CMaterials.HAFNIUM, CMaterials.CHROME, CMaterials.TITANIUM, CMaterials.STRONTIUM, CMaterials.BARIUM, CMaterials.COPPER, CMaterials.RUBIDIUM, CMaterials.CAESIUM, CMaterials.FRANCIUM, CMaterials.RADIUM, CMaterials.ACTINIUM, CMaterials.THORIUM, CMaterials.PROTACTINIUM, CMaterials.URANIUM, CMaterials.NEPTUNIUM, CMaterials.PLUTONIUM, CMaterials.AMERICIUM, CMaterials.CURIUM, CMaterials.LANTHANUM, CMaterials.CERIUM, CMaterials.PRASEODYMIUM, CMaterials.NEODYMIUM, CMaterials.PROMETHIUM, CMaterials.SAMARIUM, CMaterials.EUROPIUM, CMaterials.VANADIUM, CMaterials.COBALT, CMaterials.PALLADIUM, CMaterials.SILVER, CMaterials.PLATINUM, CMaterials.IRIDIUM, CMaterials.OSMIUM, CMaterials.RHENIUM, CMaterials.TANTALUM, CMaterials.TUNGSTEN, CMaterials.MOLYBDENUM, CMaterials.TIN, CMaterials.ANTIMONY, CMaterials.BISMUTH, CMaterials.GALLIUM, CMaterials.YTTRIUM, CMaterials.NIOBIUM, CMaterials.NAQUADAH, CMaterials.NAQUADAH_ENRICHED, CMaterials.NAQUADRIA, CMaterials.ARDITE, CMaterials.YELLORIUM, CMaterials.FZ_DARK_IRON, CMaterials.METEORIC_IRON, CMaterials.DESH, CMaterials.GRAPHITE, CMaterials.PROMETHEUM, CMaterials.DEEP_IRON, CMaterials.INFUSCOLIUM, CMaterials.OURECLASE, CMaterials.AREDRITE, CMaterials.ASTRAL_SILVER, CMaterials.CARMOT, CMaterials.MITHRIL, CMaterials.RUBRACIUM, CMaterials.ORICHALCUM, CMaterials.ADAMANTINE, CMaterials.ATLARUS, CMaterials.IGNATIUS, CMaterials.SHADOW_IRON, CMaterials.LEMURITE, CMaterials.MIDASIUM, CMaterials.VYROXERES, CMaterials.CERUCLASE, CMaterials.ALDUORITE, CMaterials.KALENDRITE, CMaterials.VULCANITE, CMaterials.SANGUINITE, CMaterials.EXIMITE, CMaterials.MEUTOITE};
        for (CMaterial material : materials) {
            if (!CMaterials.existOD(material, CMaterials.DUST)) continue;
            recipeGrinder.addRecipe("ore" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.DUST, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreRedgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.DUST, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreBlackgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.DUST, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreEndstone" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.DUST, 2), CRecipes.e(0.025, 5), 80L);
        }
        for (CMaterial material : new CMaterial[]{CMaterials.CERTUS_QUARTZ, CMaterials.CINNABAR}) {
            if (!CMaterials.existOD(material, CMaterials.CRYSTAL)) continue;
            recipeGrinder.addRecipe("ore" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.CRYSTAL, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreRedgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.CRYSTAL, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreBlackgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.CRYSTAL, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreEndstone" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.CRYSTAL, 2), CRecipes.e(0.025, 5), 80L);
        }
        for (CMaterial material : new CMaterial[]{CMaterials.QUARTZ, CMaterials.DIAMOND, CMaterials.EMERALD, CMaterials.AMETHYST, CMaterials.AMBER, CMaterials.MALACHITE, CMaterials.PERIDOT, CMaterials.RUBY, CMaterials.SAPPHIRE, CMaterials.TANZANITE, CMaterials.TOPAZ, CMaterials.OPAL, CMaterials.JASPER, CMaterials.FORCILLIUM, CMaterials.FORCE, CMaterials.GARNET_RED, CMaterials.GARNET_YELLOW, CMaterials.NITER, CMaterials.LIGNITE, CMaterials.QUARTZITE, CMaterials.DILITHIUM, CMaterials.ORICHALCUM}) {
            if (!CMaterials.existOD(material, CMaterials.GEM)) continue;
            recipeGrinder.addRecipe("ore" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreRedgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreBlackgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 2), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreEndstone" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 2), CRecipes.e(0.025, 5), 80L);
        }
        for (CMaterial material : new CMaterial[]{CMaterials.MONAZITE, CMaterials.FORCICIUM}) {
            if (!CMaterials.existOD(material, CMaterials.GEM)) continue;
            recipeGrinder.addRecipe("ore" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 4), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreRedgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 4), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreBlackgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 4), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreEndstone" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 4), CRecipes.e(0.025, 5), 80L);
        }
        for (CMaterial material : new CMaterial[]{CMaterials.LAPIS, CMaterials.LAZURITE, CMaterials.SODALITE, CMaterials.PHOSPHORUS, CMaterials.APATITE}) {
            if (!CMaterials.existOD(material, CMaterials.GEM)) continue;
            recipeGrinder.addRecipe("ore" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 10), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreRedgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 10), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreBlackgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 10), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreEndstone" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.GEM, 10), CRecipes.e(0.025, 5), 80L);
        }
        for (CMaterial material : new CMaterial[]{CMaterials.REDSTONE, CMaterials.NIKOLITE, CMaterials.ELECTROTINE, CMaterials.YELLOWSTONE, CMaterials.BLUESTONE, CMaterials.SULFUR, CMaterials.SALTPETER}) {
            if (!CMaterials.existOD(material, CMaterials.DUST)) continue;
            recipeGrinder.addRecipe("ore" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.DUST, 10), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreRedgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.DUST, 10), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreBlackgranite" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.DUST, 10), CRecipes.e(0.025, 5), 80L);
            recipeGrinder.addRecipe("oreEndstone" + material.oreDictionaryName, 5, CMaterials.getODExist(material, CMaterials.DUST, 10), CRecipes.e(0.025, 5), 80L);
        }
        recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.ZINC, CMaterials.INGOT, 9), CMaterials.getOD(CMaterials.ALUMINIUM, CMaterials.INGOT)), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.ZINCALMINIUM_ALLOY, CMaterials.INGOT, 10)), CRecipes.e(6), 50L);
        recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.ZINC, CMaterials.INGOT, 9), CMaterials.getOD(CMaterials.ALUMINIUM_OD, CMaterials.INGOT)), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.ZINCALMINIUM_ALLOY, CMaterials.INGOT, 10)), CRecipes.e(6), 50L);
        recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.MAGNESIUM, CMaterials.INGOT, 9), CMaterials.get(CMaterials.ZINCALMINIUM_ALLOY, CMaterials.INGOT)), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.AZ91D_ALLOY, CMaterials.INGOT, 10)), CRecipes.e(7), 500L);
        recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.ZINC, CMaterials.INGOT, 9), CMaterials.getOD(CMaterials.ZIRCONIUM, CMaterials.INGOT)), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.ZINCONIUM_ALLOY, CMaterials.INGOT, 10)), CRecipes.e(3.0, 7), 50L);
        recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.MAGNESIUM, CMaterials.INGOT, 19), CMaterials.get(CMaterials.ZINCONIUM_ALLOY, CMaterials.INGOT)), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.ZK60A_ALLOY, CMaterials.INGOT, 20)), CRecipes.e(3.0, 7), 500L);
        shapes = new CShape[]{CMaterials.INGOT, CMaterials.DUST};
        for (CShape shape1 : shapes) {
            for (CShape shape2 : shapes) {
                recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.COPPER, shape1, 3), CMaterials.getOD(CMaterials.TIN, shape2)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.BRONZE, CMaterials.INGOT, 4)), CRecipes.e(4), 40L);
                recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.COPPER, shape1, 3), CMaterials.getOD(CMaterials.ZINC, shape2)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.BRASS, CMaterials.INGOT, 4)), CRecipes.e(4), 40L);
                recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.GOLD, shape1), CMaterials.getOD(CMaterials.SILVER, shape2)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.ELECTRUM, CMaterials.INGOT, 2)), CRecipes.e(4), 40L);
                recipeAlloySmelter.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.IRON, shape1, 2), CMaterials.getOD(CMaterials.NICKEL, shape2)), 0, 0, CRecipes.ii(CMaterials.get(CMaterials.INVAR, CMaterials.INGOT, 3)), CRecipes.e(4), 40L);
            }
            recipeBlastFurnace.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.IRON, shape1), CRecipes.i(Items.field_151044_h, 2)), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.STEEL, CMaterials.INGOT)), CRecipes.e(6), 500L);
        }
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(CMaterials.IMPURE_ULTIMATE_ALLOY, CMaterials.INGOT, 9), CRecipes.oo(CMaterials.getODName(CMaterials.BARIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.STRONTIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.CALCIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.CLAYIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM, CMaterials.INGOT))));
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(CMaterials.IMPURE_ULTIMATE_ALLOY, CMaterials.INGOT, 9), CRecipes.oo(CMaterials.getODName(CMaterials.BARIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.STRONTIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.CALCIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.CLAYIUM, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM_OD, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM_OD, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM_OD, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM_OD, CMaterials.INGOT), CMaterials.getODName(CMaterials.ALUMINIUM_OD, CMaterials.INGOT))));
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150348_b), CRecipes.i(Blocks.field_150347_e), 1L, 3L);
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150347_e, ClayiumCore.multiplyProgressionRateStackSize(1)), CRecipes.i(Blocks.field_150351_n, ClayiumCore.multiplyProgressionRateStackSize(1)), 1L, 10L);
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150347_e, ClayiumCore.multiplyProgressionRateStackSize(4)), CRecipes.i(Blocks.field_150351_n, ClayiumCore.multiplyProgressionRateStackSize(4)), 1L, 10L);
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150347_e, ClayiumCore.multiplyProgressionRateStackSize(16)), CRecipes.i(Blocks.field_150351_n, ClayiumCore.multiplyProgressionRateStackSize(16)), 1L, 10L);
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150347_e, ClayiumCore.multiplyProgressionRateStackSize(64)), CRecipes.i(Blocks.field_150351_n, ClayiumCore.multiplyProgressionRateStackSize(64)), 1L, 10L);
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150351_n), CRecipes.i((Block)Blocks.field_150354_m), 10L, 10L);
        recipeCentrifuge.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150351_n, ClayiumCore.multiplyProgressionRateStackSize(1))), 0, 4, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK, ClayiumCore.multiplyProgressionRateStackSize(1))), 1L, 2L);
        recipeCentrifuge.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150351_n, ClayiumCore.multiplyProgressionRateStackSize(4))), 0, 4, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK, ClayiumCore.multiplyProgressionRateStackSize(4))), 2L, 2L);
        recipeCentrifuge.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150351_n, ClayiumCore.multiplyProgressionRateStackSize(16))), 0, 5, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK, ClayiumCore.multiplyProgressionRateStackSize(16))), 4L, 1L);
        recipeCentrifuge.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150351_n, ClayiumCore.multiplyProgressionRateStackSize(64))), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.DENSE_CLAY, CMaterials.BLOCK, ClayiumCore.multiplyProgressionRateStackSize(64))), 8L, 1L);
        recipeReactor.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.ORG_CLAY, CMaterials.DUST), CRecipes.i(Blocks.field_150351_n)), 0, 7, CRecipes.ii(CRecipes.i(Blocks.field_150346_d)), CRecipes.e(7), 100L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150351_n), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Blocks.field_150346_d)), CRecipes.e(2.0, 10), 60L);
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150322_A, 1, Short.MAX_VALUE), 3, CRecipes.i((Block)Blocks.field_150354_m, 4), CRecipes.e(3), 100L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i((Block)Blocks.field_150354_m), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i((Block)Blocks.field_150354_m, 1, 1)), CRecipes.e(2.0, 10), 60L);
        recipeChemicalReactor.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.ENG_CLAY, CMaterials.DUST)), 0, 5, CRecipes.ii(CMaterials.get(CMaterials.IMPURE_REDSTONE, CMaterials.DUST), CMaterials.get(CMaterials.IMPURE_GLOWSTONE, CMaterials.DUST)), CRecipes.e(5), 10L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.IMPURE_REDSTONE, CMaterials.DUST)), 0, 7, CRecipes.ii(CRecipes.i(Items.field_151137_ax)), CRecipes.e(0.1, 7), 2000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.IMPURE_GLOWSTONE, CMaterials.DUST)), 0, 7, CRecipes.ii(CRecipes.i(Items.field_151114_aO)), CRecipes.e(0.1, 7), 2000L);
        recipeCAInjector.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.REDSTONE, CMaterials.DUST), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Blocks.field_150343_Z)), CRecipes.e(2.0, 10), 60L);
        recipeCondenser.addRecipe(CMaterials.getOD(CMaterials.COAL, CMaterials.BLOCK, 8), 5, CRecipes.i(Items.field_151045_i), CRecipes.e(5), 100L);
        recipeReactor.addRecipe(CRecipes.oo(CRecipes.i(Items.field_151044_h, 64), CMaterials.get(CMaterials.IND_CLAY, CMaterials.DUST)), 0, 7, CRecipes.ii(CRecipes.i(Items.field_151045_i)), CRecipes.e(7), 10000L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.get(CMaterials.ORG_CLAY, CMaterials.DUST), CMaterials.getOD(CMaterials.POTASSIUM, CMaterials.DUST)), 0, 8, CRecipes.ii(CMaterials.get(CMaterials.SALTPETER, CMaterials.DUST)), CRecipes.e(8), 10000L);
        GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CRecipes.i(Items.field_151016_H, 2), CRecipes.oo(CMaterials.getODName(CMaterials.SALTPETER, CMaterials.DUST), CMaterials.getODName(CMaterials.SALTPETER, CMaterials.DUST), CMaterials.getODName(CMaterials.SULFUR, CMaterials.DUST), CMaterials.getODName(CMaterials.CHARCOAL, CMaterials.DUST))));
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150325_L, 1, Short.MAX_VALUE), 5, CRecipes.i(Items.field_151007_F, 4), CRecipes.e(5), 100L);
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150404_cg, 1, Short.MAX_VALUE), 5, CRecipes.i(Items.field_151007_F, 2), CRecipes.e(5), 100L);
        recipeGrinder.addRecipe(CRecipes.i(Items.field_151072_bj), 5, CRecipes.i(Items.field_151065_br, 5), CRecipes.e(5), 100L);
        recipeGrinder.addRecipe(CRecipes.i(Items.field_151103_aS), 5, CRecipes.i(Items.field_151100_aR, 5, 15), CRecipes.e(5), 100L);
        recipeCentrifuge.addRecipe(CRecipes.ii(CRecipes.i(CBlocks.blockClayTreeLog)), 0, 6, CRecipes.ii(CMaterials.get(CMaterials.ADVIND_CLAY, CMaterials.DUST, 16), CMaterials.get(CMaterials.MANGANESE, CMaterials.DUST, 5), CMaterials.get(CMaterials.LITHIUM, CMaterials.DUST, 3), CMaterials.get(CMaterials.ZIRCONIUM, CMaterials.DUST, 1)), 10000L, 400L);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150417_aV), CRecipes.i(Blocks.field_150395_bd)), 0, 6, CRecipes.ii(CRecipes.i(Blocks.field_150417_aV, 1, 1)), CRecipes.e(6), 20L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.ULTIMATE_ALLOY, CMaterials.DUST, 4), CRecipes.i(Items.field_151069_bo)), 0, 11, CRecipes.ii(CRecipes.i(Items.field_151062_by)), CRecipes.e(11), 100000000000000L);
        recipeReactor.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151174_bG)), 0, 11, CRecipes.ii(CRecipes.i(Items.field_151170_bI)), CRecipes.e(9), 10000000000L);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_ALUMINIUM, CMaterials.DUST), 200);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_MAGNESIUM, CMaterials.DUST), 60);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_SODIUM, CMaterials.DUST), 40);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_LITHIUM, CMaterials.DUST), 7);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_ZIRCONIUM, CMaterials.DUST), 5);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_ZINC, CMaterials.DUST), 10);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_MANGANESE, CMaterials.DUST), 80);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_CALCIUM, CMaterials.DUST), 20);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_POTASSIUM, CMaterials.DUST), 15);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_NICKEL, CMaterials.DUST), 13);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_IRON, CMaterials.DUST), 9);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_BERYLLIUM, CMaterials.DUST), 8);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_LEAD, CMaterials.DUST), 6);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_HAFNIUM, CMaterials.DUST), 4);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_CHROME, CMaterials.DUST), 3);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_TITANIUM, CMaterials.DUST), 3);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_STRONTIUM, CMaterials.DUST), 2);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_BARIUM, CMaterials.DUST), 2);
        TileChemicalMetalSeparator.results.add(CMaterials.get(CMaterials.IMPURE_COPPER, CMaterials.DUST), 1);
        for (CMaterial[] cMaterialArray : materialmaps = new CMaterial[][]{{CMaterials.IMPURE_ALUMINIUM, CMaterials.ALUMINIUM}, {CMaterials.IMPURE_MAGNESIUM, CMaterials.MAGNESIUM}, {CMaterials.IMPURE_SODIUM, CMaterials.SODIUM}, {CMaterials.IMPURE_LITHIUM, CMaterials.LITHIUM}, {CMaterials.IMPURE_ZIRCONIUM, CMaterials.ZIRCONIUM}, {CMaterials.IMPURE_ZINC, CMaterials.ZINC}}) {
            recipeElectrolysisReactor.addRecipe(CMaterials.get(cMaterialArray[0], CMaterials.DUST), 6, CMaterials.get(cMaterialArray[1], CMaterials.DUST), CRecipes.e(6), 100L);
        }
        for (CMaterial[] cMaterialArray : materialmaps = new CMaterial[][]{{CMaterials.IMPURE_MANGANESE, CMaterials.MANGANESE}, {CMaterials.IMPURE_POTASSIUM, CMaterials.POTASSIUM}, {CMaterials.IMPURE_HAFNIUM, CMaterials.HAFNIUM}, {CMaterials.IMPURE_STRONTIUM, CMaterials.STRONTIUM}, {CMaterials.IMPURE_BARIUM, CMaterials.BARIUM}, {CMaterials.IMPURE_CALCIUM, CMaterials.CALCIUM}}) {
            recipeElectrolysisReactor.addRecipe(CMaterials.get(cMaterialArray[0], CMaterials.DUST), 7, CMaterials.get(cMaterialArray[1], CMaterials.DUST), CRecipes.e(10.0, 7), 300L);
        }
        for (CMaterial[] cMaterialArray : materialmaps = new CMaterial[][]{{CMaterials.IMPURE_IRON, CMaterials.IRON}, {CMaterials.IMPURE_LEAD, CMaterials.LEAD}, {CMaterials.IMPURE_COPPER, CMaterials.COPPER}}) {
            recipeElectrolysisReactor.addRecipe(CMaterials.get(cMaterialArray[0], CMaterials.DUST), 8, CMaterials.get(cMaterialArray[1], CMaterials.DUST), CRecipes.e(10.0, 8), 1000L);
        }
        for (CMaterial[] cMaterialArray : materialmaps = new CMaterial[][]{{CMaterials.IMPURE_NICKEL, CMaterials.NICKEL}, {CMaterials.IMPURE_BERYLLIUM, CMaterials.BERYLLIUM}, {CMaterials.IMPURE_CHROME, CMaterials.CHROME}, {CMaterials.IMPURE_TITANIUM, CMaterials.TITANIUM}}) {
            recipeElectrolysisReactor.addRecipe(CMaterials.get(cMaterialArray[0], CMaterials.DUST), 9, CMaterials.get(cMaterialArray[1], CMaterials.DUST), CRecipes.e(10.0, 9), 3000L);
        }
        recipeCAInjector.addRecipe(CRecipes.ii(CMaterials.get(CMaterials.ORG_CLAY, CMaterials.DUST), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151014_N)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151014_N), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i((Block)Blocks.field_150327_N)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i((Block)Blocks.field_150327_N), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151034_e)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151034_e), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151120_aE)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151120_aE), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Blocks.field_150345_g)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150345_g), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i((Block)Blocks.field_150362_t)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i((Block)Blocks.field_150362_t), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Blocks.field_150364_r)), CRecipes.e(2.0, 10), 60L);
        if (CMaterials.existOD("itemRawRubber")) {
            recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Blocks.field_150364_r), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CMaterials.getODExist("itemRawRubber")), CRecipes.e(2.0, 10), 60L);
        }
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i((Block)Blocks.field_150349_c), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i((Block)Blocks.field_150329_H, 1, 1)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i((Block)Blocks.field_150391_bh), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i((Block)Blocks.field_150338_P, 1, 1)), CRecipes.e(2.0, 10), 60L);
        recipeReactor.addRecipe(CRecipes.oo(CMaterials.getOD(CMaterials.CARBON, CMaterials.DUST), CMaterials.get(CMaterials.ORG_CLAY, CMaterials.DUST)), 0, 11, CRecipes.ii(CRecipes.i(Items.field_151078_bh)), CRecipes.e(11), 100000000000000L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151078_bh), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151116_aA)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151116_aA), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151103_aS)), CRecipes.e(2.0, 10), 60L);
        recipeCAInjector.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151103_aS), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151123_aH)), CRecipes.e(2.0, 10), 60L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.LITHIUM, CMaterials.INGOT), 7, CMaterials.get(CMaterials.SODIUM, CMaterials.INGOT), CRecipes.e(10.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.SODIUM, CMaterials.INGOT), 7, CMaterials.get(CMaterials.POTASSIUM, CMaterials.INGOT), CRecipes.e(30.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.POTASSIUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.RUBIDIUM, CMaterials.INGOT), CRecipes.e(10.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.RUBIDIUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.CAESIUM, CMaterials.INGOT), CRecipes.e(20.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.CAESIUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.FRANCIUM, CMaterials.INGOT), CRecipes.e(30.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.FRANCIUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.RADIUM, CMaterials.INGOT), CRecipes.e(50.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.RADIUM, CMaterials.INGOT), 9, CMaterials.get(CMaterials.ACTINIUM, CMaterials.INGOT), CRecipes.e(10.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.ACTINIUM, CMaterials.INGOT), 9, CMaterials.get(CMaterials.THORIUM, CMaterials.INGOT), CRecipes.e(20.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.THORIUM, CMaterials.INGOT), 9, CMaterials.get(CMaterials.PROTACTINIUM, CMaterials.INGOT), CRecipes.e(30.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.PROTACTINIUM, CMaterials.INGOT), 9, CMaterials.get(CMaterials.URANIUM, CMaterials.INGOT), CRecipes.e(50.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.URANIUM, CMaterials.INGOT), 9, CMaterials.get(CMaterials.NEPTUNIUM, CMaterials.INGOT), CRecipes.e(80.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.NEPTUNIUM, CMaterials.INGOT), 10, CMaterials.get(CMaterials.PLUTONIUM, CMaterials.INGOT), CRecipes.e(20.0, 10), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.PLUTONIUM, CMaterials.INGOT), 11, CMaterials.get(CMaterials.AMERICIUM, CMaterials.INGOT), CRecipes.e(30.0, 11), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.AMERICIUM, CMaterials.INGOT), 12, CMaterials.get(CMaterials.CURIUM, CMaterials.INGOT), CRecipes.e(50.0, 12), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.BERYLLIUM, CMaterials.INGOT), 7, CMaterials.get(CMaterials.MAGNESIUM, CMaterials.INGOT), CRecipes.e(10.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.MAGNESIUM, CMaterials.INGOT), 7, CMaterials.get(CMaterials.CALCIUM, CMaterials.INGOT), CRecipes.e(20.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.CALCIUM, CMaterials.INGOT), 7, CMaterials.get(CMaterials.STRONTIUM, CMaterials.INGOT), CRecipes.e(30.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.STRONTIUM, CMaterials.INGOT), 7, CMaterials.get(CMaterials.BARIUM, CMaterials.INGOT), CRecipes.e(50.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.BARIUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.LANTHANUM, CMaterials.INGOT), CRecipes.e(10.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.LANTHANUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.CERIUM, CMaterials.INGOT), CRecipes.e(30.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.CERIUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.PRASEODYMIUM, CMaterials.INGOT), CRecipes.e(90.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.PRASEODYMIUM, CMaterials.INGOT), 9, CMaterials.get(CMaterials.NEODYMIUM, CMaterials.INGOT), CRecipes.e(20.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.NEODYMIUM, CMaterials.INGOT), 10, CMaterials.get(CMaterials.PROMETHIUM, CMaterials.INGOT), CRecipes.e(10.0, 10), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.PROMETHIUM, CMaterials.INGOT), 11, CMaterials.get(CMaterials.SAMARIUM, CMaterials.INGOT), CRecipes.e(20.0, 11), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.SAMARIUM, CMaterials.INGOT), 12, CMaterials.get(CMaterials.EUROPIUM, CMaterials.INGOT), CRecipes.e(60.0, 12), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.ZIRCONIUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.TITANIUM, CMaterials.INGOT), CRecipes.e(60.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.TITANIUM, CMaterials.INGOT), 9, CMaterials.get(CMaterials.VANADIUM, CMaterials.INGOT), CRecipes.e(60.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.MANGANESE, CMaterials.INGOT), 7, CMaterials.get(CMaterials.IRON, CMaterials.INGOT), CRecipes.e(90.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.IRON, CMaterials.INGOT), 8, CMaterials.get(CMaterials.COBALT, CMaterials.INGOT), CRecipes.e(30.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.COBALT, CMaterials.INGOT), 8, CMaterials.get(CMaterials.NICKEL, CMaterials.INGOT), CRecipes.e(90.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.NICKEL, CMaterials.INGOT), 9, CMaterials.get(CMaterials.PALLADIUM, CMaterials.INGOT), CRecipes.e(40.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.ZINC, CMaterials.INGOT), 8, CMaterials.get(CMaterials.COPPER, CMaterials.INGOT), CRecipes.e(20.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.COPPER, CMaterials.INGOT), 9, CMaterials.get(CMaterials.SILVER, CMaterials.INGOT), CRecipes.e(10.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.SILVER, CMaterials.INGOT), 9, CMaterials.get(CMaterials.GOLD, CMaterials.INGOT), CRecipes.e(50.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.GOLD, CMaterials.INGOT), 10, CMaterials.get(CMaterials.PLATINUM, CMaterials.INGOT), CRecipes.e(30.0, 10), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.PLATINUM, CMaterials.INGOT), 11, CMaterials.get(CMaterials.IRIDIUM, CMaterials.INGOT), CRecipes.e(10.0, 11), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.IRIDIUM, CMaterials.INGOT), 11, CMaterials.get(CMaterials.MAIN_OSMIUM, CMaterials.INGOT), CRecipes.e(30.0, 11), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.MAIN_OSMIUM, CMaterials.INGOT), 12, CMaterials.get(CMaterials.RHENIUM, CMaterials.INGOT), CRecipes.e(10.0, 12), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.HAFNIUM, CMaterials.INGOT), 8, CMaterials.get(CMaterials.TANTALUM, CMaterials.INGOT), CRecipes.e(70.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.TANTALUM, CMaterials.INGOT), 9, CMaterials.get(CMaterials.TUNGSTEN, CMaterials.INGOT), CRecipes.e(40.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.TUNGSTEN, CMaterials.INGOT), 10, CMaterials.get(CMaterials.MOLYBDENUM, CMaterials.INGOT), CRecipes.e(20.0, 10), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.MOLYBDENUM, CMaterials.INGOT), 11, CMaterials.get(CMaterials.CHROME, CMaterials.INGOT), CRecipes.e(10.0, 11), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.LEAD, CMaterials.INGOT), 7, CMaterials.get(CMaterials.TIN, CMaterials.INGOT), CRecipes.e(50.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.TIN, CMaterials.INGOT), 8, CMaterials.get(CMaterials.ANTIMONY, CMaterials.INGOT), CRecipes.e(20.0, 8), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.ANTIMONY, CMaterials.INGOT), 9, CMaterials.get(CMaterials.BISMUTH, CMaterials.INGOT), CRecipes.e(10.0, 9), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.SILICON, CMaterials.DUST), 7, CMaterials.get(CMaterials.PHOSPHORUS, CMaterials.DUST), CRecipes.e(10.0, 7), 200L);
        recipeTransformer.addRecipe(CMaterials.getOD(CMaterials.PHOSPHORUS, CMaterials.DUST), 7, CMaterials.get(CMaterials.SULFUR, CMaterials.DUST), CRecipes.e(30.0, 7), 200L);
        CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.IND_CLAY, CMaterials.CARBON, CMaterials.GRAPHITE, CMaterials.CHARCOAL, CMaterials.COAL, CMaterials.LAPIS, CMaterials.LAZURITE, CMaterials.SODALITE, CMaterials.MONAZITE}, CMaterials.DUST, new int[]{8, 8, 8, 4, 4, 4, 4, 4, 1}, new int[]{0, 7, 8, 9, 10, 10, 10, 10, 11}, 200);
        CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.DIAMOND, CMaterials.AMBER, CMaterials.AMETHYST, CMaterials.PERIDOT, CMaterials.SAPPHIRE, CMaterials.RUBY, CMaterials.EMERALD}, CMaterials.GEM, new int[]{0, 10, 10, 10, 10, 10, 11}, 200);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Blocks.field_150347_e), CRecipes.i(Blocks.field_150424_aL), CRecipes.i(Blocks.field_150377_bs)), new int[]{0, 9, 11}, 20);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Blocks.field_150417_aV), CRecipes.i(Blocks.field_150417_aV, 1, 2), CRecipes.i(Blocks.field_150417_aV, 1, 3)), new int[]{0, 8, 8}, 20);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Blocks.field_150346_d), CRecipes.i(Blocks.field_150346_d, 1, 2), CRecipes.i((Block)Blocks.field_150349_c), CRecipes.i((Block)Blocks.field_150391_bh), CRecipes.i(Blocks.field_150425_aM)), new int[]{0, 7, 8, 9, 10}, 20);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Blocks.field_150364_r), CRecipes.i(Blocks.field_150364_r, 1, 1), CRecipes.i(Blocks.field_150364_r, 1, 2), CRecipes.i(Blocks.field_150364_r, 1, 3), CRecipes.i(Blocks.field_150363_s), CRecipes.i(Blocks.field_150363_s, 1, 1)), new int[]{0, 7, 7, 8, 8, 8}, 80);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i((Block)Blocks.field_150362_t), CRecipes.i((Block)Blocks.field_150362_t, 1, 1), CRecipes.i((Block)Blocks.field_150362_t, 1, 2), CRecipes.i((Block)Blocks.field_150362_t, 1, 3), CRecipes.i((Block)Blocks.field_150361_u), CRecipes.i((Block)Blocks.field_150361_u, 1, 1)), new int[]{0, 7, 7, 8, 8, 8}, 20);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Blocks.field_150345_g), CRecipes.i(Blocks.field_150345_g, 1, 1), CRecipes.i(Blocks.field_150345_g, 1, 2), CRecipes.i(Blocks.field_150345_g, 1, 3), CRecipes.i(Blocks.field_150345_g, 1, 4), CRecipes.i(Blocks.field_150345_g, 1, 5)), new int[]{0, 7, 7, 8, 8, 8}, 20);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Items.field_151014_N), CRecipes.i(Items.field_151080_bb), CRecipes.i(Items.field_151081_bc), CRecipes.i(Items.field_151100_aR, 1, 3), CRecipes.i(Items.field_151075_bm)), new int[]{0, 8, 8, 8, 8}, 20);
        CRecipes.register2to1Recipe(recipeReactor, "dyeLime", CRecipes.i(Items.field_151100_aR, 1, 3), 8, CMaterials.getODExist("edamame"), 1000000000L);
        CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151081_bc), CRecipes.i(Items.field_151100_aR, 1, 3), 8, CMaterials.getODExist("soybeans"), 1000000000L);
        CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151081_bc), CRecipes.i(Items.field_151007_F, 3), 8, CMaterials.getODExist("seedCotton"), 1000000000L);
        CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151014_N), CRecipes.i(Items.field_151100_aR, 1, 3), 10, CMaterials.getODExist("cropCoffee"), 100000000000000L);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Items.field_151015_O), CRecipes.i(Items.field_151172_bF), CRecipes.i(Items.field_151174_bG)), new int[]{0, 8, 8}, 20);
        recipeReactor.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151014_N)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151015_O)), CRecipes.e(8), 2000000000000000L);
        recipeReactor.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151080_bb)), 0, 10, CRecipes.ii(CRecipes.i(Blocks.field_150423_aK)), CRecipes.e(8), 2000000000000000L);
        recipeReactor.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151081_bc)), 0, 10, CRecipes.ii(CRecipes.i(Blocks.field_150440_ba)), CRecipes.e(8), 2000000000000000L);
        CRecipes.register1to1Recipe(recipeReactor, "seedCotton", 8, CMaterials.getODExist("cropCotton"), 2000000000000000L);
        recipeGrinder.addRecipe(CRecipes.i(Blocks.field_150440_ba), 5, CRecipes.i(Items.field_151127_ba, 9), CRecipes.e(5), 100L);
        if (CMaterials.existOD("flour")) {
            CRecipes.register1to1Recipe(recipeGrinder, CRecipes.i(Items.field_151015_O), 5, CMaterials.getODExist("flour"), 60L);
        } else if (CMaterials.existOD("itemFlour")) {
            CRecipes.register1to1Recipe(recipeGrinder, CRecipes.i(Items.field_151015_O), 5, CMaterials.getODExist("itemFlour"), 60L);
        } else if (CMaterials.existOD("dustFlour")) {
            CRecipes.register1to1Recipe(recipeGrinder, CRecipes.i(Items.field_151015_O), 5, CMaterials.getODExist("dustFlour"), 60L);
        }
        CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151015_O), CRecipes.i(Items.field_151014_N), 8, CMaterials.getODExist("cropRice"), 1000000000L);
        CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151015_O), CRecipes.i((Block)Blocks.field_150329_H, 1, 1), 8, CMaterials.getODExist("cropStraw"), 1000000000L);
        CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151034_e), "dyeRed", 8, CMaterials.getODExist("apricot"), 1000000000L);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i((Block)Blocks.field_150329_H, 1, 1), CRecipes.i((Block)Blocks.field_150329_H, 1, 2), CRecipes.i((Block)Blocks.field_150330_I), CRecipes.i(Blocks.field_150395_bd), CRecipes.i(Blocks.field_150392_bi)), new int[]{0, 7, 7, 8, 9}, 20);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i((Block)Blocks.field_150327_N), CRecipes.i((Block)Blocks.field_150328_O), CRecipes.i((Block)Blocks.field_150328_O, 1, 1), CRecipes.i((Block)Blocks.field_150328_O, 1, 2), CRecipes.i((Block)Blocks.field_150328_O, 1, 3), CRecipes.i((Block)Blocks.field_150328_O, 1, 4), CRecipes.i((Block)Blocks.field_150328_O, 1, 5), CRecipes.i((Block)Blocks.field_150328_O, 1, 6), CRecipes.i((Block)Blocks.field_150328_O, 1, 7), CRecipes.i((Block)Blocks.field_150328_O, 1, 8), CRecipes.i((Block)Blocks.field_150398_cm), CRecipes.i((Block)Blocks.field_150398_cm, 1, 1), CRecipes.i((Block)Blocks.field_150398_cm, 1, 2), CRecipes.i((Block)Blocks.field_150398_cm, 1, 3), CRecipes.i((Block)Blocks.field_150398_cm, 1, 4), CRecipes.i((Block)Blocks.field_150398_cm, 1, 5)), new int[]{0, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8}, 20);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Items.field_151120_aE), CRecipes.i(Blocks.field_150434_aF)), new int[]{0, 8}, 20);
        CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151120_aE), "logWood", 8, CMaterials.getODExist("bamboo"), 1000000000L);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Items.field_151078_bh), CRecipes.i(Items.field_151147_al), CRecipes.i(Items.field_151082_bd), CRecipes.i(Items.field_151076_bf)), new int[]{64, 2, 2, 1}, new int[]{0, 9, 9, 9}, 200);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Items.field_151116_aA), CRecipes.i(Blocks.field_150325_L), CRecipes.i(Items.field_151008_G)), new int[]{1, 4, 16}, new int[]{0, 9, 9}, 80);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151116_aA, 4), CRecipes.i(Items.field_151007_F, 16)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151141_av)), CRecipes.e(10), 6000L);
        recipeAssembler.addRecipe(CRecipes.ii(CRecipes.i(Items.field_151121_aF, 2), CRecipes.i(Items.field_151007_F, 4)), 0, 10, CRecipes.ii(CRecipes.i(Items.field_151057_cb)), CRecipes.e(10), 600L);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Items.field_151103_aS), CRecipes.i(Items.field_151072_bj), CRecipes.i(Items.field_151079_bi), CRecipes.i(Items.field_151156_bN), CRecipes.i(Blocks.field_150380_bt)), new int[]{262144, 4096, 1024, 64, 1}, new int[]{0, 9, 9, 12, 12}, 200);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Items.field_151123_aH), CRecipes.i(Items.field_151110_aK), CRecipes.i(Items.field_151100_aR), CRecipes.i(Items.field_151070_bp), CRecipes.i(Items.field_151073_bk)), new int[]{0, 8, 8, 9, 10}, 100);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Items.field_151144_bL, 1, 1), CRecipes.i(Items.field_151144_bL), CRecipes.i(Items.field_151144_bL, 1, 2), CRecipes.i(Items.field_151144_bL, 1, 4), CRecipes.i(Items.field_151144_bL, 1, 3)), new int[]{0, 11, 11, 11, 12}, 1000);
        CRecipes.registerStackChainRecipes(recipeTransformer, CRecipes.ii(CRecipes.i(Blocks.field_150351_n), CRecipes.i(Items.field_151145_ak), CMaterials.getODExist(CMaterials.CINNABAR, CMaterials.CRYSTAL)), new int[]{0, 7, 10}, 1000);
        if (ClayiumCore.IntegrationID.EIO.enabled()) {
            CRecipes.register2to1Recipe(recipeAlloySmelter, CRecipes.i(Items.field_151137_ax), "itemSilicon", 6, CMaterials.getODExist(CMaterials.REDSTONE_ALLOY, CMaterials.INGOT), 100L);
            CRecipes.register2to1Recipe(recipeAlloySmelter, CRecipes.i(Items.field_151137_ax), CMaterials.getOD(CMaterials.IRON, CMaterials.INGOT), 6, CMaterials.getODExist(CMaterials.CONDUCTIVE_IRON, CMaterials.INGOT), 100L);
            CRecipes.register2to1Recipe(recipeAlloySmelter, CRecipes.i(Items.field_151137_ax), CMaterials.getOD(CMaterials.IRON, CMaterials.DUST), 6, CMaterials.getODExist(CMaterials.CONDUCTIVE_IRON, CMaterials.INGOT), 100L);
            CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151137_ax), CMaterials.getOD(CMaterials.GOLD, CMaterials.INGOT), 8, CMaterials.getODExist(CMaterials.ENERGETIC_ALLOY, CMaterials.INGOT), 1000000000L);
            CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151137_ax), CMaterials.getOD(CMaterials.GOLD, CMaterials.DUST), 8, CMaterials.getODExist(CMaterials.ENERGETIC_ALLOY, CMaterials.INGOT), 1000000000L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.STEEL, CMaterials.INGOT), "itemSilicon", 7, CMaterials.getODExist(CMaterials.ELECTRICAL_STEEL, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.STEEL, CMaterials.DUST), "itemSilicon", 7, CMaterials.getODExist(CMaterials.ELECTRICAL_STEEL, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.STEEL, CMaterials.INGOT), CRecipes.i(Blocks.field_150343_Z), 7, CMaterials.getODExist(CMaterials.DARK_STEEL, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.STEEL, CMaterials.DUST), CRecipes.i(Blocks.field_150343_Z), 7, CMaterials.getODExist(CMaterials.DARK_STEEL, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.IRON, CMaterials.INGOT), CRecipes.i(Items.field_151079_bi), 6, CMaterials.getODExist(CMaterials.PHASED_IRON, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.IRON, CMaterials.DUST), CRecipes.i(Items.field_151079_bi), 6, CMaterials.getODExist(CMaterials.PHASED_IRON, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.ENERGETIC_ALLOY, CMaterials.INGOT), CRecipes.i(Items.field_151079_bi), 6, CMaterials.getODExist(CMaterials.PHASED_GOLD, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.ENERGETIC_ALLOY, CMaterials.DUST), CRecipes.i(Items.field_151079_bi), 6, CMaterials.getODExist(CMaterials.PHASED_GOLD, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.GOLD, CMaterials.INGOT), CRecipes.i(Blocks.field_150425_aM), 6, CMaterials.getODExist(CMaterials.SOULARIUM, CMaterials.INGOT), 500L);
            CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.GOLD, CMaterials.DUST), CRecipes.i(Blocks.field_150425_aM), 6, CMaterials.getODExist(CMaterials.SOULARIUM, CMaterials.INGOT), 500L);
        }
        if (ClayiumCore.IntegrationID.TF.enabled()) {
            for (CMaterial[] cMaterialArray : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                for (CShape shape2 : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                    CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.COPPER, (CShape)cMaterialArray, 3), CMaterials.getOD(CMaterials.SILVER, shape2), 8, CMaterials.getODExist(CMaterials.SIGNALUM, CMaterials.INGOT, 4), 1000000000L);
                    CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.TIN, (CShape)cMaterialArray, 3), CMaterials.getOD(CMaterials.SILVER, shape2), 8, CMaterials.getODExist(CMaterials.LUMIUM, CMaterials.INGOT, 4), 1000000000L);
                }
                CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ELECTRUM, (CShape)cMaterialArray), CRecipes.i(Items.field_151137_ax, 2), 6, CMaterials.getODExist(CMaterials.ELECTRUM_FLUX, CMaterials.INGOT), 100L);
            }
            CRecipes.register2to1Recipe(recipeReactor, CRecipes.i(Items.field_151045_i), CRecipes.i(Items.field_151137_ax, 2), 7, CMaterials.getODExist(CMaterials.CRYSTAL_FLUX, CMaterials.GEM), 10000L);
        }
        if (ClayiumCore.IntegrationID.FFM.enabled()) {
            CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.PHOSPHORUS, CMaterials.DUST), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.APATITE, CMaterials.GEM), 60L);
            CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.PHOSPHORUS, CMaterials.GEM), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.APATITE, CMaterials.GEM), 60L);
        }
        if (ClayiumCore.IntegrationID.AE2.enabled()) {
            CRecipes.register1to1Recipe(recipeTransformer, CRecipes.i(Items.field_151128_bU), 10, CMaterials.getODExist(CMaterials.CERTUS_QUARTZ, CMaterials.CRYSTAL), 60L);
        }
        if (ClayiumCore.IntegrationID.TIC.enabled()) {
            CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.COBALT, CMaterials.INGOT), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.ARDITE, CMaterials.INGOT), 60L);
            for (CMaterial[] cMaterialArray : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                for (CShape shape2 : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ALUMINIUM, (CShape)cMaterialArray, 3), CMaterials.getOD(CMaterials.COPPER, shape2), 6, CMaterials.getODExist(CMaterials.ALUMINUM_BRASS, CMaterials.INGOT, 4), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ALUMINIUM_OD, (CShape)cMaterialArray, 3), CMaterials.getOD(CMaterials.COPPER, shape2), 6, CMaterials.getODExist(CMaterials.ALUMINUM_BRASS, CMaterials.INGOT, 4), 100L);
                    CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.ALUMINIUM, (CShape)cMaterialArray, 5), CMaterials.getOD(CMaterials.DARK_STEEL, shape2, 2), 8, CMaterials.getODExist(CMaterials.ALUMITE, CMaterials.INGOT, 3), 1000000000L);
                    CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.ALUMINIUM_OD, (CShape)cMaterialArray, 5), CMaterials.getOD(CMaterials.DARK_STEEL, shape2, 2), 8, CMaterials.getODExist(CMaterials.ALUMITE, CMaterials.INGOT, 3), 1000000000L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ARDITE, (CShape)cMaterialArray), CMaterials.getOD(CMaterials.COBALT, shape2), 6, CMaterials.getODExist(CMaterials.MANYULLYN, CMaterials.INGOT), 100L);
                    CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.IRON, (CShape)cMaterialArray), CMaterials.getOD(CMaterials.COBALT, shape2), 8, CMaterials.getODExist(CMaterials.POKEFENNIUM, CMaterials.INGOT, 2), 1000000000L);
                }
                CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.IRON, (CShape)cMaterialArray), CMaterials.getOD(CMaterials.EMERALD, CMaterials.GEM, 9), 9, CMaterials.getODExist(CMaterials.PIG_IRON, CMaterials.INGOT), 100000000000L);
                CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.ARDITE, (CShape)cMaterialArray), CRecipes.i(Blocks.field_150343_Z), 8, CMaterials.getODExist(CMaterials.FAIRY, CMaterials.INGOT, 2), 1000000000L);
            }
        }
        if (ClayiumCore.IntegrationID.PR_EX.enabled()) {
            for (CMaterial[] cMaterialArray : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                if (ClayiumCore.IntegrationID.GT.loaded() || CMaterials.existOD(CMaterials.CONDUCTIVE_IRON, CMaterials.INGOT)) {
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.COPPER, (CShape)cMaterialArray), CRecipes.i(Items.field_151137_ax, 4), 6, CMaterials.getODExist(CMaterials.RED_ALLOY, CMaterials.INGOT), 100L);
                } else {
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.IRON, (CShape)cMaterialArray), CRecipes.i(Items.field_151137_ax, 8), 6, CMaterials.getODExist(CMaterials.RED_ALLOY, CMaterials.INGOT), 100L);
                }
                CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.IRON, (CShape)cMaterialArray), CMaterials.getOD(CMaterials.ELECTROTINE, CMaterials.DUST, 8), 6, CMaterials.getODExist(CMaterials.ELECTROTINE_ALLOY, CMaterials.INGOT), 100L);
            }
        }
        if (ClayiumCore.IntegrationID.MEK.enabled()) {
            for (CMaterial[] cMaterialArray : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.OSMIUM, (CShape)cMaterialArray), CRecipes.i(Items.field_151114_aO), 6, CMaterials.getODExist(CMaterials.REFINED_GLOWSTONE, CMaterials.INGOT), 100L);
            }
            for (CMaterial[] cMaterialArray : new CShape[]{CMaterials.GEM, CMaterials.DUST}) {
                for (CShape shape2 : new CShape[]{CMaterials.BLOCK, CMaterials.DUST}) {
                    CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.DIAMOND, (CShape)cMaterialArray), CMaterials.getOD(CMaterials.OBSIDIAN, shape2), 8, CMaterials.getODExist(CMaterials.REFINED_OBSIDIAN, CMaterials.INGOT), 1000000000L);
                }
            }
        }
        if (ClayiumCore.IntegrationID.BR.enabled() && CMaterials.existOD(CMaterials.YELLORIUM, CMaterials.INGOT)) {
            boolean flag = true;
            for (ItemStack uranium : OreDictionary.getOres((String)CMaterials.getODName(CMaterials.URANIUM, CMaterials.INGOT))) {
                for (ItemStack yellorium : OreDictionary.getOres((String)CMaterials.getODName(CMaterials.YELLORIUM, CMaterials.INGOT))) {
                    if (!UtilItemStack.areTypeEqual(uranium, yellorium)) continue;
                    flag = false;
                }
            }
            if (flag) {
                CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.URANIUM, CMaterials.INGOT), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.YELLORIUM, CMaterials.INGOT), 60L);
                CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.YELLORIUM, CMaterials.CYANITE, CMaterials.BLUTONIUM, CMaterials.LUDICRITE}, CMaterials.INGOT, new int[]{16, 16, 8, 1}, new int[]{0, 7, 10, 12}, 200);
            } else {
                boolean flag1 = true;
                for (ItemStack itemStack : OreDictionary.getOres((String)CMaterials.getODName(CMaterials.PLUTONIUM, CMaterials.INGOT))) {
                    for (ItemStack blutonium : OreDictionary.getOres((String)CMaterials.getODName(CMaterials.BLUTONIUM, CMaterials.INGOT))) {
                        if (!UtilItemStack.areTypeEqual(itemStack, blutonium)) continue;
                        flag1 = false;
                    }
                }
                if (flag1) {
                    CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.CYANITE, CMaterials.BLUTONIUM, CMaterials.LUDICRITE}, CMaterials.INGOT, new int[]{16, 8, 1}, new int[]{7, 10, 12}, 200);
                } else {
                    CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.PLUTONIUM, CMaterials.INGOT, 8), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM, 64), 12, CMaterials.getODExist(CMaterials.LUDICRITE, CMaterials.INGOT), 200L);
                }
            }
        }
        if (ClayiumCore.IntegrationID.GC.enabled()) {
            CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.NICKEL, CMaterials.INGOT), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.METEORIC_IRON, CMaterials.INGOT), 60L);
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.METEORIC_IRON, CMaterials.DESH}, CMaterials.INGOT, new int[]{0, 11}, 200);
        }
        if (ClayiumCore.IntegrationID.FZ.enabled()) {
            CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.INVAR, CMaterials.INGOT), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.FZ_DARK_IRON, CMaterials.INGOT), 60L);
        }
        if (ClayiumCore.IntegrationID.MISC.enabled()) {
            CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.EMERALD, CMaterials.GEM), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.TOPAZ, CMaterials.GEM), 60L);
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.TOPAZ, CMaterials.MALACHITE, CMaterials.TANZANITE}, CMaterials.GEM, new int[]{0, 10, 10}, 200);
            CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.QUARTZ, CMaterials.GEM), CMaterials.getOD(CMaterials.LITHIUM, CMaterials.DUST), 8, CMaterials.getODExist(CMaterials.DILITHIUM, CMaterials.GEM), 1000000000L);
            CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.QUARTZ, CMaterials.GEM), CRecipes.i(Items.field_151137_ax, 4), 8, CMaterials.getODExist(CMaterials.FORCICIUM, CMaterials.GEM, 4), 1000000000L);
        }
        if (ClayiumCore.IntegrationID.GT.enabled()) {
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.ALUMINIUM, CMaterials.GALLIUM}, CMaterials.INGOT, new int[]{0, 11}, 200);
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.ALUMINIUM_OD, CMaterials.GALLIUM}, CMaterials.INGOT, new int[]{0, 11}, 200);
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.VANADIUM, CMaterials.NIOBIUM, CMaterials.YTTRIUM}, CMaterials.INGOT, new int[]{0, 11, 11}, 200);
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.EUROPIUM, CMaterials.NAQUADAH, CMaterials.NAQUADAH_ENRICHED, CMaterials.NAQUADRIA}, CMaterials.INGOT, new int[]{0, 12, 12, 12}, 200);
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.CURIUM, CMaterials.NEUTRONIUM}, CMaterials.INGOT, new int[]{0, 12}, 200);
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.REDSTONE, CMaterials.NIKOLITE, CMaterials.ELECTROTINE}, CMaterials.DUST, new int[]{0, 9, 9}, 200);
            CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.QUARTZ, CMaterials.GEM), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.QUARTZITE, CMaterials.GEM), 60L);
            CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.SALTPETER, CMaterials.DUST), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.NITER, CMaterials.GEM), 60L);
            for (CShape cShape : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                for (CShape shape2 : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                    CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.TUNGSTEN, cShape), CMaterials.getOD(CMaterials.STEEL, shape2), 10, CMaterials.getODExist(CMaterials.TUNGSTEN_STEEL, CMaterials.INGOT, 2), 1000L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.COPPER, cShape), CMaterials.getOD(CMaterials.NICKEL, shape2), 6, CMaterials.getODExist(CMaterials.CUPRONICKEL, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.NICKEL, cShape, 4), CMaterials.getOD(CMaterials.CHROME, shape2), 9, CMaterials.getODExist(CMaterials.NICHROME, CMaterials.INGOT, 5), 1000L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ALUMINIUM, cShape, 2), CMaterials.getOD(CMaterials.MAGNESIUM, shape2), 6, CMaterials.getODExist(CMaterials.MAGNALIUM, CMaterials.INGOT, 3), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ALUMINIUM_OD, cShape, 2), CMaterials.getOD(CMaterials.MAGNESIUM, shape2), 6, CMaterials.getODExist(CMaterials.MAGNALIUM, CMaterials.INGOT, 3), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.TIN, cShape, 9), CMaterials.getOD(CMaterials.ANTIMONY, shape2), 6, CMaterials.getODExist(CMaterials.SOLDERING_ALLOY, CMaterials.INGOT, 10), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.LEAD, cShape, 4), CMaterials.getOD(CMaterials.ANTIMONY, shape2), 6, CMaterials.getODExist(CMaterials.BATTERY_ALLOY, CMaterials.INGOT, 5), 100L);
                    CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.VANADIUM, cShape, 3), CMaterials.getOD(CMaterials.GALLIUM, shape2), 10, CMaterials.getODExist(CMaterials.VANADIUM_GALLIUM, CMaterials.INGOT, 4), 1000L);
                    CRecipes.register2to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.NIOBIUM, cShape), CMaterials.getOD(CMaterials.TITANIUM, shape2), 9, CMaterials.getODExist(CMaterials.NIOBIUM_TITANIUM, CMaterials.INGOT, 2), 1000L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.TIN, cShape), CMaterials.getOD(CMaterials.IRON, shape2), 6, CMaterials.getODExist(CMaterials.TIN_ALLOY, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.STEEL, cShape), CMaterials.getOD(CMaterials.IRON, shape2, 4), 8, CMaterials.getODExist(CMaterials.WROUGHT_IRON, CMaterials.INGOT, 5), 1000000000L);
                }
                CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.SILVER, cShape), CMaterials.getOD(CMaterials.NIKOLITE, CMaterials.DUST), 6, CMaterials.getODExist(CMaterials.BLUE_ALLOY, CMaterials.INGOT, 2), 100L);
            }
            CRecipes.register1to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.TUNGSTEN_STEEL, CMaterials.DUST), 10, CMaterials.getODExist(CMaterials.TUNGSTEN_STEEL, CMaterials.INGOT), 1000L);
            CRecipes.register1to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.KANTHAL, CMaterials.DUST), 7, CMaterials.getODExist(CMaterials.KANTHAL, CMaterials.INGOT), 1000L);
            CRecipes.register1to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.STAINLESS_STEEL, CMaterials.DUST), 7, CMaterials.getODExist(CMaterials.STAINLESS_STEEL, CMaterials.INGOT), 1000L);
            CRecipes.register1to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.VANADIUM_GALLIUM, CMaterials.DUST), 10, CMaterials.getODExist(CMaterials.VANADIUM_GALLIUM, CMaterials.INGOT), 1000L);
            CRecipes.register1to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.YTTRIUM_BARIUM_CUPRATE, CMaterials.DUST), 6, CMaterials.getODExist(CMaterials.YTTRIUM_BARIUM_CUPRATE, CMaterials.INGOT), 1000L);
            CRecipes.register1to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.NIOBIUM_TITANIUM, CMaterials.DUST), 9, CMaterials.getODExist(CMaterials.NIOBIUM_TITANIUM, CMaterials.INGOT), 1000L);
            CRecipes.register1to1Recipe(recipeBlastFurnace, CMaterials.getOD(CMaterials.ULTIMET, CMaterials.DUST), 9, CMaterials.getODExist(CMaterials.ULTIMET, CMaterials.INGOT), 1000L);
            if (CMaterials.existOD(CMaterials.SAPPHIRE, CMaterials.GEM)) {
                for (CMaterial cMaterial : new CMaterial[]{CMaterials.GREEN_SAPPHIRE, CMaterials.OPAL, CMaterials.JASPER, CMaterials.GARNET_RED, CMaterials.GARNET_YELLOW, CMaterials.FORCE, CMaterials.FORCILLIUM}) {
                    CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.SAPPHIRE, CMaterials.GEM), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(cMaterial, CMaterials.INGOT), 60L);
                    if (!CMaterials.existOD(cMaterial, CMaterials.GEM)) {
                        continue;
                    }
                    break;
                }
            } else {
                for (CMaterial cMaterial : new CMaterial[]{CMaterials.GREEN_SAPPHIRE, CMaterials.OPAL, CMaterials.JASPER, CMaterials.GARNET_RED, CMaterials.GARNET_YELLOW, CMaterials.FORCE, CMaterials.FORCILLIUM}) {
                    CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.DIAMOND, CMaterials.GEM), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(cMaterial, CMaterials.INGOT), 60L);
                    if (!CMaterials.existOD(cMaterial, CMaterials.GEM)) {
                        continue;
                    }
                    break;
                }
            }
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.GREEN_SAPPHIRE, CMaterials.OPAL, CMaterials.JASPER, CMaterials.GARNET_RED, CMaterials.GARNET_YELLOW, CMaterials.FORCE, CMaterials.FORCILLIUM}, CMaterials.GEM, new int[]{0, 8, 8, 9, 9, 11, 11}, 200);
            if (CMaterials.existOD(CMaterials.TOPAZ, CMaterials.GEM)) {
                CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.TOPAZ, CMaterials.GEM), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.BLUE_TOPAZ, CMaterials.GEM), 60L);
            } else {
                CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.EMERALD, CMaterials.GEM), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(CMaterials.BLUE_TOPAZ, CMaterials.GEM), 60L);
            }
        }
        if (ClayiumCore.IntegrationID.MET.enabled()) {
            for (CMaterial cMaterial : new CMaterial[]{CMaterials.PROMETHEUM, CMaterials.DEEP_IRON, CMaterials.INFUSCOLIUM, CMaterials.OURECLASE, CMaterials.AREDRITE, CMaterials.ASTRAL_SILVER, CMaterials.CARMOT, CMaterials.MITHRIL, CMaterials.RUBRACIUM, CMaterials.ORICHALCUM, CMaterials.ADAMANTINE, CMaterials.ATLARUS}) {
                CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.PROMETHIUM, CMaterials.INGOT), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(cMaterial, CMaterials.INGOT), 60L);
                if (CMaterials.existOD(cMaterial, CMaterials.INGOT)) break;
            }
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.PROMETHEUM, CMaterials.DEEP_IRON, CMaterials.INFUSCOLIUM, CMaterials.OURECLASE, CMaterials.AREDRITE, CMaterials.ASTRAL_SILVER, CMaterials.CARMOT, CMaterials.MITHRIL, CMaterials.RUBRACIUM, CMaterials.ORICHALCUM, CMaterials.ADAMANTINE, CMaterials.ATLARUS}, CMaterials.INGOT, new int[]{0, 7, 7, 8, 8, 9, 9, 9, 10, 10, 11, 11}, 200);
            for (CMaterial cMaterial : new CMaterial[]{CMaterials.IGNATIUS, CMaterials.SHADOW_IRON, CMaterials.LEMURITE, CMaterials.MIDASIUM, CMaterials.VYROXERES, CMaterials.CERUCLASE, CMaterials.ALDUORITE, CMaterials.KALENDRITE, CMaterials.VULCANITE, CMaterials.SANGUINITE}) {
                CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.RUBIDIUM, CMaterials.INGOT), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(cMaterial, CMaterials.INGOT), 60L);
                if (CMaterials.existOD(cMaterial, CMaterials.INGOT)) break;
            }
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.IGNATIUS, CMaterials.SHADOW_IRON, CMaterials.LEMURITE, CMaterials.MIDASIUM, CMaterials.VYROXERES, CMaterials.CERUCLASE, CMaterials.ALDUORITE, CMaterials.KALENDRITE, CMaterials.VULCANITE, CMaterials.SANGUINITE}, CMaterials.INGOT, new int[]{0, 7, 7, 8, 8, 8, 8, 9, 10, 11}, 200);
            for (CMaterial cMaterial : new CMaterial[]{CMaterials.EXIMITE, CMaterials.MEUTOITE}) {
                CRecipes.register2to1Recipe(recipeCAInjector, CMaterials.getOD(CMaterials.PROTACTINIUM, CMaterials.INGOT), CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 10, CMaterials.getODExist(cMaterial, CMaterials.INGOT), 60L);
                if (CMaterials.existOD(cMaterial, CMaterials.INGOT)) break;
            }
            CRecipes.registerODChainRecipes(recipeTransformer, new CMaterial[]{CMaterials.EXIMITE, CMaterials.MEUTOITE}, CMaterials.INGOT, new int[]{0, 9}, 200);
            for (CShape cShape : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                for (CShape shape2 : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.BRONZE, cShape), CMaterials.getOD(CMaterials.GOLD, shape2), 6, CMaterials.getODExist(CMaterials.HEPATIZON, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.BRONZE, cShape), CMaterials.getOD(CMaterials.IRON, shape2), 6, CMaterials.getODExist(CMaterials.DAMASCUS_STEEL, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.IRON, cShape), CMaterials.getOD(CMaterials.GOLD, shape2), 6, CMaterials.getODExist(CMaterials.ANGMALLEN, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.DEEP_IRON, cShape), CMaterials.getOD(CMaterials.INFUSCOLIUM, shape2), 6, CMaterials.getODExist(CMaterials.BLACK_STEEL, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.MITHRIL, cShape), CMaterials.getOD(CMaterials.SILVER, shape2), 6, CMaterials.getODExist(CMaterials.QUICKSILVER, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.MITHRIL, cShape), CMaterials.getOD(CMaterials.RUBRACIUM, shape2), 6, CMaterials.getODExist(CMaterials.HADEROTH, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ORICHALCUM, cShape), CMaterials.getOD(CMaterials.PLATINUM, shape2), 6, CMaterials.getODExist(CMaterials.CELENEGIL, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ADAMANTINE, cShape), CMaterials.getOD(CMaterials.ATLARUS, shape2), 6, CMaterials.getODExist(CMaterials.TARTARITE, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.SHADOW_IRON, cShape), CMaterials.getOD(CMaterials.LEMURITE, shape2), 6, CMaterials.getODExist(CMaterials.SHADOW_STEEL, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.ALDUORITE, cShape), CMaterials.getOD(CMaterials.CERUCLASE, shape2), 6, CMaterials.getODExist(CMaterials.INOLASHITE, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.KALENDRITE, cShape), CMaterials.getOD(CMaterials.PLATINUM, shape2), 6, CMaterials.getODExist(CMaterials.AMORDRINE, CMaterials.INGOT, 2), 100L);
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.EXIMITE, cShape), CMaterials.getOD(CMaterials.MEUTOITE, shape2), 6, CMaterials.getODExist(CMaterials.DESICHALKOS, CMaterials.INGOT, 2), 100L);
                }
            }
        }
        if (ClayiumCore.IntegrationID.SS2.enabled()) {
            for (CShape cShape : new CShape[]{CMaterials.INGOT, CMaterials.DUST}) {
                for (CShape shape2 : new CShape[]{CMaterials.GEM, CMaterials.DUST}) {
                    CRecipes.register2to1Recipe(recipeAlloySmelter, CMaterials.getOD(CMaterials.DIAMOND, shape2), CMaterials.getOD(CMaterials.MITHRIL, cShape), 6, CMaterials.getODExist(CMaterials.NINJA, CMaterials.INGOT), 100L);
                }
                CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.DIAMOND, CMaterials.GEM), CMaterials.getOD(CMaterials.PLATINUM, cShape), 11, CMaterials.getODExist(CMaterials.ORICHALCUM, CMaterials.GEM), 1000000000000000L);
            }
            CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.REDSTONE, CMaterials.DUST), "dyeYellow", 9, CMaterials.getODExist(CMaterials.YELLOWSTONE, CMaterials.DUST), 10000000000000L);
            CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.REDSTONE, CMaterials.DUST), "dyeBlue", 9, CMaterials.getODExist(CMaterials.BLUESTONE, CMaterials.DUST), 10000000000000L);
            CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.REDSTONE, CMaterials.DUST), CMaterials.getOD(CMaterials.YELLOWSTONE, CMaterials.DUST), 8, CMaterials.getODExist(CMaterials.YELLOWSTONE, CMaterials.INGOT), 1000000000L);
            CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.REDSTONE, CMaterials.DUST), CMaterials.getOD(CMaterials.BLUESTONE, CMaterials.DUST), 8, CMaterials.getODExist(CMaterials.BLUESTONE, CMaterials.INGOT), 1000000000L);
        }
        if (ClayiumCore.IntegrationID.MAPLE.enabled()) {
            CRecipes.register1to1Recipe(recipeGrinder, "oreMapleDiamond", 5, CMaterials.getODExist("mapleDiamond", 2), 80L);
            CRecipes.register1to1Recipe(recipeGrinder, "oreDemantoidGarnet", 5, CMaterials.getODExist("demantoidGarnet", 2), 80L);
            CRecipes.register1to1Recipe(recipeGrinder, "oreMarble", 5, CMaterials.getODExist("marble", 2), 80L);
            CRecipes.register2to1Recipe(recipeReactor, CMaterials.getOD(CMaterials.DIAMOND, CMaterials.GEM), CRecipes.i(Items.field_151034_e), 10, CMaterials.getODExist("mapleDiamond"), 100000000000000L);
            CRecipes.register1to1Recipe(recipeTransformer, "mapleDiamond", 10, CMaterials.getODExist("demantoidGarnet"), 200L);
        }
        if (ClayiumCore.IntegrationID.TOFU.enabled()) {
            CRecipes.register2to1Recipe(recipeReactor, "soybeans", "nigari", 9, CMaterials.getODExist("tofuKinu", 4), 10000000000000L);
            CRecipes.register2to1Recipe(recipeReactor, "tofuKinu", "plankWood", 7, CMaterials.getODExist("tofuMomen"), 10000L);
            CRecipes.register2to1Recipe(recipeReactor, "tofuMomen", "cobblestone", 8, CMaterials.getODExist("tofuIshi"), 1000000000L);
            CRecipes.register2to1Recipe(recipeReactor, "tofuIshi", CMaterials.getOD(CMaterials.IRON, CMaterials.INGOT), 9, CMaterials.getODExist("tofuMetal"), 10000000000000L);
            CRecipes.register2to1Recipe(recipeReactor, "tofuMetal", CMaterials.getOD(CMaterials.DIAMOND, CMaterials.GEM), 11, CMaterials.getODExist("tofuDiamond"), 10000000000000000L);
            CRecipes.register2to1Recipe(recipeReactor, "tofuMomen", CMaterials.getOD(CMaterials.DIAMOND, CMaterials.GEM), 9, CMaterials.getODExist("tofuGem"), 10000000000000L);
        }
    }

    public static class RecipeConditions {
        public int tier = 0;
        public long time = 0L;
        public Recipes recipes = null;

        public RecipeConditions(Recipes recipes, int tier, long time) {
            this.recipes = recipes;
            this.tier = tier;
            this.time = time;
        }
    }
}


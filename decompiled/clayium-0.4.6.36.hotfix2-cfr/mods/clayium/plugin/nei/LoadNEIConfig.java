/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.nei.PositionedStack
 *  codechicken.nei.api.API
 *  codechicken.nei.api.IOverlayHandler
 *  codechicken.nei.recipe.DefaultOverlayHandler
 *  codechicken.nei.recipe.GuiCraftingRecipe
 *  codechicken.nei.recipe.GuiUsageRecipe
 *  codechicken.nei.recipe.ICraftingHandler
 *  codechicken.nei.recipe.IUsageHandler
 *  codechicken.nei.recipe.TemplateRecipeHandler
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.plugin.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.DefaultOverlayHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mods.clayium.block.CBlocks;
import mods.clayium.block.tile.TileQuartzCrucible;
import mods.clayium.block.tile.TileSaltExtractor;
import mods.clayium.block.tile.TileSolarClayFabricator;
import mods.clayium.core.ClayiumCore;
import mods.clayium.gui.FDText;
import mods.clayium.gui.client.GuiClayCraftingTable;
import mods.clayium.gui.client.GuiClayMachines;
import mods.clayium.gui.container.ContainerClayCraftingTable;
import mods.clayium.item.CMaterials;
import mods.clayium.plugin.nei.INEIRecipeEntry;
import mods.clayium.plugin.nei.NEIClayMachines;
import mods.clayium.plugin.nei.NEIClayWorkTable;
import mods.clayium.plugin.nei.NEIDescription;
import mods.clayium.plugin.nei.NEIMetalSeparator;
import mods.clayium.plugin.nei.NEIShapelessSpecialResultRecipe;
import mods.clayium.plugin.nei.NEITemp;
import mods.clayium.util.UtilLocale;
import mods.clayium.util.UtilTier;
import mods.clayium.util.crafting.Recipes;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LoadNEIConfig {
    public static void load() {
        int i;
        TemplateRecipeHandler[] catchRecipes;
        API.registerRecipeHandler((ICraftingHandler)new NEIShapelessSpecialResultRecipe());
        API.registerUsageHandler((IUsageHandler)new NEIShapelessSpecialResultRecipe());
        for (Map.Entry<String, Recipes> entry : Recipes.RecipeMap.entrySet()) {
            Recipes recipes = entry.getValue();
            NEIClayMachines catchRecipe = new NEIClayMachines(recipes);
            if (entry.getKey().equals("Smelter")) {
                catchRecipe.setTierManager(UtilTier.tierSmelter);
            }
            GuiCraftingRecipe.craftinghandlers.add(catchRecipe);
            GuiUsageRecipe.usagehandlers.add(catchRecipe);
            API.registerGuiOverlay(GuiClayMachines.class, (String)catchRecipe.getOverlayIdentifier(), (int)0, (int)0);
        }
        for (TemplateRecipeHandler catchRecipe : catchRecipes = new TemplateRecipeHandler[]{new NEIClayWorkTable(), new NEIMetalSeparator()}) {
            GuiCraftingRecipe.craftinghandlers.add(catchRecipe);
            GuiUsageRecipe.usagehandlers.add(catchRecipe);
            API.registerGuiOverlay((Class)catchRecipe.getGuiClass(), (String)catchRecipe.getOverlayIdentifier(), (int)0, (int)0);
        }
        API.registerGuiOverlay(GuiClayCraftingTable.class, (String)"crafting");
        API.registerGuiOverlayHandler(GuiClayCraftingTable.class, (IOverlayHandler)new DefaultOverlayHandler(){

            public boolean canMoveFrom(Slot slot, GuiContainer gui) {
                if (gui.field_147002_h instanceof ContainerClayCraftingTable) {
                    IInventory[] inventories = ((ContainerClayCraftingTable)gui.field_147002_h).inventories;
                    for (int i = 1; i < inventories.length; ++i) {
                        if (slot.field_75224_c != inventories[i]) continue;
                        return true;
                    }
                }
                return slot.field_75224_c instanceof InventoryPlayer;
            }
        }, (String)"crafting");
        int n = 6;
        int posuY = 23;
        int posY = 43;
        int posdY = 9;
        int width = 158;
        int colorBlack = -16777216;
        int colorGray = 0x404040;
        NEIDescription description = new NEIDescription("CobblestoneGenerator", 1);
        GuiCraftingRecipe.craftinghandlers.add(description);
        GuiUsageRecipe.usagehandlers.add(description);
        List<INEIRecipeEntry> list = description.getEntryList();
        ItemStack[] cs = new ItemStack[]{new ItemStack(Blocks.field_150347_e)};
        int[] effciencies = new int[]{0, 2, 5, 15, 50, 200, 1000, 8000};
        for (int i2 = 1; i2 <= 7; ++i2) {
            ArrayList<PositionedStack> machineList = new ArrayList<PositionedStack>();
            Object[] is = new ItemStack[]{new ItemStack(CBlocks.blocksCobblestoneGenerator[i2])};
            machineList.add(new PositionedStack((Object)is, 75, 5));
            NEIDescription nEIDescription = description;
            ((Object)((Object)nEIDescription)).getClass();
            NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(nEIDescription, is, machineList, description.generateResultPositionedStacks(cs));
            entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.CobblestoneGenerator.efficiency", (Object[])new Object[]{(double)ClayiumCore.multiplyProgressionRateI(effciencies[i2]) / 100.0})), n, posY, colorBlack, width));
            entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.CobblestoneGenerator.description", (Object[])new Object[0])), n, posY + posdY, colorGray, width));
            list.add(entry);
        }
        description = new NEIDescription("SaltExtractor", 1);
        GuiCraftingRecipe.craftinghandlers.add(description);
        GuiUsageRecipe.usagehandlers.add(description);
        list = description.getEntryList();
        ItemStack[] salt = new ItemStack[]{CMaterials.get(CMaterials.SALT, CMaterials.DUST)};
        for (i = 4; i <= 7; ++i) {
            ArrayList<PositionedStack> machineList = new ArrayList<PositionedStack>();
            Object[] is = new ItemStack[]{new ItemStack(CBlocks.blocksSaltExtractor[i])};
            machineList.add(new PositionedStack((Object)is, 75, 5));
            NEIDescription nEIDescription = description;
            ((Object)((Object)nEIDescription)).getClass();
            NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(nEIDescription, is, machineList, description.generateResultPositionedStacks(salt));
            entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.SaltExtractor.efficiency", (Object[])new Object[]{(double)ClayiumCore.multiplyProgressionRateI(effciencies[i]) / 100.0})), n, posY, colorBlack, width));
            entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.SaltExtractor.energyConsumption", (Object[])new Object[]{UtilLocale.ClayEnergyNumeral(ClayiumCore.multiplyProgressionRateI(effciencies[i]) * TileSaltExtractor.energyPerWork)})), n, posY + posdY, colorBlack, width));
            entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.SaltExtractor.description", (Object[])new Object[0])), n, posY + posdY * 2, colorGray, width));
            list.add(entry);
        }
        description = new NEIDescription("QuartzCrucible", 1);
        GuiCraftingRecipe.craftinghandlers.add(description);
        GuiUsageRecipe.usagehandlers.add(description);
        list = description.getEntryList();
        for (i = 1; i <= 9; ++i) {
            Object[] toSeacrh = new ItemStack[]{new ItemStack(CBlocks.blockQuartzCrucible), CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT, i), new ItemStack(Items.field_151007_F)};
            ArrayList<PositionedStack> ingredList = new ArrayList<PositionedStack>();
            ItemStack[] is = new ItemStack[]{new ItemStack(CBlocks.blockQuartzCrucible)};
            ingredList.add(new PositionedStack((Object)is, 75, 5));
            Object[] ingredItems = new ItemStack[]{CMaterials.get(CMaterials.IMPURE_SILICON, CMaterials.INGOT, i), new ItemStack(Items.field_151007_F)};
            ingredList.addAll(description.generateIngredientPositionedStacks(ingredItems));
            ItemStack[] resultItems = new ItemStack[]{CMaterials.get(CMaterials.SILICON, CMaterials.INGOT, i)};
            NEIDescription nEIDescription = description;
            ((Object)((Object)nEIDescription)).getClass();
            NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(nEIDescription, toSeacrh, ingredList, description.generateResultPositionedStacks(resultItems));
            entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.QuartzCrucible.timeToCraft", (Object[])new Object[]{TileQuartzCrucible.timeToCraft * i, TileQuartzCrucible.timeToCraft * i / 20})), n, posY, colorBlack, width));
            entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.QuartzCrucible.description", (Object[])new Object[0])), n, posY + posdY, colorGray, width));
            list.add(entry);
        }
        description = new NEIDescription("SolarClayFabricator", 2);
        GuiCraftingRecipe.craftinghandlers.add(description);
        GuiUsageRecipe.usagehandlers.add(description);
        list = description.getEntryList();
        Block[] blocks = new Block[16];
        blocks[5] = CBlocks.blockSolarClayFabricatorMK1;
        blocks[6] = CBlocks.blockSolarClayFabricatorMK2;
        blocks[7] = CBlocks.blockLithiumSolarClayFabricator;
        int[] acceptableTiers = new int[]{0, 0, 0, 0, 0, 4, 6, 9};
        float[] baseCraftTimes = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4.0f, 3.0f, 2.0f};
        float[] efficiencies = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 5000.0f, 50000.0f, 3000000.0f};
        List[] compressedClay = new List[13];
        for (int t = 0; t <= 12; ++t) {
            ArrayList<Object> clist = new ArrayList<Object>();
            clist.add(TileSolarClayFabricator.getCompressedClay(t));
            if (t == 2) {
                clist.add(new ItemStack((Block)Blocks.field_150354_m));
            }
            if (t == 8) {
                clist.add(CMaterials.getOD(CMaterials.LITHIUM, CMaterials.INGOT));
            }
            compressedClay[t] = clist;
        }
        for (int i3 = 5; i3 <= 7; ++i3) {
            ItemStack machine = new ItemStack(blocks[i3]);
            float multCraftTime = (float)(Math.pow(10.0, acceptableTiers[i3] + 1) * (double)(baseCraftTimes[i3] - 1.0f) / ((double)baseCraftTimes[i3] * (Math.pow(baseCraftTimes[i3], acceptableTiers[i3]) - 1.0)) / (double)(ClayiumCore.multiplyProgressionRateF(efficiencies[i3]) / 20.0f));
            for (int t = 0; t <= acceptableTiers[i3]; ++t) {
                long energy = (long)(t + 1 < 5 ? 0.0 : Math.pow(10.0, t + 1));
                for (Object clay : compressedClay[t]) {
                    Object[] toSeacrh = new Object[]{machine, clay};
                    ArrayList<PositionedStack> ingredList = new ArrayList<PositionedStack>();
                    ItemStack[] is = new ItemStack[]{machine};
                    ingredList.add(new PositionedStack((Object)is, 75, 5));
                    Object[] ingredItems = new Object[]{clay};
                    ingredList.addAll(description.generateIngredientPositionedStacks(ingredItems));
                    ItemStack[] resultItems = new ItemStack[]{TileSolarClayFabricator.getCompressedClay(t + 1)};
                    long timeToCraft = (long)(Math.pow(baseCraftTimes[i3], t) * (double)multCraftTime);
                    NEIDescription nEIDescription = description;
                    ((Object)((Object)nEIDescription)).getClass();
                    NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(nEIDescription, toSeacrh, ingredList, description.generateResultPositionedStacks(resultItems));
                    entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.SolarClayFabricator.timeToCraft", (Object[])new Object[]{UtilLocale.craftTimeNumeral(timeToCraft), timeToCraft / 20L, UtilLocale.ClayEnergyNumeral(energy / timeToCraft)})), n, posY, colorBlack, width));
                    entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.SolarClayFabricator.description", (Object[])new Object[0])), n, posY + posdY, colorGray, width));
                    list.add(entry);
                }
            }
        }
        description = new NEIDescription("ClayTree", "clayium:textures/gui/back.png", null, 1);
        GuiCraftingRecipe.craftinghandlers.add(description);
        GuiUsageRecipe.usagehandlers.add(description);
        list = description.getEntryList();
        ArrayList<PositionedStack> treeList = new ArrayList<PositionedStack>();
        ItemStack[] is = new ItemStack[]{new ItemStack(CBlocks.blockClayTreeSapling), new ItemStack(CBlocks.blockClayTreeLog), new ItemStack(CBlocks.blockClayTreeLeaf)};
        treeList.add(new PositionedStack((Object)is, 75, 5));
        NEIDescription nEIDescription = description;
        ((Object)((Object)nEIDescription)).getClass();
        NEITemp.NEITemplateEntry entry = new NEITemp.NEITemplateEntry(nEIDescription, new Object[0], new ArrayList<PositionedStack>(), treeList, false);
        entry.fdList.add(FDText.INSTANCE.getFDText(FDText.getStringHandler(I18n.func_135052_a((String)"recipe.ClayTree.description", (Object[])new Object[0])), n, posuY, colorGray, width));
        list.add(entry);
    }
}


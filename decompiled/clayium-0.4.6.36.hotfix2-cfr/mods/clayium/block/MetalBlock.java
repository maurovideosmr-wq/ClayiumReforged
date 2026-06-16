/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraftforge.oredict.ShapelessOreRecipe
 */
package mods.clayium.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import mods.clayium.block.BlockDamaged;
import mods.clayium.block.itemblock.ItemBlockDamaged;
import mods.clayium.core.ClayiumCore;
import mods.clayium.gui.TextureExtra;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.crafting.CRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class MetalBlock
extends BlockDamaged {
    public static Map<Integer, MetalBlock> metalBlockMap = new HashMap<Integer, MetalBlock>();
    public CMaterial[] materials = new CMaterial[16];

    public static void registerMaterials() {
        CMaterial[] materials;
        for (CMaterial material : materials = new CMaterial[]{CMaterials.MAGNESIUM, CMaterials.SODIUM, CMaterials.LITHIUM, CMaterials.ZIRCONIUM, CMaterials.ZINC, CMaterials.MANGANESE, CMaterials.CALCIUM, CMaterials.POTASSIUM, CMaterials.NICKEL, CMaterials.BERYLLIUM, CMaterials.LEAD, CMaterials.HAFNIUM, CMaterials.CHROME, CMaterials.TITANIUM, CMaterials.STRONTIUM, CMaterials.BARIUM, CMaterials.AZ91D_ALLOY, CMaterials.ZK60A_ALLOY, CMaterials.RUBIDIUM, CMaterials.CAESIUM, CMaterials.FRANCIUM, CMaterials.RADIUM, CMaterials.ACTINIUM, CMaterials.THORIUM, CMaterials.PROTACTINIUM, CMaterials.URANIUM, CMaterials.NEPTUNIUM, CMaterials.PLUTONIUM, CMaterials.AMERICIUM, CMaterials.CURIUM, CMaterials.LANTHANUM, CMaterials.CERIUM, CMaterials.PRASEODYMIUM, CMaterials.NEODYMIUM, CMaterials.PROMETHIUM, CMaterials.SAMARIUM, CMaterials.EUROPIUM, CMaterials.VANADIUM, CMaterials.COBALT, CMaterials.PALLADIUM, CMaterials.PLATINUM, CMaterials.IRIDIUM, CMaterials.OSMIUM, CMaterials.RHENIUM, CMaterials.TANTALUM, CMaterials.TUNGSTEN, CMaterials.MOLYBDENUM, CMaterials.ANTIMONY, CMaterials.BISMUTH, CMaterials.BRASS, CMaterials.ELECTRUM, CMaterials.INVAR, CMaterials.STEEL, CMaterials.COPPER, CMaterials.TIN, CMaterials.SILVER, CMaterials.BRONZE}) {
            MetalBlock.registerBlockMaterial(material);
        }
    }

    public static void registerBlockMaterial(CMaterial material) {
        MetalBlock block;
        if (!CMaterials.exist(material, CMaterials.INGOT)) {
            return;
        }
        boolean oreDictionary = UtilItemStack.hasOreName(CMaterials.get(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT));
        if (!metalBlockMap.containsKey(material.meta / 16)) {
            block = new MetalBlock();
            GameRegistry.registerBlock((Block)block, ItemBlockDamaged.class, (String)("blockMetal" + String.format("%03d", material.meta / 16)));
            metalBlockMap.put(material.meta / 16, block);
        }
        block = MetalBlock.getBlock(material);
        block.registerMaterial(material);
        CMaterials.registerMaterialShape(material, CMaterials.BLOCK, block.get(material.name));
        if (oreDictionary) {
            CMaterials.addItemToOD(material, CMaterials.BLOCK);
            GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(material, CMaterials.BLOCK), CRecipes.oo(CMaterials.getODName(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT), CMaterials.getODName(material, CMaterials.INGOT))));
            GameRegistry.addRecipe((IRecipe)new ShapelessOreRecipe(CMaterials.get(material, CMaterials.INGOT, 9), CRecipes.oo(CMaterials.getODName(material, CMaterials.BLOCK))));
        } else {
            GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(material, CMaterials.BLOCK), (Object[])CRecipes.oo(CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.INGOT), CMaterials.get(material, CMaterials.INGOT)));
            GameRegistry.addShapelessRecipe((ItemStack)CMaterials.get(material, CMaterials.INGOT, 9), (Object[])CRecipes.oo(CMaterials.get(material, CMaterials.BLOCK)));
        }
    }

    public static MetalBlock getBlock(CMaterial material) {
        return metalBlockMap.get(material.meta / 16);
    }

    public MetalBlock() {
        super(Material.field_151573_f);
        this.func_149672_a(field_149777_j);
        this.func_149647_a(ClayiumCore.creativeTabClayium);
        this.func_149711_c(2.0f);
        this.func_149752_b(2.0f);
        this.func_149663_c("blockMetal");
    }

    public void registerMaterial(CMaterial material) {
        this.addBlockList(material.name, material.meta % 16);
        this.materials[material.meta % 16] = material;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister register) {
        for (int i = 0; i < 16; ++i) {
            if (!this.isRegistered(i)) continue;
            TextureExtra tex = new TextureExtra("clayium:metalblock_" + this.materials[i].name, "clayium:metalblock_base", "clayium:metalblock_dark", "clayium:metalblock_light");
            int[] colors = new int[this.materials[i].colors.length];
            for (int j = 0; j < this.materials[i].colors.length; ++j) {
                colors[j] = (this.materials[i].colors[j][0] << 16) + (this.materials[i].colors[j][1] << 8) + this.materials[i].colors[j][2] + -16777216;
            }
            tex.setColorTable(colors);
            this.setIcon(i, tex.register(register));
        }
    }
}


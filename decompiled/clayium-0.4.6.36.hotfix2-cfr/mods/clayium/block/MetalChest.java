/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.config.Configuration
 */
package mods.clayium.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mods.clayium.block.ClayContainer;
import mods.clayium.block.ISpecialUnlocalizedName;
import mods.clayium.block.tile.TileMetalChest;
import mods.clayium.core.ClayiumCore;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

public class MetalChest
extends ClayContainer
implements ISpecialUnlocalizedName {
    protected static HashMap<CMaterial, HashMap<String, Integer>> metalChestMaterials = new HashMap();

    public static void registerMaterials() {
        try {
            MetalChest.addChestMaterial(CMaterials.SILICON, 9, 5, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.ALUMINIUM, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.CLAY_STEEL, 9, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.CLAYIUM, 13, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.ULTIMATE_ALLOY, 13, 8, 3, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.MAGNESIUM, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.SODIUM, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.LITHIUM, 9, 7, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.ZIRCONIUM, 9, 7, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.ZINC, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.MANGANESE, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.CALCIUM, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.POTASSIUM, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.NICKEL, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.BERYLLIUM, 10, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.LEAD, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.HAFNIUM, 9, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.CHROME, 13, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.TITANIUM, 13, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.STRONTIUM, 10, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.BARIUM, 10, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.AZ91D_ALLOY, 13, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.ZK60A_ALLOY, 13, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.RUBIDIUM, 13, 3, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.CAESIUM, 13, 3, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.FRANCIUM, 13, 4, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.RADIUM, 13, 4, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.ACTINIUM, 13, 5, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.THORIUM, 13, 5, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.PROTACTINIUM, 13, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.URANIUM, 13, 6, 2, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.NEPTUNIUM, 13, 6, 3, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.PLUTONIUM, 13, 6, 4, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.AMERICIUM, 13, 6, 5, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.CURIUM, 13, 6, 6, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.LANTHANUM, 13, 2, 2, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.CERIUM, 13, 2, 4, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.PRASEODYMIUM, 13, 2, 6, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.NEODYMIUM, 13, 2, 8, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.PROMETHIUM, 13, 4, 8, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.SAMARIUM, 13, 6, 8, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.EUROPIUM, 13, 8, 8, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.VANADIUM, 4, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.COBALT, 11, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.PALLADIUM, 11, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.PLATINUM, 13, 8, 2, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.IRIDIUM, 13, 8, 3, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.OSMIUM, 13, 8, 4, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.RHENIUM, 13, 8, 5, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.TANTALUM, 10, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.TUNGSTEN, 13, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.MOLYBDENUM, 13, 8, 2, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.ANTIMONY, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.BISMUTH, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.BRASS, 9, 6, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.ELECTRUM, 13, 8, 1, ClayiumCore.configrationDefault);
            MetalChest.addChestMaterial(CMaterials.INVAR, 9, 8, 1, ClayiumCore.configrationDefault);
        }
        finally {
            ClayiumCore.configrationDefault.save();
        }
    }

    public MetalChest() {
        super(Material.field_151573_f, TileMetalChest.class, null, 11, 1);
        this.func_149711_c(2.0f);
        this.func_149752_b(2.0f);
        this.func_149672_a(field_149777_j);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149666_a(Item item, CreativeTabs creativeTab, List list) {
        Iterator<Map.Entry<CMaterial, HashMap<String, Integer>>> iterator = metalChestMaterials.entrySet().iterator();
        while (iterator.hasNext()) {
            CMaterial material = iterator.next().getKey();
            list.add(new ItemStack(item, 1, material.meta));
            Collections.sort(list, new Comparator(){

                public int compare(Object o1, Object o2) {
                    if (o1 instanceof ItemStack && o2 instanceof ItemStack) {
                        if (((ItemStack)o1).func_77960_j() < ((ItemStack)o2).func_77960_j()) {
                            return -1;
                        }
                        if (((ItemStack)o1).func_77960_j() > ((ItemStack)o2).func_77960_j()) {
                            return 1;
                        }
                    }
                    return 0;
                }
            });
        }
    }

    public int func_149643_k(World world, int x, int y, int z) {
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te instanceof TileMetalChest) {
            return ((TileMetalChest)te).getMaterialId();
        }
        return super.func_149643_k(world, x, y, z);
    }

    @Override
    public int func_149645_b() {
        return ClayiumCore.metalChestRenderId;
    }

    @Override
    public boolean func_149686_d() {
        return false;
    }

    @Override
    public boolean func_149662_c() {
        return false;
    }

    @Override
    public boolean canChangeRenderType() {
        return false;
    }

    @Override
    public void setInitialBlockBounds() {
        this.func_149676_a(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public IIcon func_149691_a(int p_149673_1_, int p_149673_2_) {
        return Blocks.field_150339_S.func_149691_a(0, 0);
    }

    @SideOnly(value=Side.CLIENT)
    public int func_149741_i(int meta) {
        CMaterial material = CMaterials.getMaterialFromId(meta);
        return material == null ? super.func_149741_i(meta) : (material.colors[0][0] << 16) + (material.colors[0][1] << 8) + material.colors[0][2];
    }

    @SideOnly(value=Side.CLIENT)
    public int func_149720_d(IBlockAccess world, int x, int y, int z) {
        TileEntity te = UtilBuilder.safeGetTileEntity(world, x, y, z);
        if (te instanceof TileMetalChest) {
            return this.func_149741_i(((TileMetalChest)te).getMaterialId());
        }
        return super.func_149720_d(world, x, y, z);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        CMaterial material = CMaterials.getMaterialFromId(itemStack.func_77960_j());
        return MetalChest.getUnlocalizedMetalChestName((Block)this, material);
    }

    public static String getUnlocalizedMetalChestName(Block block, CMaterial material) {
        return block.func_149739_a() + (material == null ? "" : "." + material.name);
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        ArrayList<String> res = new ArrayList<String>();
        CMaterial material = CMaterials.getMaterialFromId(itemStack.func_77960_j());
        if (material != null) {
            if (MetalChest.getContainerP(material) <= 1 && UtilLocale.canLocalize("tooltip.MetalChest.capacity1")) {
                res.add(UtilLocale.localizeAndFormat("tooltip.MetalChest.capacity1", MetalChest.getContainerX(material), MetalChest.getContainerY(material), MetalChest.getContainerX(material) * MetalChest.getContainerY(material)));
            }
            if (MetalChest.getContainerP(material) >= 2 && UtilLocale.canLocalize("tooltip.MetalChest.capacity2")) {
                res.add(UtilLocale.localizeAndFormat("tooltip.MetalChest.capacity2", MetalChest.getContainerX(material), MetalChest.getContainerY(material), MetalChest.getContainerP(material), MetalChest.getContainerX(material) * MetalChest.getContainerY(material) * MetalChest.getContainerP(material)));
            }
        }
        return res;
    }

    public static void addChestMaterial(CMaterial material, int containerX, int containerY, int containerP, Configuration cfg) {
        containerX = cfg.getInt(material.name + "ChestWidth", "metalchest", containerX, 1, 13, "");
        containerY = cfg.getInt(material.name + "ChestHeight", "metalchest", containerY, 1, 8, "");
        containerP = cfg.getInt(material.name + "ChestNumberOfPages", "metalchest", containerP, 1, 8, "");
        MetalChest.addChestMaterial(material, containerX, containerY, containerP);
    }

    public static void addChestMaterial(CMaterial material, int containerX, int containerY, int containerP) {
        HashMap<String, Integer> value = new HashMap<String, Integer>();
        value.put("x", containerX);
        value.put("y", containerY);
        value.put("p", containerP);
        metalChestMaterials.put(material, value);
    }

    public static boolean containsKey(CMaterial material) {
        return metalChestMaterials.containsKey(material);
    }

    public static int getContainerX(CMaterial material) {
        return MetalChest.containsKey(material) ? metalChestMaterials.get(material).get("x") : 1;
    }

    public static int getContainerY(CMaterial material) {
        return MetalChest.containsKey(material) ? metalChestMaterials.get(material).get("y") : 1;
    }

    public static int getContainerP(CMaterial material) {
        return MetalChest.containsKey(material) ? metalChestMaterials.get(material).get("p") : 1;
    }

    public static HashMap<CMaterial, HashMap<String, Integer>> getChestMaterialMap() {
        return metalChestMaterials;
    }
}


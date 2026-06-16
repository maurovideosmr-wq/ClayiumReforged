/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Iterables
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package mods.clayium.block;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mods.clayium.block.ClayNoRecipeMachines;
import mods.clayium.block.tile.TileClayRFGenerator;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ClayRFGenerator
extends ClayNoRecipeMachines {
    public String blockName = null;
    protected static final Splitter splitter1 = Splitter.on((char)';').limit(2);
    protected static volatile Map<String, Map<String, Object>> configMap = null;
    protected static final Splitter splitter2 = Splitter.on((char)':');
    protected static final String[] keys = new String[]{"IconName", "Tier", "CEConsumptionPerTick", "RFProductionPerTick", "RFOutputPerTick", "RFStorageSize", "OverclockExponent"};
    protected static final Class[] valueTypes = new Class[]{String.class, Integer.class, Long.class, Integer.class, Integer.class, Integer.class, Double.class};

    public ClayRFGenerator(String blockName, String iconSuffix, int tier) {
        super(null, "clayium:" + iconSuffix, tier, TileClayRFGenerator.class, 2);
        this.guiId = 90;
        this.blockName = blockName;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
        this.registerInsertIcons(par1IconRegister, "import_energy");
        this.registerExtractIcons(par1IconRegister, "export_rf");
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        super.func_149651_a(par1IconRegister);
        this.setSameOverlayIcons(par1IconRegister.func_94245_a(this.iconstr));
    }

    public void func_149695_a(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        super.func_149695_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        this.updatePower(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }

    public void func_149726_b(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        super.func_149726_b(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.updatePower(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    public void updatePower(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_) {
        TileEntity tile;
        if (!p_149695_1_.field_72995_K && (tile = UtilBuilder.safeGetTileEntity((IBlockAccess)p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) instanceof TileClayRFGenerator) {
            ((TileClayRFGenerator)tile).setPowered(p_149695_1_.func_72864_z(p_149695_2_, p_149695_3_, p_149695_4_));
        }
    }

    @Override
    public TileEntity func_149915_a(World world, int par2) {
        TileEntity tile = super.func_149915_a(world, par2);
        if (tile instanceof TileClayRFGenerator) {
            ((TileClayRFGenerator)tile).setBlockName(this.blockName);
        }
        return tile;
    }

    public static synchronized Map<String, Map<String, Object>> getConfigMap() {
        if (configMap == null) {
            configMap = new HashMap<String, Map<String, Object>>();
            for (String str : ClayiumCore.cfgRFGenerator) {
                Map<String, Object> map;
                String[] strs = (String[])Iterables.toArray((Iterable)splitter1.split((CharSequence)str), String.class);
                if (strs == null || strs.length < 2 || (map = ClayRFGenerator.readConfig(strs[1])) == null) continue;
                configMap.put(strs[0], map);
            }
        }
        return configMap;
    }

    public static Map<String, Object> getConfig(String blockName) {
        Map<String, Map<String, Object>> map = ClayRFGenerator.getConfigMap();
        return map == null ? null : map.get(blockName);
    }

    public static Map<String, Object> readConfig(String configStr) {
        String[] strs = (String[])Iterables.toArray((Iterable)splitter2.split((CharSequence)configStr), String.class);
        if (strs == null) {
            return null;
        }
        HashMap<String, Object> ret = new HashMap<String, Object>();
        for (int i = 0; i < strs.length && i < keys.length && i < valueTypes.length; ++i) {
            if ("CEConsumptionPerTick".equals(keys[i])) {
                try {
                    long v = Long.parseLong(strs[i]);
                    ret.put(keys[i], v *= 100000L);
                }
                catch (NumberFormatException e) {
                    try {
                        double v = Double.parseDouble(strs[i]);
                        ret.put(keys[i], (long)(v *= 100000.0));
                    }
                    catch (NumberFormatException e1) {
                        ClayiumCore.logger.catching((Throwable)e1);
                    }
                }
                continue;
            }
            if (valueTypes[i] == String.class) {
                ret.put(keys[i], strs[i]);
                continue;
            }
            if (valueTypes[i] == Integer.class) {
                try {
                    int v = Integer.parseInt(strs[i]);
                    ret.put(keys[i], v);
                }
                catch (NumberFormatException e) {
                    ClayiumCore.logger.catching((Throwable)e);
                }
                continue;
            }
            if (valueTypes[i] == Long.class) {
                try {
                    long v = Long.parseLong(strs[i]);
                    ret.put(keys[i], v);
                }
                catch (NumberFormatException e) {
                    ClayiumCore.logger.catching((Throwable)e);
                }
                continue;
            }
            if (valueTypes[i] != Double.class) continue;
            try {
                double v = Double.parseDouble(strs[i]);
                ret.put(keys[i], v);
                continue;
            }
            catch (NumberFormatException e) {
                ClayiumCore.logger.catching((Throwable)e);
            }
        }
        return ret;
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        List<String> ret = UtilLocale.localizeTooltip("tooltip.RFGenerator");
        ret.addAll(super.getTooltip(itemStack));
        Map<String, Object> config = ClayRFGenerator.getConfig(this.blockName);
        if (config != null) {
            long ceConsumptionPerTickBase = 100L;
            int rfProductionPerTickBase = 10;
            int rfOutputPerTickBase = 10;
            Object obj = config.get("CEConsumptionPerTick");
            if (obj instanceof Number) {
                ceConsumptionPerTickBase = ((Number)obj).longValue();
            }
            if ((obj = config.get("RFProductionPerTick")) instanceof Number) {
                rfProductionPerTickBase = ((Number)obj).intValue();
            }
            if ((obj = config.get("RFOutputPerTick")) instanceof Number) {
                rfOutputPerTickBase = ((Number)obj).intValue();
            }
            if (UtilLocale.canLocalize("tooltip.RFGenerator.convertRate")) {
                ret.add(UtilLocale.localizeAndFormat("tooltip.RFGenerator.convertRate", UtilLocale.ClayEnergyNumeral(ceConsumptionPerTickBase), UtilLocale.rfNumeral(rfProductionPerTickBase)));
            }
            if (UtilLocale.canLocalize("tooltip.RFGenerator.output")) {
                ret.add(UtilLocale.localizeAndFormat("tooltip.RFGenerator.output", UtilLocale.rfNumeral(rfOutputPerTickBase)));
            }
        }
        return ret;
    }
}


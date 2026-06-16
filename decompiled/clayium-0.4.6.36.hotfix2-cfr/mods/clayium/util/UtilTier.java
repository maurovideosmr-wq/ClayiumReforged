/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraftforge.common.config.Configuration
 */
package mods.clayium.util;

import java.util.HashMap;
import java.util.Map;
import mods.clayium.block.ClayMachines;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.core.ClayiumCore;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;

public class UtilTier {
    public static TierManager tierSmelter;
    public static TierManager tierGeneric;
    public static TierManager tierCACondenser;
    public static TierManager tierMachineTransport;
    public static TierManager tierBufferTransport;
    public static final String multCraftTime = "multCraftTime";
    public static final String multConsumingEnergy = "multConsumingEnergy";
    public static final String autoInsertInterval = "autoInsertInterval";
    public static final String autoExtractInterval = "autoExtractInterval";
    public static final String maxAutoInsertDefault = "maxAutoInsertDefault";
    public static final String maxAutoExtractDefault = "maxAutoExtractDefault";

    public static boolean canAutoTransfer(int tier) {
        return tier >= 3;
    }

    public static boolean canManufactualCraft(int tier) {
        return tier <= 2;
    }

    public static boolean acceptWaterWheel(int tier) {
        return tier == 2 || tier == 3;
    }

    public static boolean acceptEnergyClay(int tier) {
        return tier >= 4;
    }

    public static void setTierManagers() {
        tierGeneric = TierManager.getMachineTierManager("MachineBase_Crafting");
        ((TieredStatus)tierGeneric.get(multCraftTime)).put(5, Float.valueOf(0.25f));
        ((TieredStatus)tierGeneric.get(multCraftTime)).put(6, Float.valueOf(0.0625f));
        ((TieredStatus)tierGeneric.get(multCraftTime)).put(10, Float.valueOf(0.01f));
        ((TieredStatus)tierGeneric.get(multConsumingEnergy)).put(5, Float.valueOf(5.0f));
        ((TieredStatus)tierGeneric.get(multConsumingEnergy)).put(6, Float.valueOf(25.0f));
        ((TieredStatus)tierGeneric.get(multConsumingEnergy)).put(10, Float.valueOf(250.0f));
        tierSmelter = TierManager.getMachineTierManager("MachineSmelter_Crafting");
        ((TieredStatus)tierSmelter.get(multCraftTime)).put(4, Float.valueOf(2.0f));
        ((TieredStatus)tierSmelter.get(multCraftTime)).put(5, Float.valueOf(0.5f));
        ((TieredStatus)tierSmelter.get(multCraftTime)).put(6, Float.valueOf(0.125f));
        ((TieredStatus)tierSmelter.get(multCraftTime)).put(7, Float.valueOf(0.03f));
        ((TieredStatus)tierSmelter.get(multCraftTime)).put(8, Float.valueOf(0.01f));
        ((TieredStatus)tierSmelter.get(multCraftTime)).put(9, Float.valueOf(0.0025f));
        ((TieredStatus)tierSmelter.get(multConsumingEnergy)).put(4, Float.valueOf(1.0f));
        ((TieredStatus)tierSmelter.get(multConsumingEnergy)).put(5, Float.valueOf(14.0f));
        ((TieredStatus)tierSmelter.get(multConsumingEnergy)).put(6, Float.valueOf(200.0f));
        ((TieredStatus)tierSmelter.get(multConsumingEnergy)).put(7, Float.valueOf(2800.0f));
        ((TieredStatus)tierSmelter.get(multConsumingEnergy)).put(8, Float.valueOf(40000.0f));
        ((TieredStatus)tierSmelter.get(multConsumingEnergy)).put(9, Float.valueOf(560000.0f));
        tierCACondenser = TierManager.getMachineTierManager("MachineCACondenser_Crafting");
        ((TieredStatus)tierCACondenser.get(multCraftTime)).put(10, Float.valueOf(0.1f));
        ((TieredStatus)tierCACondenser.get(multConsumingEnergy)).put(10, Float.valueOf(10.0f));
        ((TieredStatus)tierCACondenser.get(multCraftTime)).put(11, Float.valueOf(0.01f));
        ((TieredStatus)tierCACondenser.get(multConsumingEnergy)).put(11, Float.valueOf(100.0f));
        tierMachineTransport = TierManager.getTransportTierManager("MachineBase_Transport");
        tierBufferTransport = TierManager.getTransportTierManager("MachineBuffer_Transport");
        block11: for (int tier = 0; tier <= 13; ++tier) {
            if (tier <= 4) {
                ((TieredStatus)tierMachineTransport.get(autoExtractInterval)).put(tier, 20);
                ((TieredStatus)tierMachineTransport.get(autoInsertInterval)).put(tier, 20);
                ((TieredStatus)tierMachineTransport.get(maxAutoExtractDefault)).put(tier, 8);
                ((TieredStatus)tierMachineTransport.get(maxAutoInsertDefault)).put(tier, 8);
            } else if (tier == 5) {
                ((TieredStatus)tierMachineTransport.get(autoExtractInterval)).put(tier, 2);
                ((TieredStatus)tierMachineTransport.get(autoInsertInterval)).put(tier, 2);
                ((TieredStatus)tierMachineTransport.get(maxAutoExtractDefault)).put(tier, 16);
                ((TieredStatus)tierMachineTransport.get(maxAutoInsertDefault)).put(tier, 16);
            } else if (tier >= 6) {
                ((TieredStatus)tierMachineTransport.get(autoExtractInterval)).put(tier, 1);
                ((TieredStatus)tierMachineTransport.get(autoInsertInterval)).put(tier, 1);
                ((TieredStatus)tierMachineTransport.get(maxAutoExtractDefault)).put(tier, 64);
                ((TieredStatus)tierMachineTransport.get(maxAutoInsertDefault)).put(tier, 64);
            }
            if (tier <= 4) {
                ((TieredStatus)tierBufferTransport.get(autoExtractInterval)).put(tier, 8);
                ((TieredStatus)tierBufferTransport.get(autoInsertInterval)).put(tier, 8);
                ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 1);
                ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 1);
            }
            if (tier >= 7) {
                ((TieredStatus)tierBufferTransport.get(autoExtractInterval)).put(tier, 1);
                ((TieredStatus)tierBufferTransport.get(autoInsertInterval)).put(tier, 1);
            }
            switch (tier) {
                case 5: {
                    ((TieredStatus)tierBufferTransport.get(autoExtractInterval)).put(tier, 4);
                    ((TieredStatus)tierBufferTransport.get(autoInsertInterval)).put(tier, 4);
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 4);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 4);
                    continue block11;
                }
                case 6: {
                    ((TieredStatus)tierBufferTransport.get(autoExtractInterval)).put(tier, 2);
                    ((TieredStatus)tierBufferTransport.get(autoInsertInterval)).put(tier, 2);
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 16);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 16);
                    continue block11;
                }
                case 7: {
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 64);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 64);
                    continue block11;
                }
                case 8: {
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 128);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 128);
                    continue block11;
                }
                case 9: {
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 192);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 192);
                    continue block11;
                }
                case 10: {
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 256);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 256);
                    continue block11;
                }
                case 11: {
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 512);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 512);
                    continue block11;
                }
                case 12: {
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 1024);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 1024);
                    continue block11;
                }
                case 13: {
                    ((TieredStatus)tierBufferTransport.get(maxAutoExtractDefault)).put(tier, 6400);
                    ((TieredStatus)tierBufferTransport.get(maxAutoInsertDefault)).put(tier, 6400);
                }
            }
        }
    }

    public static void loadConfig(Configuration cfg) {
        tierSmelter.loadAllConfig(cfg);
        tierGeneric.loadAllConfig(cfg);
        tierCACondenser.loadAllConfig(cfg);
        tierMachineTransport.loadAllConfig(cfg);
        tierBufferTransport.loadAllConfig(cfg);
    }

    public static class TieredNumericStatus<T extends Number>
    extends TieredStatus<T> {
        public T min;
        public T max;

        public TieredNumericStatus(T defvalue) {
            super(defvalue);
        }

        public TieredNumericStatus(T defvalue, T min, T max) {
            this(defvalue);
            this.min = min;
            this.max = max;
        }

        @Override
        public Object getConfig(String name, Object value, Configuration cfg) {
            if (value instanceof Integer) {
                int minValue = this.min instanceof Integer ? (Integer)this.min : Integer.MIN_VALUE;
                int maxValue = this.max instanceof Integer ? (Integer)this.max : Integer.MAX_VALUE;
                return cfg.getInt(name, this.configCategory, ((Integer)value).intValue(), minValue, maxValue, this.configComment);
            }
            if (value instanceof Float) {
                float minValue = this.min instanceof Float ? ((Float)this.min).floatValue() : Float.MIN_VALUE;
                float maxValue = this.max instanceof Float ? ((Float)this.max).floatValue() : Float.MAX_VALUE;
                return Float.valueOf(cfg.getFloat(name, this.configCategory, ((Float)value).floatValue(), minValue, maxValue, this.configComment));
            }
            return super.getConfig(name, value, cfg);
        }
    }

    public static class TieredStatus<T>
    extends HashMap<Integer, T> {
        public T defvalue;
        public String configComment = "";
        public String configCategory = "tier balance";

        public TieredStatus(T defvalue) {
            this.defvalue = defvalue;
        }

        public T get(int tier) {
            return (T)(this.containsKey(tier) ? this.get((Object)tier) : this.defvalue);
        }

        public Object getConfig(String name, Object value, Configuration cfg) {
            if (value instanceof Boolean) {
                return cfg.getBoolean(name, this.configCategory, ((Boolean)value).booleanValue(), this.configComment);
            }
            if (value instanceof String) {
                return cfg.getString(name, this.configCategory, (String)value, this.configComment);
            }
            return null;
        }

        public void loadAllConfig(String name, Configuration cfg) {
            for (Integer tier : this.keySet()) {
                int t = tier;
                String cstr = String.format(name + "_%02d", t);
                Object value = this.getConfig(cstr, this.get(t), cfg);
                try {
                    this.put(t, value);
                }
                catch (Exception e) {
                    ClayiumCore.logger.error("Config Error @ " + cstr);
                    ClayiumCore.logger.catching((Throwable)e);
                }
            }
            if (this.defvalue != null) {
                String cstr = name + "_def";
                Object value = this.getConfig(cstr, this.defvalue, cfg);
                try {
                    this.defvalue = value;
                }
                catch (Exception e) {
                    ClayiumCore.logger.error("Config Error @ " + cstr);
                    ClayiumCore.logger.catching((Throwable)e);
                }
            }
        }
    }

    public static class TierManager
    extends HashMap<String, TieredStatus> {
        protected String configName;

        public static TierManager getMachineTierManager() {
            return TierManager.getMachineTierManager("");
        }

        public static TierManager getMachineTierManager(String configName) {
            TierManager res = new TierManager(configName);
            res.put(UtilTier.multCraftTime, new TieredNumericStatus<Float>(Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0E9f)));
            res.put(UtilTier.multConsumingEnergy, new TieredNumericStatus<Float>(Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0E9f)));
            return res;
        }

        public static TierManager getTransportTierManager(String configName) {
            TierManager res = new TierManager(configName);
            res.put(UtilTier.autoInsertInterval, new TieredNumericStatus<Integer>(20, 1, 999));
            res.put(UtilTier.autoExtractInterval, new TieredNumericStatus<Integer>(20, 1, 999));
            res.put(UtilTier.maxAutoInsertDefault, new TieredNumericStatus<Integer>(8, 1, 9999));
            res.put(UtilTier.maxAutoExtractDefault, new TieredNumericStatus<Integer>(8, 1, 9999));
            return res;
        }

        public TierManager(String configName) {
            this.configName = configName;
        }

        public static void applyMachineTierManager(Block[] machines, TierManager manager) {
            for (int i = 0; i < machines.length; ++i) {
                if (!(machines[i] instanceof ClayMachines)) continue;
                ((ClayMachines)machines[i]).multCraftTime = manager.getF(UtilTier.multCraftTime, i);
                ((ClayMachines)machines[i]).multConsumingEnergy = manager.getF(UtilTier.multConsumingEnergy, i);
            }
        }

        public static void applyTransportTierManager(TileClayContainer tile, int tier, TierManager manager) {
            tile.autoExtractInterval = manager.getI(UtilTier.autoExtractInterval, tier);
            tile.autoInsertInterval = manager.getI(UtilTier.autoInsertInterval, tier);
            tile.maxAutoExtractDefault = manager.getI(UtilTier.maxAutoExtractDefault, tier);
            tile.maxAutoInsertDefault = manager.getI(UtilTier.maxAutoInsertDefault, tier);
        }

        public Object get(String string, int tier) {
            return this.containsKey(string) ? ((TieredStatus)this.get(string)).get(tier) : null;
        }

        public float getF(String string, int tier) {
            Object res = this.get(string, tier);
            return res instanceof Float ? ((Float)res).floatValue() : 0.0f;
        }

        public int getI(String string, int tier) {
            Object res = this.get(string, tier);
            return res instanceof Integer ? (Integer)res : 0;
        }

        public void loadAllConfig(Configuration cfg) {
            for (Map.Entry entry : this.entrySet()) {
                ((TieredStatus)entry.getValue()).loadAllConfig(this.configName + "_" + (String)entry.getKey(), cfg);
            }
        }
    }
}


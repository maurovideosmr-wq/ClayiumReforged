/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.HashBiMap
 *  com.google.common.collect.Iterables
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.common.config.Configuration
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidRegistry$FluidRegisterEvent
 *  net.minecraftforge.fluids.FluidStack
 */
package mods.clayium.util;

import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import mods.clayium.core.ClayiumCore;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class UtilFluid {
    public static UtilFluid INSTANCE = new UtilFluid();
    static BiMap<Fluid, Integer> fluidIDs = HashBiMap.create();
    static BiMap<Integer, String> fluidNames = HashBiMap.create();
    static Set<String> overridableNames = new TreeSet<String>();
    static final int offset = 4096;
    static boolean useRegistry = false;
    static Configuration cfgFluidIDs = null;
    protected static final String prefix = "[FluidID Loader] ";
    protected static final Splitter splitter;
    protected static boolean overrideFlag;
    static int index;

    public static void log(String message) {
        if (ClayiumCore.cfgVerboseLoggingForFluidIDLoader) {
            ClayiumCore.logger.info(prefix + message);
        }
    }

    public static void loadMapsFromConfig(Configuration cfg) {
        boolean error = false;
        HashBiMap newFluidNames = HashBiMap.create();
        HashBiMap newFluidIDs = HashBiMap.create();
        HashBiMap loadedFluidNames = HashBiMap.create();
        cfgFluidIDs = cfg;
        UtilFluid.log("Started to load FluidID.");
        if (cfgFluidIDs != null && cfgFluidIDs.hasCategory("fluid") && cfgFluidIDs.getCategory("fluid").containsKey("IDsForCapsule")) {
            String[] cfgs = cfgFluidIDs.getStringList("IDsForCapsule", "fluid", new String[0], "ID Map for Clay Fluid Capsule.");
            for (String s : cfgs) {
                String[] result = (String[])Iterables.toArray((Iterable)splitter.split((CharSequence)s), String.class);
                if (result == null || result.length != 2) continue;
                loadedFluidNames.put((Object)Integer.parseInt(result[0]), (Object)result[1]);
                newFluidNames.put((Object)Integer.parseInt(result[0]), (Object)result[1]);
            }
            UtilFluid.log("Loading FluidID from cfg.");
            UtilFluid.log("  List of FluidIDs in cfg");
            for (Map.Entry entry : newFluidNames.entrySet()) {
                String fluidName = (String)entry.getValue();
                int newID = (Integer)entry.getKey();
                UtilFluid.log("    FluidID = " + newID + ", Fluid Name = " + fluidName);
            }
            UtilFluid.log("  List of loaded FluidIDs");
            for (Map.Entry entry : fluidNames.entrySet()) {
                String fluidName = (String)entry.getValue();
                int newID = (Integer)entry.getKey();
                UtilFluid.log("    FluidID = " + newID + ", Fluid Name = " + fluidName);
            }
            UtilFluid.log("Scanning already loaded Fluid list");
            for (Map.Entry entry : fluidNames.entrySet()) {
                int oldID;
                String fluidName = (String)entry.getValue();
                int newID = oldID = ((Integer)entry.getKey()).intValue();
                UtilFluid.log("  Checking FluidID = " + newID + ", Fluid Name = " + fluidName);
                if (loadedFluidNames.containsValue((Object)fluidName)) {
                    newID = (Integer)newFluidNames.inverse().get((Object)fluidName);
                    UtilFluid.log("    Found in cfg. Set FluidID to " + newID);
                } else {
                    while (newFluidNames.containsKey((Object)newID)) {
                        ++newID;
                    }
                    newFluidNames.put((Object)newID, (Object)fluidName);
                    UtilFluid.log("    Not found in cfg. Set FluidID to " + newID);
                }
                Fluid fluid = (Fluid)fluidIDs.inverse().get((Object)oldID);
                try {
                    newFluidIDs.put((Object)fluid, (Object)newID);
                }
                catch (IllegalArgumentException e) {
                    ClayiumCore.logger.error(prefix + e.getMessage());
                    error = true;
                }
            }
            fluidNames = newFluidNames;
            fluidIDs = newFluidIDs;
        }
        UtilFluid.log("Done.");
        if (error) {
            ClayiumCore.logger.error("[FluidID Loader] An exception occurred. The FluidID database may be corrupted.");
            ClayiumCore.logger.error("Enable B:VerboseLoggingForFluidIDLoader to get more information.");
        }
    }

    public static void saveMapsToConfig() {
        if (cfgFluidIDs != null) {
            String[] toSave = new String[fluidNames.size()];
            int pos = 0;
            for (Map.Entry entry : fluidNames.entrySet()) {
                String fluidName = (String)entry.getValue();
                int ID = (Integer)entry.getKey();
                toSave[pos] = ID + "=" + fluidName;
                ++pos;
            }
            cfgFluidIDs.get("fluid", "IDsForCapsule", new String[0]).set(toSave);
            cfgFluidIDs.save();
        }
    }

    @SubscribeEvent
    public void subscribeFluidRegisterEvent(FluidRegistry.FluidRegisterEvent event) {
        Fluid fluid = FluidRegistry.getFluid((String)event.fluidName);
        UtilFluid.registerFluid(fluid, 1, overrideFlag);
        UtilFluid.saveMapsToConfig();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static boolean registerFluid(Fluid fluid, int fluidID, boolean overrideFluidID) {
        while (fluidIDs.containsValue((Object)fluidID)) {
            ++fluidID;
        }
        String fluidName = fluid.getName();
        if (fluidNames.containsValue((Object)fluidName)) {
            if (overridableNames.contains(fluidName)) {
                if (!overrideFluidID) return false;
                int oldID = (Integer)fluidNames.inverse().get((Object)fluidName);
                fluidIDs.inverse().remove((Object)oldID);
                fluidNames.remove((Object)oldID);
            } else {
                fluidID = (Integer)fluidNames.inverse().get((Object)fluidName);
                if (fluidIDs.inverse().containsKey((Object)fluidID)) {
                    fluidIDs.inverse().remove((Object)fluidID);
                }
                fluidIDs.put((Object)fluid, (Object)fluidID);
                return true;
            }
        }
        fluidIDs.put((Object)fluid, (Object)fluidID);
        fluidNames.put((Object)fluidID, (Object)fluidName);
        return true;
    }

    public static void registerFluid(Fluid fluid) {
        UtilFluid.registerFluid(fluid, index, false);
        overridableNames.add(fluid.getName());
        ++index;
        overrideFlag = false;
        FluidRegistry.registerFluid((Fluid)fluid);
        overrideFlag = true;
    }

    public static int getFluidID(String fluidName) {
        if (useRegistry) {
            return FluidRegistry.getFluidID((String)fluidName);
        }
        Integer ret = (Integer)fluidIDs.get((Object)FluidRegistry.getFluid((String)fluidName));
        if (ret == null && fluidName != null && (ret = (Integer)fluidNames.inverse().get((Object)fluidName)) == null) {
            ClayiumCore.logger.error("Can't get Fluid ID! FluidName = " + fluidName);
        }
        return ret == null ? -1 : ret;
    }

    public static int getFluidID(Fluid fluid) {
        if (useRegistry) {
            return FluidRegistry.getFluidID((Fluid)fluid);
        }
        Integer ret = (Integer)fluidIDs.get((Object)fluid);
        if (ret == null && fluid != null) {
            return UtilFluid.getFluidID(fluid.getName());
        }
        return ret == null ? -1 : ret;
    }

    public static int getFluidID(FluidStack fluidStack) {
        return fluidStack != null ? UtilFluid.getFluidID(fluidStack.getFluid()) : -1;
    }

    public static Fluid getFluid(int fluidID) {
        return useRegistry ? FluidRegistry.getFluid((int)fluidID) : (Fluid)fluidIDs.inverse().get((Object)fluidID);
    }

    public static String getFluidName(int fluidID) {
        return useRegistry ? FluidRegistry.getFluidName((int)fluidID) : (String)fluidNames.get((Object)fluidID);
    }

    static {
        INSTANCE.subscribeFluidRegisterEvent(new FluidRegistry.FluidRegisterEvent("water", 1));
        INSTANCE.subscribeFluidRegisterEvent(new FluidRegistry.FluidRegisterEvent("lava", 2));
        splitter = Splitter.on((char)'=').limit(2);
        overrideFlag = true;
        index = 4096;
    }
}


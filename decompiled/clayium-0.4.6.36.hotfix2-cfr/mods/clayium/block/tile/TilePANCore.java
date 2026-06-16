/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.WorldServer
 *  net.minecraftforge.common.DimensionManager
 */
package mods.clayium.block.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import mods.clayium.block.CBlocks;
import mods.clayium.block.tile.TileChemicalMetalSeparator;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.block.tile.TileCobblestoneGenerator;
import mods.clayium.block.tile.TileSaltExtractor;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import mods.clayium.item.CShape;
import mods.clayium.pan.IPANComponent;
import mods.clayium.pan.IPANConversion;
import mods.clayium.pan.IPANConversionProvider;
import mods.clayium.pan.UtilPAN;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilItemStack;
import mods.clayium.util.UtilLocale;
import mods.clayium.util.crafting.IItemPattern;
import mods.clayium.util.crafting.WeightedList;
import mods.clayium.util.crafting.WeightedResult;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class TilePANCore
extends TileClayContainer {
    public static int refreshRate = 200;
    protected static Random random = new Random();
    protected int refreshPhase = 0;
    protected long lastTotalWorldTime = -1L;
    protected Set<ItemStackWithEnergy> conversionItemSet;
    protected Set<ItemStackWithEnergy> ingredientItemSet;
    protected Set<ItemStackWithEnergy> prohibitedItemSet;
    public List<IPANConversion> conversionList;
    private Set<Vec> conductors;
    private int depth;
    private int num;
    public static int maxDepth = 1000;
    public static int maxNum = 10000;

    public TilePANCore() {
        this.insertRoutes = new int[]{-1, -1, -1, 0, -1, -1};
        this.extractRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.autoInsert = true;
        this.autoExtract = true;
        this.containerItemStacks = new ItemStack[1];
        this.maxAutoExtractDefault = 64;
        this.maxAutoInsertDefault = 64;
        this.autoInsertInterval = 1;
        this.autoExtractInterval = 1;
        this.listSlotsInsert.clear();
        this.listSlotsExtract.clear();
        this.listSlotsInsert.add(new int[]{0});
        this.refreshPhase = random.nextInt(refreshRate);
    }

    public Set<ItemStackWithEnergy> getConversionItemSet() {
        return this.conversionItemSet;
    }

    public Set<ItemStackWithEnergy> getIngredientItemSet() {
        return this.ingredientItemSet;
    }

    public Set<ItemStackWithEnergy> getProhibitedItemSet() {
        return this.prohibitedItemSet;
    }

    @Override
    public void func_145845_h() {
        super.func_145845_h();
        if (this.lastTotalWorldTime != -1L) {
            this.refreshPhase -= (int)(this.field_145850_b.func_82737_E() - this.lastTotalWorldTime);
            if (this.refreshPhase > refreshRate) {
                this.refreshPhase = refreshRate;
            }
        }
        if (this.field_145850_b != null) {
            this.lastTotalWorldTime = this.field_145850_b.func_82737_E();
        }
        if (this.refreshPhase <= 0) {
            this.refreshPhase = refreshRate;
            this.refreshNetwork();
            if (this.getConversionItemSet() == null) {
                this.refreshItemSet();
            }
        }
    }

    public void refreshNetwork() {
        if (!this.field_145850_b.field_72995_K) {
            Vec v = Vec.get(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.field_73011_w.field_76574_g);
            this.conductors = new TreeSet<Vec>(new DistanceComparator(v));
            this.depth = 0;
            this.num = 0;
            this.conversionList = new ArrayList<IPANConversion>();
            this.search(v);
            for (Vec conductor : this.conductors) {
                IPANConversion[] conversions;
                TileEntity te = TilePANCore.getTileEntity(conductor);
                if (te instanceof TilePANCore) {
                    // empty if block
                }
                if (te instanceof IPANConversionProvider && (conversions = ((IPANConversionProvider)te).getConversion()) != null) {
                    this.conversionList.addAll(Arrays.asList(conversions));
                }
                if (!(te instanceof IPANComponent)) continue;
                ((IPANComponent)te).setPANCore(this, refreshRate * 2);
            }
        }
    }

    public void refreshItemSet() {
        boolean bl;
        if (this.field_145850_b.field_72995_K) {
            return;
        }
        TreeSet<ItemStackWithEnergy> clays = new TreeSet<ItemStackWithEnergy>(new ItemStackComparator());
        double d0 = 1.0;
        clays.add(new ItemStackWithEnergy(new ItemStack(Blocks.field_150435_aG), d0, d0));
        for (int i = 0; i < 13; ++i) {
            clays.add(new ItemStackWithEnergy(new ItemStack(CBlocks.blockCompressedClay, 1, i), d0 *= 10.0, d0));
        }
        TreeSet<ItemStackWithEnergy> basematerials = new TreeSet<ItemStackWithEnergy>(new ItemStackComparator());
        d0 = TileCobblestoneGenerator.progressMax * TileSaltExtractor.energyPerWork / 6;
        basematerials.add(new ItemStackWithEnergy(CMaterials.get(CMaterials.SALT, CMaterials.DUST), d0, d0));
        basematerials.add(new ItemStackWithEnergy(CMaterials.get(CMaterials.ANTIMATTER, CMaterials.GEM), 100000.0, 100000.0));
        TreeSet<ItemStackWithEnergy> prohibiteds = new TreeSet<ItemStackWithEnergy>(new ItemStackComparator());
        for (CMaterial material : new CMaterial[]{CMaterials.CLAY, CMaterials.DENSE_CLAY, CMaterials.IND_CLAY, CMaterials.ADVIND_CLAY, CMaterials.ANTIMATTER, CMaterials.OCTUPLE_CLAY, CMaterials.COMPRESSED_PURE_ANTIMATTER[0], CMaterials.COMPRESSED_PURE_ANTIMATTER[1], CMaterials.COMPRESSED_PURE_ANTIMATTER[2], CMaterials.COMPRESSED_PURE_ANTIMATTER[3], CMaterials.COMPRESSED_PURE_ANTIMATTER[4], CMaterials.COMPRESSED_PURE_ANTIMATTER[5], CMaterials.COMPRESSED_PURE_ANTIMATTER[6], CMaterials.COMPRESSED_PURE_ANTIMATTER[7], CMaterials.COMPRESSED_PURE_ANTIMATTER[8]}) {
            for (CShape shape : new CShape[]{CMaterials.PLATE, CMaterials.STICK, CMaterials.SHORT_STICK, CMaterials.RING, CMaterials.SMALL_RING, CMaterials.GEAR, CMaterials.BLADE, CMaterials.NEEDLE, CMaterials.DISC, CMaterials.SMALL_DISC, CMaterials.CYLINDER, CMaterials.PIPE, CMaterials.LARGE_BALL, CMaterials.LARGE_PLATE, CMaterials.GRINDING_HEAD, CMaterials.BEARING, CMaterials.SPINDLE, CMaterials.CUTTING_HEAD, CMaterials.WATER_WHEEL, CMaterials.BLOCK, CMaterials.BALL, CMaterials.DUST, CMaterials.INGOT, CMaterials.GEM}) {
                if (!CMaterials.exist(material, shape)) continue;
                prohibiteds.add(new ItemStackWithEnergy(CMaterials.get(material, shape), 0.0, 0.0));
            }
        }
        TreeSet<ItemStackWithEnergy> impuredusts = new TreeSet<ItemStackWithEnergy>(new ItemStackComparator());
        WeightedList<ItemStack> recipeResults = TileChemicalMetalSeparator.results;
        double base = TileChemicalMetalSeparator.baseConsumingEnergy * TileChemicalMetalSeparator.baseCraftTime + 1000;
        for (WeightedResult weightedResult : recipeResults) {
            double rate = (double)weightedResult.weight / (double)recipeResults.getWeightSum();
            impuredusts.add(new ItemStackWithEnergy((ItemStack)weightedResult.result, base / rate, base / rate));
        }
        TreeSet<ItemStackWithEnergy> list = new TreeSet<ItemStackWithEnergy>(new ItemStackComparator());
        list.add(new ItemStackWithEnergy(null, 0.0, 0.0));
        list.add(new ItemStackWithEnergy(new ItemStack(Blocks.field_150347_e), 1.0, 1.0));
        list.add(new ItemStackWithEnergy(new ItemStack(Blocks.field_150364_r), 1.0, 1.0));
        list.addAll(clays);
        list.addAll(impuredusts);
        list.addAll(basematerials);
        boolean bl2 = true;
        block4: while (bl) {
            bl = false;
            block5: for (int i = 0; i < this.conversionList.size(); ++i) {
                IItemPattern[] patterns;
                double cost = 0.0;
                double consumption = 0.0;
                for (IItemPattern pattern : patterns = this.conversionList.get(i).getPatterns()) {
                    if (pattern == null) continue;
                    double mincost = 0.0;
                    double minconsumption = Double.MAX_VALUE;
                    for (ItemStackWithEnergy ie : list) {
                        int n;
                        int stackSize = 1;
                        if (!pattern.match(ie.itemstack, false)) continue;
                        stackSize = pattern.getStackSize(ie.itemstack);
                        if (!(minconsumption > ie.consumption * (double)n)) continue;
                        minconsumption = ie.consumption * (double)stackSize;
                        mincost = ie.cost * (double)stackSize;
                    }
                    if (minconsumption == Double.MAX_VALUE) continue block5;
                    cost += mincost;
                    consumption += minconsumption;
                }
                ItemStack[] results = this.conversionList.get(i).getResults();
                consumption += this.conversionList.get(i).getEnergy();
                block8: for (ItemStack result : results) {
                    for (ItemStackWithEnergy l : list) {
                        if (!UtilItemStack.areTypeEqual(result, l.itemstack)) continue;
                        continue block8;
                    }
                    ItemStack r = result.func_77946_l();
                    r.field_77994_a = 1;
                    list.add(new ItemStackWithEnergy(r, cost / (double)Math.max(result.field_77994_a, 1), consumption / (double)Math.max(result.field_77994_a, 1)));
                }
                this.conversionList.remove(i);
                bl = true;
                continue block4;
            }
        }
        TreeSet<ItemStackWithEnergy> list2 = new TreeSet<ItemStackWithEnergy>(new ItemStackComparator());
        list2.addAll(list);
        list.removeAll(clays);
        prohibiteds.addAll(clays);
        list.removeAll(prohibiteds);
        this.prohibitedItemSet = prohibiteds;
        this.ingredientItemSet = list2;
        this.conversionItemSet = list;
    }

    public void debug(EntityPlayer player) {
        if (!player.field_70170_p.field_72995_K) {
            this.refreshNetwork();
            this.refreshItemSet();
            for (ItemStackWithEnergy itemStackWithEnergy : this.conversionItemSet) {
            }
        }
    }

    public void search(Vec v) {
        ++this.depth;
        ++this.num;
        this.conductors.add(v);
        if (this.depth < maxDepth && this.num < maxNum) {
            for (Vec u : TilePANCore.getConnection(v)) {
                if (this.conductors.contains(u)) continue;
                this.search(u);
            }
        }
        --this.depth;
    }

    public static Vec[] getConnection(Vec v) {
        ArrayList<Vec> neighbors = new ArrayList<Vec>(Arrays.asList(TilePANCore.getNeighbor(v)));
        ArrayList<Vec> res = new ArrayList<Vec>();
        for (Vec neighbor : neighbors) {
            if (!TilePANCore.isConductor(neighbor)) continue;
            res.add(neighbor);
        }
        return res.toArray(new Vec[0]);
    }

    public static boolean isConductor(Vec v) {
        return UtilPAN.isPANConductor((IBlockAccess)DimensionManager.getWorld((int)v.d), v.x, v.y, v.z);
    }

    public static Block getBlock(Vec v) {
        WorldServer world = DimensionManager.getWorld((int)v.d);
        return world == null ? null : world.func_147439_a(v.x, v.y, v.z);
    }

    public static TileEntity getTileEntity(Vec v) {
        WorldServer world = DimensionManager.getWorld((int)v.d);
        return world == null ? null : UtilBuilder.safeGetTileEntity((IBlockAccess)world, v.x, v.y, v.z);
    }

    public static Vec[] getNeighbor(Vec v) {
        return new Vec[]{Vec.get(v.x + 1, v.y, v.z, v.d), Vec.get(v.x - 1, v.y, v.z, v.d), Vec.get(v.x, v.y + 1, v.z, v.d), Vec.get(v.x, v.y - 1, v.z, v.d), Vec.get(v.x, v.y, v.z + 1, v.d), Vec.get(v.x, v.y, v.z - 1, v.d)};
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }

    public static class Vec {
        public int x;
        public int y;
        public int z;
        public int d;

        private Vec(int x, int y, int z, int d) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.d = d;
        }

        public static Vec get(int x, int y, int z, int d) {
            return new Vec(x, y, z, d);
        }

        public boolean equals(Object object) {
            return object instanceof Vec ? this.x == ((Vec)object).x && this.y == ((Vec)object).y && this.z == ((Vec)object).z && this.d == ((Vec)object).d : false;
        }

        public int hashCode() {
            return this.x + 23059 * this.y + 668243 * this.z + 9118819 * this.d;
        }

        public String toString() {
            return "(" + this.x + "," + this.y + "," + this.z + ";" + this.d + ")";
        }
    }

    public static class DistanceComparator
    implements Comparator<Vec> {
        protected Vec center;

        public DistanceComparator(Vec center) {
            this.center = center;
        }

        @Override
        public int compare(Vec o1, Vec o2) {
            double d2;
            double d1 = this.dimDistance(o1);
            if (d1 < (d2 = this.dimDistance(o2))) {
                return -1;
            }
            if (d1 > d2) {
                return 1;
            }
            d1 = this.coordDistance(o1);
            if (d1 < (d2 = this.coordDistance(o2))) {
                return -1;
            }
            if (d1 > d2) {
                return 1;
            }
            if (o1.x < o2.x) {
                return -1;
            }
            if (o1.x > o2.x) {
                return 1;
            }
            if (o1.z < o2.z) {
                return -1;
            }
            if (o1.z > o2.z) {
                return 1;
            }
            if (o1.y < o2.y) {
                return -1;
            }
            if (o1.y > o2.y) {
                return 1;
            }
            return 0;
        }

        protected double dimDistance(Vec o1) {
            return o1.d >= this.center.d ? (double)(o1.d - this.center.d) : (double)(this.center.d - o1.d) - 0.5;
        }

        protected double coordDistance(Vec o1) {
            double dx = (double)o1.x - (double)this.center.x;
            double dy = (double)o1.y - (double)this.center.y;
            double dz = (double)o1.z - (double)this.center.z;
            return dx * dx + dy * dy + dz * dz;
        }
    }

    public static class ItemStackComparator
    implements Comparator<ItemStackWithEnergy> {
        @Override
        public int compare(ItemStackWithEnergy o1, ItemStackWithEnergy o2) {
            int i2;
            ItemStack s1 = o1.itemstack;
            ItemStack s2 = o2.itemstack;
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 == null) {
                return -1;
            }
            if (s2 == null) {
                return 1;
            }
            int i1 = Item.func_150891_b((Item)s1.func_77973_b());
            if (i1 < (i2 = Item.func_150891_b((Item)s2.func_77973_b()))) {
                return -1;
            }
            if (i1 > i2) {
                return 1;
            }
            if (s1.func_77960_j() < s2.func_77960_j()) {
                return -1;
            }
            if (s1.func_77960_j() > s2.func_77960_j()) {
                return 1;
            }
            return 0;
        }
    }

    public static class ItemStackWithEnergy {
        public double cost;
        public double consumption;
        public ItemStack itemstack;

        public ItemStackWithEnergy(ItemStack itemstack, double cost, double consumption) {
            this.cost = cost;
            this.consumption = consumption;
            this.itemstack = itemstack != null ? itemstack.func_77946_l() : null;
        }

        public String toString() {
            return "[ " + (this.itemstack == null ? "null" : this.itemstack.func_82833_r()) + " : " + UtilLocale.ClayEnergyNumeral(this.cost) + "CE : " + UtilLocale.ClayEnergyNumeral(this.consumption) + "CE ]";
        }
    }
}


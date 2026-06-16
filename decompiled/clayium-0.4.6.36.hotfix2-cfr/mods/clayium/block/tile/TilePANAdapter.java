/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.ForgeDirection
 */
package mods.clayium.block.tile;

import java.util.ArrayList;
import java.util.List;
import mods.clayium.block.tile.TileClayContainerTiered;
import mods.clayium.pan.IPANAdapter;
import mods.clayium.pan.IPANAdapterConversionFactory;
import mods.clayium.pan.IPANConversion;
import mods.clayium.pan.IPANConversionProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TilePANAdapter
extends TileClayContainerTiered
implements IPANConversionProvider {
    protected int pageNum;
    protected ForgeDirection connectedDirection = null;
    protected boolean connected = false;
    public static List<IPANAdapterConversionFactory> conversionFactories = new ArrayList<IPANAdapterConversionFactory>();
    private int count = 0;
    protected IPANConversion[] conversions = null;
    protected boolean refreshFlag = true;
    protected boolean refreshResultSlotInstantFlag = true;
    protected boolean refreshResultSlotDelayedFlag = true;
    protected IPANConversion[][] conversionPages = new IPANConversion[6][8];
    protected boolean[][] pagesRefreshFlag = new boolean[6][8];

    public TilePANAdapter() {
        for (int i = 0; i < this.pagesRefreshFlag.length; ++i) {
            for (int j = 0; j < this.pagesRefreshFlag[i].length; ++j) {
                this.pagesRefreshFlag[i][j] = true;
            }
        }
    }

    public static void addConversionFactory(IPANAdapterConversionFactory factory) {
        if (conversionFactories == null) {
            conversionFactories = new ArrayList<IPANAdapterConversionFactory>();
        }
        conversionFactories.add(factory);
    }

    @Override
    public void initParams() {
        super.initParams();
        this.insertRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.extractRoutes = new int[]{-1, -1, -1, -1, -1, -1};
        this.autoInsert = false;
        this.autoExtract = false;
        this.containerItemStacks = new ItemStack[153];
        this.slotsDrop = new int[9];
        for (int i = 0; i < this.slotsDrop.length; ++i) {
            this.slotsDrop[i] = 144 + i;
        }
    }

    @Override
    public void initParamsByTier(int tier) {
        this.pageNum = 1;
        switch (tier) {
            case 11: {
                this.pageNum = 2;
                break;
            }
            case 12: {
                this.pageNum = 4;
                break;
            }
            case 13: {
                this.pageNum = 8;
            }
        }
    }

    public int getPageNum() {
        return this.pageNum;
    }

    @Override
    public void func_145845_h() {
        super.func_145845_h();
        ++this.count;
        if ((this.count % 40 == 0 && this.refreshResultSlotDelayedFlag || this.refreshResultSlotInstantFlag) && !this.field_145850_b.field_72995_K) {
            this.refreshResultSlot();
        }
    }

    public void refreshResultSlot() {
        this.getConversion();
        for (int p = 0; p < 8; ++p) {
            IPANConversion conversion;
            IPANConversion iPANConversion = conversion = this.connected ? this.getConversion(this.connectedDirection, p) : null;
            if (conversion != null) {
                ItemStack[] results = conversion.getResults();
                for (int i = 0; i < 9; ++i) {
                    this.containerItemStacks[9 + i + 18 * p] = i < results.length ? results[i] : null;
                }
                continue;
            }
            for (int i = 0; i < 9; ++i) {
                this.containerItemStacks[9 + i + 18 * p] = null;
            }
        }
        this.setSyncFlag();
        this.refreshResultSlotDelayedFlag = false;
        this.refreshResultSlotInstantFlag = false;
    }

    @Override
    public boolean func_94041_b(int slot, ItemStack itemstack) {
        if (slot % 18 >= 0 && slot % 18 < 9) {
            return true;
        }
        return super.func_94041_b(slot, itemstack);
    }

    public IPANConversion[] refreshConversion() {
        this.connectedDirection = null;
        this.connected = false;
        ArrayList<IPANConversion> res = new ArrayList<IPANConversion>();
        for (int p = 0; p < 8; ++p) {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                IPANConversion conversion;
                if (this.connectedDirection != null && this.connectedDirection != direction || (conversion = this.getConversion(direction, p)) == null) continue;
                this.connectedDirection = direction;
                this.connected = true;
                res.add(conversion);
            }
        }
        return this.connected ? res.toArray(new IPANConversion[0]) : null;
    }

    @Override
    public IPANConversion[] getConversion() {
        if (this.refreshFlag) {
            this.conversions = this.refreshConversion();
        }
        return this.conversions;
    }

    public void setRefreshConversionFlag() {
        this.refreshFlag = true;
        for (int i = 0; i < this.pagesRefreshFlag.length; ++i) {
            for (int j = 0; j < this.pagesRefreshFlag[i].length; ++j) {
                this.pagesRefreshFlag[i][j] = true;
            }
        }
    }

    public void onNeighborChange() {
        this.setRefreshConversionFlag();
        this.setRefreshResultSlotFlag();
        this.setSyncFlag();
    }

    public void onSlotChange() {
        this.setRefreshConversionFlag();
        this.setInstantRefreshResultSlotFlag();
        this.setSyncFlag();
    }

    public void setRefreshResultSlotFlag() {
        this.refreshResultSlotDelayedFlag = true;
    }

    public void setInstantRefreshResultSlotFlag() {
        this.refreshResultSlotInstantFlag = true;
    }

    public IPANConversion getConversion(ForgeDirection direction, int page) {
        int d = direction.ordinal();
        if (this.pagesRefreshFlag[d][page]) {
            this.conversionPages[d][page] = this.refreshConversion(direction, page);
        }
        return this.conversionPages[d][page];
    }

    public IPANConversion refreshConversion(ForgeDirection direction, int page) {
        for (IPANAdapterConversionFactory factory : conversionFactories) {
            IPANConversion conversion;
            if (!factory.match(this.field_145850_b, this.field_145851_c + direction.offsetX, this.field_145848_d + direction.offsetY, this.field_145849_e + direction.offsetZ) || (conversion = factory.getConversion(new InternalPANAdapter(direction, page))) == null) continue;
            return conversion;
        }
        return null;
    }

    @Override
    public void func_70295_k_() {
    }

    @Override
    public void func_70305_f() {
    }

    public class InternalPANAdapter
    implements IPANAdapter {
        int page = 0;
        ForgeDirection direction = null;

        public InternalPANAdapter(ForgeDirection direction, int page) {
            this.page = page;
            this.direction = direction;
        }

        @Override
        public ItemStack[] getPatternItems() {
            ItemStack[] res = new ItemStack[9];
            for (int i = 0; i < 9; ++i) {
                res[i] = TilePANAdapter.this.containerItemStacks[i + this.page * 18];
            }
            return res;
        }

        @Override
        public ItemStack[] getSubItems() {
            ItemStack[] res = new ItemStack[9];
            for (int i = 0; i < 9; ++i) {
                res[i] = TilePANAdapter.this.containerItemStacks[i + 144];
            }
            return res;
        }

        @Override
        public World getConnectedWorld() {
            return TilePANAdapter.this.field_145850_b;
        }

        @Override
        public int getConnectedXCoord() {
            return TilePANAdapter.this.field_145851_c + (this.direction != null ? this.direction.offsetX : 0);
        }

        @Override
        public int getConnectedYCoord() {
            return TilePANAdapter.this.field_145848_d + (this.direction != null ? this.direction.offsetY : 0);
        }

        @Override
        public int getConnectedZCoord() {
            return TilePANAdapter.this.field_145849_e + (this.direction != null ? this.direction.offsetZ : 0);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.StatCollector
 *  net.minecraft.world.World
 */
package mods.clayium.block.tile;

import java.util.ArrayList;
import mods.clayium.block.MetalChest;
import mods.clayium.block.tile.INormalInventory;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.item.CMaterial;
import mods.clayium.item.CMaterials;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class TileMetalChest
extends TileClayContainer
implements INormalInventory {
    public float lidAngle;
    public float prevLidAngle;
    public int numPlayersUsing;
    protected int containerX = 13;
    protected int containerY = 8;
    protected int containerP = 8;
    protected int materialId = 0;
    public CMaterial material = CMaterials.ALUMINIUM;
    public static int maxContainerSize = 936;

    public TileMetalChest() {
        this.insertRoutes = new int[]{0, 0, 0, 0, 0, 0};
        this.extractRoutes = new int[]{0, 0, 0, 0, 0, 0};
        this.autoInsert = false;
        this.autoExtract = false;
        this.containerItemStacks = new ItemStack[maxContainerSize];
        this.slotsDrop = new int[maxContainerSize];
        for (int i = 0; i < maxContainerSize; ++i) {
            this.slotsDrop[i] = i;
        }
    }

    @Override
    public int func_70302_i_() {
        return this.containerX * this.containerY * this.containerP;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
        super.onBlockPlacedBy(world, x, y, z, entity, itemstack);
        if (this.getMaterialId() == 0) {
            this.setMaterialId(itemstack.func_77960_j());
        }
    }

    @Override
    public boolean shouldRefresh() {
        return false;
    }

    @Override
    public boolean hasSpecialDrops() {
        return true;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, Block block, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ItemStack itemstack = TileMetalChest.getNormalDrop(world, block, metadata, fortune);
        itemstack.func_77964_b(this.getMaterialId());
        ret.add(itemstack);
        return ret;
    }

    public int getMaterialId() {
        return this.materialId;
    }

    public void setMaterialId(int id) {
        CMaterial material = CMaterials.getMaterialFromId(id);
        if (material != null) {
            this.materialId = id;
            this.material = material;
            this.containerX = MetalChest.getContainerX(this.material);
            this.containerY = MetalChest.getContainerY(this.material);
            this.containerP = MetalChest.getContainerP(this.material);
            int slotNum = this.containerX * this.containerY * this.containerP;
            int[] slots = new int[slotNum];
            int[] slots2 = new int[slotNum];
            for (int i = 0; i < slots.length; ++i) {
                slots[i] = i;
                slots2[i] = slots.length - i - 1;
            }
            this.listSlotsInsert.add(slots);
            this.listSlotsExtract.add(slots2);
        }
    }

    @Override
    public void func_145845_h() {
        super.func_145845_h();
        this.prevLidAngle = this.lidAngle;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f) {
            double d1 = (double)this.field_145851_c + 0.5;
            double d2 = (double)this.field_145849_e + 0.5;
            this.field_145850_b.func_72908_a(d1, (double)this.field_145848_d + 0.5, d2, "random.chestopen", 0.5f, this.field_145850_b.field_73012_v.nextFloat() * 0.1f + 0.9f);
        }
        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0f || this.numPlayersUsing > 0 && this.lidAngle < 1.0f) {
            float f2;
            float f1 = this.lidAngle;
            float f = 0.1f;
            this.lidAngle = this.numPlayersUsing > 0 ? (this.lidAngle += f) : (this.lidAngle -= f);
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            if (this.lidAngle < (f2 = 0.5f) && f1 >= f2) {
                double d2 = (double)this.field_145851_c + 0.5;
                double d0 = (double)this.field_145849_e + 0.5;
                this.field_145850_b.func_72908_a(d2, (double)this.field_145848_d + 0.5, d0, "random.chestclosed", 0.5f, this.field_145850_b.field_73012_v.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }

    @Override
    public void func_145841_b(NBTTagCompound tagCompound) {
        super.func_145841_b(tagCompound);
        tagCompound.func_74768_a("MaterialId", this.getMaterialId());
    }

    @Override
    public void func_145839_a(NBTTagCompound tagCompound) {
        this.setMaterialId(tagCompound.func_74762_e("MaterialId"));
        super.func_145839_a(tagCompound);
    }

    public boolean func_145842_c(int p_145842_1_, int p_145842_2_) {
        if (p_145842_1_ == 1) {
            this.numPlayersUsing = p_145842_2_;
            return true;
        }
        return super.func_145842_c(p_145842_1_, p_145842_2_);
    }

    @Override
    public void func_70295_k_() {
        if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
        }
        ++this.numPlayersUsing;
        this.field_145850_b.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q(), 1, this.numPlayersUsing);
        this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q());
        this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.func_145838_q());
    }

    @Override
    public void func_70305_f() {
        if (this.func_145838_q() instanceof MetalChest) {
            --this.numPlayersUsing;
            this.field_145850_b.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q(), 1, this.numPlayersUsing);
            this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q());
            this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.func_145838_q());
        }
    }

    @Override
    public String getDefaultInventoryName() {
        return StatCollector.func_74838_a((String)(MetalChest.getUnlocalizedMetalChestName(this.func_145838_q(), this.material) + ".name"));
    }

    @Override
    public boolean canExtractItemUnsafe(int slot, ItemStack itemstack, int route) {
        return route == 0;
    }

    @Override
    public boolean canInsertItemUnsafe(int slot, ItemStack itemstack, int route) {
        return route == 0;
    }

    @Override
    public int getInventoryX() {
        return this.containerX;
    }

    @Override
    public int getInventoryY() {
        return this.containerY;
    }

    @Override
    public int getInventoryP() {
        return this.containerP;
    }

    @Override
    public int getInventoryStart() {
        return 0;
    }

    @Override
    public void overrideTo(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, Block overriddenBlock, int overriddenMeta, Class overriddenTileEntityClass, NBTTagCompound overriddenTileEntityTag) {
        super.overrideTo(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ, overriddenBlock, overriddenMeta, overriddenTileEntityClass, overriddenTileEntityTag);
        NBTTagCompound tagCompound = overriddenTileEntityTag;
        if (tagCompound != null) {
            NBTTagList tagList = tagCompound.func_150295_c("Items", 10);
            this.containerItemStacks = new ItemStack[this.func_70302_i_()];
            for (int i = 0; i < tagList.func_74745_c(); ++i) {
                NBTTagCompound tagCompound1 = tagList.func_150305_b(i);
                short byte0 = tagCompound1.func_74765_d("Slot");
                if (byte0 < 0 || byte0 >= this.containerItemStacks.length) continue;
                this.containerItemStacks[byte0] = ItemStack.func_77949_a((NBTTagCompound)tagCompound1);
            }
        }
    }

    @Override
    public void onOverridden(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (itemStack != null) {
            CMaterial material = CMaterials.getMaterialFromId(itemStack.func_77960_j());
            int max = MetalChest.getContainerX(material) * MetalChest.getContainerY(material) * MetalChest.getContainerP(material);
            for (int i = 0; i < Math.min(max, this.containerItemStacks.length); ++i) {
                this.containerItemStacks[i] = null;
            }
        }
    }
}


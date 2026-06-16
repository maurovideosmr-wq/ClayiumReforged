/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.BlockPistonBase
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.ForgeDirection
 *  net.minecraftforge.fluids.IFluidHandler
 */
package mods.clayium.block;

import cofh.api.energy.IEnergyConnection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mods.clayium.block.ISpecialToolTip;
import mods.clayium.block.itemblock.IOverridableBlock;
import mods.clayium.block.tile.TileClayBuffer;
import mods.clayium.block.tile.TileClayContainer;
import mods.clayium.block.tile.TileFluidTranslator;
import mods.clayium.block.tile.TileGeneric;
import mods.clayium.block.tile.TileMultitrackBuffer;
import mods.clayium.block.tile.TileStorageContainer;
import mods.clayium.core.ClayiumCore;
import mods.clayium.util.PlayerKey;
import mods.clayium.util.UtilBuilder;
import mods.clayium.util.UtilDirection;
import mods.clayium.util.UtilKeyInput;
import mods.clayium.util.UtilLocale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class ClayContainer
extends BlockContainer
implements IOverridableBlock,
ISpecialToolTip {
    private final Random random = new Random();
    public Class<? extends TileEntity> tileEntityClass;
    public int guiId = -1;
    public int metaMode;
    protected boolean isTransparent = false;
    protected boolean isOverlayTransparent = false;
    protected boolean confirmFirstpass = true;
    private static UtilDirection tracingDirection = null;
    public static float pipeWidth = 0.1875f;
    @SideOnly(value=Side.CLIENT)
    public IIcon FrontIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon RightIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon LeftIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon BackIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon UpIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon DownIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon FrontOverlayIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon RightOverlayIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon LeftOverlayIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon BackOverlayIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon UpOverlayIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon DownOverlayIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon[] InsertIcons;
    @SideOnly(value=Side.CLIENT)
    public IIcon[] ExtractIcons;
    @SideOnly(value=Side.CLIENT)
    public IIcon FilterIcon;
    @SideOnly(value=Side.CLIENT)
    public IIcon[] InsertPipeIcons;
    @SideOnly(value=Side.CLIENT)
    public IIcon[] ExtractPipeIcons;
    public String machineIconStr;
    public static int renderPass = 0;
    protected ForgeDirection[][] validRotations = new ForgeDirection[][]{new ForgeDirection[0], {ForgeDirection.UP, ForgeDirection.DOWN}, ForgeDirection.VALID_DIRECTIONS};

    public ClayContainer(Material material, Class<? extends TileEntity> tileEntityClass, int metaMode) {
        this(material, tileEntityClass, null, metaMode);
    }

    public ClayContainer(Material material, Class<? extends TileEntity> tileEntityClass, int guiId, int metaMode) {
        this(material, tileEntityClass, null, metaMode);
        this.guiId = guiId;
    }

    public ClayContainer(Material material, Class<? extends TileEntity> tileEntityClass, String machineIconStr, int metaMode) {
        super(material);
        this.tileEntityClass = tileEntityClass;
        this.metaMode = metaMode;
        this.machineIconStr = machineIconStr;
    }

    public ClayContainer(Material material, Class<? extends TileEntity> tileEntityClass, String machineIconStr, int guiId, int metaMode) {
        this(material, tileEntityClass, machineIconStr, metaMode);
        this.guiId = guiId;
    }

    public void func_149749_a(World world, int x, int y, int z, Block block, int meta) {
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te instanceof TileClayContainer) {
            TileClayContainer tileentity = (TileClayContainer)te;
            for (int i : tileentity.getSlotsDrop()) {
                ItemStack itemstack = tileentity.func_70301_a(i);
                if (itemstack == null) continue;
                float f = this.random.nextFloat() * 0.6f + 0.1f;
                float f1 = this.random.nextFloat() * 0.6f + 0.1f;
                float f2 = this.random.nextFloat() * 0.6f + 0.1f;
                while (itemstack.field_77994_a > 0) {
                    int j = this.random.nextInt(21) + 10;
                    if (j > itemstack.field_77994_a) {
                        j = itemstack.field_77994_a;
                    }
                    itemstack.field_77994_a -= j;
                    EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
                    if (itemstack.func_77942_o()) {
                        entityitem.func_92059_d().func_77982_d((NBTTagCompound)itemstack.func_77978_p().func_74737_b());
                    }
                    float f3 = 0.025f;
                    entityitem.field_70159_w = (float)this.random.nextGaussian() * f3;
                    entityitem.field_70181_x = (float)this.random.nextGaussian() * f3 + 0.1f;
                    entityitem.field_70179_y = (float)this.random.nextGaussian() * f3;
                    world.func_72838_d((Entity)entityitem);
                }
            }
        }
        world.func_147453_f(x, y, z, block);
        if (te instanceof TileGeneric) {
            if (((TileGeneric)te).shouldRefresh() || ((TileGeneric)te).getRemoveFlag()) {
                world.func_147475_p(x, y, z);
            } else {
                ((TileGeneric)te).setRemoveFlag();
            }
        } else {
            super.func_149749_a(world, x, y, z, block, meta);
        }
    }

    public TileEntity func_149915_a(World world, int par2) {
        if (this.tileEntityClass == null) {
            return null;
        }
        try {
            return this.tileEntityClass.newInstance();
        }
        catch (Exception exception) {
            ClayiumCore.logger.catching((Throwable)exception);
            return null;
        }
    }

    public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        if (!(UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z) instanceof TileGeneric)) {
            return false;
        }
        if (UtilKeyInput.getKeyLength(player, PlayerKey.KeyType.SPRINT) < 0) {
            ItemStack itemStack = player.func_71045_bC();
            TileGeneric te = (TileGeneric)world.func_147438_o(x, y, z);
            if (te.isUsable(itemStack, player, side, hitX, hitY, hitZ)) {
                te.useItem(itemStack, player, side, hitX, hitY, hitZ);
                return true;
            }
            return this.onBlockRightClicked(world, x, y, z, player, side, hitX, hitY, hitZ);
        }
        return false;
    }

    protected void openGui(int guiId, World world, int x, int y, int z, EntityPlayer player) {
        player.openGui((Object)ClayiumCore.INSTANCE, guiId, world, x, y, z);
    }

    protected void openGui(World world, int x, int y, int z, EntityPlayer player) {
        Block block = world.func_147439_a(x, y, z);
        if (block instanceof ClayContainer && UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z) instanceof TileGeneric) {
            this.openGui(((ClayContainer)block).guiId, world, x, y, z, player);
        }
    }

    protected boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        world.func_147471_g(x, y, z);
        this.openGui(world, x, y, z, player);
        return true;
    }

    public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
        TileEntity tile = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        int m = -1;
        if (this.metaMode == 0) {
            m = 0;
        } else if (this.metaMode == 1) {
            int direction = MathHelper.func_76128_c((double)((double)(entity.field_70177_z * 4.0f / 360.0f) + 0.5)) & 3;
            if (direction == 0) {
                m = 2;
            }
            if (direction == 1) {
                m = 5;
            }
            if (direction == 2) {
                m = 3;
            }
            if (direction == 3) {
                m = 4;
            }
        } else if (this.metaMode == 2) {
            m = BlockPistonBase.func_150071_a((World)world, (int)x, (int)y, (int)z, (EntityLivingBase)entity);
        }
        if (m != -1) {
            world.func_72921_c(x, y, z, m, 2);
            if (tile != null) {
                tile.field_145847_g = m;
            }
        }
        if (itemstack.func_82837_s()) {
            // empty if block
        }
        if (world.func_147438_o(x, y, z) instanceof TileGeneric) {
            ((TileGeneric)world.func_147438_o(x, y, z)).onBlockPlacedBy(world, x, y, z, entity, itemstack);
        }
    }

    public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z) {
        return this.metaMode >= 0 && this.metaMode < this.validRotations.length ? this.validRotations[this.metaMode] : null;
    }

    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
        ForgeDirection orientation;
        ForgeDirection rotated;
        if (worldObj.field_72995_K) {
            return false;
        }
        ForgeDirection[] axes = this.getValidRotations(worldObj, x, y, z);
        if (axes == null || axes.length == 0) {
            return false;
        }
        boolean flag = false;
        ForgeDirection axis1 = axes[0];
        for (ForgeDirection axis2 : axes) {
            if (axis2 != axis) continue;
            axis1 = axis;
        }
        int meta = worldObj.func_72805_g(x, y, z);
        if (meta >= 6) {
            meta -= 6;
        }
        if ((meta = (rotated = (orientation = ForgeDirection.getOrientation((int)meta)).getRotation(axis1)).ordinal()) == -1) {
            return false;
        }
        worldObj.func_72921_c(x, y, z, meta, 3);
        TileEntity tile = UtilBuilder.safeGetTileEntity((IBlockAccess)worldObj, x, y, z);
        if (tile instanceof TileGeneric) {
            ((TileGeneric)tile).setRenderSyncFlag();
        }
        return true;
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> res = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z) instanceof TileGeneric && ((TileGeneric)UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z)).hasSpecialDrops() ? ((TileGeneric)UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z)).getDrops(world, x, y, z, (Block)this, metadata, fortune) : super.getDrops(world, x, y, z, metadata, fortune);
        if (UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z) instanceof TileGeneric && ((TileGeneric)UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z)).getRemoveFlag()) {
            world.func_147475_p(x, y, z);
        }
        return res;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
        UtilDirection frontDirection = UtilDirection.getOrientation(this.getFront(meta % 16));
        UtilDirection sideDirection = UtilDirection.getOrientation(side);
        switch (frontDirection.getSideOfDirection(sideDirection)) {
            case FRONTSIDE: {
                return this.FrontIcon;
            }
            case BACKSIDE: {
                return this.BackIcon;
            }
            case RIGHTSIDE: {
                return this.RightIcon;
            }
            case LEFTSIDE: {
                return this.LeftIcon;
            }
            case UPSIDE: {
                return this.UpIcon;
            }
            case DOWNSIDE: {
                return this.DownIcon;
            }
        }
        return this.field_149761_L;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getOverlayIcon(int side, int meta) {
        UtilDirection frontDirection = UtilDirection.getOrientation(this.getFront(meta % 16));
        UtilDirection sideDirection = UtilDirection.getOrientation(side);
        switch (frontDirection.getSideOfDirection(sideDirection)) {
            case FRONTSIDE: {
                return this.FrontOverlayIcon;
            }
            case BACKSIDE: {
                return this.BackOverlayIcon;
            }
            case RIGHTSIDE: {
                return this.RightOverlayIcon;
            }
            case LEFTSIDE: {
                return this.LeftOverlayIcon;
            }
            case UPSIDE: {
                return this.UpOverlayIcon;
            }
            case DOWNSIDE: {
                return this.DownOverlayIcon;
            }
        }
        return null;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
        return this.func_149691_a(side, this.getFront(world, x, y, z));
    }

    public int getFront(TileGeneric tile) {
        return this.getFront(tile.func_145832_p());
    }

    public int getFront(IBlockAccess world, int x, int y, int z) {
        return this.getFront(world.func_72805_g(x, y, z));
    }

    public int getFront(int meta) {
        return this.metaMode == 0 ? UtilDirection.NORTH.ordinal() : (meta < 6 ? meta : meta - 6);
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getOverlayIcon(IBlockAccess world, int x, int y, int z, int side) {
        return this.getOverlayIcon(side, this.getFront(world, x, y, z));
    }

    public int func_149692_a(int meta) {
        return 0;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_149666_a(Item item, CreativeTabs creativeTab, List list) {
        list.add(new ItemStack(item, 1, this.metaMode == 0 ? 0 : 0));
    }

    @SideOnly(value=Side.CLIENT)
    public void func_149651_a(IIconRegister par1IconRegister) {
        this.FilterIcon = par1IconRegister.func_94245_a("clayium:filter");
        if (this.machineIconStr != null && !this.machineIconStr.equals("")) {
            this.setSameIcons(par1IconRegister.func_94245_a(this.machineIconStr));
        }
        this.registerIOIcons(par1IconRegister);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerIOIcons(IIconRegister par1IconRegister) {
    }

    @SideOnly(value=Side.CLIENT)
    public void registerInsertIcons(IIconRegister par1IconRegister, String ... iconstrs) {
        if (iconstrs != null) {
            this.InsertIcons = new IIcon[iconstrs.length];
            this.InsertPipeIcons = new IIcon[iconstrs.length];
            for (int i = 0; i < iconstrs.length; ++i) {
                this.InsertIcons[i] = par1IconRegister.func_94245_a("clayium:" + iconstrs[i]);
                this.InsertPipeIcons[i] = par1IconRegister.func_94245_a("clayium:" + iconstrs[i] + "_p");
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerExtractIcons(IIconRegister par1IconRegister, String ... iconstrs) {
        if (iconstrs != null) {
            this.ExtractIcons = new IIcon[iconstrs.length];
            this.ExtractPipeIcons = new IIcon[iconstrs.length];
            for (int i = 0; i < iconstrs.length; ++i) {
                this.ExtractIcons[i] = par1IconRegister.func_94245_a("clayium:" + iconstrs[i]);
                this.ExtractPipeIcons[i] = par1IconRegister.func_94245_a("clayium:" + iconstrs[i] + "_p");
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon[] getInsertIcons(IBlockAccess world, int x, int y, int z) {
        return this.InsertIcons;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon[] getExtractIcons(IBlockAccess world, int x, int y, int z) {
        return this.ExtractIcons;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon[] getInsertPipeIcons(IBlockAccess world, int x, int y, int z) {
        return this.InsertPipeIcons;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon[] getExtractPipeIcons(IBlockAccess world, int x, int y, int z) {
        return this.ExtractPipeIcons;
    }

    public int func_149645_b() {
        return ClayiumCore.clayContainerRenderId;
    }

    public boolean func_149686_d() {
        return false;
    }

    public boolean renderAsPipe(IBlockAccess world, int x, int y, int z) {
        return world.func_72805_g(x, y, z) >= 6;
    }

    public void setSameIcons(IIcon iicon) {
        this.UpIcon = this.BackIcon = iicon;
        this.DownIcon = this.BackIcon;
        this.LeftIcon = this.BackIcon;
        this.RightIcon = this.BackIcon;
        this.FrontIcon = this.BackIcon;
    }

    public void setSameOverlayIcons(IIcon iicon) {
        this.UpOverlayIcon = this.BackOverlayIcon = iicon;
        this.DownOverlayIcon = this.BackOverlayIcon;
        this.LeftOverlayIcon = this.BackOverlayIcon;
        this.RightOverlayIcon = this.BackOverlayIcon;
        this.FrontOverlayIcon = this.BackOverlayIcon;
    }

    public boolean canChangeRenderType() {
        return true;
    }

    public boolean func_149662_c() {
        return !this.canChangeRenderType();
    }

    @SideOnly(value=Side.CLIENT)
    public int func_149701_w() {
        return this.isTransparent() || this.isOverlayTransparent() ? 1 : 0;
    }

    public boolean isTransparent() {
        return this.isTransparent;
    }

    public boolean isOverlayTransparent() {
        return this.isOverlayTransparent;
    }

    public void setTransparent() {
        this.isTransparent = true;
    }

    public void setOverlayTransparent() {
        this.isOverlayTransparent = true;
    }

    public void preventFirstPass() {
        this.confirmFirstpass = false;
    }

    public boolean canRenderInPass(int pass) {
        renderPass = pass;
        return pass == 0 && this.confirmFirstpass || pass == 1 && (!this.confirmFirstpass || this.isTransparent() || this.isOverlayTransparent());
    }

    public void setInitialBlockBounds() {
        this.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    public MovingObjectPosition func_149731_a(World world, int x, int y, int z, Vec3 start, Vec3 end) {
        UtilDirection[] directions;
        if (!this.renderAsPipe((IBlockAccess)world, x, y, z)) {
            super.func_149719_a((IBlockAccess)world, x, y, z);
            this.setInitialBlockBounds();
            return super.func_149731_a(world, x, y, z, start, end);
        }
        UtilDirection mindirection = null;
        MovingObjectPosition res = null;
        double o = pipeWidth;
        this.func_149676_a((float)(0.5 - o), (float)(0.5 - o), (float)(0.5 - o), (float)(0.5 + o), (float)(0.5 + o), (float)(0.5 + o));
        res = super.func_149731_a(world, x, y, z, start, end);
        for (UtilDirection direction : directions = new UtilDirection[]{UtilDirection.NORTH, UtilDirection.SOUTH, UtilDirection.EAST, UtilDirection.WEST, UtilDirection.UP, UtilDirection.DOWN}) {
            if (!this.checkPipeConnection((IBlockAccess)world, x, y, z, direction)) continue;
            this.func_149676_a((float)(0.5 - o + (direction.offsetX == 1 ? o * 2.0 : 0.0) - (direction.offsetX == -1 ? 0.5 - o : 0.0)), (float)(0.5 - o + (direction.offsetY == 1 ? o * 2.0 : 0.0) - (direction.offsetY == -1 ? 0.5 - o : 0.0)), (float)(0.5 - o + (direction.offsetZ == 1 ? o * 2.0 : 0.0) - (direction.offsetZ == -1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetX == -1 ? o * 2.0 : 0.0) + (direction.offsetX == 1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetY == -1 ? o * 2.0 : 0.0) + (direction.offsetY == 1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetZ == -1 ? o * 2.0 : 0.0) + (direction.offsetZ == 1 ? 0.5 - o : 0.0)));
            MovingObjectPosition pos = super.func_149731_a(world, x, y, z, start, end);
            if (pos == null || res != null && !(pos.field_72307_f.func_72438_d(start) < res.field_72307_f.func_72438_d(start))) continue;
            res = pos;
            mindirection = direction;
        }
        this.func_149676_a((float)(0.5 - o), (float)(0.5 - o), (float)(0.5 - o), (float)(0.5 + o), (float)(0.5 + o), (float)(0.5 + o));
        tracingDirection = mindirection;
        return res;
    }

    public boolean checkPipeConnection(IBlockAccess world, int x, int y, int z, UtilDirection direction) {
        int i2;
        TileEntity te2;
        TileEntity te1 = UtilBuilder.safeGetTileEntity(world, x, y, z);
        int i1 = ClayContainer.getConnectionAsImport(te1, direction, te2 = direction.getTileEntity(world, x, y, z));
        if (i1 >= 0 && i1 + (i2 = ClayContainer.getConnectionAsExport(te2, direction.getOpposite(), te1)) >= 1) {
            return true;
        }
        i1 = ClayContainer.getConnectionAsExport(te1, direction, te2);
        return i1 >= 0 && i1 + (i2 = ClayContainer.getConnectionAsImport(te2, direction.getOpposite(), te1)) >= 1;
    }

    public static int getConnectionAsImport(TileEntity tile, UtilDirection from, TileEntity tile2) {
        if (tile instanceof IInventory) {
            if (tile instanceof IEnergyConnection && ((IEnergyConnection)tile).canConnectEnergy(from.toForgeDirection()) && tile2 instanceof IEnergyConnection && ((IEnergyConnection)tile2).canConnectEnergy(from.getOpposite().toForgeDirection())) {
                return 2;
            }
            if (!(tile instanceof TileClayContainer)) {
                return 1;
            }
            TileClayContainer container = (TileClayContainer)tile;
            int i = UtilDirection.direction2Side(container.getFrontDirection(), from.ordinal()) - 6;
            if (container instanceof TileFluidTranslator && tile2 instanceof IFluidHandler && !(tile2 instanceof TileFluidTranslator)) {
                return 2;
            }
            if (container.insertRoutes[i] != -1) {
                return container.autoExtract ? 1 : 0;
            }
            return container instanceof TileClayBuffer || container instanceof TileMultitrackBuffer || container instanceof TileStorageContainer ? 0 : -1;
        }
        return -1;
    }

    public static int getConnectionAsExport(TileEntity tile, UtilDirection to, TileEntity tile2) {
        if (tile instanceof IInventory) {
            if (tile instanceof IEnergyConnection && ((IEnergyConnection)tile).canConnectEnergy(to.toForgeDirection()) && tile2 instanceof IEnergyConnection && ((IEnergyConnection)tile2).canConnectEnergy(to.getOpposite().toForgeDirection())) {
                return 2;
            }
            if (!(tile instanceof TileClayContainer)) {
                return 1;
            }
            TileClayContainer container = (TileClayContainer)tile;
            int i = UtilDirection.direction2Side(container.getFrontDirection(), to.ordinal()) - 6;
            if (container instanceof TileFluidTranslator && tile2 instanceof IFluidHandler && !(tile2 instanceof TileFluidTranslator)) {
                return 2;
            }
            if (container.extractRoutes[i] != -1) {
                return container.autoInsert ? 1 : 0;
            }
            return container instanceof TileClayBuffer || container instanceof TileMultitrackBuffer || container instanceof TileStorageContainer ? 0 : -1;
        }
        return -1;
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB func_149633_g(World world, int x, int y, int z) {
        if (!this.renderAsPipe((IBlockAccess)world, x, y, z)) {
            super.func_149719_a((IBlockAccess)world, x, y, z);
            this.setInitialBlockBounds();
        } else {
            double o = pipeWidth;
            if (tracingDirection == null) {
                this.func_149676_a((float)(0.5 - o), (float)(0.5 - o), (float)(0.5 - o), (float)(0.5 + o), (float)(0.5 + o), (float)(0.5 + o));
            } else {
                UtilDirection direction = tracingDirection;
                this.func_149676_a((float)(0.5 - o + (direction.offsetX == 1 ? o * 2.0 : 0.0) - (direction.offsetX == -1 ? 0.5 - o : 0.0)), (float)(0.5 - o + (direction.offsetY == 1 ? o * 2.0 : 0.0) - (direction.offsetY == -1 ? 0.5 - o : 0.0)), (float)(0.5 - o + (direction.offsetZ == 1 ? o * 2.0 : 0.0) - (direction.offsetZ == -1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetX == -1 ? o * 2.0 : 0.0) + (direction.offsetX == 1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetY == -1 ? o * 2.0 : 0.0) + (direction.offsetY == 1 ? 0.5 - o : 0.0)), (float)(0.5 + o - (direction.offsetZ == -1 ? o * 2.0 : 0.0) + (direction.offsetZ == 1 ? 0.5 - o : 0.0)));
            }
        }
        return super.func_149633_g(world, x, y, z);
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getNormalSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return super.func_149633_g(world, x, y, z);
    }

    public AxisAlignedBB func_149668_a(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        this.func_149719_a((IBlockAccess)p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.func_149668_a(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    public void func_149719_a(IBlockAccess world, int x, int y, int z) {
        if (!this.renderAsPipe(world, x, y, z)) {
            super.func_149719_a(world, x, y, z);
            this.setInitialBlockBounds();
        }
    }

    public void func_149743_a(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity p_149743_7_) {
        if (!this.renderAsPipe((IBlockAccess)world, x, y, z)) {
            super.func_149743_a(world, x, y, z, aabb, list, p_149743_7_);
        } else {
            UtilDirection[] directions;
            double o = pipeWidth;
            AxisAlignedBB axisalignedbb1 = AxisAlignedBB.func_72330_a((double)((double)x + 0.5 - o), (double)((double)y + 0.5 - o), (double)((double)z + 0.5 - o), (double)((double)x + 0.5 + o), (double)((double)y + 0.5 + o), (double)((double)z + 0.5 + o));
            if (axisalignedbb1 != null && aabb.func_72326_a(axisalignedbb1)) {
                list.add(axisalignedbb1);
            }
            for (UtilDirection direction : directions = new UtilDirection[]{UtilDirection.NORTH, UtilDirection.SOUTH, UtilDirection.EAST, UtilDirection.WEST, UtilDirection.UP, UtilDirection.DOWN}) {
                TileEntity te = direction.getTileEntity((IBlockAccess)world, x, y, z);
                if (te == null || !(te instanceof IInventory) || (axisalignedbb1 = AxisAlignedBB.func_72330_a((double)((double)x + 0.5 - o + (direction.offsetX == 1 ? o * 2.0 : 0.0) - (direction.offsetX == -1 ? 0.5 - o : 0.0)), (double)((double)y + 0.5 - o + (direction.offsetY == 1 ? o * 2.0 : 0.0) - (direction.offsetY == -1 ? 0.5 - o : 0.0)), (double)((double)z + 0.5 - o + (direction.offsetZ == 1 ? o * 2.0 : 0.0) - (direction.offsetZ == -1 ? 0.5 - o : 0.0)), (double)((double)x + 0.5 + o - (direction.offsetX == -1 ? o * 2.0 : 0.0) + (direction.offsetX == 1 ? 0.5 - o : 0.0)), (double)((double)y + 0.5 + o - (direction.offsetY == -1 ? o * 2.0 : 0.0) + (direction.offsetY == 1 ? 0.5 - o : 0.0)), (double)((double)z + 0.5 + o - (direction.offsetZ == -1 ? o * 2.0 : 0.0) + (direction.offsetZ == 1 ? 0.5 - o : 0.0)))) == null || !aabb.func_72326_a(axisalignedbb1)) continue;
                list.add(axisalignedbb1);
            }
        }
    }

    public void addNormalCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity p_149743_7_) {
        super.func_149743_a(world, x, y, z, aabb, list, p_149743_7_);
    }

    @Override
    public void overrideTo(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, Block overriddenBlock, int overriddenMeta, Class overriddenTileEntityClass, NBTTagCompound overriddenTileEntityTag) {
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te instanceof IOverridableBlock) {
            ((IOverridableBlock)te).overrideTo(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ, overriddenBlock, overriddenMeta, overriddenTileEntityClass, overriddenTileEntityTag);
        }
    }

    @Override
    public boolean canOverride(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te instanceof IOverridableBlock) {
            return ((IOverridableBlock)te).canOverride(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
        return false;
    }

    @Override
    public void onOverridden(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        TileEntity te = UtilBuilder.safeGetTileEntity((IBlockAccess)world, x, y, z);
        if (te instanceof IOverridableBlock) {
            ((IOverridableBlock)te).onOverridden(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
    }

    @Override
    public List getTooltip(ItemStack itemStack) {
        return UtilLocale.localizeTooltip(this.func_149739_a() + ".tooltip");
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.GameData
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidContainerRegistry
 *  net.minecraftforge.fluids.FluidContainerRegistry$FluidContainerData
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  org.lwjgl.opengl.GL11
 */
package mods.clayium.item;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import mods.clayium.gui.IMultipleRenderIcons;
import mods.clayium.gui.MultipleIcon;
import mods.clayium.item.ItemDamaged;
import mods.clayium.util.UtilFluid;
import mods.clayium.util.crafting.ISpecialResultRecipe;
import mods.clayium.util.crafting.RecipeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class ItemCapsule
extends ItemDamaged {
    private int capacity = 1000;
    private IMultipleRenderIcons icon;
    private static volatile List<ItemCapsule> listCapsules;

    public int getCapacity() {
        return this.capacity;
    }

    public ItemCapsule(int capacity, IMultipleRenderIcons icon) {
        this.capacity = capacity;
        this.icon = icon;
    }

    public ItemCapsule(int capacity, String iconName) {
        this(capacity, new MultipleIcon(3).addIcons(iconName + "_mask", iconName + "_mask", iconName));
    }

    public ItemStack getCapsule(String fluidname, int stackSize) {
        return fluidname == null ? new ItemStack((Item)this, stackSize) : new ItemStack((Item)this, stackSize, UtilFluid.getFluidID(fluidname));
    }

    public ItemStack getCapsule(String fluidname) {
        return this.getCapsule(fluidname, 1);
    }

    public String getFluidNameFromItemStack(ItemStack itemstack) {
        return itemstack == null ? null : UtilFluid.getFluidName(itemstack.func_77960_j());
    }

    public Fluid getFluidFromItemStack(ItemStack itemstack) {
        return itemstack == null ? null : UtilFluid.getFluid(itemstack.func_77960_j());
    }

    public FluidStack getFluidStackFromItemStack(ItemStack itemstack) {
        Fluid fluid = this.getFluidFromItemStack(itemstack);
        return fluid == null ? null : new FluidStack(fluid, this.getCapacity());
    }

    public void registerFluidContainer(boolean display) {
        this.addItemList(String.valueOf(0), 0, this.icon);
        ItemStack empty = this.getCapsule(null);
        for (String fluidname : FluidRegistry.getRegisteredFluids().keySet()) {
            this.addItemList(String.valueOf(UtilFluid.getFluidID(fluidname)), UtilFluid.getFluidID(fluidname), this.icon, display);
            ItemStack itemstack = this.getCapsule(fluidname);
            FluidContainerRegistry.registerFluidContainer((FluidStack)this.getFluidStackFromItemStack(itemstack), (ItemStack)itemstack.func_77946_l(), (ItemStack)empty.func_77946_l());
        }
    }

    public static void registerCompressionRecipe(ItemCapsule[] items, int[] compressionChain) {
        for (int i = 0; i < compressionChain.length; ++i) {
            ItemCapsule.registerCompressionRecipe(items[i], items[i + 1], compressionChain[i]);
        }
    }

    public static void registerCompressionRecipe(ItemCapsule ingred, ItemCapsule result, int num) {
        GameRegistry.addRecipe((IRecipe)new RecipeCapsuleCompression(ingred, num, result, 1));
        GameRegistry.addRecipe((IRecipe)new RecipeCapsuleCompression(result, 1, ingred, num));
    }

    public static synchronized List<ItemCapsule> getAllCapsules() {
        if (listCapsules == null) {
            listCapsules = new ArrayList<ItemCapsule>();
            for (Item item : GameData.getItemRegistry().typeSafeIterable()) {
                if (!(item instanceof ItemCapsule)) continue;
                listCapsules.add((ItemCapsule)item);
            }
        }
        return listCapsules;
    }

    public static ItemStack getItemCapsuleFromFluidStack(FluidStack fluid) {
        if (fluid == null) {
            return null;
        }
        for (ItemCapsule capsule : ItemCapsule.getAllCapsules()) {
            if (capsule.getCapacity() != fluid.amount) continue;
            return new ItemStack((Item)capsule, 1, UtilFluid.getFluidID(fluid));
        }
        return null;
    }

    @Override
    public String func_77667_c(ItemStack itemStack) {
        return super.func_77658_a();
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
        super.func_77624_a(itemstack, player, list, flag);
        Fluid fluid = this.getFluidFromItemStack(itemstack);
        if (fluid != null) {
            list.add(fluid.getLocalizedName(this.getFluidStackFromItemStack(itemstack)) + "(" + this.getFluidNameFromItemStack(itemstack) + ")");
        }
        list.add(this.getCapacity() + "mB");
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 1) {
            Fluid fluid = this.getFluidFromItemStack(stack);
            if (fluid != null) {
                return fluid.getIcon();
            }
            return Blocks.field_150435_aG.func_149691_a(0, 0);
        }
        return this.func_77618_c(stack.func_77960_j(), pass);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public int func_82790_a(ItemStack itemstack, int pass) {
        if (pass == 1) {
            Fluid fluid = this.getFluidFromItemStack(itemstack);
            if (fluid != null) {
                return fluid.getColor(this.getFluidStackFromItemStack(itemstack));
            }
            return 0xFFFFFF;
        }
        return super.func_82790_a(itemstack, pass);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public int func_94901_k() {
        return this.renderPass == 1 ? 0 : 1;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void preRenderItemPass(IItemRenderer.ItemRenderType type, ItemStack itemstack, int pass, Object ... data) {
        super.preRenderItemPass(type, itemstack, pass, data);
        if (pass == 1) {
            GL11.glDepthMask((boolean)false);
            GL11.glDepthFunc((int)514);
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void postRenderItemPass(IItemRenderer.ItemRenderType type, ItemStack itemstack, int pass, Object ... data) {
        super.postRenderItemPass(type, itemstack, pass, data);
        if (pass == 1) {
            GL11.glDepthMask((boolean)true);
            GL11.glDepthFunc((int)515);
        }
    }

    public static class RecipeCapsuleUnpackaging
    implements IRecipe,
    ISpecialResultRecipe {
        @Override
        public RecipeMap[] getUsageRecipeMap(ItemStack ingredient) {
            if (ingredient == null) {
                return null;
            }
            if (ingredient.func_77973_b() instanceof ItemCapsule) {
                if (ingredient.func_77960_j() != 0) {
                    ItemStack ingredtemp = ingredient.func_77946_l();
                    ingredtemp.field_77994_a = 1;
                    ArrayList<RecipeMap> list = new ArrayList<RecipeMap>();
                    for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                        if (((ItemCapsule)ingredient.func_77973_b()).getCapacity() != data.fluid.amount || ingredient.func_77960_j() != UtilFluid.getFluidID(data.fluid) || data.emptyContainer == null || data.filledContainer.func_77973_b() instanceof ItemCapsule) continue;
                        list.add(new RecipeMap(new ItemStack[]{data.emptyContainer.func_77946_l(), ingredtemp.func_77946_l()}, data.filledContainer.func_77946_l(), "shapeless"));
                    }
                    return list.toArray(new RecipeMap[0]);
                }
                return null;
            }
            return null;
        }

        @Override
        public RecipeMap[] getCraftingRecipeMap(ItemStack result) {
            if (result == null) {
                return null;
            }
            if (!(result.func_77973_b() instanceof ItemCapsule) && FluidContainerRegistry.isFilledContainer((ItemStack)result)) {
                ItemStack empty = FluidContainerRegistry.drainFluidContainer((ItemStack)result);
                if (empty == null) {
                    return null;
                }
                ArrayList<RecipeMap> list = new ArrayList<RecipeMap>();
                ItemStack resulttemp = result.func_77946_l();
                resulttemp.field_77994_a = 1;
                FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem((ItemStack)resulttemp);
                for (ItemCapsule capsule : ItemCapsule.getAllCapsules()) {
                    if (capsule.getCapacity() != fluid.amount) continue;
                    list.add(new RecipeMap(new ItemStack[]{empty.func_77946_l(), new ItemStack((Item)capsule, 1, UtilFluid.getFluidID(fluid))}, resulttemp.func_77946_l(), "shapeless"));
                }
                return list.toArray(new RecipeMap[0]);
            }
            return null;
        }

        public boolean func_77569_a(InventoryCrafting inv, World p_77569_2_) {
            return this.func_77572_b(inv) != null;
        }

        public ItemStack func_77572_b(InventoryCrafting inv) {
            if (!this.checkPlacedItem(inv)) {
                return null;
            }
            ItemStack fluidContainer = this.getFluidContainer(inv);
            if (fluidContainer == null) {
                return null;
            }
            ItemStack capsule = this.getCapsule(inv);
            if (capsule == null) {
                return null;
            }
            ItemCapsule itemCapsule = (ItemCapsule)capsule.func_77973_b();
            ItemStack filledContainer = FluidContainerRegistry.fillFluidContainer((FluidStack)itemCapsule.getFluidStackFromItemStack(capsule), (ItemStack)fluidContainer);
            if (filledContainer == null || itemCapsule.getCapacity() != FluidContainerRegistry.getContainerCapacity((ItemStack)filledContainer)) {
                return null;
            }
            if (capsule.func_77942_o()) {
                filledContainer.func_77982_d((NBTTagCompound)capsule.func_77978_p().func_74737_b());
            }
            return filledContainer.func_77946_l();
        }

        private boolean checkPlacedItem(InventoryCrafting inv) {
            if (inv == null) {
                return false;
            }
            int count = 0;
            for (int i = 0; i < inv.func_70302_i_(); ++i) {
                if (inv.func_70301_a(i) != null) {
                    ++count;
                }
                if (count <= 2) continue;
                return false;
            }
            return count == 2;
        }

        private ItemStack getFluidContainer(InventoryCrafting inv) {
            if (inv == null) {
                return null;
            }
            ItemStack ret = null;
            for (int i = 0; i < inv.func_70302_i_(); ++i) {
                ItemStack item = inv.func_70301_a(i);
                if (item == null || item.func_77973_b() instanceof ItemCapsule || !FluidContainerRegistry.isEmptyContainer((ItemStack)item)) continue;
                if (ret != null) {
                    return null;
                }
                ret = item.func_77946_l();
            }
            return ret;
        }

        private ItemStack getCapsule(InventoryCrafting inv) {
            if (inv == null) {
                return null;
            }
            ItemStack ret = null;
            for (int i = 0; i < inv.func_70302_i_(); ++i) {
                ItemStack item = inv.func_70301_a(i);
                if (item == null || !(item.func_77973_b() instanceof ItemCapsule) || item.func_77960_j() == 0) continue;
                if (ret != null) {
                    return null;
                }
                ret = item.func_77946_l();
            }
            return ret;
        }

        public int func_77570_a() {
            return 2;
        }

        public ItemStack func_77571_b() {
            return null;
        }
    }

    public static class RecipeCapsulePackaging
    implements IRecipe,
    ISpecialResultRecipe {
        @Override
        public RecipeMap[] getUsageRecipeMap(ItemStack ingredient) {
            if (ingredient == null) {
                return null;
            }
            ItemStack ingredtemp = ingredient.func_77946_l();
            ingredtemp.field_77994_a = 1;
            if (ingredtemp.func_77973_b() instanceof ItemCapsule) {
                if (ingredtemp.func_77960_j() == 0) {
                    ArrayList<RecipeMap> list = new ArrayList<RecipeMap>();
                    for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                        if (((ItemCapsule)ingredient.func_77973_b()).getCapacity() != data.fluid.amount || !(data.filledContainer.func_77973_b() instanceof ItemCapsule)) continue;
                        list.add(new RecipeMap(new ItemStack[]{data.filledContainer.func_77946_l(), ingredtemp.func_77946_l()}, new ItemStack(ingredtemp.func_77973_b(), 1, UtilFluid.getFluidID(data.fluid)), "shapeless"));
                    }
                    return list.toArray(new RecipeMap[0]);
                }
                return null;
            }
            if (FluidContainerRegistry.isFilledContainer((ItemStack)ingredtemp)) {
                ArrayList<RecipeMap> list = new ArrayList<RecipeMap>();
                FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem((ItemStack)ingredtemp);
                for (ItemCapsule capsule : ItemCapsule.getAllCapsules()) {
                    if (capsule.getCapacity() != fluid.amount) continue;
                    list.add(new RecipeMap(new ItemStack[]{ingredtemp.func_77946_l(), new ItemStack((Item)capsule)}, new ItemStack((Item)capsule, 1, UtilFluid.getFluidID(fluid)), "shapeless"));
                }
                return list.toArray(new RecipeMap[0]);
            }
            return null;
        }

        @Override
        public RecipeMap[] getCraftingRecipeMap(ItemStack result) {
            if (result == null) {
                return null;
            }
            if (result.func_77973_b() instanceof ItemCapsule && result.func_77960_j() != 0) {
                ArrayList<RecipeMap> list = new ArrayList<RecipeMap>();
                ItemStack resulttemp = result.func_77946_l();
                resulttemp.field_77994_a = 1;
                ItemCapsule capsule = (ItemCapsule)resulttemp.func_77973_b();
                for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                    if (capsule.getCapacity() != data.fluid.amount || resulttemp.func_77960_j() != UtilFluid.getFluidID(data.fluid) || data.filledContainer.func_77973_b() instanceof ItemCapsule) continue;
                    list.add(new RecipeMap(new ItemStack[]{data.filledContainer.func_77946_l(), new ItemStack((Item)capsule)}, resulttemp.func_77946_l(), "shapeless"));
                }
                return list.toArray(new RecipeMap[0]);
            }
            return null;
        }

        public boolean func_77569_a(InventoryCrafting inv, World p_77569_2_) {
            return this.checkPlacedItem(inv) && this.getFluidContainer(inv) != null && this.getCapsule(inv) != null;
        }

        public ItemStack func_77572_b(InventoryCrafting inv) {
            if (!this.checkPlacedItem(inv)) {
                return null;
            }
            ItemStack fluidContainer = this.getFluidContainer(inv);
            if (fluidContainer == null) {
                return null;
            }
            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem((ItemStack)fluidContainer);
            if (fluid == null) {
                return null;
            }
            ItemStack capsule = this.getCapsule(inv);
            if (capsule == null) {
                return null;
            }
            ItemCapsule itemCapsule = (ItemCapsule)capsule.func_77973_b();
            if (itemCapsule.getCapacity() != fluid.amount) {
                return null;
            }
            ItemStack ret = new ItemStack((Item)itemCapsule, 1, UtilFluid.getFluidID(fluid));
            if (fluidContainer.func_77942_o()) {
                ret.func_77982_d((NBTTagCompound)fluidContainer.func_77978_p().func_74737_b());
            }
            return ret;
        }

        private boolean checkPlacedItem(InventoryCrafting inv) {
            if (inv == null) {
                return false;
            }
            Object ret = null;
            int count = 0;
            for (int i = 0; i < inv.func_70302_i_(); ++i) {
                if (inv.func_70301_a(i) != null) {
                    ++count;
                }
                if (count <= 2) continue;
                return false;
            }
            return count == 2;
        }

        private ItemStack getFluidContainer(InventoryCrafting inv) {
            if (inv == null) {
                return null;
            }
            ItemStack ret = null;
            for (int i = 0; i < inv.func_70302_i_(); ++i) {
                ItemStack item = inv.func_70301_a(i);
                if (item == null || item.func_77973_b() instanceof ItemCapsule || !FluidContainerRegistry.isFilledContainer((ItemStack)item)) continue;
                if (ret != null) {
                    return null;
                }
                ret = item.func_77946_l();
            }
            return ret;
        }

        private ItemStack getCapsule(InventoryCrafting inv) {
            if (inv == null) {
                return null;
            }
            ItemStack ret = null;
            for (int i = 0; i < inv.func_70302_i_(); ++i) {
                ItemStack item = inv.func_70301_a(i);
                if (item == null || !(item.func_77973_b() instanceof ItemCapsule) || item.func_77960_j() != 0) continue;
                if (ret != null) {
                    return null;
                }
                ret = item.func_77946_l();
            }
            return ret;
        }

        public int func_77570_a() {
            return 2;
        }

        public ItemStack func_77571_b() {
            return null;
        }
    }

    public static class RecipeCapsuleCompression
    implements IRecipe,
    ISpecialResultRecipe {
        ItemCapsule ingred;
        int ingredNum = 9;
        ItemCapsule result;
        int resultNum = 1;

        public RecipeCapsuleCompression(ItemCapsule ingred, int ingredNum, ItemCapsule result, int resultNum) {
            this.ingred = ingred;
            this.ingredNum = ingredNum;
            this.result = result;
            this.resultNum = resultNum;
        }

        public boolean func_77569_a(InventoryCrafting inv, World world) {
            return this.func_77572_b(inv) != null;
        }

        public ItemStack func_77572_b(InventoryCrafting inv) {
            int damage = -1;
            int c = 0;
            for (int i = 0; i < inv.func_70302_i_(); ++i) {
                ItemStack ing = inv.func_70301_a(i);
                if (ing == null) continue;
                if (ing.func_77973_b() != this.ingred || damage != -1 && ing.func_77960_j() != damage) {
                    return null;
                }
                damage = ing.func_77960_j();
                ++c;
            }
            return c == this.ingredNum ? this.getRecipeOutput(damage) : null;
        }

        public int func_77570_a() {
            return this.ingredNum;
        }

        public ItemStack func_77571_b() {
            return new ItemStack((Item)this.result, this.resultNum);
        }

        public ItemStack getRecipeOutput(int damage) {
            return new ItemStack((Item)this.result, this.resultNum, damage);
        }

        public List<ItemStack> getRecipeInput(int damage) {
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            for (int i = 0; i < this.ingredNum; ++i) {
                list.add(new ItemStack((Item)this.ingred, 1, damage));
            }
            return list;
        }

        @Override
        public RecipeMap[] getUsageRecipeMap(ItemStack ingredient) {
            if (ingredient != null && ingredient.func_77973_b() == this.ingred) {
                return new RecipeMap[]{new RecipeMap(this.getRecipeInput(ingredient.func_77960_j()).toArray(new ItemStack[0]), this.getRecipeOutput(ingredient.func_77960_j()), "shapeless")};
            }
            return null;
        }

        @Override
        public RecipeMap[] getCraftingRecipeMap(ItemStack result) {
            if (result != null && result.func_77973_b() == this.result) {
                return new RecipeMap[]{new RecipeMap(this.getRecipeInput(result.func_77960_j()).toArray(new ItemStack[0]), this.getRecipeOutput(result.func_77960_j()), "shapeless")};
            }
            return null;
        }
    }
}


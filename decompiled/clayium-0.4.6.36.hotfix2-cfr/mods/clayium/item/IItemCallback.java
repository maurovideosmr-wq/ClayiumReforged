/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package mods.clayium.item;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IItemCallback {
    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5);

    public int getItemStackLimit(ItemStack var1);

    public boolean onItemUseFirst(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10);

    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10);

    public boolean doesSneakBypassUse(World var1, int var2, int var3, int var4, EntityPlayer var5);

    public boolean itemInteractionForEntity(ItemStack var1, EntityPlayer var2, EntityLivingBase var3);

    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3);

    public boolean canHarvestBlock(Block var1, ItemStack var2);

    public float getDigSpeed(ItemStack var1, Block var2, int var3);

    public boolean onBlockStartBreak(ItemStack var1, int var2, int var3, int var4, EntityPlayer var5);

    public boolean onBlockDestroyed(ItemStack var1, World var2, Block var3, int var4, int var5, int var6, EntityLivingBase var7);

    public boolean onLeftClickEntity(ItemStack var1, EntityPlayer var2, Entity var3);

    public boolean hitEntity(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3);

    public boolean onEntitySwing(EntityLivingBase var1, ItemStack var2);

    public EnumAction getItemUseAction(ItemStack var1);

    public int getMaxItemUseDuration(ItemStack var1);

    public void onUsingTick(ItemStack var1, EntityPlayer var2, int var3);

    public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4);

    public ItemStack onEaten(ItemStack var1, World var2, EntityPlayer var3);

    public Set<String> getToolClasses(ItemStack var1);

    public int getHarvestLevel(ItemStack var1, String var2);

    public Multimap getAttributeModifiers(ItemStack var1);

    public boolean isItemTool(ItemStack var1);

    public boolean getIsRepairable(ItemStack var1, ItemStack var2);

    public int getItemEnchantability(ItemStack var1);

    public boolean showDurabilityBar(ItemStack var1);

    public int getMaxDamage(ItemStack var1);

    public int getDamage(ItemStack var1);

    public boolean isDamaged(ItemStack var1);

    public void setDamage(ItemStack var1, int var2);

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack var1);

    public ItemStack getContainerItem(ItemStack var1);

    public boolean hasContainerItem(ItemStack var1);

    public void onCreated(ItemStack var1, World var2, EntityPlayer var3);

    public boolean onDroppedByPlayer(ItemStack var1, EntityPlayer var2);

    public int getEntityLifespan(ItemStack var1, World var2);

    public boolean hasCustomEntity(ItemStack var1);

    public Entity createEntity(World var1, Entity var2, ItemStack var3);

    public boolean onEntityItemUpdate(EntityItem var1);

    public boolean isValidArmor(ItemStack var1, int var2, Entity var3);

    public void onArmorTick(World var1, EntityPlayer var2, ItemStack var3);

    public String getArmorTexture(ItemStack var1, Entity var2, int var3, String var4);

    @SideOnly(value=Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase var1, ItemStack var2, int var3);

    @SideOnly(value=Side.CLIENT)
    public void renderHelmetOverlay(ItemStack var1, EntityPlayer var2, ScaledResolution var3, float var4, boolean var5, int var6, int var7);

    public String getPotionEffect(ItemStack var1);

    public boolean isPotionIngredient(ItemStack var1);

    public float getSmeltingExperience(ItemStack var1);

    public boolean isBookEnchantable(ItemStack var1, ItemStack var2);

    public boolean isBeaconPayment(ItemStack var1);

    @SideOnly(value=Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack var1);
}


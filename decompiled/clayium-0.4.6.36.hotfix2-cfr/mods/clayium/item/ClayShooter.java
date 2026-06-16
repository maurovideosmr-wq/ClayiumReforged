/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.Event
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.ArrowLooseEvent
 */
package mods.clayium.item;

import cpw.mods.fml.common.eventhandler.Event;
import mods.clayium.core.ClayiumCore;
import mods.clayium.entity.EntityClayBall;
import mods.clayium.item.ItemTiered;
import mods.clayium.util.UtilPlayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class ClayShooter
extends ItemTiered {
    protected int bulletLifespan;
    protected float bulletInitialVelocity;
    protected float bulletDiffusion;
    protected int bulletDamage;
    protected float bulletShootingRate;
    protected int bulletCooldownTime;
    protected int chargeTime = 0;
    protected boolean infinity = false;

    public ClayShooter(int maxDamage, String unlocalizedName, String textureName, int bulletLifespan, float bulletInitialVelocity, float bulletDiffusion, int bulletDamage, float bulletShootingRate, int bulletCooldownTime, int chargeTime) {
        this.field_77777_bU = 1;
        this.func_77637_a(ClayiumCore.creativeTabClayium);
        if (maxDamage >= 0) {
            this.func_77656_e(maxDamage);
        } else {
            this.infinity = true;
        }
        this.func_77655_b(unlocalizedName);
        this.func_111206_d("clayium:" + textureName);
        this.bulletLifespan = bulletLifespan;
        this.bulletInitialVelocity = bulletInitialVelocity;
        this.bulletDiffusion = bulletDiffusion;
        this.bulletDamage = bulletDamage;
        this.bulletShootingRate = bulletShootingRate;
        this.bulletCooldownTime = bulletCooldownTime;
        this.chargeTime = chargeTime;
        this.func_77664_n();
    }

    public ClayShooter(int maxDamage, String unlocalizedName, String textureName, int bulletLifespan, float bulletInitialVelocity, float bulletDiffusion, int bulletDamage, int bulletShootingFrame, int chargeTime) {
        this(maxDamage, unlocalizedName, textureName, bulletLifespan, bulletInitialVelocity, bulletDiffusion, bulletDamage, 3.0f / (float)bulletShootingFrame, bulletShootingFrame / 3, chargeTime);
    }

    public void shootBullet(ItemStack stack, EntityPlayer player) {
        this.shootBullet(stack, player, 1.0f);
    }

    public void shootBullet(ItemStack stack, EntityPlayer player, float per) {
        this.shootBullet(stack, player, per, false);
    }

    public void shootBullet(ItemStack stack, EntityPlayer player, float per, boolean critical) {
        player.field_70170_p.func_72956_a((Entity)player, "game.neutral.hurt.fall.small", 0.6f, 5.0f / (field_77697_d.nextFloat() * 0.7f + (float)this.getDamage(stack, player) * per + 1.0f));
        float v = this.getInitialVelocity(stack, player) * per;
        if (v >= 6.0f) {
            player.field_70170_p.func_72956_a((Entity)player, "fireworks.launch", 0.01f * (v - 6.0f), 1.0f);
            player.field_70170_p.func_72956_a((Entity)player, "random.explode", 0.01f * (v - 6.0f), 6.0f / (v + 2.0f) + 0.2f);
        }
        this.spawnEntity(stack, player, per, critical);
        if (!this.infinity) {
            stack.func_77972_a((int)(per * (float)this.getChargeTime(stack, player)) + 1, (EntityLivingBase)player);
        }
    }

    public void spawnEntity(ItemStack stack, EntityPlayer player, float per, boolean critical) {
        if (!player.field_70170_p.field_72995_K) {
            player.field_70170_p.func_72838_d((Entity)new EntityClayBall(player.field_70170_p, (EntityLivingBase)player, this.getLifespan(stack, player), this.getInitialVelocity(stack, player) * per, this.getDiffusion(stack, player), (int)((float)this.getDamage(stack, player) * per), 1, critical));
        }
    }

    public int getLifespan(ItemStack stack, EntityPlayer player) {
        return this.bulletLifespan;
    }

    public float getInitialVelocity(ItemStack stack, EntityPlayer player) {
        return this.bulletInitialVelocity;
    }

    public float getDiffusion(ItemStack stack, EntityPlayer player) {
        return this.bulletDiffusion;
    }

    public int getDamage(ItemStack stack, EntityPlayer player) {
        return this.bulletDamage;
    }

    public float getShootingRate(ItemStack stack, EntityPlayer player) {
        return this.bulletShootingRate;
    }

    public int getCooldownTime(ItemStack stack, EntityPlayer player) {
        return this.bulletCooldownTime;
    }

    public EnumAction func_77661_b(ItemStack p_77661_1_) {
        return EnumAction.bow;
    }

    public int func_77626_a(ItemStack p_77626_1_) {
        return 72000;
    }

    public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        float cooldown;
        if (this.isCharger(p_77659_1_, p_77659_3_) && (cooldown = ((Float)UtilPlayer.getPlayerInstantDataWithSafety(p_77659_3_, "ClayShooterCoolDown", new Float(0.0f))).floatValue()) <= -((float)this.getCooldownTime(p_77659_1_, p_77659_3_))) {
            p_77659_3_.func_71008_a(p_77659_1_, this.func_77626_a(p_77659_1_));
        }
        return p_77659_1_;
    }

    public int getChargeTime(ItemStack stack, EntityPlayer player) {
        return this.chargeTime;
    }

    public boolean isCharger(ItemStack stack, EntityPlayer player) {
        return this.chargeTime > 0;
    }

    public void func_77615_a(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {
        if (this.isCharger(p_77615_1_, p_77615_3_)) {
            int j = this.func_77626_a(p_77615_1_) - p_77615_4_;
            ArrowLooseEvent event = new ArrowLooseEvent(p_77615_3_, p_77615_1_, j);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                return;
            }
            j = event.charge;
            boolean flag = p_77615_3_.field_71075_bZ.field_75098_d || EnchantmentHelper.func_77506_a((int)Enchantment.field_77342_w.field_77352_x, (ItemStack)p_77615_1_) > 0;
            flag = true;
            if (flag) {
                float f = (float)j / (float)this.chargeTime;
                if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
                    f = 1.0f;
                }
                this.shootBullet(p_77615_1_, p_77615_3_, f, f == 1.0f);
            }
        }
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (this.isCharger(stack, player)) {
            int c = this.func_77626_a(stack) - count;
            if (c == 0) {
                player.field_70170_p.func_72956_a((Entity)player, "random.click", 0.5f, 1.3f);
            }
            if (c == this.getChargeTime(stack, player)) {
                player.field_70170_p.func_72956_a((Entity)player, "note.hat", 0.5f, 0.6f);
                player.field_70170_p.func_72956_a((Entity)player, "mob.enderdragon.hit", 0.2f, 2.5f);
            }
        }
    }
}


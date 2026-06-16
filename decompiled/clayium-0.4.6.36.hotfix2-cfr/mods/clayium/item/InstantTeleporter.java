/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 */
package mods.clayium.item;

import mods.clayium.entity.EntityTeleportBall;
import mods.clayium.item.ClayShooter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InstantTeleporter
extends ClayShooter {
    public InstantTeleporter(int maxDamage, String unlocalizedName, String textureName, int bulletLifespan, float bulletInitialVelocity, float bulletDiffusion, int bulletDamage, float bulletShootingRate, int bulletCooldownTime, int chargeTime) {
        super(maxDamage, unlocalizedName, textureName, bulletLifespan, bulletInitialVelocity, bulletDiffusion, bulletDamage, bulletShootingRate, bulletCooldownTime, chargeTime);
    }

    public InstantTeleporter(int maxDamage, String unlocalizedName, String textureName, int bulletLifespan, float bulletInitialVelocity, float bulletDiffusion, int bulletDamage, int bulletShootingFrame, int chargeTime) {
        super(maxDamage, unlocalizedName, textureName, bulletLifespan, bulletInitialVelocity, bulletDiffusion, bulletDamage, bulletShootingFrame, chargeTime);
    }

    @Override
    public void spawnEntity(ItemStack stack, EntityPlayer player, float per, boolean critical) {
        if (!player.field_70170_p.field_72995_K) {
            player.field_70170_p.func_72838_d((Entity)new EntityTeleportBall(player.field_70170_p, (EntityLivingBase)player, this.getLifespan(stack, player), this.getInitialVelocity(stack, player) * per, this.getDiffusion(stack, player), (int)((float)this.getDamage(stack, player) * per), 1, critical));
        }
    }
}


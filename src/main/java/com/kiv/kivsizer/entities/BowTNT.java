package com.kiv.kivsizer.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BowTNT extends TNTEntity {
    public BowTNT(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(worldIn, x, y, z, igniter);
    }

    @Override
    protected void explode() {
        boolean shouldFire = false;
        if (this.getFireTimer() > 0) {
            shouldFire = true;
        }
            this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 4.0F, shouldFire, Explosion.Mode.BREAK);
    }
}

package com.kiv.kivsizer.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BowBlazerBall extends SmallFireballEntity {
    public BowBlazerBall(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        LivingEntity entity = (LivingEntity) p_213868_1_.getEntity();
        entity.setHealth(entity.getHealth() - 2);
        //World world = entity.getEntityWorld();
        //world.createExplosion(entity,entity.getPosX(),entity.getPosY(),entity.getPosZ(),1, Explosion.Mode.NONE);
        super.onEntityHit(p_213868_1_);
    }
}

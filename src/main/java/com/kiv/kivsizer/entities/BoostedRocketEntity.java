package com.kiv.kivsizer.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BoostedRocketEntity extends FireworkRocketEntity{

    private int lifetime;

    public BoostedRocketEntity(World p_i231581_1_, @Nullable Entity p_i231581_2_, double p_i231581_3_, double p_i231581_5_, double p_i231581_7_, ItemStack p_i231581_9_) {
        super(p_i231581_1_, p_i231581_2_, p_i231581_3_, p_i231581_5_, p_i231581_7_, p_i231581_9_);
    }

    @Override
    public void tick() {
        this.lifetime = 100;
        super.tick();
    }
}

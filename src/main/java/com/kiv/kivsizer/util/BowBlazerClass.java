package com.kiv.kivsizer.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class BowBlazerClass {
    public PlayerEntity Shooter;
    public int BallsLeft;
    public ItemStack CheckHolding;

    public BowBlazerClass(PlayerEntity ShooterIn, int BallsLeftIn, ItemStack CheckHoldingIn) {
        this.Shooter = ShooterIn;
        this.BallsLeft = BallsLeftIn;
        this.CheckHolding = CheckHoldingIn;
    }
}

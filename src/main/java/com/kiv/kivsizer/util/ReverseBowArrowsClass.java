package com.kiv.kivsizer.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ReverseBowArrowsClass {

    public int timer;
    public PlayerEntity TrackedPlayer;
    public AbstractArrowEntity TrackedArrow;
    public World TrackedWorld;

    public ReverseBowArrowsClass(int timerIn, PlayerEntity ShooterIn, AbstractArrowEntity FiredArrow, World worldin) {
        this.timer = timerIn;
        this.TrackedPlayer = ShooterIn;
        this.TrackedArrow = FiredArrow;
        this.TrackedWorld = worldin;
    }
}

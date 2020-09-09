package com.kiv.kivsizer.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class TrackedArrowsClass {

    public ArrayList<BlockPos> TrackedBlocks = new ArrayList<>();
    public ArrayList<Integer> Lifetime = new ArrayList<>();
    public PlayerEntity TrackedPlayer;
    public AbstractArrowEntity TrackedArrow;
    public World TrackedWorld;

    public TrackedArrowsClass(PlayerEntity ShooterIn, AbstractArrowEntity FiredArrow, World worldin) {
        this.TrackedPlayer = ShooterIn;
        this.TrackedArrow = FiredArrow;
        this.TrackedWorld = worldin;
    }
}

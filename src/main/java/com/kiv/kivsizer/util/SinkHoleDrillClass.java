package com.kiv.kivsizer.util;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class SinkHoleDrillClass {
    public BlockPos[] DrillSites;
    public int DrillDepth;
    public PlayerEntity AttToPlayer;
    public LivingEntity SinkHoleTarget;
    public int EnchantLevel;

    public SinkHoleDrillClass(BlockPos[] DrillSitesIn, int DrillDepthIn, PlayerEntity AttToPlayerIn, LivingEntity SinkHoleTargetIn, int EnchantLevelIn) {
        this.DrillSites = DrillSitesIn;
        this.DrillDepth = DrillDepthIn;
        this.AttToPlayer = AttToPlayerIn;
        this.SinkHoleTarget = SinkHoleTargetIn;
        this.EnchantLevel = EnchantLevelIn;
    }
}

package com.kiv.kivsizer.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class SliceSlicerClass {
    public Block block;
    public Item HarvestResults;
    public int MaxHarvestUses;
    public int minDrops;
    public int maxDrops;

    public SliceSlicerClass (Block blockin, Item itemIn, int MaxHarvestUses, int minDrops, int MaxDrops) {
        this.block = blockin;
        this.HarvestResults = itemIn;
        this.MaxHarvestUses = MaxHarvestUses;
        this.minDrops = minDrops;
        this.maxDrops = maxDrops;
    }
}

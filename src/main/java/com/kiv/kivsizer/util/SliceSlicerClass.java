package com.kiv.kivsizer.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class SliceSlicerClass {
    public static Block block;
    public static Item HarvestResults;
    public static int MaxHarvestUses;
    public static int minDrops;
    public static int maxDrops;

    public SliceSlicerClass (Block blockin, Item itemIn, int MaxHarvestUses, int minDrops, int MaxDrops) {
        block = blockin;
        HarvestResults = itemIn;
        SliceSlicerClass.MaxHarvestUses = MaxHarvestUses;
        SliceSlicerClass.minDrops = minDrops;
        SliceSlicerClass.maxDrops = maxDrops;
    }
}

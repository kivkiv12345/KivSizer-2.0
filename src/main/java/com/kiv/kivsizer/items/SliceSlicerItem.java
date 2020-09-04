package com.kiv.kivsizer.items;

import com.kiv.kivsizer.KivSizer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class SliceSlicerItem extends ToolItem {
    public SliceSlicerItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Set<Block> effectiveBlocksIn, Properties builderIn) {
        super(attackDamageIn, attackSpeedIn, tier, effectiveBlocksIn, builderIn);
    }

    /*public static ArrayList<Block> UsableBlocks = new ArrayList<>();
    public static ArrayList<Item> HarvestResults = new ArrayList<>();*/

    public static Block[] UsableBlocks = {Blocks.OAK_LOG, Blocks.STONE};
    public static Item[] HarvestResults = {Items.STICK, Items.FLINT};

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        int i = 0;
        KivSizer.LOGGER.info("SliceSlicer was used on " + context.getWorld().getBlockState(context.getPos()));
        for (Block block : UsableBlocks) {
            KivSizer.LOGGER.info("Checking if used on " + block);
            if (block.getDefaultState() == context.getWorld().getBlockState(context.getPos())) {
                KivSizer.LOGGER.info("Block match was found!");

                ItemEntity itemEntity = new ItemEntity(context.getWorld(), context.getPos().getX(), context.getPos().getY(),context.getPos().getZ(), new ItemStack(HarvestResults[i]));
                context.getWorld().addEntity(itemEntity);

                context.getItem().damageItem(1, Objects.requireNonNull(context.getPlayer()), (p_220009_1_) -> {
                    p_220009_1_.sendBreakAnimation(context.getPlayer().getActiveHand());
                });
                return ActionResultType.func_233537_a_(context.getWorld().isRemote());
            }
            i++;
        }
        return super.onItemUse(context);
    }
}

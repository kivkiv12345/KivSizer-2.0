package com.kiv.kivsizer.items;

import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.util.SliceSlicerClass;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import org.omg.CORBA.Current;

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

    /*public static Block[] UsableBlocks = {Blocks.OAK_LOG, Blocks.STONE, Blocks.DIORITE};
    public static Item[] HarvestResults = {Items.STICK, Items.FLINT, Items.DIAMOND};
    public static int[] MaxHarvestUses = {5,10, 10};*/
    public BlockPos LastUsedOn = null;
    public static int CurrentUses = 0;

    public static SliceSlicerClass[] UsableClasses = {
            new SliceSlicerClass(Blocks.OAK_LOG, Items.STICK, 5, 4, 6),
            new SliceSlicerClass(Blocks.STONE, Items.FLINT, 10, 1, 3)
            //new SliceSlicerClass(Blocks.DIORITE, Items.DIAMOND_BOOTS, 10, 1, 1)
    };

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        KivSizer.LOGGER.info(LastUsedOn);
        //KivSizer.LOGGER.info("SliceSlicer was used on " + context.getWorld().getBlockState(context.getPos()));

        for (SliceSlicerClass currentClass : UsableClasses){
            KivSizer.LOGGER.info(currentClass.block);
            KivSizer.LOGGER.info(currentClass.HarvestResults);
            KivSizer.LOGGER.info(currentClass.MaxHarvestUses);
        }

        for (SliceSlicerClass currentclass : UsableClasses) {
            //KivSizer.LOGGER.info("Checking if used on " + block);
            //KivSizer.LOGGER.info("Chceking if " + LastUsedOn + " == " + context.getPos());
            KivSizer.LOGGER.info(currentclass.block);
            KivSizer.LOGGER.info(SliceSlicerClass.block);

            if (currentclass.block.getDefaultState() == context.getWorld().getBlockState(context.getPos())) {
                KivSizer.LOGGER.info("Block match was found!");
                KivSizer.LOGGER.info("Item was used on" + context.getPos());

                /*if (context.getPos() != LastUsedOn) {
                    KivSizer.LOGGER.info("Resetting values!");
                    CurrentUses = 0;
                } else {*/
                    if (CurrentUses < currentclass.MaxHarvestUses) {
                        CurrentUses++;
                        KivSizer.LOGGER.info("Incrementing CurrentUses, now: " + CurrentUses);
                    } else {
                        KivSizer.LOGGER.info("Should now harvest!");
                        ItemEntity itemEntity = new ItemEntity(context.getWorld(), context.getPos().getX(), context.getPos().getY(),context.getPos().getZ(), new ItemStack(currentclass.HarvestResults));
                        context.getWorld().addEntity(itemEntity);
                        context.getWorld().destroyBlock(context.getPos(),false,context.getPlayer());

                        context.getItem().damageItem(1, Objects.requireNonNull(context.getPlayer()), (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(context.getPlayer().getActiveHand());
                        });
                        CurrentUses = 0;
                    }
                //}
                LastUsedOn = context.getPos();
                return ActionResultType.func_233537_a_(context.getWorld().isRemote());
            }
        }
        return super.onItemUse(context);
    }
}

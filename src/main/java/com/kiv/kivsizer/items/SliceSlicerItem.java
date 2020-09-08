package com.kiv.kivsizer.items;

import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.util.SliceSlicerClass;
import com.kiv.kivsizer.util.helpers.KeyboardHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.omg.CORBA.Current;

import javax.naming.Context;
import java.util.*;

public class SliceSlicerItem extends ToolItem {
    public SliceSlicerItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Set<Block> effectiveBlocksIn, Properties builderIn) {
        super(attackDamageIn, attackSpeedIn, tier, effectiveBlocksIn, builderIn);
    }
    public BlockPos LastUsedOn = null;
    public static int CurrentUses = 0;

    public static SliceSlicerClass[] UsableClasses = {
            new SliceSlicerClass(Blocks.OAK_LOG, Items.STICK, 5, 4, 6),
            new SliceSlicerClass(Blocks.STONE, Items.FLINT, 10, 1, 3),
            new SliceSlicerClass(Blocks.DIORITE, Items.DIAMOND, 10, 1, 1)
    };

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(KeyboardHelper.isHoldingShift()) {
            tooltip.add(new StringTextComponent("Use on specific blocks to refined resources"));
        } else {
            tooltip.add(new StringTextComponent("Hold SHIFT for more information"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        KivSizer.LOGGER.info(LastUsedOn);
        for (SliceSlicerClass currentClass : UsableClasses){
            KivSizer.LOGGER.info(currentClass.block);
            KivSizer.LOGGER.info(currentClass.HarvestResults);
            KivSizer.LOGGER.info(currentClass.MaxHarvestUses);
        }

        for (SliceSlicerClass currentclass : UsableClasses) {
            KivSizer.LOGGER.info(currentclass.block);

            if (currentclass.block.getDefaultState() == context.getWorld().getBlockState(context.getPos())) {
                //KivSizer.LOGGER.info("Block match was found!");
                //KivSizer.LOGGER.info("Item was used on" + context.getPos());
                Objects.requireNonNull(context.getPlayer()).getCooldownTracker().setCooldown(context.getPlayer().getHeldItemMainhand().getItem(), 10);
                    if (CurrentUses < currentclass.MaxHarvestUses) {
                        CurrentUses++;
                        KivSizer.LOGGER.info("Incrementing CurrentUses, now: " + CurrentUses);
                    } else {
                        KivSizer.LOGGER.info("Should now harvest!");
                        Random rand = new Random();
                        try {
                            KivSizer.LOGGER.info("Current bound: " + currentclass.minDrops + " to: " + currentclass.maxDrops);
                            KivSizer.LOGGER.info("rnd was: " + currentclass.minDrops + rand.nextInt(currentclass.maxDrops));
                        } catch (Exception e) {
                            KivSizer.LOGGER.info("Exception caught: " + e);
                        }

                        if (!context.getWorld().isRemote) {
                            ItemEntity itemEntity = new ItemEntity(context.getWorld(), context.getPos().getX(), context.getPos().getY(),context.getPos().getZ(), new ItemStack(currentclass.HarvestResults, currentclass.minDrops + rand.nextInt(currentclass.maxDrops)));
                            context.getWorld().addEntity(itemEntity);
                        }
                        context.getWorld().destroyBlock(context.getPos(),false,context.getPlayer());

                        context.getItem().damageItem(1, Objects.requireNonNull(context.getPlayer()), (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(context.getPlayer().getActiveHand());
                        });
                        CurrentUses = 0;
                    }
                LastUsedOn = context.getPos();
                return ActionResultType.func_233537_a_(context.getWorld().isRemote());
            }
        }
        return super.onItemUse(context);
    }
}

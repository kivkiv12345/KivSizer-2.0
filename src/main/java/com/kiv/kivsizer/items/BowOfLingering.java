package com.kiv.kivsizer.items;

import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.entities.BowBlazerBall;
import com.kiv.kivsizer.events.ModClientEvents;
import com.kiv.kivsizer.util.BowBlazerClass;
import com.kiv.kivsizer.util.TrackedArrowsClass;
import com.kiv.kivsizer.util.helpers.KeyboardHelper;
import net.minecraft.advancements.criterion.CuredZombieVillagerTrigger;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class BowOfLingering extends BowItem {
    public BowOfLingering(Properties builder) {
        super(builder);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(KeyboardHelper.isHoldingShift()) {
            tooltip.add(new StringTextComponent("The most powerful tool in the planet!"));
        } else {
            tooltip.add(new StringTextComponent("Hold SHIFT for more information"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!worldIn.isRemote) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            abstractarrowentity.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            abstractarrowentity.setFire(100);
                        }

                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                        });
                        if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        BowOfLingeringEvents.LingeringArrows.add(abstractarrowentity);
                        worldIn.addEntity(abstractarrowentity);
                    }

                    worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
    @Mod.EventBusSubscriber(modid = KivSizer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class BowOfLingeringEvents {

        public static ArrayList<AbstractArrowEntity> LingeringArrows = new ArrayList<>();

        @SubscribeEvent
        public static void BowOfLingeringEvent(TickEvent.PlayerTickEvent event){
            if (!LingeringArrows.isEmpty() && KivSizer.TickCounter == 0) {
                ArrayList<AbstractArrowEntity> CleanupList = new ArrayList<>();
                for (AbstractArrowEntity CurrentClass : LingeringArrows){
                    if (CurrentClass.isAirBorne){
                        KivSizer.LOGGER.info("BowOfLingering was cleaned");
                        CleanupList.add(CurrentClass);
                    } else {
                        PotionEntity potionentity = new PotionEntity(event.player.world, CurrentClass.getPosX(), CurrentClass.getPosY(), CurrentClass.getPosZ());
                        potionentity.setItem(new ItemStack(Items.LINGERING_POTION));
                        potionentity.func_234612_a_(event.player, event.player.rotationPitch, event.player.rotationYaw, -20.0F, 0.5F, 1.0F);
                        event.player.world.addEntity(potionentity);
                    }
                }
                if (!CleanupList.isEmpty()) {
                    try {
                        for (AbstractArrowEntity cleancurrent : CleanupList) {
                            LingeringArrows.remove(cleancurrent);
                        }
                    } catch (Exception e){
                        KivSizer.LOGGER.info("An exception was caught while cleaning BowOfLingering");
                    }
                }
            }
        }
    }
}
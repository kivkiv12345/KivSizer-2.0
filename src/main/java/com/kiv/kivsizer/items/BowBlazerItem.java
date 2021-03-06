package com.kiv.kivsizer.items;

import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.entities.BowBlazerBall;
import com.kiv.kivsizer.util.BowBlazerClass;
import com.kiv.kivsizer.util.RegistryHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

public class BowBlazerItem extends BowItem {

    /*public static ArrayList<TNTEntity> SavedTNTs = new ArrayList<TNTEntity>();
    public static PlayerEntity bowHolder = null;*/

    public BowBlazerItem(Properties builder) {
        super(builder.maxDamage(200));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            //bowHolder = playerentity;
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

                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                        });

                        Vector3d look = entityLiving.getLookVec();

                        //KivSizer.LOGGER.info("f is equal to: " + f);
                        if (playerentity.isCrouching()) {
                            BowBlazerBall smallFireballEntity = new BowBlazerBall(worldIn, playerentity, (look.x * 1.0D) + (Math.random() - 0.5D) / 5, look.y * 1.0D + (Math.random() - 0.5D) / 5, look.z * 1.0D + (Math.random() - 0.5D) / 5);
                            smallFireballEntity.setPosition(smallFireballEntity.getPosX(), smallFireballEntity.getPosY() + 1, smallFireballEntity.getPosZ());
                            worldIn.addEntity(smallFireballEntity);
                            worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                            BowBlazerMG.PlayersShooting.add(new BowBlazerClass(playerentity, (int) (f * 10), playerentity.getHeldItemMainhand()));
                            /*BowBlazerMG.PlayersShooting.add(playerentity);
                            BowBlazerMG.BallsLeft.add((int) (f * 10));
                            BowBlazerMG.CheckHolding.add(playerentity.getHeldItemMainhand());*/
                        } else {
                            for (int j = 0; j < f * 10; j++) {
                                BowBlazerBall smallFireballEntity = new BowBlazerBall(worldIn, playerentity, (look.x * 1.0D * f) + (Math.random() - 0.5D) / 5, look.y * 1.0D * f + (Math.random() - 0.5D) / 5, look.z * 1.0D * f + (Math.random() - 0.5D) / 5);
                                //smallFireballEntity.setVelocity(look.x * 1.0D * f, look.y * 1.0D * f, look.z * 1.0D * f);
                                smallFireballEntity.setPosition(smallFireballEntity.getPosX(), smallFireballEntity.getPosY() + 1, smallFireballEntity.getPosZ());
                                worldIn.addEntity(smallFireballEntity);
                                worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                            }
                        }
                    }

                    if (/*!flag1 && */!playerentity.abilities.isCreativeMode) {
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
    public static class BowBlazerMG {

        public static ArrayList<BowBlazerClass> PlayersShooting = new ArrayList<>();

        @SubscribeEvent
        public static void BowBlazerMGShoot(TickEvent.PlayerTickEvent event){
            if (!PlayersShooting.isEmpty() && (KivSizer.TickCounter == 7 || KivSizer.TickCounter == 3)) {
                KivSizer.LOGGER.info("PlayersShooting was not empty!");
                ArrayList<BowBlazerClass> CleanupList = new ArrayList<>();
                int i = 0;
                for (BowBlazerClass CurrentClass : PlayersShooting){
                    if (CurrentClass.Shooter == event.player) {
                        if (CurrentClass.CheckHolding != event.player.getHeldItemMainhand()) {
                            KivSizer.LOGGER.info("Cleaning was set to true!");
                            CleanupList.add(CurrentClass);
                        } else {
                            PlayerEntity player = event.player;
                            World world = player.getEntityWorld();
                            Vector3d look = player.getLookVec();
                            CurrentClass.BallsLeft = CurrentClass.BallsLeft - 1;
                            BowBlazerBall smallFireballEntity = new BowBlazerBall(world, player, (look.x * 1.0D) + (Math.random() - 0.5D) / 5, look.y * 1.0D + (Math.random() - 0.5D) / 5, look.z * 1.0D + (Math.random() - 0.5D) / 5);
                            smallFireballEntity.setPosition(smallFireballEntity.getPosX(), smallFireballEntity.getPosY() + 1, smallFireballEntity.getPosZ());
                            world.addEntity(smallFireballEntity);
                            world.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + (CurrentClass.BallsLeft / 10) * 0.5F);
                        }

                        if (CurrentClass.BallsLeft <= 0){
                            KivSizer.LOGGER.info("Cleaning was set to true!");
                            CleanupList.add(CurrentClass);
                        }
                    }
                }
                if (!CleanupList.isEmpty()) {
                    try {
                        for (BowBlazerClass cleancurrent : CleanupList) {
                            PlayersShooting.remove(cleancurrent);
                        }
                    } catch (Exception e){

                    }
                }
            }
        }
    }
}

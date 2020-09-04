package com.kiv.kivsizer.items;

import com.kiv.kivsizer.KivSizer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

public class TNTBowItem extends BowItem {

    public static ArrayList<TNTEntity> SavedTNTs = new ArrayList<TNTEntity>();
    public static PlayerEntity bowHolder = null;

    public TNTBowItem(Properties builder) {
        super(builder.maxDamage(200));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            bowHolder = playerentity;
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
                        TNTEntity tntEntity = new TNTEntity(worldIn, entityLiving.getPosX() + look.x * 1.5D, entityLiving.getPosY() + look.y * 1.5D + 1, entityLiving.getPosZ() + look.z * 1.5D, playerentity);
                        tntEntity.setVelocity(look.x * 1.0D * f, look.y * 1.0D * f, look.z * 1.0D * f);
                        tntEntity.setFuse(tntEntity.getFuse()*2);
                        SavedTNTs.add(tntEntity);
                        worldIn.addEntity(tntEntity);


                    }

                    worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
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
    public static class TNTBowFunctions {

        @SubscribeEvent
        public static void ExplodeOnGround(TickEvent.WorldTickEvent event){
            //KivSizer.LOGGER.info("TNTBow event ran!");
            if (!SavedTNTs.isEmpty()) {
                //KivSizer.LOGGER.info("The TNT list was not empty!");
                for (TNTEntity tnt : SavedTNTs){
                    //KivSizer.LOGGER.info("The foreach loop was entered!");
                    if (tnt.isOnGround() && bowHolder != null) {
                        //KivSizer.LOGGER.info("All conditions was met!");
                        //event.world.createExplosion(bowHolder, tnt.getPosX(), tnt.getPosY(), tnt.getPosZ(), 2, Explosion.Mode.BREAK);
                        SavedTNTs.remove(tnt);
                        tnt.setFuse(0);
                        //tnt.remove();
                        break;
                    }
                }
            }
        }
    }
}

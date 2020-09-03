package com.kiv.kivsizer.enchantments;

import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.events.ModClientEvents;
import com.kiv.kivsizer.util.RegistryHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SinkHoleEnchantment extends Enchantment {

    public SinkHoleEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Mod.EventBusSubscriber(modid = KivSizer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class SinkHoleFunctions {

        @SubscribeEvent
        public static void EnableSinkHole(LivingAttackEvent event) {
            if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
                Entity target = event.getEntity();
                if (EnchantmentHelper.getEnchantmentLevel(RegistryHandler.SINK_HOLE.get(), player.getItemStackFromSlot(EquipmentSlotType.MAINHAND)) > 0) {
                    target.setVelocity(0, -1, 0);
                    BlockPos drillPos = target.getPosition().add(0,-1,0);
                    BlockPos[] drillArray = {drillPos, drillPos.add(1, 0, -1), drillPos.add(1, 0, 0), drillPos.add(1, 0, 1), drillPos.add(0, 0, -1), drillPos.add(0, 0, 1), drillPos.add(-1, 0, -1), drillPos.add(-1, 0, 0), drillPos.add(-1, 0, 1)};
                    ModClientEvents.DrillSites.add(drillArray);
                    ModClientEvents.DrillDepth.add(0);
                    ModClientEvents.AttToPlayer.add((PlayerEntity) player);
                    ModClientEvents.SinkHoleTargets.add((LivingEntity) target);
                    ModClientEvents.EnchantLevel.add(EnchantmentHelper.getEnchantmentLevel(RegistryHandler.SINK_HOLE.get(), player.getItemStackFromSlot(EquipmentSlotType.MAINHAND)));
                    player.getHeldItemMainhand().damageItem(player.getHeldItemMainhand().getMaxDamage() / 8, player, Entity::extinguish);
                    player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 40);
                }
                //KivSizer.LOGGER.info(event.getSource().toString());
                //KivSizer.LOGGER.info(event.getEntityLiving().toString());
            }
        }
    }
}

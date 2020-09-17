package com.kiv.kivsizer.enchantments;

import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.events.ModClientEvents;
import com.kiv.kivsizer.util.RegistryHandler;
import com.kiv.kivsizer.util.SinkHoleDrillClass;
import com.kiv.kivsizer.util.TrackedArrowsClass;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SwiftnessFieldEnchantment extends Enchantment {

    public SwiftnessFieldEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
    public static class SwiftnessFunctions {

        public static float GetRightCharge(float charge){
            charge = charge / 2.0f;
            if (charge < 3.0F){
                return charge;
            } else {
                return 3.0F;
            }
        }

        @SubscribeEvent
        public static void AddSwiftnessField(ArrowLooseEvent event) {
            if (EnchantmentHelper.getEnchantmentLevel(RegistryHandler.SWIFTNESS_FIELD.get(), event.getPlayer().getItemStackFromSlot(EquipmentSlotType.MAINHAND)) > 0) {

                ItemStack itemstack = event.getPlayer().findAmmo(event.getBow());
                if (!event.getWorld().isRemote) {
                    ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                    AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(event.getWorld(), itemstack, event.getPlayer());
                    //abstractarrowentity = BowItem.customArrow(abstractarrowentity);

                    abstractarrowentity.func_234612_a_(event.getPlayer(), event.getPlayer().rotationPitch, event.getPlayer().rotationYaw, 0.0F, GetRightCharge(event.getCharge()), 1.0F);
                    KivSizer.LOGGER.info("Charge was: " + event.getCharge());

                    KivSizer.LOGGER.info("Added entity: " + event.getEntity());
                    try {
                        ModClientEvents.TrackedArrows.add(new TrackedArrowsClass(event.getPlayer(), abstractarrowentity, event.getWorld()));
                    } catch (Exception e) {
                        KivSizer.LOGGER.info("Exception caught: " + e);
                    }
                    event.getWorld().addEntity(abstractarrowentity);
                }
                //KivSizer.LOGGER.info(event.getSource().toString());
                //KivSizer.LOGGER.info(event.getEntityLiving().toString());
            }
        }
    }
}

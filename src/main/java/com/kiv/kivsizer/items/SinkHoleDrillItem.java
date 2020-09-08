package com.kiv.kivsizer.items;

import com.kiv.kivsizer.events.ModClientEvents;
import com.kiv.kivsizer.tools.ModItemTier;
import com.kiv.kivsizer.util.SinkHoleDrillClass;
import com.kiv.kivsizer.util.helpers.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class SinkHoleDrillItem extends SwordItem {
    public SinkHoleDrillItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties p_i48460_4_) {
        super(ModItemTier.SIZERTOOLS, attackDamageIn, attackSpeedIn, p_i48460_4_);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(KeyboardHelper.isHoldingShift()) {
            tooltip.add(new StringTextComponent("Will drill a hole below the attacked entity, this will however damage the drill.."));
        } else {
            tooltip.add(new StringTextComponent("Hold SHIFT for more information"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setVelocity(0, -1, 0);
        BlockPos drillPos = target.getPosition().add(0,-1,0);
        BlockPos[] drillArray = {drillPos, drillPos.add(1, 0, -1), drillPos.add(1, 0, 0), drillPos.add(1, 0, 1), drillPos.add(0, 0, -1), drillPos.add(0, 0, 1), drillPos.add(-1, 0, -1), drillPos.add(-1, 0, 0), drillPos.add(-1, 0, 1)};
        ModClientEvents.DrillSites.add(new SinkHoleDrillClass(drillArray, 0, (PlayerEntity) attacker, target, 1));
        stack.damageItem(stack.getMaxDamage() / 5, attacker, Entity::extinguish);
        return super.hitEntity(stack, target, attacker);
    }
}

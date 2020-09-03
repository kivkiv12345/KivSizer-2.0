package com.kiv.kivsizer.items;

import com.kiv.kivsizer.tools.ModItemTier;
import com.kiv.kivsizer.util.helpers.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.List;

public class BlenderItem extends SwordItem {
    public BlenderItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties p_i48460_4_) {
        super(ModItemTier.SIZERTOOLS, attackDamageIn, attackSpeedIn, p_i48460_4_);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockPos pos = playerIn.getPosition();
        worldIn.createExplosion(playerIn,pos.getX(),pos.getY(),pos.getZ(),3, Explosion.Mode.NONE);
        return super.onItemRightClick(worldIn,playerIn,handIn);
    }
}

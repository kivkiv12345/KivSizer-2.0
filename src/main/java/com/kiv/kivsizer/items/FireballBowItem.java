package com.kiv.kivsizer.items;

import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.tools.ModItemTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireballBowItem extends SwordItem {
    public FireballBowItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(ModItemTier.SIZERTOOLS, attackDamageIn, attackSpeedIn, builderIn);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        KivSizer.LOGGER.info("Event was registered!");
        ItemStack item = playerIn.getHeldItemMainhand();
        Vector3d look = playerIn.getLookVec();
        FireballEntity fireball = new FireballEntity(worldIn, playerIn, 1D, 1D, 1D);
        fireball.setPosition(playerIn.getPosX() + look.x * 1.5D, playerIn.getPosY() + look.y * 1.5D, playerIn.getPosZ() + look.z * 1.5D);
        fireball.accelerationX = look.x * 0.07D;
        fireball.accelerationY = look.y * 0.07D;
        fireball.accelerationZ = look.z * 0.07D;
        fireball.explosionPower = 1;
        worldIn.addEntity(fireball);
        playerIn.getCooldownTracker().setCooldown(this, 15);
        if (!playerIn.isCrouching()) {
            playerIn.startRiding(fireball, true);
        }
        item.damageItem(1, playerIn, PlayerEntity::jump);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

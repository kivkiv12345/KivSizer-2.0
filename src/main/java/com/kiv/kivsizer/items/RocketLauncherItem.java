package com.kiv.kivsizer.items;

import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.entities.BoostedRocketEntity;
import com.kiv.kivsizer.tools.ModItemTier;
import com.kiv.kivsizer.util.RegistryHandler;
import com.kiv.kivsizer.util.helpers.KeyboardHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class RocketLauncherItem extends SwordItem {
    public RocketLauncherItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties p_i48460_4_) {
        super(ModItemTier.SIZERTOOLS, attackDamageIn, attackSpeedIn, p_i48460_4_);
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
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote) {
            ItemStack itemstack = context.getItem();
            Vector3d vector3d = context.getHitVec();
            Direction direction = context.getFace();
            FireworkRocketEntity fireworkrocketentity = new BoostedRocketEntity(world, context.getPlayer(), vector3d.x + (double)direction.getXOffset() * 0.15D, vector3d.y + (double)direction.getYOffset() * 0.15D, vector3d.z + (double)direction.getZOffset() * 0.15D, itemstack);
            world.addEntity(fireworkrocketentity);
            TNTEntity tntEntity = new TNTEntity(world, fireworkrocketentity.getPosX(), fireworkrocketentity.getPosY(), fireworkrocketentity.getPosZ(), context.getPlayer());
            world.addEntity(tntEntity);
            try {
                context.getPlayer().isPassenger(tntEntity);
            }catch (Exception e){
                KivSizer.LOGGER.info(e);
            }
            fireworkrocketentity.isPassenger(tntEntity);
        }
        return super.onItemUse(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        KivSizer.LOGGER.info("Ran this!");
        try{
            Entity en = Minecraft.getInstance().pointedEntity;
            Minecraft.getInstance().playerController.attackEntity(Objects.requireNonNull(playerIn), en);
        }catch (Exception e){
            KivSizer.LOGGER.info(e);
        }

        /*try {
            Pose pose = playerIn.getPose();
            playerIn.getSize(pose).scale(2);
        }catch (Exception e){
            KivSizer.LOGGER.info(e);
        }*/
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

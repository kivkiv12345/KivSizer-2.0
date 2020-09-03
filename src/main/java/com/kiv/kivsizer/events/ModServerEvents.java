package com.kiv.kivsizer.events;

import com.kiv.kivsizer.KivSizer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KivSizer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ModServerEvents {

    public static void DrillOnServer(PlayerEntity player, BlockPos drillPos, World world, boolean willDrop) {
        world.destroyBlock(drillPos, willDrop, player);
    }

    /*@SubscribeEvent
    public static void SendMessege(LivingEvent.LivingJumpEvent event) {
        LivingEntity player = event.getEntityLiving();
        if(player.getHeldItemMainhand().getItem() == Items.STICK) {
            KivSizer.LOGGER.info("A player tried to jump with a stick!");
            String msg = TextFormatting.RED + "This is a message for a player!";
            player.sendMessage(new StringTextComponent(msg), player.getUniqueID());

            //Ting fra ClientSide som forhåbeligt virker ServerSide
            World world = player.getEntityWorld();
            BlockPos playerloc = player.func_233580_cy_();
            BlockPos blockatloc = playerloc.add(0, -1, 0);
            if (world.getBlockState(blockatloc) == Blocks.GRASS_BLOCK.getDefaultState()) {
                world.setBlockState(blockatloc, Blocks.DIRT.getDefaultState());
                //world.playSound(playerloc.getX(), playerloc.getY(), playerloc.getZ(), );
                player.playSound(SoundEvents.BLOCK_GRASS_BREAK, 0.5f, 1.0f);
                Block.spawnDrops(world.getBlockState(blockatloc), world, blockatloc);
                ((PlayerEntity) player).addItemStackToInventory(new ItemStack(Items.PUMPKIN, 3));
            }
        }
    }*/
}

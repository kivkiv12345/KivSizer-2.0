package com.kiv.kivsizer.events;

import com.kiv.kivsizer.KivSizer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = KivSizer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)//, value = Dist.CLIENT)
public class ModClientEvents {

    public static float PlayerPrevGround = 0;
    //public static boolean isDrilling = false;

    // Drill boots
    public static ArrayList<Boolean> DrillbootDrillers = new ArrayList<Boolean>();
    public static ArrayList<UUID> UUIDs = new ArrayList<UUID>();
    public static int BootLinkage = 0;

    // Sink holes
    public static ArrayList<BlockPos[]> DrillSites = new ArrayList<BlockPos[]>();
    public static ArrayList<Integer> DrillDepth = new ArrayList<Integer>();
    public static ArrayList<PlayerEntity> AttToPlayer = new ArrayList<PlayerEntity>();
    public static ArrayList<LivingEntity> SinkHoleTargets = new ArrayList<>();
    public static ArrayList<Integer> EnchantLevel = new ArrayList<>();
    public static int HoleLinkage = 0;

    //KeyTest //Unfinished
    /*@SubscribeEvent
    public static void KeyTest(InputEvent.KeyInputEvent event)
    {
        KivSizer.LOGGER.info("The player pressed a key!");
        if (event.getKey() == )
    }*/


    @SubscribeEvent(priority= EventPriority.LOWEST)
    public static void LandHardEvent(LivingDamageEvent event) {
        LivingEntity player = event.getEntityLiving();
        if (player instanceof PlayerEntity && event.getSource() == DamageSource.FALL) {
            //KivSizer.LOGGER.info("Player Y velocity = " + player.getMotion().getY());
                KivSizer.LOGGER.info("The player took some falldamage");
                World world = player.getEntityWorld();
                BlockPos blockatloc = player.getPosition().add(0, -1, 0);//func_233580_cy_ will be getPosition or something when fixed
                if (world.getBlockState(blockatloc).getMaterial() == Material.EARTH || world.getBlockState(blockatloc).getMaterial() == Material.ORGANIC || world.getBlockState(blockatloc).getMaterial() == Material.SAND) {//|| world.getBlockState(blockatloc) == Blocks.DIRT.getDefaultState() || world.getBlockState(blockatloc) == Blocks.GRASS_BLOCK.getDefaultState()) {
                    world.destroyBlock(blockatloc, true);
                    player.playSound(SoundEvents.BLOCK_GRAVEL_BREAK, 0.5f, 1.0f);
                } else if (world.getBlockState(blockatloc).getMaterial() == Material.ROCK) {
                    player.setHealth(player.getHealth() - event.getAmount());
                }
                float playerFallDistance = Math.abs(player.getPosition().getY() - PlayerPrevGround);
                if (player.isSneaking() && ((PlayerEntity) player).inventory.armorInventory.get(0).getItem() == Items.DIAMOND_BOOTS) {
                    //isDrilling = true;
                    UUIDs.add(event.getEntityLiving().getUniqueID());
                    DrillbootDrillers.add(true);
                }
            KivSizer.LOGGER.info("The player fell for: " + player.fallDistance + " blocks (player.fallDistance)");
            if (player.fallDistance > 7) {
                    if (player.fallDistance > 50) {
                        world.createExplosion(player, player.getPosX(),player.getPosY(),player.getPosZ(),player.fallDistance/10,true, Explosion.Mode.DESTROY);
                    } else {
                        world.createExplosion(player,player.getPosX(),player.getPosY(),player.getPosZ(),player.fallDistance / 10, Explosion.Mode.DESTROY);
                    }
                    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, Math.round(playerFallDistance * 10), 2));
                    //player.setMotion(0,-1,0);
                    KivSizer.LOGGER.info("The player fell for: " + playerFallDistance + " blocks (playerFallDistance)");
                }
                player.setMotion(0,-1,0);
                KivSizer.LOGGER.info("Player movement speed is: " + ((PlayerEntity) player).abilities.getWalkSpeed());

                /*new Thread(() ->{
                    //float playerOGWalk = ((PlayerEntity) player).abilities.getWalkSpeed();
                    float playerOGWalk = 0.1f;
                    ((PlayerEntity) player).abilities.setWalkSpeed(0);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((PlayerEntity) player).abilities.setWalkSpeed(playerOGWalk);
                    //KeyEvent.VK_W;
                }).start();*/
        }
    }

    @SubscribeEvent(priority= EventPriority.LOWEST) //Probably only runs on client
    public static void SlowSetEvent(PlayerEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World world = event.getEntity().getEntityWorld();
            if (player.isSprinting()) {
                player.stepHeight = 1.0F;
            } else {
                player.stepHeight = 0.5F;
            }

            if (player.getMotion().getY() >= -0.1f && player.getMotion().getY() <= 0.0f) {
                //KivSizer.LOGGER.info("The player is not falling");
                PlayerPrevGround = player.getPosition().getY();
            }
            //player.abilities.setWalkSpeed(0.1f);
            //KivSizer.LOGGER.info("Player movement speed: " + Math.abs(player.getMotion().getX()) + "... Player movement speed is: " + player.abilities.getWalkSpeed());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void PlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (!UUIDs.isEmpty()){
            KivSizer.LOGGER.info("UUIDs is not empty, and contains " + UUIDs.size() + " entries.");
            for (int i = 0; i < UUIDs.size(); i++){
                if (event.player.getUniqueID() == UUIDs.get(i) && event.player.isSneaking()) {
                    PlayerEntity player = event.player;
                    World world = player.getEntityWorld();
                    BlockPos drillPos = player.getPosition().add(0, -1, 0);
                    if (world.getBlockState(drillPos) != Blocks.BEDROCK.getDefaultState()) {
                        world.destroyBlock(drillPos, true, player);
                    }
                    BlockPos[] drillArray = {drillPos.add(1, 0, -1), drillPos.add(1, 0, 0), drillPos.add(1, 0, 1), drillPos.add(0, 0, -1), drillPos.add(0, 0, 1), drillPos.add(-1, 0, -1), drillPos.add(-1, 0, 0), drillPos.add(-1, 0, 1)};
                    for (BlockPos pos : drillArray) {
                        boolean willDrop = false;
                        if (Math.random() > 0.5f) {
                            willDrop = true;
                        }
                        if (world.getBlockState(pos) != Blocks.BEDROCK.getDefaultState() && world.getBlockState(pos) != Blocks.AIR.getDefaultState()) {
                            world.destroyBlock(pos, willDrop, player);
                            player.inventory.armorInventory.get(0).damageItem(1,player,PlayerEntity::respawnPlayer);
                        }
                    }
                } else {
                    UUIDs.remove(i);
                }
            }
        }


        /*if (event.player.isSneaking() && isDrilling) {
            PlayerEntity player = event.player;
            World world = player.getEntityWorld();
            BlockPos drillPos = player.getPosition().add(0, -1, 0);
            if (world.getBlockState(drillPos) != Blocks.BEDROCK.getDefaultState()) {
                world.destroyBlock(drillPos, true, player);
            }
            BlockPos[] drillArray = {drillPos.add(1, 0, -1), drillPos.add(1, 0, 0), drillPos.add(1, 0, 1), drillPos.add(0, 0, -1), drillPos.add(0, 0, 1), drillPos.add(-1, 0, -1), drillPos.add(-1, 0, 0), drillPos.add(-1, 0, 1)};
            for (BlockPos pos : drillArray) {
                    boolean willDrop = false;
                    if (Math.random() > 0.5f) {
                        willDrop = true;
                    }
                    if (world.getBlockState(pos) != Blocks.BEDROCK.getDefaultState() && world.getBlockState(pos) != Blocks.AIR.getDefaultState()) {
                        world.destroyBlock(pos, willDrop, player);
                        player.inventory.armorInventory.get(0).damageItem(1,player,PlayerEntity::respawnPlayer);
                    }
            }
        } else {
            isDrilling = false;
        }*/

        // Tick Counter
        int MaxTicks = 7;
        if (KivSizer.TickCounter > MaxTicks) {
            KivSizer.TickCounter = 0;
            //KivSizer.LOGGER.info(MaxTicks + " ticks have passed!");
        } else {
            KivSizer.TickCounter++;
        }

        // Sinkhole Enchantment handling
        ArrayList<Integer> CleanupList = new ArrayList<Integer>();
        if (!DrillSites.isEmpty() && KivSizer.TickCounter == 0) {
            //int i = 0;
            for (BlockPos[] DrillArr : DrillSites) {
                World world = AttToPlayer.get(HoleLinkage).getEntityWorld();
                Boolean PlacesBlocks = false;
                BlockState BlockToPlace = Blocks.DIRT.getDefaultState();
                if (EnchantLevel.get(HoleLinkage) > 2) {
                    PlacesBlocks = true;
                    switch (EnchantLevel.get(HoleLinkage)){
                        case 3:
                            BlockToPlace = Blocks.DIRT.getDefaultState();
                            break;
                        case 4:
                            BlockToPlace = Blocks.COBBLESTONE.getDefaultState();
                            break;
                        case 5:
                            BlockToPlace = Blocks.OBSIDIAN.getDefaultState();
                            break;
                        default:
                    }
                }
                if (DrillDepth.get(HoleLinkage) < 5 * EnchantLevel.get(HoleLinkage) / 2 + 0.5f){
                    //KivSizer.LOGGER.info("I is equal to: " + i);
                    for (BlockPos pos : DrillArr) {
                        if (world.getBlockState(pos.add(0,DrillDepth.get(HoleLinkage) * -1,0)) != Blocks.BEDROCK.getDefaultState() && world.getBlockState(pos.add(0,DrillDepth.get(HoleLinkage) * -1,0)) != Blocks.AIR.getDefaultState()) {
                            world.destroyBlock(pos.add(0,DrillDepth.get(HoleLinkage) * -1,0),false,AttToPlayer.get(HoleLinkage));
                            //KivSizer.LOGGER.info("Destoyed block at: " + pos);
                        }
                        if (PlacesBlocks && DrillDepth.get(HoleLinkage) > 3){
                            world.setBlockState(pos.add(0,(DrillDepth.get(HoleLinkage)-4) * -1,0), BlockToPlace);
                        }
                    }
                    SinkHoleTargets.get(HoleLinkage).setVelocity(0,-1,0);
                    DrillDepth.set(HoleLinkage, DrillDepth.get(HoleLinkage) + 1);
                    //KivSizer.LOGGER.info("Should drill at depth: " + DrillDepth.get(i) * -1);
                    HoleLinkage++;
                    if (HoleLinkage == DrillSites.size()) {
                        HoleLinkage = 0;
                    }
                }else{
                   CleanupList.add(HoleLinkage);
                   /*if (EnchantLevel.get(i) > 4){
                       for (BlockPos pos : DrillArr){
                           if (world.getBlockState(pos.add(0,DrillDepth.get(i) * -1,0)) != Blocks.BEDROCK.getDefaultState()) {
                               world.setBlockState(pos.add(0,DrillDepth.get(i),0), Blocks.OBSIDIAN.getDefaultState());
                           }
                       }
                   }*/
                   //KivSizer.LOGGER.info("DrillDepth " + i + " was " + DrillDepth.get(i));
                }
            }
        }
        if (!CleanupList.isEmpty()){
                DrillSites.remove(0);
                DrillDepth.remove(0);
                AttToPlayer.remove(0);
                SinkHoleTargets.remove(0);
                EnchantLevel.remove(0);
        }

    }

    @SubscribeEvent
    public static void KivSizerCommands(ClientChatEvent event) {
        KivSizer.LOGGER.info("The player wrote: " + event.getMessage() + "in the chat.");
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    public static void EatCarrotEvent(LivingEntityUseItemEvent.Finish event)
    {
        LivingEntity player = event.getEntityLiving();
        KivSizer.LOGGER.info("An entity finished using an item!");
        if (player.getHeldItemMainhand().getItem() == Items.CARROT)
        {
            int WallLength = 5;
            int WallHeight = 5;
            //int WallOffset = WallLength/2;
            World world = player.getEntityWorld();
            BlockPos playerpos = player.getPosition();

            for (int i = 0; i < WallLength; i++) {
                for (int j = 0; j < WallHeight; j++) {
                    world.setBlockState(playerpos.add(-3,j,i-2), Blocks.BEDROCK.getDefaultState());
                    world.setBlockState(playerpos.add(3,j,i-2), Blocks.BEDROCK.getDefaultState());
                }
            }
            for (int i = 0; i < WallLength; i++) {
                for (int j = 0; j < WallHeight; j++) {
                    world.setBlockState(playerpos.add(i-2,j,-3), Blocks.BEDROCK.getDefaultState());
                    world.setBlockState(playerpos.add(i-2,j,3), Blocks.BEDROCK.getDefaultState());
                }
            }
            for (int i = 0; i < WallLength; i++) {
                for (int j = 0; j < WallHeight; j++) {
                    world.setBlockState(playerpos.add(i-2,-1,j-2), Blocks.BEDROCK.getDefaultState());
                    world.setBlockState(playerpos.add(i-2,5,j-2), Blocks.BEDROCK.getDefaultState());
                }
            }
            world.setBlockState(playerpos, Blocks.TORCH.getDefaultState());
            world.destroyBlock(playerpos.add(0,5,0), false);
            player.playSound(SoundEvents.AMBIENT_CAVE, 0.5f, 1.0f);
        }
    }


    // Unused code
    /*

    //world.createExplosion(player, blockatloc.getX(),blockatloc.getY(),blockatloc.getZ(),5, Explosion.Mode.BREAK);
    //world.playSound(playerloc.getX(), playerloc.getY(), playerloc.getZ(), );
    //Block.spawnDrops(world.getBlockState(blockatloc), world, blockatloc);
    //((PlayerEntity) player).addItemStackToInventory(new ItemStack(Items.PUMPKIN, 3));

    //KivSizer.LOGGER.info("An entity took some damawge somewhere!");
    //KivSizer.LOGGER.info("The player took some damage");
    //Block.spawnDrops(world.getBlockState(blockatloc), world, blockatloc);
    //world.setBlockState(blockatloc, Blocks.AIR.getDefaultState());
    //((PlayerEntity) player).abilities.setWalkSpeed(0.0f);
    //((PlayerEntity) player).abilities.setFlySpeed(0.0f);

    //KivSizer.LOGGER.info("Player Y motion = " + player.getMotion().getY());
    //if (player.isPassenger()) {
    //world.createExplosion(null, player.getPosX(), player.getPosY(), player.getPosZ(), 5, Explosion.Mode.DESTROY);
    //}

     */
}

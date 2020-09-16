package com.kiv.kivsizer.util;

import com.google.common.collect.ImmutableSet;
import com.kiv.kivsizer.KivSizer;
import com.kiv.kivsizer.enchantments.SinkHoleEnchantment;
import com.kiv.kivsizer.items.*;
import com.kiv.kivsizer.tools.ModItemTier;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class RegistryHandler {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KivSizer.MOD_ID);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KivSizer.MOD_ID);
    public static DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, KivSizer.MOD_ID);
    public static DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, KivSizer.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items
    //public static final RegistryObject<Item> FIREBALL_BOW = ITEMS.register("fireball_bow", ItemBase::new);

    // Tools
    private static final Set<Block> EFFECTIVE_ON_NONE = ImmutableSet.of();

    public static final RegistryObject<SwordItem> PIZZA_CUTTER = ITEMS.register("pizza_cutter", () ->
            new SwordItem(ModItemTier.SIZERTOOLS, 4, -2.4F, new Item.Properties().group(KivSizer.TAB)));
    public static final RegistryObject<SwordItem> PIZZA_BLENDER = ITEMS.register("pizza_blender", () ->
            new BlenderItem(ModItemTier.SIZERTOOLS, 5, -2.4F, new Item.Properties().group(KivSizer.TAB)));
    public static final RegistryObject<SwordItem> SINKHOLE_DRILL = ITEMS.register("sinkhole_drill", () ->
            new SinkHoleDrillItem(ModItemTier.SIZERTOOLS, -2, -2.4F, new Item.Properties().group(KivSizer.TAB)));
    public static final RegistryObject<BowItem> ROCKET_LAUNCHER = ITEMS.register("rocket_launcher", () ->
            new RocketLauncherItem(new Item.Properties().group(KivSizer.TAB)));
    public static final RegistryObject<SwordItem> FIREBALL_BOW = ITEMS.register("fireball_bow", () ->
            new FireballBowItem(ModItemTier.SIZERTOOLS, 0, -2.4F, new Item.Properties().group(KivSizer.TAB)));
    public static final RegistryObject<BowItem> TNT_BOW = ITEMS.register("tnt_bow", () ->
            new TNTBowItem(new Item.Properties().group(KivSizer.TAB)));
    public static final RegistryObject<BowItem> BOWBLAZER = ITEMS.register("bowblazer", () ->
            new BowBlazerItem(new Item.Properties().group(KivSizer.TAB)));
    public static final RegistryObject<BowItem> BOWOFLINGERING = ITEMS.register("bowoflingering", () ->
            new BowOfLingering(new Item.Properties().group(KivSizer.TAB)));
    public static final RegistryObject<ToolItem> SLICESLICER = ITEMS.register("sliceslicer", () ->
            new SliceSlicerItem(0,-2.4f, ModItemTier.SIZERTOOLS, EFFECTIVE_ON_NONE, new Item.Properties().group(KivSizer.TAB).maxDamage(200)));

    // Blocks


    // Block Items


    // Enchantments
    public static final RegistryObject<Enchantment> SINK_HOLE = ENCHANTMENTS.register("sink_hole", () -> new SinkHoleEnchantment(Enchantment.Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND}));

    // Effects
    //public static final RegistryObject<Effect> SINK_HOLE_EFFECT = EFFECTS.register(200, "sink_hole_effect", (new SinkHoleEffect(EffectType.HARMFUL, 4866583)).addAttributesModifier(Attributes.field_233825_h_, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    // Entities / Entity Types
    /*public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, KivSizer.MOD_ID);
    public static final RegistryObject<EntityType<MeteorEntity>> METEOR = ENTITY_TYPES.register("meteor",
            () -> EntityType.Builder.create(MeteorEntity::new, EntityClassification.MISC)
                    .size(2.0f, 2.0f)
                    .build(new ResourceLocation(KivSizer.MOD_ID, "meteor").toString()));*/

}

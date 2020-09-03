package com.kiv.kivsizer;

import com.kiv.kivsizer.entities.MeteorEntity;
import com.kiv.kivsizer.util.RegistryHandler;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("kivsizer")
public class KivSizer
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "kivsizer";
    public static int TickCounter = 0;

    public KivSizer() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        /*DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(RegistryHandler.METEOR.get(), MeteorEntity.setCustomAttributes().create());
        });*/
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    public static final ItemGroup TAB = new ItemGroup("KivTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.CARROT);
        }
    };
}

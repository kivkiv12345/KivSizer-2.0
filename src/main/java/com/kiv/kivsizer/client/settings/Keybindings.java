package com.kiv.kivsizer.client.settings;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybindings {
    //public static KeyBinding charge = new KeyBinding(Names.Keys.CHARGE, Swing., Names.Keys.CATEGORY);

    public static KeyBinding hello;

    public static void register()
    {
        ClientRegistry.registerKeyBinding(hello);
    }
}

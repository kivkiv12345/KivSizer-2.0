package com.kiv.kivsizer.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
    @Override
    public void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(null);
    }

    public void preInit(){
        registerKeyBindings();
    }
}

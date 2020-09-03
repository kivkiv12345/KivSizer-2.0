package com.kiv.kivsizer.client.settings;

import com.sun.glass.events.KeyEvent;
import net.minecraft.client.settings.KeyBinding;

public enum Keybindings2 {

    EXPLODE("key.kivsizer.explode", KeyEvent.VK_A);

    private KeyBinding keybinding;

    Keybindings2(String s, int vkA) {
    }

    private void Keybindings(String keyName, int defaultKeyCode){
        keybinding = new KeyBinding(keyName, defaultKeyCode, "key.categories.kivsizer");
    }

    public KeyBinding getKeybind(){
        return keybinding;
    }

    public boolean isPressed(){
        return keybinding.isPressed();
    }
}

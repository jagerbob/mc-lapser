package com.jagerbob.lapser.helpers;

import com.jagerbob.lapser.controller.IClientController;
import com.jagerbob.lapser.model.KeyBind;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class KeyBindManager {

    private IClientController controller;
    private final KeyBind[] keyBinds = new KeyBind[] {
            new KeyBind("com.jagerbob.lapser.toggleZoneEditor", GLFW.GLFW_KEY_R, "general.lapser", (MinecraftClient client) -> controller.toggleEditor(client)),
            new KeyBind("com.jagerbob.lapser.play", GLFW.GLFW_KEY_Y, "general.lapser", (client) -> controller.play(Objects.requireNonNull(client.player).getBlockPos(), "square_layer"))
    };

    public KeyBindManager(IClientController controller)
    {
        this.controller = controller;
    }
    
    public void RegisterBindings() {
        for (KeyBind keyBind: keyBinds)
            KeyBindingHelper.registerKeyBinding(keyBind);

        ClientTickEvents.END_CLIENT_TICK.register((client -> {
            for (KeyBind keyBind: keyBinds)
                while(keyBind.wasPressed()) {
                    keyBind.OnClick(client);
                }
        }));
    }


}

package com.jagerbob.lapser.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public class KeyBind extends KeyBinding {

    private final IAction action;

    public KeyBind(String translationKey, int code, String category, IAction action) {
        super(translationKey, code, category);
        this.action = action;
    }

    public void OnClick(MinecraftClient client) {
        action.onAction(client);
    }
}

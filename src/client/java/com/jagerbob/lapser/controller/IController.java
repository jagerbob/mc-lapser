package com.jagerbob.lapser.controller;

import net.minecraft.client.MinecraftClient;

public interface IController {
    void toggleEditor(MinecraftClient client);
    void play(MinecraftClient client);
    void setCoordinatesA();
    void setCoordinatesB();
}

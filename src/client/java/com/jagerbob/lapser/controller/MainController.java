package com.jagerbob.lapser.controller;

import com.jagerbob.lapser.commands.CommandHandler;
import com.jagerbob.lapser.model.IMainViewModel;
import com.jagerbob.lapser.model.MainViewModel;
import com.jagerbob.lapser.view.AreaEditor;
import com.jagerbob.lapser.view.LapserScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

public class MainController implements IController {
    private final AreaEditor areaEditor;
    private MinecraftClient client;
    private ClientPlayerEntity player;
    private final CommandHandler commandHandler;
    private final IMainViewModel viewModel;

    public MainController(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.viewModel = new MainViewModel();
        this.areaEditor = new AreaEditor(this);
    }

    @Override
    public void toggleEditor(MinecraftClient client) {
        this.client = client;
        this.player = Objects.requireNonNull(client.player);
        this.player.sendMessage(Text.literal("Toggle Editor Triggered"), false);
        this.client.setScreen(new LapserScreen(areaEditor));
    }

    @Override
    public void play(MinecraftClient client) {
        this.client = client;
        this.player = Objects.requireNonNull(client.player);
        this.player.sendMessage(Text.literal("Play triggered"), false);
    }

    @Override
    public void setCoordinatesA() {
        viewModel.setCoordinatesA(this.player.getBlockPos());
        update();
    }

    @Override
    public void setCoordinatesB() {
        viewModel.setCoordinatesB(this.player.getBlockPos());
        update();
    }

    private void update() {
        areaEditor.update(this.viewModel);
    }
}

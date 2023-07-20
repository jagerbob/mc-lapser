package com.jagerbob.lapser.controller;

import com.jagerbob.lapser.config.Packets;
import com.jagerbob.lapser.model.IMainViewModel;
import com.jagerbob.lapser.model.MainViewModel;
import com.jagerbob.lapser.view.AreaEditor;
import com.jagerbob.lapser.view.LapserScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

public class ClientController implements IClientController {
    private final AreaEditor areaEditor;
    private MinecraftClient client;
    private ClientPlayerEntity player;
    private final IMainViewModel viewModel;

    public ClientController() {
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

    @Override
    public void saveArea() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(viewModel.getCoordinatesA());
        ClientPlayNetworking.send(Packets.SAVE_AREA_PACKET, buf);
    }

    @Override
    public void retrieveArea(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        try {
            BlockState state = deserializeObject(buf.readByteArray());
            this.player.sendMessage(Text.literal(state.getBlock().getTranslationKey()), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private BlockState deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        BlockState yourObject = (BlockState) objectInputStream.readObject();
        objectInputStream.close();
        return yourObject;
    }

    private void update() {
        areaEditor.update(this.viewModel);
    }
}

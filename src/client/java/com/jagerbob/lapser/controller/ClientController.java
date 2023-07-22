package com.jagerbob.lapser.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSerializer;
import com.jagerbob.lapser.config.Packets;
import com.jagerbob.lapser.helpers.BlockPosMapper;
import com.jagerbob.lapser.helpers.BlockStateMapper;
import com.jagerbob.lapser.model.IMainViewModel;
import com.jagerbob.lapser.model.LapserFile;
import com.jagerbob.lapser.model.MainViewModel;
import com.jagerbob.lapser.view.AreaEditor;
import com.jagerbob.lapser.view.LapserScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
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
        LapserFile file = LapserFile.fromJson(viewModel.getTempFile());
        BlockPos coordA = player.getBlockPos().add(file.getRelativeCoordinatesA());
        BlockPos coordB = player.getBlockPos().add(file.getRelativeCoordinatesB());
        new Thread(() -> {
            int index = 0;
            for(BlockPos pos: BlockPos.iterate(coordA, coordB)) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(pos);
                buf.writeString(file.getScan().get(index));
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                index++;
                ClientPlayNetworking.send(Packets.PLACE, buf);
            }
        }).start();
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
        viewModel.setOrigin(this.player.getBlockPos());
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(viewModel.getCoordinatesA());
        buf.writeBlockPos(viewModel.getCoordinatesB());
        buf.writeBlockPos(viewModel.getOrigin());
        ClientPlayNetworking.send(Packets.SAVE_AREA_PACKET, buf);
    }

    @Override
    public void retrieveArea(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        LapserFile file = LapserFile.fromBuffer(buf);
        String fileAsJson = new Gson().toJson(file);
        viewModel.setTempFile(fileAsJson);
    }

    private void update() {
        areaEditor.update(this.viewModel);
    }
}

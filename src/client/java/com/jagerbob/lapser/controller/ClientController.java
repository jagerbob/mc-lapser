package com.jagerbob.lapser.controller;

import com.jagerbob.lapser.algorithms.TimeLapseAlgorithm;
import com.jagerbob.lapser.algorithms.TimeLapseAlgorithmFactory;
import com.jagerbob.lapser.config.Packets;
import com.jagerbob.lapser.helpers.BlockPosMapper;
import com.jagerbob.lapser.model.IMainViewModel;
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
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class ClientController implements IClientController {

    private final IMainViewModel viewModel;
    private final AreaEditor areaEditor;
    private MinecraftClient client;
    private ClientPlayerEntity player;

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
        TimeLapseAlgorithm algorithm = TimeLapseAlgorithmFactory.create("SQUARE_LAYER");
        algorithm.play(player.getBlockPos(), viewModel.getOrigin(), viewModel.getCoordinatesA(), viewModel.getScan());
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
        buf.writeBlockPos(viewModel.getCoordinatesB());
        buf.writeBlockPos(viewModel.getCoordinatesA());
        ClientPlayNetworking.send(Packets.SAVE_AREA_PACKET, buf);
    }

    @Override
    public void retrieveArea(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        BlockPos diff = BlockPosMapper.toRelativeCoordinates(viewModel.getRelativeCoordinatesA(), viewModel.getRelativeCoordinatesB());
        String[][][] scan = new String[diff.getX() + 1][diff.getY() + 1][diff.getZ() + 1];
        for(BlockPos pos : BlockPos.iterate(BlockPos.ORIGIN, diff))
            scan[pos.getX()][pos.getY()][pos.getZ()] = buf.readString();
        viewModel.setScan(scan);
    }

    private void update() {
        areaEditor.update(this.viewModel);
    }
}

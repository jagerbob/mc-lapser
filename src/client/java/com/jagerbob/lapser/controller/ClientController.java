package com.jagerbob.lapser.controller;

import com.jagerbob.lapser.algorithms.TimeLapseAlgorithm;
import com.jagerbob.lapser.algorithms.TimeLapseAlgorithmFactory;
import com.jagerbob.lapser.config.Messages;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class ClientController implements IClientController {

    private final IMainViewModel viewModel;
    private final AreaEditor areaEditor;

    public ClientController() {
        this.viewModel = new MainViewModel();
        this.areaEditor = new AreaEditor(this);
    }

    @Override
    public void toggleEditor(MinecraftClient client) {
        client.setScreen(new LapserScreen(areaEditor));
        this.areaEditor.setPlayer(Objects.requireNonNull(client.player));
    }

    @Override
    public void play(BlockPos origin, String algorithmAsString, int speed) {
        TimeLapseAlgorithm algorithm = TimeLapseAlgorithmFactory.create(algorithmAsString);
        algorithm.configure(true, 101 - speed);
        algorithm.play(origin, viewModel.getOrigin(), viewModel.getCoordinatesA(), viewModel.getScan());
    }

    @Override
    public void setCoordinatesA(BlockPos pos, PlayerEntity player) {
        viewModel.setCoordinatesA(pos);
        player.sendMessage(Messages.getCoordinateASetText(pos));
        update();
    }

    @Override
    public void setCoordinatesB(BlockPos pos, PlayerEntity player) {
        viewModel.setCoordinatesB(pos);
        player.sendMessage(Messages.getCoordinateBSetText(pos));
        update();
    }

    @Override
    public void saveArea(BlockPos origin, PlayerEntity player) {
        viewModel.setOrigin(origin);
        BlockPos diff = BlockPosMapper.toRelativeCoordinates(viewModel.getRelativeCoordinatesA(), viewModel.getRelativeCoordinatesB());
        viewModel.setScan(new String[diff.getX() + 1][diff.getY() + 1][diff.getZ() + 1]);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(origin);
        buf.writeBlockPos(viewModel.getCoordinatesB());
        buf.writeBlockPos(viewModel.getCoordinatesA());
        ClientPlayNetworking.send(Packets.SAVE_AREA_PACKET, buf);
        player.sendMessage(Messages.getSaveSuccessfullText());
    }

    @Override
    public void retrieveArea(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        viewModel.setScanBlock(buf.readBlockPos(), buf.readString());
    }

    private void update() {
        areaEditor.update(this.viewModel);
    }
}

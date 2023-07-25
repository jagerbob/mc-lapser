package com.jagerbob.lapser.view;

import com.jagerbob.lapser.controller.IClientController;
import com.jagerbob.lapser.helpers.MessageHelper;
import com.jagerbob.lapser.model.IMainViewModel;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class AreaEditor extends LightweightGuiDescription implements ILapserView {

    private final MessageHelper messageHelper;
    private final IClientController controller;
    private final WGridPanel root;
    private final WButton setCornerABtn;
    private final WButton setCornerBBtn;
    private final WButton saveAreaBtn;
    private final WLabel editorTitle;
    private final WLabel coordinatesA;
    private final WLabel coordinatesB;
    private ClientPlayerEntity player;

    public AreaEditor(IClientController controller){
        this.messageHelper = new MessageHelper();
        this.controller = controller;
        this.root = new WGridPanel();
        this.editorTitle = new WLabel(Text.literal("Lapser area editor"));
        this.setCornerABtn = new WButton(Text.literal("Set corner A"));
        this.setCornerBBtn = new WButton(Text.literal("Set corner B"));
        this.saveAreaBtn = new WButton(Text.literal("Save Area"));
        this.coordinatesA = new WLabel(Text.literal("X: 509, Y: 64, Z: 48"));
        this.coordinatesB = new WLabel(Text.literal("X: 509, Y: 64, Z: 48"));
        buildView();
    }

    @Override
    public void update(IMainViewModel viewModel) {
        coordinatesA.setText(Text.literal(messageHelper.toStringCoordinates(viewModel.getCoordinatesA())));
        coordinatesB.setText(Text.literal(messageHelper.toStringCoordinates(viewModel.getCoordinatesB())));
    }

    private void onCoordinateAClick() {
        controller.setCoordinatesA(player.getBlockPos(), player);
    }

    private void onCoordinateBClick() {
        controller.setCoordinatesB(player.getBlockPos(), player);
    }

    private void onSaveClick() {
        controller.saveArea(player.getBlockPos(), player);
    }

    private void buildView() {
        setRootPanel(root);
        root.setSize(280, 150);
        root.setGaps(10,10);
        setCornerABtn.setOnClick(this::onCoordinateAClick);
        setCornerBBtn.setOnClick(this::onCoordinateBClick);
        saveAreaBtn.setOnClick(this::onSaveClick);
        editorTitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        editorTitle.setVerticalAlignment(VerticalAlignment.TOP);
        coordinatesA.setVerticalAlignment(VerticalAlignment.CENTER);
        coordinatesB.setVerticalAlignment(VerticalAlignment.CENTER);
        coordinatesA.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        coordinatesB.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        setCornerABtn.setOnClick(this::onCoordinateAClick);
        setCornerBBtn.setOnClick(this::onCoordinateBClick);
        saveAreaBtn.setOnClick(this::onSaveClick);
        root.add(editorTitle, 1, 1, 8, 1);
        root.add(setCornerABtn, 1, 2, 4, 1);
        root.add(setCornerBBtn, 1, 3, 4, 1);
        root.add(coordinatesA, 5, 2, 4, 1);
        root.add(coordinatesB, 5, 3, 4, 1);
        root.add(saveAreaBtn, 1, 4, 8, 1);
        root.validate(this);
    }

    public void setPlayer(ClientPlayerEntity player) {
        this.player = player;
    }
}

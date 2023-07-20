package com.jagerbob.lapser.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AreaEditor extends LightweightGuiDescription {

    public AreaEditor(){
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(280, 150);
        root.setGaps(10,10);

        WLabel editorTitle = new WLabel(Text.literal("Lapser area editor"));

        WButton setCornerABtn = new WButton(Text.literal("Set corner A"));
        WButton setCornerBBtn = new WButton(Text.literal("Set corner B"));
        WButton saveAreaBtn = new WButton(Text.literal("Save Area"));

        WLabel coordinatesA = new WLabel(Text.literal("X: 509, Y: 64, Z: 48"));
        WLabel coordinatesB = new WLabel(Text.literal("X: 509, Y: 64, Z: 48"));

        editorTitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        editorTitle.setVerticalAlignment(VerticalAlignment.TOP);
        coordinatesA.setVerticalAlignment(VerticalAlignment.CENTER);
        coordinatesB.setVerticalAlignment(VerticalAlignment.CENTER);
        coordinatesA.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        coordinatesB.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        root.add(editorTitle, 1, 1, 8, 1);
        root.add(setCornerABtn, 1, 2, 4, 1);
        root.add(setCornerBBtn, 1, 3, 4, 1);
        root.add(coordinatesA, 5, 2, 4, 1);
        root.add(coordinatesB, 5, 3, 4, 1);
        root.add(saveAreaBtn, 1, 4, 8, 1);

        root.validate(this);
    }
}

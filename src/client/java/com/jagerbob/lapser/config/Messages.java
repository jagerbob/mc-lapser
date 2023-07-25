package com.jagerbob.lapser.config;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class Messages {

    public static Text getSaveSuccessfullText() {
        return Text.translatable("lapser.on_save_successfull").setStyle(Style.EMPTY.withColor(Formatting.GOLD));
    }

    public static Text getCoordinateASetText(BlockPos pos) {
        return Text.translatable("lapser.set_corner_sucessfully", "A", pos.getX(), pos.getY(), pos.getZ()).setStyle(Style.EMPTY.withColor(Formatting.GOLD));
    }

    public static Text getCoordinateBSetText(BlockPos pos) {
        return Text.translatable("lapser.set_corner_sucessfully", "B", pos.getX(), pos.getY(), pos.getZ()).setStyle(Style.EMPTY.withColor(Formatting.GOLD));
    }

}

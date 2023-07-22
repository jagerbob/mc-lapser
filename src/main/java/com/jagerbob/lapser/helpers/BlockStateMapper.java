package com.jagerbob.lapser.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.util.StringIdentifiable;

import java.util.Iterator;
import java.util.Objects;

public class BlockStateMapper {

    public static String toString(BlockState state){
        StringBuilder builder = new StringBuilder();
        builder.append(Objects.requireNonNull(Registries.BLOCK.getId(state.getBlock())));
        boolean flag = true;
        Iterator<Property<?>> iterator = state.getProperties().iterator();
        while (iterator.hasNext()) {
            if (flag) {
                builder.append("[");
                flag = false;
            }

            Property<?> property = iterator.next();
            builder.append(property.getName());
            builder.append("=");

            if (state.get(property) instanceof Enum<?>) {
                // Enum might have override toString
                builder.append(((StringIdentifiable) state.get(property)).asString());
            } else {
                builder.append(state.get(property).toString());
            }

            if (iterator.hasNext()) {
                builder.append(",");
            }
        }
        if (!flag) {
            builder.append("]");
        }
        return builder.toString();
    }
}

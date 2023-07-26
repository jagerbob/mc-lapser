package com.jagerbob.lapser.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

import java.util.Iterator;
import java.util.Objects;

public class BlockStateMapper {

    public static BlockState toModel(String string) {
        if (!string.contains("[") && !string.contains("]")) {
            return Registries.BLOCK.get(new Identifier(string)).getDefaultState();
        } else {
            Block block = Registries.BLOCK.get(new Identifier(string.substring(0, string.indexOf("["))));
            BlockState state = block.getDefaultState();

            System.out.println(state);

            String[] stateArray = string.substring(string.indexOf("[") + 1, string.length() - 1).split(",");
            for (String stateString : stateArray) {
                String name = stateString.split("=")[0];
                Property<?> property = block.getStateManager().getProperty(name);
                state = process(property, stateString.split("=")[1], state);
            }

            System.out.println(state);

            return state;
        }
    }
    private static <T extends Comparable<T>> BlockState process(Property<T> property, String value, BlockState state) {
        return state.with(property, property.parse(value).orElseThrow(NullPointerException::new));
    }

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

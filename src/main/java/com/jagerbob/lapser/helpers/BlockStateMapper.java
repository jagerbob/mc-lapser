package com.jagerbob.lapser.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlockStateMapper {

    private static final String STATE_REGEX = "^(.*?)(?:\\[(.*?)\\])?$";
    private final Pattern pattern;

    public BlockStateMapper() {
        this.pattern = Pattern.compile(STATE_REGEX);
    }

    public BlockState toModel(String string) {
        Matcher matcher = pattern.matcher(string);
        if (!matcher.matches())
            throw new InvalidParameterException("Unable to parse string");

        Block block = Registries.BLOCK.get(new Identifier(matcher.group(1)));
        BlockState state = block.getDefaultState();
        String stateAsString = matcher.group(2);

        if (stateAsString != null && !stateAsString.isEmpty()) {
            for (String stringProperty : stateAsString.split(",")) {
                Property<?> property = block.getStateManager().getProperty(stringProperty.split("=")[0]);
                state = process(property, stringProperty.split("=")[1], state);
            }
        }

        return state;
    }

    public String toString(BlockState state){
        StringBuilder builder = new StringBuilder();
        builder.append(Objects.requireNonNull(Registries.BLOCK.getId(state.getBlock())));
        List<String> propertiesAsStrings = new ArrayList<>();

        for(Property<?> property: state.getProperties())
        {
            String value = (state.get(property) instanceof Enum<?>) ? ((StringIdentifiable) state.get(property)).asString() : state.get(property).toString();
            propertiesAsStrings.add(property.getName() + "=" + value);
        }

        if(!propertiesAsStrings.isEmpty())
            builder.append("[").append(String.join(",", propertiesAsStrings)).append("]");

        return builder.toString();
    }

    private <T extends Comparable<T>> BlockState process(Property<T> property, String value, BlockState state) {
        return state.with(property, property.parse(value).orElseThrow(NullPointerException::new));
    }
}

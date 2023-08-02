package com.jagerbob.lapser.algorithms;

public class TimeLapseAlgorithmFactory {

    public static TimeLapseAlgorithm create(String algName) {
        return switch (algName) {
            case "z_vertical" -> new ZVerticalAlgorithm();
            case "full_layer" -> new FullLayerAlgorithm();
            case "square_layer" -> new SquareLayeredAlgorithm();
            default -> new XVerticalAlgorithm();
        };
    }
}

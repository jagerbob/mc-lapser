package com.jagerbob.lapser.algorithms;

public class TimeLapseAlgorithmFactory {

    public static TimeLapseAlgorithm create(String algName) {
        switch (algName) {
            case "x_vertical":
                return new XVerticalAlgorithm();
            case "z_vertical":
                return new ZVerticalAlgorithm();
            case "full_layer":
                return new FullLayerAlgorithm();
            case "square_layer":
                return new SquareLayeredAlgorithm();
            default:
                return new XVerticalAlgorithm();
        }
    }
}

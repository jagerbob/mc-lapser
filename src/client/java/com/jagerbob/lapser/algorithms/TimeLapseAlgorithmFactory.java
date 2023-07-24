package com.jagerbob.lapser.algorithms;

public class TimeLapseAlgorithmFactory {

    public static TimeLapseAlgorithm create(String algName) {
        switch (algName) {
            case "X_VERTICAL":
                return new XVerticalAlgorithm();
            case "Z_VERTICAL":
                return new ZVerticalAlgorithm();
            case "FULL_LAYER":
                return new FullLayerAlgorithm();
            case "SQUARE_LAYER":
                return new SquareLayeredAlgorithm();
            default:
                return new XVerticalAlgorithm();
        }
    }
}

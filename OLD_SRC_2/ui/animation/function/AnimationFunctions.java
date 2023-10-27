package com.sleepwalker.sleeplib.ui.animation.function;

import javax.annotation.Nonnull;

public final class AnimationFunctions {

    private static final double C1 = 1.70158;
    private static final double C2 = C1 * 1.525;
    private static final double C3 = C1 + 1.0;
    private static final double C4 = (2.0 * Math.PI) / 3.0;
    private static final double C5 = (2.0 * Math.PI) / 4.5;
    private static final double N1 = 7.5625;
    private static final double D1 = 2.75;

    @Nonnull public static final AnimationFunction EASE_IN_SINE = input -> 1 - Math.cos((input * Math.PI) / 2);
    @Nonnull public static final AnimationFunction EASE_OUT_SINE = input -> Math.sin((input * Math.PI) / 2);
    @Nonnull public static final AnimationFunction EASE_IN_OUT_SINE = input -> -(Math.cos(Math.PI * input) - 1) / 2;
    @Nonnull public static final AnimationFunction EASE_IN_QUAD = input -> input * input;
    @Nonnull public static final AnimationFunction EASE_OUT_QUAD = input -> 1 - (1 - input) * (1 - input);
    @Nonnull public static final AnimationFunction EASE_IN_OUT_QUAD = input -> input < 0.5 ? 2 * input * input : 1 - Math.pow(-2 * input + 2, 2) / 2;
    @Nonnull public static final AnimationFunction EASE_IN_CUBIC = input -> input * input * input;
    @Nonnull public static final AnimationFunction EASE_OUT_CUBIC = input -> 1 - Math.pow(1 - input, 3);
    @Nonnull public static final AnimationFunction EASE_IN_OUT_CUBIC = input -> input < 0.5 ? 4 * input * input * input : 1 - Math.pow(-2 * input + 2, 3) / 2;
    @Nonnull public static final AnimationFunction EASE_IN_QUART = input -> input * input * input * input;
    @Nonnull public static final AnimationFunction EASE_OUT_QUART = input -> 1 - Math.pow(1 - input, 4);
    @Nonnull public static final AnimationFunction EASE_IN_OUT_QUART = input -> input < 0.5 ? 8 * input * input * input * input : 1 - Math.pow(-2 * input + 2, 4) / 2;
    @Nonnull public static final AnimationFunction EASE_IN_QUINT = input -> input * input * input * input * input;
    @Nonnull public static final AnimationFunction EASE_OUT_QUINT = input -> 1 - Math.pow(1 - input, 5);
    @Nonnull public static final AnimationFunction EASE_IN_OUT_QUINT = input -> input < 0.5 ? 16 * input * input * input * input * input : 1 - Math.pow(-2 * input + 2, 5) / 2;
    @Nonnull public static final AnimationFunction EASE_IN_EXPO = input -> input == 0 ? 0 : Math.pow(2, 10 * input - 10);
    @Nonnull public static final AnimationFunction EASE_OUT_EXPO = input -> input == 1 ? 1 : 1 - Math.pow(2, -10 * input);
    @Nonnull public static final AnimationFunction EASE_IN_OUT_EXPO = input -> input == 0 ? 0 : input == 1 ? 1 : input < 0.5 ? Math.pow(2, 20 * input - 10) / 2 : (2 - Math.pow(2, -20 * input + 10)) / 2;
    @Nonnull public static final AnimationFunction EASE_IN_CIRC = input -> 1 - Math.sqrt(1 - Math.pow(input, 2));
    @Nonnull public static final AnimationFunction EASE_OUT_CIRC = input -> Math.sqrt(1 - Math.pow(input - 1, 2));
    @Nonnull public static final AnimationFunction EASE_IN_OUT_CIRC = input -> input < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * input, 2))) / 2 : (Math.sqrt(1 - Math.pow(-2 * input + 2, 2)) + 1) / 2;;
    @Nonnull public static final AnimationFunction EASE_IN_BACK = input -> C3 * input * input * input - C1 * input * input;
    @Nonnull public static final AnimationFunction EASE_OUT_BACK = input -> 1 + C3 * Math.pow(input - 1, 3) + C1 * Math.pow(input - 1, 2);
    @Nonnull public static final AnimationFunction EASE_IN_OUT_BACK = input -> input < 0.5 ? (Math.pow(2 * input, 2) * ((C2 + 1) * 2 * input - C2)) / 2 : (Math.pow(2 * input - 2, 2) * ((C2 + 1) * (input * 2 - 2) + C2) + 2) / 2;
    @Nonnull public static final AnimationFunction EASE_IN_ELASTIC = input -> input == 0 ? 0 : input == 1 ? 1 : -Math.pow(2, 10 * input - 10) * Math.sin((input * 10 - 10.75) * C4);
    @Nonnull public static final AnimationFunction EASE_OUT_ELASTIC = input -> input == 0 ? 0 : input == 1 ? 1 : Math.pow(2, -10 * input) * Math.sin((input * 10 - 0.75) * C4) + 1;
    @Nonnull public static final AnimationFunction EASE_IN_OUT_ELASTIC = input -> input == 0 ? 0 : input == 1 ? 1 : input < 0.5 ? -(Math.pow(2, 20 * input - 10) * Math.sin((20 * input - 11.125) * C5)) / 2 : (Math.pow(2, -20 * input + 10) * Math.sin((20 * input - 11.125) * C5)) / 2 + 1;
    @Nonnull public static final AnimationFunction EASE_OUT_BOUNCE = input -> {
        if (input < 1 / D1) {
            return N1 * input * input;
        }
        else if (input < 2 / D1) {
            return N1 * (input -= 1.5 / D1) * input + 0.75;
        }
        else if (input < 2.5 / D1) {
            return N1 * (input -= 2.25 / D1) * input + 0.9375;
        }
        else {
            return N1 * (input -= 2.625 / D1) * input + 0.984375;
        }
    };
    @Nonnull public static final AnimationFunction EASE_IN_BOUNCE = input -> 1 - EASE_OUT_BOUNCE.getOutput(1 - input);
    @Nonnull public static final AnimationFunction EASE_IN_OUT_BOUNCE = input -> input < 0.5 ? (1 - EASE_OUT_BOUNCE.getOutput(1 - 2 * input)) / 2 : (1 + EASE_OUT_BOUNCE.getOutput(2 * input - 1)) / 2;
}

package com.sleepwalker.sleeplib.ui.animation;

import com.sleepwalker.sleeplib.ui.animation.function.AnimationFunction;

import javax.annotation.Nonnull;

public class DoubleAnimation {

    @Nonnull private final AnimationFunction function;
    private final long ms;
    private final double value;

    public DoubleAnimation(@Nonnull AnimationFunction function, long ms, double start, double end) {
        this.function = function;
        this.ms = ms;
        this.value = end - start;
    }

    public double getValue(double milliseconds){
        return function.getOutput(milliseconds / ms) * value;
    }

    public double getMs() {
        return ms;
    }
}

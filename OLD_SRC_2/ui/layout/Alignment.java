package com.sleepwalker.sleeplib.ui.layout;

import javax.annotation.Nonnull;

public class Alignment {

    @Nonnull public static final Alignment LEFT_TOP      = new Alignment(0f, 1f);
    @Nonnull public static final Alignment LEFT_MIDDLE   = new Alignment(0f, 0.5f);
    @Nonnull public static final Alignment LEFT_BOTTOM   = new Alignment(0f, 0f);
    @Nonnull public static final Alignment CENTER_TOP    = new Alignment(0.5f, 1f);
    @Nonnull public static final Alignment CENTER_MIDDLE = new Alignment(0.5f, 0.5f);
    @Nonnull public static final Alignment CENTER_BOTTOM = new Alignment(0.5f, 0f);
    @Nonnull public static final Alignment RIGHT_TOP     = new Alignment(1f, 1f);
    @Nonnull public static final Alignment RIGHT_MIDDLE  = new Alignment(1f, 0.5f);
    @Nonnull public static final Alignment RIGHT_BOTTOM  = new Alignment(1f, 0f);

    private final float biasX;
    private final float biasY;

    public Alignment(float biasX, float biasY) {
        this.biasX = biasX;
        this.biasY = biasY;
    }

    public float getBiasX() {
        return biasX;
    }

    public float getBiasY() {
        return biasY;
    }
}

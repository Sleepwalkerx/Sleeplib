package com.sleepwalker.sleeplib.ui.layout;

import javax.annotation.Nonnull;

public class Offset {

    public static @Nonnull Offset ZERO = new Offset(0, 0);

    private final float x, y;

    public Offset(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

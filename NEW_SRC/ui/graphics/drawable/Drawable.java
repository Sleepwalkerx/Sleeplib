package com.sleepwalker.sleeplib.ui.graphics.drawable;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;

import javax.annotation.Nonnull;

public interface Drawable {

    int INDEFINABLE = -1;

    void draw(@Nonnull Canvas canvas);

    int getWidth();
    int getHeight();
}

package com.sleepwalker.sleeplib.ui.graphics.drawable;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;

import javax.annotation.Nonnull;

public final class Drawables {

    @Nonnull public static final Drawable EMPTY = new Drawable() {
        @Override
        public void draw(@Nonnull Canvas canvas) { }
        @Override
        public int getWidth() {
            return Drawable.INDEFINABLE;
        }
        @Override
        public int getHeight() {
            return Drawable.INDEFINABLE;
        }
    };
}

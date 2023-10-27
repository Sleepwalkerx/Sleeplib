package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;

import javax.annotation.Nonnull;

public class MCBackground extends BaseWidget {

    @Override
    public void draw(@Nonnull Canvas canvas) {
        root.getScreen().renderBackground(canvas.getMatrix());
    }
}

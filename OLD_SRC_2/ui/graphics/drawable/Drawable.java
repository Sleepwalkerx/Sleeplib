package com.sleepwalker.sleeplib.ui.graphics.drawable;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;

import javax.annotation.Nonnull;

public interface Drawable {

    void draw(@Nonnull Canvas canvas);

    default float getDrawableWidth(){
        return 0;
    }

    default float getDrawableHeight(){
        return 0;
    }
}

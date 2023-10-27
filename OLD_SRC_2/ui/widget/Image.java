package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.property.Properties;

import javax.annotation.Nonnull;

public class Image extends BaseClickable {

    @Override
    public void draw(@Nonnull Canvas canvas) {
        setupCanvas(canvas);
        getPropertyValue(Properties.BACKGROUND).draw(canvas);
    }

    @Override
    public float getX() {
        return super.getX() + getPropertyValue(Properties.ANIMATION_X);
    }

    @Override
    public float getY() {
        return super.getY() + getPropertyValue(Properties.ANIMATION_Y);
    }
}

package com.sleepwalker.sleeplib.ui.graphics;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public interface Canvas {

    void push();
    void pop();

    void setPosition(float x, float y);
    void setPosition(float x, float y, float z);
    void setScale(float width, float height);

    void setMatrix(@Nonnull MatrixStack matrix);
    @Nonnull MatrixStack getMatrix();

    void setPartialTick(float partialTick);
    float getPartialTick();

    float getX();
    float getY();
    float getZ();

    float getWidth();
    float getHeight();

    int getIntWidth();
    int getIntHeight();
}

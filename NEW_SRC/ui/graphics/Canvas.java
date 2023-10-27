package com.sleepwalker.sleeplib.ui.graphics;

import com.mojang.blaze3d.matrix.MatrixStack;

import javax.annotation.Nonnull;

public interface Canvas {

    void translate(float x, float y, float z);
    void scale(float width, float height);

    void setMatrix(@Nonnull MatrixStack matrix);
    @Nonnull MatrixStack getMatrix();

    void setPartialTick(float partialTick);
    float getPartialTick();

    float getX();
    float getY();
    float getZ();

    float getWidth();
    float getHeight();
}

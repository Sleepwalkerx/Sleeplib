package com.sleepwalker.sleeplib.ui.graphics;

import com.mojang.blaze3d.matrix.MatrixStack;

import javax.annotation.Nonnull;

public class DynamicCanvas implements Canvas {

    private float x, y, z;
    private float width, height;
    private MatrixStack matrix;
    private float partialTick;

    @Override
    public void translate(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void scale(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setPartialTick(float partialTick) {
        this.partialTick = partialTick;
    }

    @Override
    public float getPartialTick() {
        return partialTick;
    }

    @Override
    public void setMatrix(@Nonnull MatrixStack matrix) {
        this.matrix = matrix;
    }

    @Nonnull
    @Override
    public MatrixStack getMatrix() {
        return matrix;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}

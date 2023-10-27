package com.sleepwalker.sleeplib.ui.graphics;

import com.mojang.blaze3d.matrix.MatrixStack;

import javax.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.Deque;

public class DynamicCanvas implements Canvas {

    @Nonnull private final Deque<Entry> entries = new ArrayDeque<>();

    private float x, y, z;
    private float width, height;
    private MatrixStack matrix;
    private float partialTick;

    @Override
    public void push() {
        entries.addLast(new Entry(x, y, z, width, height));
    }

    @Override
    public void pop() {
        Entry entry = entries.removeLast();
        entry.writeToCanvas(this);
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void setScale(float width, float height) {
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
    public int getIntWidth() {
        return (int) width;
    }

    @Override
    public int getIntHeight() {
        return (int) height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public static class Entry {

        private final float x, y, z;
        private final float width, height;

        public Entry(float x, float y, float z, float width, float height) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.width = width;
            this.height = height;
        }

        public void writeToCanvas(@Nonnull Canvas canvas){
            canvas.setPosition(x, y, z);
            canvas.setScale(width, height);
        }
    }
}

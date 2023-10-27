package com.sleepwalker.sleeplib.ui.graphics.drawable.sprite;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TextureSource {

    @Nonnull
    private final ResourceLocation location;
    private final int width, height;

    public TextureSource(@Nonnull ResourceLocation location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    @Nonnull
    public ResourceLocation getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

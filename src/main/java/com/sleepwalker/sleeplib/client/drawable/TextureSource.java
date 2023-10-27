package com.sleepwalker.sleeplib.client.drawable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TextureSource {

    @Nonnull private final ResourceLocation location;
    private final int width;
    private final int height;

    public TextureSource(@Nonnull ResourceLocation location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    @Nonnull
    public static TextureSource of(@Nonnull ResourceLocation resourceLocation, int width, int height){
        return new TextureSource(resourceLocation, width, height);
    }

    @Nonnull
    public static TextureSource of(@Nonnull String namespace, @Nonnull String path, int width, int height){
        return new TextureSource(new ResourceLocation(namespace, path), width, height);
    }

    public void bind(){
        Minecraft.getInstance().getTextureManager().bindTexture(location);
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

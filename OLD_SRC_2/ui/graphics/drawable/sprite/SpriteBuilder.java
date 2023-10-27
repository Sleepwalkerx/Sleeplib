package com.sleepwalker.sleeplib.ui.graphics.drawable.sprite;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class SpriteBuilder {

    private TextureSource source;

    private int width = 16, height = 16;
    private int right = 1, left = 1, top = 1, bottom = 1;
    private int u = 0, v = 0;

    @Nonnull
    public SpriteBuilder setSource(@Nonnull String domain, @Nonnull String path, int width, int height){
        source = new TextureSource(new ResourceLocation(domain, path), width, height);
        return this;
    }

    @Nonnull
    public SpriteBuilder setSize(int width, int height){
        this.width = width;
        this.height = height;
        return this;
    }

    @Nonnull
    public SpriteBuilder setUVFromSize(int uF, int vF){
        this.u = width * uF;
        this.v = height * vF;
        return this;
    }

    @Nonnull
    public SpriteBuilder setUV(int u, int v){
        this.u = u;
        this.v = v;
        return this;
    }

    @Nonnull
    public SpriteBuilder setBorder(int border){
        return setBorder(border, border, border, border);
    }

    @Nonnull
    public SpriteBuilder setBorder(int right, int left, int top, int bottom){
        this.right = right;
        this.left = left;
        this.top = top;
        this.bottom = bottom;
        return this;
    }

    @Nonnull
    public ModalSprite modal(){
        return new ModalSprite(source, u, v, width, height);
    }

    @Nonnull
    public NineSliceSprite continuousBox(){
        return new NineSliceSprite(source, u, v, width, height, right, left, top, bottom);
    }
}

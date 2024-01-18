package com.sleepwalker.sleeplib.client.util;

import com.sleepwalker.sleeplib.client.drawable.*;

import javax.annotation.Nonnull;

public final class SpriteUtil {

    @Nonnull
    public static ModalSprite newModal(@Nonnull TextureSource texture){
        return newModal(texture, texture.getWidth(), texture.getHeight());
    }

    @Nonnull
    public static ModalSprite newModal(@Nonnull TextureSource texture, int uWidth, int vHeight){
        return newModal(texture, 0, 0, uWidth, vHeight);
    }

    @Nonnull
    public static ModalSprite newModal(@Nonnull TextureSource texture, int uOffset, int vOffset, int uWidth, int vHeight){
        return new ModalSprite(texture, uOffset, vOffset, uWidth, vHeight);
    }

    @Nonnull
    public static ModalSprite newModalFill(@Nonnull TextureSource texture){
        return newModalFill(texture, texture.getWidth(), texture.getHeight());
    }

    @Nonnull
    public static ModalSprite newModalFill(@Nonnull TextureSource texture, int uWidth, int vHeight){
        return newModalFill(texture, 0, 0, uWidth, vHeight);
    }

    @Nonnull
    public static ModalSprite newModalFill(@Nonnull TextureSource texture, int uOffset, int vOffset, int uWidth, int vHeight){
        return new ModalFillSprite(texture, uOffset, vOffset, uWidth, vHeight);
    }

    @Nonnull
    public static NineSliceSprite newNineSlice(@Nonnull TextureSource texture, int uOffset, int vOffset, int uWidth, int vHeight, int border){
        return newNineSlice(texture, uOffset, vOffset, uWidth, vHeight, border, border, border, border);
    }

    @Nonnull
    public static NineSliceSprite newNineSlice(@Nonnull TextureSource texture, int uOffset, int vOffset, int uWidth, int vHeight, int left, int top, int right, int bottom){
        return new NineSliceSprite(texture, uOffset, vOffset, uWidth, vHeight, left, top, right, bottom);
    }

    @Nonnull
    public static ComplexSprite newComplex(int width, int height, @Nonnull ComplexSprite.Drawer drawer){
        return new ComplexSprite(width, height, drawer);
    }

    @Nonnull
    public static ComplexSprite newComplex(@Nonnull Drawable origin, @Nonnull ComplexSprite.Drawer drawer){
        return newComplex(origin.getWidth(), origin.getHeight(), drawer);
    }
}

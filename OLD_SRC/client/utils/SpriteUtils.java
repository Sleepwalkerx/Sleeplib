package com.sleepwalker.sleeplib.client.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.math.Vector2i;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import com.sleepwalker.sleeplib.client.widget.base.sprite.BaseSprite;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;

import javax.annotation.Nonnull;

public final class SpriteUtils {

    @SuppressWarnings("rawtypes")
    @Nonnull
    private final static ISprite.IStateSupplier DEFAULT_STATE_SUPPLIER = obj -> 0;

    @SuppressWarnings("unchecked")
    @Nonnull
    public static <T> ISprite.IStateSupplier<T> defaultStateSupplier(){
        return (ISprite.IStateSupplier<T>)DEFAULT_STATE_SUPPLIER;
    }

    @Nonnull
    public static Vector2i spriteAlign(@Nonnull ISprite sprite, @Nonnull Align align, int parentWidth, int parentHeight){
        return align.mathInt(sprite.getWidth(), sprite.getHeight(), parentWidth, parentHeight);
    }

    @Nonnull
    public static ISprite scaledStateWidth(@Nonnull ResourceLocation texture, int u, int v, int border){
        return scaledStateWidth(texture, u, v, border, border, border, border);
    }

    @Nonnull
    public static ISprite scaledStateWidth(@Nonnull ResourceLocation texture, int u, int v, int top, int bottom, int left, int right){
        return scaledStateWidth(texture, u, v, top, bottom, left, right, 16, 16);
    }

    @Nonnull
    public static ISprite scaledStateWidth(@Nonnull ResourceLocation texture, int u, int v, int top, int bottom, int left, int right, int defWidth, int defHeight){
        return new BaseSprite(defWidth, defHeight) {
            @Override
            public void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state) {
                Minecraft.getInstance().textureManager.bind(texture);
                RenderSystem.enableAlphaTest();
                SLGuiUtils.drawContinuousTexturedBox(
                    ms,
                    posX, posY,
                    u + (state * getWidth()), v,
                    width, height,
                    getWidth(), getHeight(),
                    top, bottom, left, right,
                    zLevel
                );
            }
        };
    }

    @Nonnull
    public static ISprite scaled(@Nonnull ResourceLocation texture, int u, int v, int border){
        return scaled(texture, u, v, border, border, border, border);
    }

    @Nonnull
    public static ISprite scaled(@Nonnull ResourceLocation texture, int u, int v, int top, int bottom, int left, int right){
        return scaled(texture, u, v, top, bottom, left, right, 16, 16);
    }

    @Nonnull
    public static ISprite scaled(@Nonnull ResourceLocation texture, int u, int v, int top, int bottom, int left, int right, int defWidth, int defHeight){
        return new BaseSprite(defWidth, defHeight) {
            @Override
            public void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state) {
                Minecraft.getInstance().textureManager.bind(texture);
                RenderSystem.enableAlphaTest();
                SLGuiUtils.drawContinuousTexturedBox(
                    ms,
                    posX, posY,
                    u, v,
                    width, height,
                    getWidth(), getHeight(),
                    top, bottom, left, right,
                    zLevel
                );
            }
        };
    }

    @Nonnull
    public static ISprite iconStateWidth(@Nonnull ResourceLocation texture, int u, int v, int width, int height){
        return new BaseSprite(width, height) {
            @Override
            public void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state) {
                Minecraft.getInstance().textureManager.bind(texture);
                RenderSystem.enableAlphaTest();
                SLGuiUtils.drawTexturedModalRect(
                    ms,
                    posX, posY,
                    u + (state * getWidth()), v,
                    getWidth(), getHeight(),
                    zLevel
                );
            }
        };
    }

    @Nonnull
    public static ISprite iconStateHeight(@Nonnull ResourceLocation texture, int u, int v, int width, int height){
        return new BaseSprite(width, height) {
            @Override
            public void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state) {
                Minecraft.getInstance().textureManager.bind(texture);
                RenderSystem.enableAlphaTest();
                SLGuiUtils.drawTexturedModalRect(
                    ms,
                    posX, posY,
                    u, v + (state * getHeight()),
                    getWidth(), getHeight(),
                    zLevel
                );
            }
        };
    }

    @Nonnull
    public static ISprite icon(@Nonnull ResourceLocation texture, int u, int v, int width, int height){
        return new BaseSprite(width, height) {
            @Override
            public void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state) {
                Minecraft.getInstance().textureManager.bind(texture);
                RenderSystem.enableAlphaTest();
                SLGuiUtils.drawTexturedModalRect(
                    ms,
                    posX, posY,
                    u, v,
                    getWidth(), getHeight(),
                    zLevel
                );
            }
        };
    }
}

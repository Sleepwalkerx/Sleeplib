package com.sleepwalker.sleeplib.client.widget.base.sprite;

import com.mojang.blaze3d.matrix.MatrixStack;

import javax.annotation.Nonnull;

public interface ISprite {

    default void render(@Nonnull MatrixStack ms, float posX, float posY){
        render(ms, posX, posY, 0);
    }

    default void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel){
        render(ms, posX, posY, zLevel, 0);
    }

    default void render(@Nonnull MatrixStack ms, float posX, float posY, int state){
        render(ms, posX, posY, getWidth(), getHeight(), state);
    }

    default void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int state){
        render(ms, posX, posY, zLevel, getWidth(), getHeight(), state);
    }

    default void render(@Nonnull MatrixStack ms, float posX, float posY, int width, int height){
        render(ms, posX, posY, width, height, 0);
    }

    default void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height){
        render(ms, posX, posY, zLevel, width, height, 0);
    }

    default void render(@Nonnull MatrixStack ms, float posX, float posY, int width, int height, int state){
        render(ms, posX, posY, 0, width, height, state);
    }

    void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state);

    int getWidth();

    int getHeight();

    interface IStateSupplier<T> {
        int getSpriteState(T obj);
    }
}

package com.sleepwalker.sleeplib.client.widget.base.sprite;

import com.mojang.blaze3d.matrix.MatrixStack;

import javax.annotation.Nonnull;

public class SubSprite extends BaseSprite {

    @Nonnull
    private final ISprite sprite;

    public SubSprite(@Nonnull ISprite sprite, int width, int height) {
       super(width, height);

       this.sprite = sprite;
    }

    @Override
    public void render(@Nonnull MatrixStack ms, float posX, float posY, float zLevel, int width, int height, int state) {
        sprite.render(ms, posX, posY, zLevel, width, height, state);
    }
}

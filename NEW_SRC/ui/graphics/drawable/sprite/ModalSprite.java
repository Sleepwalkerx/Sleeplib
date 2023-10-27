package com.sleepwalker.sleeplib.ui.graphics.drawable.sprite;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class ModalSprite extends BaseSprite {

    public ModalSprite(@Nonnull TextureSource source, int u, int v, int width, int height) {
        super(source, u, v, width, height);
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {
        Minecraft.getInstance().getTextureManager().bind(source.getLocation());
        blit(canvas.getMatrix(), canvas.getX(), canvas.getY(), canvas.getZ(), u, v, width, height, source.getWidth(), source.getHeight());
    }
}

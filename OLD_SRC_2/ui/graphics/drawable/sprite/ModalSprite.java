package com.sleepwalker.sleeplib.ui.graphics.drawable.sprite;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class ModalSprite extends BaseSprite {

    public ModalSprite(@Nonnull TextureSource source, int uOffset, int vOffset, int uWidth, int vHeight) {
        super(source, uOffset, vOffset, uWidth, vHeight);
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {
        Minecraft.getInstance().getTextureManager().bind(source.getLocation());
        blit(canvas.getMatrix(), canvas.getX(), canvas.getY(), canvas.getZ(), uOffset, vOffset, uWidth, vHeight);
        /*innerBlit(canvas.getMatrix().last().pose(),
            canvas.getX(), canvas.getX() + canvas.getWidth(),
            canvas.getY(), canvas.getY() + canvas.getHeight(),
            canvas.getZ(), u0, u1, v0, v1
        );*/
    }
}

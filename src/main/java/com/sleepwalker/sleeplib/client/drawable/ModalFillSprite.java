package com.sleepwalker.sleeplib.client.drawable;

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;

public class ModalFillSprite extends ModalSprite {

    public ModalFillSprite(@NotNull TextureSource texture, int uOffset, int vOffset, int uWidth, int vHeight) {
        super(texture, uOffset, vOffset, uWidth, vHeight);
    }

    @Override
    public void drawImage(@Nonnull UMatrixStack ms, double x, double y, double width, double height, @Nonnull Color color) {
        texture.bind();
        innerBlit(ms.toMC(), color, (float) x, (float) (x + width), (float) y, (float) (y + height), 0, uWidth, vHeight, uOffset, vOffset, texture.getWidth(), texture.getHeight());
    }
}

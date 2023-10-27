package com.sleepwalker.sleeplib.client.drawable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack;

import javax.annotation.Nonnull;
import java.awt.*;

public class NineSliceSprite extends ModalSprite {

    protected final int left;
    protected final int top;
    protected final int right;
    protected final int bottom;

    public NineSliceSprite(@Nonnull TextureSource texture, int uOffset, int vOffset, int uWidth, int vHeight, int left, int top, int right, int bottom) {
        super(texture, uOffset, vOffset, uWidth, vHeight);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void drawImage(@Nonnull UMatrixStack matrixStack, double x, double y, double width, double height, @Nonnull Color color) {
        MatrixStack ms = matrixStack.toMC();
        texture.bind();
        float xx = (float) x;
        float yy = (float) y;
        int fillerWidth = uWidth - left - right;
        int fillerHeight = vHeight - top - bottom;
        int canvasWidth = (int)(width - left - right);
        int canvasHeight = (int)(height - top - bottom);
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;

        blit(ms, color, xx, yy, 0, uOffset, vOffset, left, top);
        blit(ms, color, xx + left + canvasWidth, yy, 0, left + fillerWidth + uOffset, vOffset, right, top);
        blit(ms, color, xx, yy + top + canvasHeight, 0, uOffset, top + fillerHeight + vOffset, left, bottom);
        blit(ms, color, xx + left + canvasWidth, yy + top + canvasHeight, 0, left + fillerWidth + uOffset, top + fillerHeight + vOffset, right, bottom);

        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++) {
            blit(ms, color, xx + left + (i * fillerWidth), yy, 0, left + uOffset, vOffset, (i == xPasses ? remainderWidth : fillerWidth), top);
            blit(ms, color, xx + left + (i * fillerWidth), yy + top + canvasHeight, 0, left + uOffset, top + fillerHeight + vOffset, (i == xPasses ? remainderWidth : fillerWidth), bottom);

            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++){
                blit(ms, color, xx + left + (i * fillerWidth), yy + top + (j * fillerHeight), 0, left + uOffset, top + vOffset, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight));
            }
        }

        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
            blit(ms, color, xx, yy + top + (j * fillerHeight), 0, uOffset, top + vOffset, left, (j == yPasses ? remainderHeight : fillerHeight));
            blit(ms, color, xx + left + canvasWidth, yy + top + (j * fillerHeight), 0, left + fillerWidth + uOffset, top + vOffset, right, (j == yPasses ? remainderHeight : fillerHeight));
        }
    }
}

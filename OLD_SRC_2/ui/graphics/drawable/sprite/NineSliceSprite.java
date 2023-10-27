package com.sleepwalker.sleeplib.ui.graphics.drawable.sprite;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class NineSliceSprite extends BaseSprite {

    private final int right, left, top, bottom;

    public NineSliceSprite(@Nonnull TextureSource source, int uOffset, int vOffset, int baseWidth, int baseHeight, int right, int left, int top, int bottom) {
        super(source, uOffset, vOffset, baseWidth, baseHeight);
        this.right = right;
        this.left = left;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {

        Minecraft.getInstance().getTextureManager().bind(source.getLocation());

        MatrixStack ms = canvas.getMatrix();
        int fillerWidth = uWidth - left - right;
        int fillerHeight = vHeight - top - bottom;
        int canvasWidth = (int)(canvas.getWidth() - left - right);
        int canvasHeight = (int)(canvas.getHeight() - top - bottom);
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;

        // Draw Border
        // Top Left
        blit(ms, canvas.getX(), canvas.getY(), canvas.getZ(), uOffset, vOffset, left, top);
        //drawTexturedModalRect(matrixStack, x, y, u, v, leftBorder, topBorder, zLevel);
        // Top Right
        blit(ms, canvas.getX() + left + canvasWidth, canvas.getY(), canvas.getZ(), left + fillerWidth + uOffset, vOffset, right, top);
        //drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder, zLevel);
        // Bottom Left
        blit(ms, canvas.getX(), canvas.getY() + top + canvasHeight, canvas.getZ(), uOffset, top + fillerHeight + vOffset, left, bottom);
        //drawTexturedModalRect(matrixStack, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder, zLevel);
        // Bottom Right
        blit(ms, canvas.getX() + left + canvasWidth, canvas.getY() + top + canvasHeight, canvas.getZ(), left + fillerWidth + uOffset, top + fillerHeight + vOffset, right, bottom);
        //drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);

        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++) {
            // Top Border
            blit(ms, canvas.getX() + left + (i * fillerWidth), canvas.getY(), canvas.getZ(), left + uOffset, vOffset, (i == xPasses ? remainderWidth : fillerWidth), top);
            //drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, (i == xPasses ? remainderWidth : fillerWidth), topBorder, zLevel);
            // Bottom Border
            blit(ms, canvas.getX() + left + (i * fillerWidth), canvas.getY() + top + canvasHeight, canvas.getZ(), left + uOffset, top + fillerHeight + vOffset, (i == xPasses ? remainderWidth : fillerWidth), bottom);
            //drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder, zLevel);

            // Throw in some filler for good measure
            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++){
                blit(ms, canvas.getX() + left + (i * fillerWidth), canvas.getY() + top + (j * fillerHeight), canvas.getZ(), left + uOffset, top + vOffset, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight));
                //drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight), zLevel);
            }
        }

        // Side Borders
        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
            // Left Border
            blit(ms, canvas.getX(), canvas.getY() + top + (j * fillerHeight), canvas.getZ(), uOffset, top + vOffset, left, (j == yPasses ? remainderHeight : fillerHeight));
            //drawTexturedModalRect(matrixStack, x, y + topBorder + (j * fillerHeight), u, v + topBorder, leftBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel);
            // Right Border
            blit(ms, canvas.getX() + left + canvasWidth, canvas.getY() + top + (j * fillerHeight), canvas.getZ(), left + fillerWidth + uOffset, top + vOffset, right, (j == yPasses ? remainderHeight : fillerHeight));
            //drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel);
        }
    }
}

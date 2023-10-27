package com.sleepwalker.sleeplib.ui.graphics.drawable.sprite;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;

public class NineSliceSprite extends BaseSprite {

    private final int right, left, top, bottom;

    protected NineSliceSprite(@Nonnull TextureSource source, int u, int v, int width, int height, int right, int left, int top, int bottom) {
        super(source, u, v, width, height);
        this.right = right;
        this.left = left;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {

        Minecraft.getInstance().getTextureManager().bind(source.getLocation());

        MatrixStack ms = canvas.getMatrix();
        int fillerWidth = width - left - right;
        int fillerHeight = height - top - bottom;
        int canvasWidth = (int)(canvas.getWidth() - left - right);
        int canvasHeight = (int)(canvas.getHeight() - top - bottom);
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;

        // Draw Border
        // Top Left
        blit(ms, canvas.getX(), canvas.getY(), canvas.getZ(), 0, 0, left, top, width, height);
        //drawTexturedModalRect(matrixStack, x, y, u, v, leftBorder, topBorder, zLevel);
        // Top Right
        blit(ms, canvas.getX() + left + canvasWidth, canvas.getY(), canvas.getZ(), left + fillerWidth, 0, right, top, width, height);
        //drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder, zLevel);
        // Bottom Left
        blit(ms, canvas.getX(), canvas.getY() + top + canvasHeight, canvas.getZ(), 0, top + fillerHeight, left, bottom, width, height);
        //drawTexturedModalRect(matrixStack, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder, zLevel);
        // Bottom Right
        blit(ms, canvas.getX() + left + canvasWidth, canvas.getY() + top + canvasHeight, canvas.getZ(), left + fillerWidth, top + fillerHeight, right, bottom, width, height);
        //drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);

        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++) {
            // Top Border
            blit(ms, canvas.getX() + left + (i * fillerWidth), canvas.getY(), canvas.getZ(), left, 0, (i == xPasses ? remainderWidth : fillerWidth), top, width, height);
            //drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, (i == xPasses ? remainderWidth : fillerWidth), topBorder, zLevel);
            // Bottom Border
            blit(ms, canvas.getX() + left + (i * fillerWidth), canvas.getY() + top + canvasHeight, canvas.getZ(), left, top + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottom, width, height);
            //drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder, zLevel);

            // Throw in some filler for good measure
            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++){
                blit(ms, canvas.getX() + left + (i * fillerWidth), canvas.getY() + top + (j * fillerHeight), canvas.getZ(), left, top, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight), width, height);
                //drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight), zLevel);
            }
        }

        // Side Borders
        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
            // Left Border
            blit(ms, canvas.getX(), canvas.getY() + top + (j * fillerHeight), canvas.getZ(), 0, top, left, (j == yPasses ? remainderHeight : fillerHeight), width, height);
            //drawTexturedModalRect(matrixStack, x, y + topBorder + (j * fillerHeight), u, v + topBorder, leftBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel);
            // Right Border
            blit(ms, canvas.getX() + left + canvasWidth, canvas.getY() + top + (j * fillerHeight), canvas.getZ(), left + fillerWidth, top, right, (j == yPasses ? remainderHeight : fillerHeight), width, height);
            //drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel);
        }
    }
}

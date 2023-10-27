package com.sleepwalker.sleeplib.client.drawable;

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack;

import javax.annotation.Nonnull;
import java.awt.*;

public class ComplexSprite implements Drawable {

    private final int width;
    private final int height;
    private final Drawer drawer;

    public ComplexSprite(int width, int height, Drawer drawer) {
        this.width = width;
        this.height = height;
        this.drawer = drawer;
    }

    @Override
    public void drawImage(@Nonnull UMatrixStack matrixStack, double x, double y, double width, double height, @Nonnull Color color) {
        drawer.drawImage(matrixStack, x, y, width, height, color);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @FunctionalInterface
    public interface Drawer {
        void drawImage(@Nonnull UMatrixStack ms, double x, double y, double width, double height, @Nonnull Color color);
    }
}

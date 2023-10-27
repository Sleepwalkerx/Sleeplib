package com.sleepwalker.sleeplib.client.drawable;

import com.sleepwalker.sleeplib.gg.essential.elementa.components.image.ImageProvider;
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack;

import javax.annotation.Nonnull;
import java.awt.*;

public interface Drawable extends ImageProvider {

        Drawable EMPTY = new Drawable() {
            @Override
            public void drawImage(@Nonnull UMatrixStack matrixStack, double x, double y, double width, double height, @Nonnull Color color) {}
            @Override
            public int getWidth() {
                return 0;
            }
            @Override
            public int getHeight() {
                return 0;
            }
        };

    @Override
    void drawImage(@Nonnull UMatrixStack matrixStack, double x, double y, double width, double height, @Nonnull Color color);

    @SuppressWarnings("deprecation")
    @Override
    default void drawImage(double x, double y, double width, double height, @Nonnull Color color) {
        drawImage(UMatrixStack.Compat.INSTANCE.get(), x, y, width, height, color);
    }

    @Override
    default void drawImageCompat(@Nonnull UMatrixStack matrixStack, double x, double y, double width, double height, @Nonnull Color color) {
        drawImage(matrixStack, x, y, width, height, color);
    }

    int getWidth();
    int getHeight();
}

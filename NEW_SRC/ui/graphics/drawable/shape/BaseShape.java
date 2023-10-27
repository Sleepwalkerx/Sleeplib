package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;

import javax.annotation.Nonnull;

public abstract class BaseShape implements Shape {

    @Nonnull protected final ShapePadding padding;
    @Nonnull protected final ShapeSize size;
    @Nonnull protected final ShapeSolid solid;
    @Nonnull protected final ShapeStroke stroke;

    public BaseShape(@Nonnull ShapePadding padding, @Nonnull ShapeSize size, @Nonnull ShapeSolid solid, @Nonnull ShapeStroke stroke) {
        this.padding = padding;
        this.size = size;
        this.solid = solid;
        this.stroke = stroke;
    }

    protected float computeX(@Nonnull Canvas canvas, float width){
        return canvas.getX() + padding.getBiasX() * canvas.getWidth() - padding.getBiasX() * width + padding.getX();
    }

    protected float computeY(@Nonnull Canvas canvas, float height){
        return canvas.getY() + padding.getBiasY() * canvas.getHeight() - padding.getBiasY() * height + padding.getY();
    }

    @Nonnull
    public ShapePadding getPadding() {
        return padding;
    }

    @Nonnull
    public ShapeSize getSize() {
        return size;
    }

    @Nonnull
    public ShapeSolid getSolid() {
        return solid;
    }

    @Nonnull
    public ShapeStroke getStroke() {
        return stroke;
    }
}

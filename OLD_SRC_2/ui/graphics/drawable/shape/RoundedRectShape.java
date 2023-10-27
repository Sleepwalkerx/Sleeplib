package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.ui.GlfwUtil;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class RoundedRectShape extends RectShape {

    @Nonnull protected final CornerRadius leftTopRadius;
    @Nonnull protected final CornerRadius rightTopRadius;
    @Nonnull protected final CornerRadius rightBottomRadius;
    @Nonnull protected final CornerRadius leftBottomRadius;

    public RoundedRectShape(@Nonnull ShapePadding padding, @Nonnull ShapeSize size, @Nonnull ShapeSolid solid, @Nonnull ShapeStroke stroke,
                            @Nonnull CornerRadius leftTopRadius, @Nonnull CornerRadius rightTopRadius,
                            @Nonnull CornerRadius rightBottomRadius, @Nonnull CornerRadius leftBottomRadius) {
        super(padding, size, solid, stroke);
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.rightBottomRadius = rightBottomRadius;
        this.leftBottomRadius = leftBottomRadius;
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {

        float width = size.getWidth(canvas);
        float height = size.getHeight(canvas);
        float pX = computeX(canvas, width);
        float pY = computeY(canvas, height);

        float maxRadius = Math.min(width, height) / 2f;
        float leftTopRadius = MathHelper.clamp(this.leftTopRadius.compute(maxRadius), 0f, maxRadius);
        float rightTopRadius = MathHelper.clamp(this.rightTopRadius.compute(maxRadius), 0f, maxRadius);
        float rightBottomRadius = MathHelper.clamp(this.rightBottomRadius.compute(maxRadius), 0f, maxRadius);
        float leftBottomRadius = MathHelper.clamp(this.leftBottomRadius.compute(maxRadius), 0f, maxRadius);

        if(solid.isDrawable()){

            float sWidth = width - stroke.getWidth() * 2;
            float sHeight = height - stroke.getWidth() * 2;
            float sMaxRadius = Math.min(sWidth, sHeight) / 2f;

            GlfwUtil.drawRoundedRect(canvas.getMatrix(),
                pX + stroke.getWidth(), pY + stroke.getWidth(), canvas.getZ(),
                sWidth, sHeight,
                MathHelper.clamp(leftTopRadius - stroke.getWidth(), 0f, sMaxRadius),
                MathHelper.clamp(rightTopRadius - stroke.getWidth(), 0f, sMaxRadius),
                MathHelper.clamp(rightBottomRadius - stroke.getWidth(), 0f, sMaxRadius),
                MathHelper.clamp(leftBottomRadius - stroke.getWidth(), 0f, sMaxRadius),
                solid.getColor()
            );
        }

        if(stroke.isDrawable()){

            GlfwUtil.drawRoundedHollowRect(canvas.getMatrix(), pX, pY, canvas.getZ(), width, height,
                stroke.getWidth(), leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, stroke.getColor()
            );
        }
    }
}

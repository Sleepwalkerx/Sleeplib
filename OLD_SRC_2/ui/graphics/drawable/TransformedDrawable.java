package com.sleepwalker.sleeplib.ui.graphics.drawable;

import com.sleepwalker.sleeplib.ui.GlfwUtil;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.layout.Alignment;

import javax.annotation.Nonnull;

public class TransformedDrawable implements Drawable {

    @Nonnull private final Drawable target;

    @Nonnull private final Fit fit;
    @Nonnull private final Alignment alignment;
    private final float offsetX;
    private final float offsetY;

    public TransformedDrawable(@Nonnull Drawable target, @Nonnull Fit fit, @Nonnull Alignment alignment, float offsetX, float offsetY) {
        this.target = target;
        this.fit = fit;
        this.alignment = alignment;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {

        GlfwUtil.enableScissorTest(canvas);
        canvas.push();

        float pX = canvas.getX() + offsetX;
        float pY = canvas.getY() + offsetY;

        float biasX = alignment.getBiasX();
        pX = (pX + canvas.getWidth() * biasX) - target.getDrawableWidth() * biasX;

        float biasY = alignment.getBiasY();
        pY = (pY + canvas.getHeight() * biasY) - target.getDrawableHeight() * biasY;

        canvas.setPosition(pX, pY);

        switch (fit){

            case FILL:
                break;

            case CONTAIN:

                float factor = target.getDrawableWidth() > target.getDrawableHeight() ?
                    safeDivision(canvas.getWidth(), target.getDrawableWidth()) : safeDivision(canvas.getHeight(), target.getDrawableHeight());
                canvas.setScale(target.getDrawableWidth() * factor, target.getDrawableHeight() * factor);
                break;

            case COVER:
                float scale = target.getDrawableWidth() > target.getDrawableHeight() ? safeDivision(canvas.getHeight(), target.getDrawableHeight()) :
                    safeDivision(canvas.getWidth(), target.getDrawableWidth());
                canvas.setScale(target.getDrawableWidth() * scale, target.getDrawableHeight() * scale);
                break;

            case NONE:
                canvas.setScale(target.getDrawableWidth(), target.getDrawableHeight());
                break;
        }

        target.draw(canvas);

        canvas.pop();
        GlfwUtil.disableScissorTest();
    }

    private float safeDivision(float a, float b){
        return b == 0 ? 0 : a / b;
    }

    @Nonnull
    public Drawable getTarget() {
        return target;
    }
}

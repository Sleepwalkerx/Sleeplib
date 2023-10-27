package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.GlfwUtil;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;
import com.sleepwalker.sleeplib.ui.property.Properties;

import javax.annotation.Nonnull;

public class Image extends BaseWidget {

    @Override
    public void draw(@Nonnull Canvas canvas) {

        GlfwUtil.enableScissorTest(getX(), getY(), getWidth(), getHeight());

        Drawable image = getPropertyValue(Properties.SRC);

        float pX = getX() + getPropertyValue(Properties.IMAGE_OFFSET_X);
        float pY = getY() + getPropertyValue(Properties.IMAGE_OFFSET_Y);

        if(image.getWidth() == Drawable.INDEFINABLE || image.getHeight() == Drawable.INDEFINABLE){
            canvas.translate(pX, pY, getLayer());
            canvas.scale(getWidth(), getHeight());
        }
        else {

            float biasX = getPropertyValue(Properties.IMAGE_BIAS_X);
            pX = (pX + getWidth() * biasX) - image.getWidth() * biasX;

            float biasY = getPropertyValue(Properties.IMAGE_BIAS_Y);
            pY = (pY + getHeight() * biasY) - image.getHeight() * biasY;

            canvas.translate(pX, pY, getLayer());

            switch (getPropertyValue(Properties.IMAGE_FIT)){
                case FILL:
                    canvas.scale(getWidth(), getHeight());
                    break;

                case CONTAIN:
                    float factor = image.getWidth() > image.getHeight() ? getWidth() / image.getWidth() : getHeight() / image.getHeight();
                    canvas.scale(image.getWidth() * factor, image.getHeight() * factor);
                    break;

                case COVER:
                    float scale = image.getWidth() > image.getHeight() ? getHeight() / image.getHeight() : getWidth() / image.getWidth();
                    canvas.scale(image.getWidth() * scale, image.getHeight() * scale);
                    break;

                case NONE:
                    canvas.scale(image.getWidth(), image.getHeight());
                    break;
            }
        }

        image.draw(canvas);

        GlfwUtil.disableScissorTest();
    }
}

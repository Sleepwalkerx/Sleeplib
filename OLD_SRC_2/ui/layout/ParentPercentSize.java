package com.sleepwalker.sleeplib.ui.layout;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public class ParentPercentSize implements WidgetSize {

    private final float percent;

    public ParentPercentSize(float percent) {
        this.percent = percent;
    }

    @Override
    public float calculate(@Nonnull Widget target, @Nonnull Dimension dimension) {
        if(dimension == Dimension.WIDTH){
            return target.getParent().getWidth() * percent;
        }
        else {
            return target.getParent().getHeight() * percent;
        }
    }
}

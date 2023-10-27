package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.math.Dimension;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.widget.WidgetSize;

import javax.annotation.Nonnull;

public class ParentPercentSize implements WidgetSize {

    private final float percent;

    public ParentPercentSize(float percent) {
        this.percent = percent;
    }

    @Override
    public float compute(@Nonnull Widget target, @Nonnull Dimension dimension) {
        if(dimension == Dimension.WIDTH){
            return target.getParent().getWidth() * percent;
        }
        else {
            return target.getParent().getHeight() * percent;
        }
    }
}

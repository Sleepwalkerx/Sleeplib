package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.math.Dimension;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.widget.WidgetSize;

import javax.annotation.Nonnull;

public class AbsoluteSize implements WidgetSize {

    private final float value;

    public AbsoluteSize(float value) {
        this.value = value;
    }

    @Override
    public float compute(@Nonnull Widget target, @Nonnull Dimension dimension) {
        return value;
    }
}

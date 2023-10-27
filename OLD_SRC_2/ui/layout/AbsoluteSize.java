package com.sleepwalker.sleeplib.ui.layout;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public class AbsoluteSize implements WidgetSize {

    private final float value;

    public AbsoluteSize(float value) {
        this.value = value;
    }

    @Override
    public float calculate(@Nonnull Widget target, @Nonnull Dimension dimension) {
        return value;
    }
}

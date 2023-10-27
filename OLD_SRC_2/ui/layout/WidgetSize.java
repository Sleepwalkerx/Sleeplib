package com.sleepwalker.sleeplib.ui.layout;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public interface WidgetSize {

    @Nonnull WidgetSize ZERO = new AbsoluteSize(0);
    @Nonnull WidgetSize FIT_PARENT = new ParentPercentSize(1f);

    float calculate(@Nonnull Widget target, @Nonnull Dimension dimension);
}

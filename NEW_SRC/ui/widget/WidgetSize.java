package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.math.Dimension;

import javax.annotation.Nonnull;

public interface WidgetSize {

    @Nonnull
    WidgetSize DEFAULT = new AbsoluteSize(16);

    float compute(@Nonnull Widget target, @Nonnull Dimension dimension);
}

package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface WidgetReference {

    @Nullable Widget getFrom(@Nonnull Widget target);
}

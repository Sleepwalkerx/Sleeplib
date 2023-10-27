package com.sleepwalker.sleeplib.ui.property;

import javax.annotation.Nonnull;

public final class WidgetStates {

    @Nonnull public static final WidgetState ENABLED = WidgetState.create("enabled");
    @Nonnull public static final WidgetState FOCUSED = WidgetState.create("focused");
    @Nonnull public static final WidgetState PRESSED = WidgetState.create("pressed");
    @Nonnull public static final WidgetState CHECKED = WidgetState.create("checked");
}

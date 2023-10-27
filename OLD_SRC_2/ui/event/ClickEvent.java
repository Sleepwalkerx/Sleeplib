package com.sleepwalker.sleeplib.ui.event;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public class ClickEvent implements Event {

    @Nonnull
    private final Pointer pointer;
    @Nonnull
    private final Widget source;

    public ClickEvent(@Nonnull Pointer pointer, @Nonnull Widget source) {
        this.pointer = pointer;
        this.source = source;
    }

    @Nonnull
    public Pointer getPointer() {
        return pointer;
    }

    @Nonnull
    public Widget getSource() {
        return source;
    }
}

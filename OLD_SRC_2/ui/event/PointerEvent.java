package com.sleepwalker.sleeplib.ui.event;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PointerEvent implements Event {

    @Nonnull
    private final Pointer pointer;
    @Nullable
    private final Widget target;

    public PointerEvent(@Nonnull Pointer pointer, @Nullable Widget target) {
        this.pointer = pointer;
        this.target = target;
    }

    @Nonnull
    public Pointer getPointer(){
        return pointer;
    }

    @Nullable
    public Widget getTarget(){
        return target;
    }
}

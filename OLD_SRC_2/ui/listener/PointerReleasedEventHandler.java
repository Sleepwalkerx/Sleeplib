package com.sleepwalker.sleeplib.ui.listener;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public interface PointerReleasedEventHandler {

    void onPointerReleased(@Nonnull PointerEvent event);

    static void fireIfInstance(@Nonnull Pointer pointer, @Nonnull Widget target){
        if(target instanceof PointerReleasedEventHandler){
            ((PointerReleasedEventHandler) target).onPointerReleased(new PointerEvent(pointer, target));
        }
    }
}

package com.sleepwalker.sleeplib.ui.listener;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public interface PointerEnterEventHandler {

    void onPointerEnter(@Nonnull PointerEvent event);

    static void fireIfInstance(@Nonnull Pointer pointer, @Nonnull Widget target){
        if(target instanceof PointerEnterEventHandler){
            ((PointerEnterEventHandler) target).onPointerEnter(new PointerEvent(pointer, target));
        }
    }
}

package com.sleepwalker.sleeplib.ui.listener;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public interface PointerScrollEventHandler {

    boolean onPointerScroll(@Nonnull PointerEvent event);

    static boolean fireIfInstance(@Nonnull Pointer pointer, @Nonnull Widget target){
        if(target instanceof PointerScrollEventHandler){
            return ((PointerScrollEventHandler) target).onPointerScroll(new PointerEvent(pointer, target));
        }
        else {
            return false;
        }
    }
}

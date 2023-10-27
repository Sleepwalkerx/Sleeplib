package com.sleepwalker.sleeplib.ui.listener;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public interface PointerDownEventHandler {

    boolean onPointerDown(@Nonnull PointerEvent event);

    static boolean fireIfInstance(@Nonnull Pointer pointer, @Nonnull Widget target){
        if(target instanceof PointerDownEventHandler){
            return  ((PointerDownEventHandler) target).onPointerDown(new PointerEvent(pointer, target));
        }
        else {
            return false;
        }
    }
}

package com.sleepwalker.sleeplib.ui.listener;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public interface PointerMoveEventHandler {

    void onPointerMove(@Nonnull PointerEvent event);

    static void fireIfInstance(@Nonnull Pointer pointer, @Nonnull Widget target){
        if(target instanceof PointerMoveEventHandler){
            ((PointerMoveEventHandler) target).onPointerMove(new PointerEvent(pointer, target));
        }
    }
}

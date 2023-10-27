package com.sleepwalker.sleeplib.ui.listener;

import com.sleepwalker.sleeplib.ui.event.PointerEvent;

import javax.annotation.Nonnull;

public interface ClickEventListener extends EventListener {

    void onClick(@Nonnull PointerEvent event);
}

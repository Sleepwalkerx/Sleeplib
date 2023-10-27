package com.sleepwalker.sleeplib.ui.listener;

import com.sleepwalker.sleeplib.ui.event.Event;

import javax.annotation.Nonnull;

public interface EventListener<E extends Event> {

    void onEvent(@Nonnull E event);
}

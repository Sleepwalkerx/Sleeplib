package com.sleepwalker.sleeplib.ui;

import com.sleepwalker.sleeplib.ui.animation.AnimationManager;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.layer.LayerManager;
import com.sleepwalker.sleeplib.ui.listener.*;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import net.minecraft.client.gui.screen.Screen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Root {

    @Nullable Widget findWidgetById(@Nonnull String id);
    @Nullable Widget findWidgetByName(@Nonnull String name);
    @Nullable Widget findWidgetByHierarchy(@Nonnull String hierarchy);

    @Nonnull ListenerStack<PointerEvent> getPointerDownEventListeners();
    @Nonnull ListenerStack<PointerEvent> getPointerUpEventListeners();
    @Nonnull ListenerStack<PointerEvent> getPointerMoveEventListeners();
    @Nonnull ListenerStack<PointerEvent> getPointerEnterEventListeners();
    @Nonnull ListenerStack<PointerEvent> getPointerOutEventListeners();
    @Nonnull ListenerStack<PointerEvent> getPointerScrollEventListeners();

    @Nonnull LayerManager getLayerManager();

    @Nonnull Screen getScreen();

    @Nonnull Pointer getPointer();
}

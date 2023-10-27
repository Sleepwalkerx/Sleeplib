package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.event.ClickEvent;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.listener.*;
import com.sleepwalker.sleeplib.ui.property.WidgetStates;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;

public class Pinchable extends BaseWidget implements PointerDownEventHandler, PointerUpEventHandler, PointerEnterEventHandler, PointerOutEventHandler, WidgetReleasedEventHandler {

    @Nonnull public final ListenerStack<ClickEvent> clickListeners = ListenerStack.newStack();
    protected boolean pinched;

    @Override
    public void draw(@Nonnull Canvas canvas) { }

    @Override
    public boolean onPointerUp(@Nonnull PointerEvent event) {

        if(pinched){
            ClickEvent clickEvent = new ClickEvent(event.getPointer(), this);
            clickListeners.post(clickEvent);
            getDynamicPropertyMap().fireStateChanged(WidgetStates.PRESSED, false, this);
            pinched = false;

            return true;
        }

        return false;
    }

    @Override
    public void onWidgetReleased(@Nonnull PointerEvent event) {

        if(pinched){
            getDynamicPropertyMap().fireStateChanged(WidgetStates.PRESSED, false, this);
            pinched = false;
        }
    }

    @Override
    public boolean onPointerDown(@Nonnull PointerEvent event) {

        if(event.getPointer().getButton() == GLFW.GLFW_MOUSE_BUTTON_1){
            pinched = true;
            getDynamicPropertyMap().fireStateChanged(WidgetStates.PRESSED, false, this);
            return true;
        }

        return false;
    }

    @Override
    public void onPointerEnter(@Nonnull PointerEvent event) {
        getDynamicPropertyMap().fireStateChanged(WidgetStates.HOVERED, true, this);
    }

    @Override
    public void onPointerOut(@Nonnull PointerEvent event) {
        getDynamicPropertyMap().fireStateChanged(WidgetStates.HOVERED, false, this);
    }
}

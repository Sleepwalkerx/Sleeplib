package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.event.ClickEvent;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.listener.*;
import com.sleepwalker.sleeplib.ui.property.WidgetStates;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;

public abstract class BaseClickable extends BaseWidget implements PointerDownEventHandler, PointerUpEventHandler, PointerEnterEventHandler, PointerOutEventHandler, PointerReleasedEventHandler {

    @Nonnull public final ListenerStack<ClickEvent> clickListeners = ListenerStack.newStack();
    protected boolean pinched;

    @Override
    public boolean onPointerUp(@Nonnull PointerEvent event) {

        if(pinched){
            getPropertyManager().fireStateDisabled(WidgetStates.PRESSED, this);
            pinched = false;
            return true;
        }

        return false;
    }

    @Override
    public void onPointerReleased(@Nonnull PointerEvent event) {

        if(pinched){

            ClickEvent clickEvent = new ClickEvent(event.getPointer(), this);
            clickListeners.post(clickEvent);

            getPropertyManager().fireStateDisabled(WidgetStates.PRESSED, this);
            pinched = false;
        }
    }

    @Override
    public boolean onPointerDown(@Nonnull PointerEvent event) {

        if(isValidPointerDown(event)){
            pinched = true;
            getPropertyManager().fireStateActivated(WidgetStates.PRESSED, this);
            return true;
        }

        return false;
    }

    protected boolean isValidPointerDown(@Nonnull PointerEvent event){
        return event.getPointer().getButton() == GLFW.GLFW_MOUSE_BUTTON_1;
    }

    @Override
    public void onPointerEnter(@Nonnull PointerEvent event) {
        getPropertyManager().fireStateActivated(WidgetStates.FOCUSED, this);
    }

    @Override
    public void onPointerOut(@Nonnull PointerEvent event) {
        getPropertyManager().fireStateDisabled(WidgetStates.FOCUSED, this);
    }
}

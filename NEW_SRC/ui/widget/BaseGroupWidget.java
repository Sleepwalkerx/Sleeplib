package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.GlfwUtil;
import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.listener.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BaseGroupWidget extends BaseWidget implements WidgetGroup, PointerEnterEventHandler, PointerOutEventHandler,
    PointerScrollEventHandler, PointerMoveEventHandler, PointerDownEventHandler, PointerUpEventHandler {

    @Nullable
    protected Widget lastEntered;
    @Nullable
    protected Widget lastPitched;

    @Override
    public void draw(@Nonnull Canvas canvas) {

        GlfwUtil.enableScissorTest(getX(), getY(), getWidth(), getHeight());

        for (Widget widget : getByChildrenLayer()){
            widget.draw(canvas);
        }

        GlfwUtil.disableScissorTest();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        updateLayout();
    }

    @Override
    public boolean onPointerDown(@Nonnull PointerEvent event) {

        Widget child = findFocusedChild(event.getPointer());

        updatePitchedState(event.getPointer());
        lastPitched = child;

        return child != null && PointerDownEventHandler.fireIfInstance(event.getPointer(), child);
    }

    @Override
    public boolean onPointerUp(@Nonnull PointerEvent event) {

        updatePitchedState(event.getPointer());

        Widget child = findFocusedChild(event.getPointer());
        return child != null && PointerUpEventHandler.fireIfInstance(event.getPointer(), child);
    }

    protected void updatePitchedState(@Nonnull Pointer pointer){
        if(lastPitched != null){
            WidgetReleasedEventHandler.fireIfInstance(pointer, lastPitched);
            lastPitched = null;
        }
    }

    @Override
    public void onPointerEnter(@Nonnull PointerEvent event) {

        Widget child = findFocusedChild(event.getPointer());
        updateEnteredState(event.getPointer(), child);

        if(child != null){
            PointerEnterEventHandler.fireIfInstance(event.getPointer(), child);
        }
    }

    @Override
    public void onPointerOut(@Nonnull PointerEvent event) {

        Widget child = findFocusedChild(event.getPointer());
        updateEnteredState(event.getPointer(), child);

        if(child != null){
            PointerOutEventHandler.fireIfInstance(event.getPointer(), child);
        }
    }

    @Override
    public boolean onPointerScroll(@Nonnull PointerEvent event) {
        Widget child = findFocusedChild(event.getPointer());
        return child != null && PointerUpEventHandler.fireIfInstance(event.getPointer(), child);
    }

    @Override
    public void onPointerMove(@Nonnull PointerEvent event) {

        Widget child = findFocusedChild(event.getPointer());
        updateEnteredState(event.getPointer(), child);

        if(child != null){
            PointerMoveEventHandler.fireIfInstance(event.getPointer(), child);
        }
    }

    protected void updateEnteredState(@Nonnull Pointer pointer, @Nullable Widget child){

        if(child != lastEntered){

            if(lastEntered != null){
                PointerOutEventHandler.fireIfInstance(pointer, lastEntered);
            }

            if(child != null){
                PointerEnterEventHandler.fireIfInstance(pointer, child);
            }

            lastEntered = child;
        }
    }

    @Nullable
    protected Widget findFocusedChild(@Nonnull Pointer pointer){

        for(Widget child : getByChildrenLayer()){
            if(child.isPointerOverUI(pointer)){
                return child;
            }
        }

        return null;
    }
}

package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

public class EmptyWidget extends BaseWidget implements WidgetGroup {

    @Override
    public void draw(@Nonnull Canvas canvas) {
        //Empty :/
    }

    @Override
    public void updateLayout() { }

    @Override
    public void updateLayer() { }

    @Override
    public boolean addChild(@Nonnull Widget widget) {
        return false;
    }

    @Override
    public boolean addChild(@Nonnull Widget widget, int index) {
        return false;
    }

    @Override
    public boolean removeChild(@Nonnull Widget widget) {
        return false;
    }

    @Override
    public boolean removeChild(int index) {
        return false;
    }

    @Override
    public int getChildrenCount() {
        return 0;
    }

    @Nonnull
    @Override
    public Collection<Widget> getChildren() {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public Collection<Widget> getByChildrenLayer() {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public Widget getChildAt(int index) {
        return null;
    }

    @Nullable
    @Override
    public Widget getChildByName(@Nonnull String name) {
        return null;
    }

    @Nullable
    @Override
    public Widget getChildById(@Nonnull String id) {
        return null;
    }
}

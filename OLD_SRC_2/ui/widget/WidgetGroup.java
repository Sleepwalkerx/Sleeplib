package com.sleepwalker.sleeplib.ui.widget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;

public interface WidgetGroup extends Widget {

    @Nonnull Comparator<Widget> BY_LAYER = Comparator.comparing(Widget::getLayer);

    void updateLayout();
    void updateLayer();

    boolean addChild(@Nonnull Widget widget);
    boolean addChild(@Nonnull Widget widget, int index);
    boolean removeChild(@Nonnull Widget widget);
    boolean removeChild(int index);

    int getChildrenCount();
    @Nonnull Collection<Widget> getChildren();
    @Nonnull Collection<Widget> getByChildrenLayer();

    @Nullable Widget getChildAt(int index);
    @Nullable Widget getChildByName(@Nonnull String name);
    @Nullable Widget getChildById(@Nonnull String id);
}

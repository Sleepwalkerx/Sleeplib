package com.sleepwalker.sleeplib.ui.widget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseLayoutGroupWidget extends BaseGroupWidget {

    @Nonnull protected final List<Widget> children = new ArrayList<>();
    @Nonnull protected final List<Widget> byLayer = new ArrayList<>();

    @Override
    public boolean addChild(@Nonnull Widget widget) {
        return addChild(widget, -1);
    }

    @Override
    public boolean addChild(@Nonnull Widget widget, int index) {

        if(children.contains(widget)){
            return false;
        }

        if(index == -1){
            children.add(widget);
        }
        else {
            children.add(index, widget);
        }

        widget.setParent(this);

        byLayer.add(widget);
        updateLayer();

        return true;
    }

    @Override
    public boolean removeChild(@Nonnull Widget widget) {

        if(!children.remove(widget)){
            return false;
        }

        byLayer.remove(widget);
        updateLayer();

        return true;
    }

    @Override
    public boolean removeChild(int index) {

        Widget widget = children.remove(index);
        byLayer.remove(widget);
        updateLayer();

        return true;
    }

    @Override
    public void updateLayer() {
        byLayer.sort(WidgetGroup.BY_LAYER);
    }

    @Override
    public int getChildrenCount() {
        return children.size();
    }

    @Nonnull
    @Override
    public Collection<Widget> getByChildrenLayer() {
        return byLayer;
    }

    @Override
    @Nonnull
    public List<Widget> getChildren() {
        return children;
    }

    @Nullable
    @Override
    public Widget getChildAt(int index) {

        List<Widget> children = getChildren();

        if(index < 0 || index >= children.size()){
            return null;
        }

        return children.get(index);
    }

    @Nullable
    @Override
    public Widget getChildByName(@Nonnull String name) {

        for(Widget child : getChildren()){
            if(child.getName().equals(name)){
                return child;
            }
        }

        return null;
    }

    @Nullable
    @Override
    public Widget getChildById(@Nonnull String id) {

        for(Widget child : getChildren()){
            if(child.getId().equals(id)){
                return child;
            }
        }

        return null;
    }
}

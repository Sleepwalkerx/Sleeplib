package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.property.Properties;

public class AbsoluteLayout extends BaseLayoutGroupWidget {

    @Override
    public void updateLayout() {

        for (Widget child : getChildren()){
            child.updateSize();
            child.setPosition(getX() + child.getPropertyValue(Properties.X), getY() + child.getPropertyValue(Properties.Y));
        }
    }
}

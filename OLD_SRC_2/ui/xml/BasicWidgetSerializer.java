package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public class BasicWidgetSerializer extends BaseWidgetSerializer<Widget> {

    @Nonnull
    @Override
    public Class<Widget> getWidgetType() {
        return Widget.class;
    }
}

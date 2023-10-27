package com.sleepwalker.sleeplib.client.widget.base.button;

import com.sleepwalker.sleeplib.client.widget.core.ISelectable;

public class SelectableButton extends Button implements ISelectable {

    protected boolean selected;

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }
}

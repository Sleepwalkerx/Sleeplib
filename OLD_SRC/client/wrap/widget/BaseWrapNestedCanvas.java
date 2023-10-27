package com.sleepwalker.sleeplib.client.wrap.widget;

import com.sleepwalker.sleeplib.client.widget.core.BaseNestedWidget;
import com.sleepwalker.sleeplib.client.wrap.IWrapCanvas;
import com.sleepwalker.sleeplib.client.wrap.IWrapHandler;

import javax.annotation.Nonnull;

public abstract class BaseWrapNestedCanvas extends BaseNestedWidget implements IWrapCanvas {

    protected IWrapHandler wrapHandler;

    @Override
    public void setWrapHandler(@Nonnull IWrapHandler wrapHandler) {
        this.wrapHandler = wrapHandler;
    }

    @Nonnull
    @Override
    public IWrapHandler getWrapHandler() {
        return wrapHandler;
    }
}

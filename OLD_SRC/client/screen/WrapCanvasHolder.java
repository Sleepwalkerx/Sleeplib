package com.sleepwalker.sleeplib.client.screen;

import net.minecraft.client.gui.IGuiEventListener;
import com.sleepwalker.sleeplib.client.wrap.IWrapCanvas;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class WrapCanvasHolder {

    @Nonnull
    private final IWrapCanvas wrapCanvas;

    @Nonnull
    private final List<IGuiEventListener> listeners = new ArrayList<>();

    public WrapCanvasHolder(@Nonnull IWrapCanvas wrapCanvas) {
        this.wrapCanvas = wrapCanvas;
    }

    @Nonnull
    public IWrapCanvas getWrapCanvas() {
        return wrapCanvas;
    }

    @Nonnull
    public List<IGuiEventListener> getListeners() {
        return listeners;
    }
}

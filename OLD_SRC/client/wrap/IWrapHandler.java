package com.sleepwalker.sleeplib.client.wrap;

import javax.annotation.Nonnull;

public interface IWrapHandler {

    default void activateWrapCanvas(@Nonnull IWrapCanvas wrapCanvas){
        activateWrapCanvas(wrapCanvas, this);
    }

    void activateWrapCanvas(@Nonnull IWrapCanvas wrapCanvas, @Nonnull IWrapHandler wrapHandler);
    
    void deactivateWrapCanvas();
}

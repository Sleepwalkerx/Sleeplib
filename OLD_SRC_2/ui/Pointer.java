package com.sleepwalker.sleeplib.ui;

public interface Pointer {

    double getX();
    double getY();
    double getDeltaX();
    double getDeltaY();

    double getScrollDelta();

    int getButton();
}

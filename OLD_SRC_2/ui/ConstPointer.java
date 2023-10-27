package com.sleepwalker.sleeplib.ui;

public class ConstPointer implements Pointer {

    private final double x;
    private final double y;
    private final double deltaX;
    private final double deltaY;
    private final double scrollDelta;
    private final int button;

    public ConstPointer(double x, double y, double deltaX, double deltaY, double scrollDelta, int button) {
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.scrollDelta = scrollDelta;
        this.button = button;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getDeltaX() {
        return deltaX;
    }

    @Override
    public double getDeltaY() {
        return deltaY;
    }

    @Override
    public double getScrollDelta() {
        return scrollDelta;
    }

    @Override
    public int getButton() {
        return button;
    }
}

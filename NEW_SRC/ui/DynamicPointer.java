package com.sleepwalker.sleeplib.ui;

public class DynamicPointer implements Pointer {

    private double x;
    private double y;
    private double deltaX;
    private double deltaY;
    private double scrollDelta;
    private int button;

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setDelta(double deltaX, double deltaY){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public void setScrollDelta(double scrollDelta) {
        this.scrollDelta = scrollDelta;
    }

    public void setButton(int button) {
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

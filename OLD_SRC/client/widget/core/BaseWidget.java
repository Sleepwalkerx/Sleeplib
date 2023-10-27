package com.sleepwalker.sleeplib.client.widget.core;

public abstract class BaseWidget implements IWidget {

    protected boolean mouseFocused;
    protected boolean active = true;

    protected int height;
    protected int width;
    protected int posX;
    protected int posY;
    protected int posEndX, posEndY;


    @Override
    public boolean isMouseFocused() {
        return mouseFocused;
    }

    @Override
    public void setMouseFocused(boolean mouseFocused) {
        this.mouseFocused = mouseFocused;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public int getPosEndX() {
        return posEndX;
    }

    @Override
    public int getPosEndY() {
        return posEndY;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
        posEndY = posY + height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
        posEndX = posX + width;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
        posEndX = posX + width;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
        posEndY = posY + height;
    }
}

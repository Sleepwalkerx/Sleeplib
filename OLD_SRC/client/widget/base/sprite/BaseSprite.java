package com.sleepwalker.sleeplib.client.widget.base.sprite;

public abstract class BaseSprite implements ISprite {

    protected final int defWidth, defHeight;

    protected BaseSprite(int defWidth, int defHeight) {
        this.defWidth = defWidth;
        this.defHeight = defHeight;
    }

    protected BaseSprite() {
        this.defWidth = 16;
        this.defHeight = 16;
    }

    @Override
    public int getWidth() {
        return defWidth;
    }

    @Override
    public int getHeight() {
        return defHeight;
    }
}

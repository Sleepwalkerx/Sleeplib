package com.sleepwalker.sleeplib.client.drawable;

public interface Shape extends Drawable {

    @Override
    default int getWidth(){
        return 0;
    }

    @Override
    default int getHeight(){
        return 0;
    }
}

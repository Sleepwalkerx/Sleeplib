package com.sleepwalker.sleeplib.client.widget.core.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MouseAdapter implements IMouseListener {

    @Override
    public void onMouseMoved(double mX, double mY) { }

    @Override
    public void onMouseClicked(double mX, double mY, int bId) { }

    @Override
    public void onMouseReleased(double mX, double mY, int bId) { }

    @Override
    public void onMouseDragged(double mX, double mY, int bId, double dX, double dY) { }

    @Override
    public void onMouseScrolled(double mX, double mY, double delta) { }
}

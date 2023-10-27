package com.sleepwalker.sleeplib.client.widget.core.event;

public interface IMouseListener extends IEventListener {

    void onMouseMoved(double mX, double mY);

    void onMouseClicked(double mX, double mY, int bId);

    void onMouseReleased(double mX, double mY, int bId);

    void onMouseDragged(double mX, double mY, int bId, double dX, double dY);

    void onMouseScrolled(double mX, double mY, double delta);
}

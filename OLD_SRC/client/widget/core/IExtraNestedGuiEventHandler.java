package com.sleepwalker.sleeplib.client.widget.core;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IExtraNestedGuiEventHandler extends INestedGuiEventHandler, ITickable {

    @Override
    default boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {

        IGuiEventListener last = getFocused();

        if(getLastMouseFocused() != null && getLastMouseFocused().mouseClicked(pMouseX, pMouseY, pButton)){

            if(last instanceof ILostFocusListener && last != getLastMouseFocused()){
                ((ILostFocusListener) last).onLostFocus();
            }
            this.setFocused(getLastMouseFocused());

            //if (pButton == 0) {
                this.setDragging(true);
            //}

            return true;
        }

        if(last instanceof ILostFocusListener){
            ((ILostFocusListener) last).onLostFocus();
        }

        return false;
    }

    @Override
    default boolean mouseReleased(double x, double y, int button) {

        setDragging(false);

        IGuiEventListener focused = getLastMouseFocused() != null && getLastMouseFocused().mouseReleased(x, y, button) ? getLastMouseFocused() : null;

        if((focused == null || focused != getFocused()) && getFocused() instanceof IWidget){
            ((IWidget) getFocused()).mouseReleasedFocus(x, y, button);
        }

        return focused != null;
    }

    @Override
    default boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        return getLastMouseFocused() != null && getLastMouseFocused().mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    default boolean mouseDragged(double mX, double mY, int button, double dragX, double dragY) {
        return getFocused() != null && isDragging() && getFocused().mouseDragged(mX, mY, button, dragX, dragY);
    }

    @Nullable
    IGuiEventListener getLastMouseFocused();

    @Nonnull
    List<ITickable> getTickables();

    void setRoot(@Nonnull IRoot root);

    @Nullable
    IRoot getRoot();

    void addChildren(@Nonnull IGuiEventListener element);

    void removeChildren(@Nonnull IGuiEventListener element);

    void addTickable(@Nonnull ITickable element);

    void removeTickable(@Nonnull ITickable element);

    int getHeight();

    int getWidth();

    int getPosX();

    int getPosY();

    int getPosEndY();

    int getPosEndX();
}

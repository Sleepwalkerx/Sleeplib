package com.sleepwalker.sleeplib.client.widget.base.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.core.BaseWidget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Button extends BaseWidget {

    protected boolean pressed, blocked;

    @Nullable
    protected IClickListener clickListener;

    @Nonnull
    protected ISprite sprite = SLSprites.RECT_4;

    @Override
    public boolean mouseClicked(double x, double y, int button) {

        if(blocked){
            return false;
        }

        if(isMouseOver(x, y)){
            pressed = true;
            return true;
        }
        else return false;
    }

    @Override
    public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int button) {

        if(!pressed){
            return false;
        }

        pressed = false;

        if(clickListener != null){
            clickListener.onClick(this, button);
        }

        return true;
    }

    @Override
    public void mouseReleasedFocus(double x, double y, int key) {
        pressed = false;
    }

    public void setClickListener(@Nullable IClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {
        sprite.render(ms, posX, posY, width, height, isMouseFocused() ? 1 : 0);
    }

    public void setSprite(@Nonnull ISprite sprite) {
        this.sprite = sprite;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public interface IClickListener {
        void onClick(@Nonnull Button element, int button);
    }
}

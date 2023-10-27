package com.sleepwalker.sleeplib.client.widget.base.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.SLSprites;

import javax.annotation.Nonnull;

public abstract class DisposableButton extends SelectableButton {

    public DisposableButton(){
        setSprite(SLSprites.DISPOSABLE_BUTTON);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {

        if(pressed){
            sprite.render(ms, posX, posY, width, height, 2);
            renderOnCanvas(ms, x, y, pt, 1);
        }
        else {
            sprite.render(ms, posX, posY, width, height, blocked ? 3 : isMouseFocused() ? selected ? 5 : 1 : selected ? 4 : 0);
            renderOnCanvas(ms, x, y, pt, 0);
        }
    }

    protected abstract void renderOnCanvas(@Nonnull MatrixStack ms, int x, int y, float pt, int yIntent);
}

package com.sleepwalker.sleeplib.client.widget.base.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.SpriteUtils;
import com.sleepwalker.sleeplib.math.Vector2i;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;

import javax.annotation.Nonnull;

public class DisposableImageButton extends DisposableButton {

    @Nonnull
    public Vector2i imgPos = Vector2i.ZERO;
    public final ISprite sprite;
    private final ISprite.IStateSupplier<DisposableImageButton> stateGetter;

    public DisposableImageButton(@Nonnull ISprite sprite) {
        this(sprite, SpriteUtils.defaultStateSupplier());
    }

    public DisposableImageButton(@Nonnull ISprite sprite, @Nonnull ISprite.IStateSupplier<DisposableImageButton> stateGetter) {
        this.sprite = sprite;
        this.stateGetter = stateGetter;
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {
        super.initOnScreen(posX, posY, width, height, screen);
        calcImagePos();
    }

    @Override
    protected void renderOnCanvas(@Nonnull MatrixStack ms, int x, int y, float pt, int yIntent) {
        sprite.render(ms, getPosX() + imgPos.x, getPosY() + imgPos.y + yIntent, stateGetter.getSpriteState(this));
    }

    private void calcImagePos(){
        imgPos = SpriteUtils.spriteAlign(sprite, Align.CENTER_MIDDLE, width, height - 4);
    }
}

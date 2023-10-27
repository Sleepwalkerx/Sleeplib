package com.sleepwalker.sleeplib.client.widget.base.slider;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;

import javax.annotation.Nonnull;

public class VerticalSlider extends Slider {

    public VerticalSlider(){
        this(SLSprites.VERTICAL_SLIDER);
    }

    public VerticalSlider(@Nonnull ISprite sprite) {
        super(sprite);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {
        RenderSystem.enableDepthTest();
        sprite.render(ms, posX, pos, blocked ? 3 : pressed ? 2 : isMouseFocused() ? 1 : 0);
    }

    @Override
    public void initSliderOnScreen(int posX, int posY, int max, int min, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        this.min = min;
        this.max = max - height;
        zoneSize = this.max - this.min;

        setPosY((int)(value * zoneSize + this.min));
        pos = this.posY;

        setValue(value);
    }

    @Override
    public void setPos(float pos) {
        super.setPos(pos);

        setPosY((int) pos);
    }

    @Override
    public boolean mouseDragged(double x, double y, int button, double deltaX, double deltaY) {

        if(blocked) return false;

        setPos(pos + (float) deltaY);

        value = (pos - min) / zoneSize;

        onUpdateValue();

        return true;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= getPosX() && getPosEndX() > mouseX && mouseY >= pos && getPosEndY() >= mouseY;
    }
}

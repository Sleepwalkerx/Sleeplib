package com.sleepwalker.sleeplib.client.widget.base.slider;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import net.minecraft.util.math.MathHelper;
import com.sleepwalker.sleeplib.client.SLSprites;

import javax.annotation.Nonnull;

public class HorizontalSlider extends Slider {

    public HorizontalSlider(ISprite sprite) {
        super(sprite);
    }

    public HorizontalSlider() {
        this(SLSprites.HORIZONTAL_SLIDER);
    }

    @Override
    public void initSliderOnScreen(int posX, int posY, int max, int min, @Nonnull IExtraNestedGuiEventHandler screen) {
        super.initOnScreen(posX, posY, width, height, screen);

        this.min = min;
        this.max = max - width;
        zoneSize = this.max - this.min;

        setPosX((int)(value * zoneSize + this.min));
        pos = this.posX;

        setValue(value);
    }

    @Override
    public void setPos(float pos) {
        super.setPos(pos);
        setPosX((int) pos);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {
        SLSprites.HORIZONTAL_SLIDER.render(ms, pos, posY, blocked ? 3 : pressed ? 2 : isMouseFocused() ? 1 : 0);
    }

    @Override
    public boolean mouseDragged(double x, double y, int button, double deltaX, double deltaY) {

        if(blocked) return false;

        pos = MathHelper.clamp(pos + (float) deltaX, min, max);

        setPosX((int) pos);

        value = (pos - min) / zoneSize;

        onUpdateValue();

        return true;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= pos && getPosEndX() > mouseX && mouseY >= getPosY() && getPosEndY() >= mouseY;
    }
}

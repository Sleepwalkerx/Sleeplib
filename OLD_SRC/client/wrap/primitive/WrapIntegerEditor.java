package com.sleepwalker.sleeplib.client.wrap.primitive;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.widget.grouped.IntegerExtraField;
import com.sleepwalker.sleeplib.client.wrap.IWrapCanvas;
import com.sleepwalker.sleeplib.client.wrap.IWrapHandler;
import com.sleepwalker.sleeplib.client.SLSprites;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

public class WrapIntegerEditor extends IntegerExtraField implements IWrapCanvas {

    private IWrapHandler wrapHandler;

    @Nonnull
    private final BiConsumer<WrapIntegerEditor, Integer> confirmConsumer;

    public WrapIntegerEditor(@Nonnull BiConsumer<WrapIntegerEditor, Integer> confirmConsumer, int minValue, int maxValue, int defaultValue) {

        this.confirmConsumer = confirmConsumer;

        integerField.setBoundaries(maxValue, minValue);

        integerField.setValue(String.valueOf(defaultValue));

        setSize(60, 22);
    }

    @Override
    @Nonnull
    public IWrapHandler getWrapHandler() {
        return wrapHandler;
    }

    @Override
    public void setWrapHandler(@Nonnull IWrapHandler wrapHandler) {
        this.wrapHandler = wrapHandler;
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        SLSprites.RECT_3.render(ms, posX - 26, posY - 20, 26 * 2 + width, height + 40);
        super.renderWidget(ms, mX, mY, pt);
    }

    @Override
    public void onClose() {
        confirmConsumer.accept(this, integerField.getNumber());
    }
}

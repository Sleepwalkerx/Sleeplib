package com.sleepwalker.sleeplib.client.wrap;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.scrollrect.VerticalScrollRect;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

public class WrapContentEditor<T> extends VerticalScrollRect<WrapContentElement<T>> implements IWrapCanvas {

    @Nonnull
    private final BiConsumer<WrapContentElement<T>, WrapContentEditor<T>> actionConsumer;

    private IWrapHandler wrapHandler;

    public WrapContentEditor(@Nonnull BiConsumer<WrapContentElement<T>, WrapContentEditor<T>> actionConsumer){
        super();

        this.actionConsumer = actionConsumer;

        setSize(167 - slider.getWidth(), 187);
    }

    void onAction(@Nonnull WrapContentElement<T> element){
        actionConsumer.accept(element, this);
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
        SLSprites.RECT_3.render(ms, posX - 13, posY - 13, 13 * 2 + width + slider.getWidth(), 13 * 2 + height);
        SLSprites.SCROLL_RECT_BACKGROUND.render(ms, posX - 2, posY - 2, width + slider.getWidth() + 4, height + 4);
        super.renderWidget(ms, mX, mY, pt);
    }
}

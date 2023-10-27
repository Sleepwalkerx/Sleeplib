package com.sleepwalker.sleeplib.client.wrap;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.button.SelectableButton;
import com.sleepwalker.sleeplib.client.widget.base.scrollrect.VerticalScrollRect;
import com.sleepwalker.sleeplib.client.widget.base.text.SimpleText;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;
import com.sleepwalker.sleeplib.client.wrap.widget.BaseWrapNestedCanvas;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class WrapCollectionSelector<T> extends BaseWrapNestedCanvas {

    @Nonnull
    protected final BiConsumer<WrapCollectionSelector<T>, T> confirmConsumer;
    @Nonnull
    protected final Function<T, ITextComponent> displayNameSupplier;
    @Nonnull
    protected final SimpleText title;
    @Nonnull
    protected final VerticalScrollRect<WrapElement> scrollRect;

    public WrapCollectionSelector(
        @Nonnull ITextComponent title,
        @Nonnull Collection<T> values,
        @Nonnull Function<T, ITextComponent> displayNameSupplier,
        @Nullable T selected,
        @Nonnull BiConsumer<WrapCollectionSelector<T>, T> confirmConsumer
    ) {
        super();

        this.confirmConsumer = confirmConsumer;
        this.displayNameSupplier = displayNameSupplier;
        this.title = new SimpleText(title, Align.CENTER_TOP, new Vector2f(0, 6));
        scrollRect = new VerticalScrollRect<>();

        setSize(167 + 16, 187 + 26);

        scrollRect.setSize(width - scrollRect.getSlider().getWidth() - 16, height - 26);

        for (T elem : values){

            WrapElement element = new WrapElement(elem);

            scrollRect.addScrollElement(element);

            if(element.value == selected){
                element.setSelected(true);
            }
        }
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        scrollRect.initOnScreen(posX + 8, getPosEndY() - scrollRect.getHeight() - 8, this);
        title.mathTextPos(this);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        SLSprites.RECT_3.render(ms, posX, posY, width, height);
        SLSprites.SCROLL_RECT_BACKGROUND.render(
            ms, scrollRect.getPosX() - 2, scrollRect.getPosY() - 2,
            scrollRect.getWidth() + scrollRect.getSlider().getWidth() + 4, scrollRect.getHeight() + 4
        );

        scrollRect.render(ms, mX, mY, pt);

        title.render(ms, mX, mY, pt);
    }

    private void setSelectedElement(@Nonnull WrapElement element){
        wrapHandler.deactivateWrapCanvas();
        confirmConsumer.accept(this, element.value);
    }

    protected class WrapElement extends SelectableButton {

        @Nonnull
        public final T value;

        @Nonnull
        public final SimpleText text;

        public WrapElement(@Nonnull T value) {
            this.value = value;

            text = new SimpleText(displayNameSupplier.apply(value), Align.LEFT_MIDDLE, new Vector2f(4, 0));
            setSize(scrollRect.getWidth(), 20);
        }

        @Override
        public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {
            super.initOnScreen(posX, posY, width, height, screen);
            text.mathTextPos(this);
        }

        @Override
        public void setPosY(int posY) {

            Vector2f vector2f = text.getTextPosCache();
            text.setTextPosCache(new Vector2f(vector2f.x, vector2f.y + posY - this.posY));

            super.setPosY(posY);
        }

        @Override
        public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
            SLSprites.RECT_4.render(ms, posX, posY, width, height, isMouseFocused() ? 1 : 0);
            text.render(ms, mX, mY, pt);
        }

        @Override
        public void setSelected(boolean selected) {
            super.setSelected(selected);
            text.setColor(selected ? 0xffa64d : -1);
        }

        @Override
        public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int button) {

            boolean sel = super.mouseReleased(p_231048_1_, p_231048_3_, button);

            if(sel){
                setSelectedElement(this);
            }

            return sel;
        }
    }
}
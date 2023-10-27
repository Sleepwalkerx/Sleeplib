package com.sleepwalker.sleeplib.client.widget.base.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.widget.base.text.SimpleText;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;
import com.sleepwalker.sleeplib.client.widget.core.math.AlignX;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class TextButton extends Button {

    public static final ITextComponent EMPTY = new StringTextComponent("");

    @Nonnull
    protected final SimpleText text;

    public TextButton(@Nonnull ITextComponent text, @Nonnull Align align) {
        this.text = new SimpleText(text, align, new Vector2f(align.alignX == AlignX.LEFT ? 4 : -4, 1));
        setText(text);
    }

    public TextButton(@Nonnull Align align) {
        this(EMPTY, align);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {
        super.initOnScreen(posX, posY, width, height, screen);
        text.mathTextPos(this);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {
        super.renderWidget(ms, x, y, pt);
        text.render(ms, x, y, pt);
    }

    public void setText(@Nonnull ITextComponent text) {
        this.text.setText(text);
        this.text.mathTextPos(this);
    }

    @Nonnull
    public SimpleText getText() {
        return text;
    }
}

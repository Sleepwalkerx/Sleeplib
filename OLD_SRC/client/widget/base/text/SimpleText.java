package com.sleepwalker.sleeplib.client.widget.base.text;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.TextUtils;
import com.sleepwalker.sleeplib.client.widget.core.IWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class SimpleText implements IRenderable {

    protected ITextComponent text;

    @Nonnull
    protected Vector2f textPosCache = Vector2f.ZERO;

    @Nonnull
    protected Vector2f indent;

    @Nonnull
    protected Align align;

    protected int color = -1;

    protected final Consumer<MatrixStack> render;

    public SimpleText(@Nonnull ITextComponent text, @Nonnull Align align, @Nonnull Vector2f indent, boolean shadow){
        this.text = text;
        this.align = align;
        this.indent = indent;

        if(!shadow){
            render = (ms -> Minecraft.getInstance().font.draw(
                ms, this.text, this.textPosCache.x + this.indent.x, textPosCache.y + this.indent.y, color)
            );
        }
        else {
            render = (ms -> Minecraft.getInstance().font.drawShadow(
                ms, this.text, this.textPosCache.x + this.indent.x, textPosCache.y + this.indent.y, color)
            );
        }
    }

    public SimpleText(@Nonnull ITextComponent text, @Nonnull Align align, boolean shadow){
        this(text, align, Vector2f.ZERO, shadow);
    }

    public SimpleText(@Nonnull ITextComponent text, @Nonnull Align align, @Nonnull Vector2f indent){
        this(text, align, indent, false);
    }

    public SimpleText(@Nonnull ITextComponent text, @Nonnull Align align){
        this(text, align, Vector2f.ZERO, false);
    }

    @Override
    public void render(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        render.accept(ms);
    }

    public void mathTextPos(int width, int height, int posX, int posY){
        textPosCache = TextUtils.textAlign(Minecraft.getInstance().font, text, align, width, height);
        textPosCache = new Vector2f(textPosCache.x + posX, textPosCache.y + posY);
    }

    public void mathTextPos(@Nonnull IWidget parent){
        mathTextPos(parent.getWidth(), parent.getHeight(), parent.getPosX(), parent.getPosY());
    }

    public void setIndent(@Nonnull Vector2f indent) {
        this.indent = indent;
    }

    public void setAlign(@Nonnull Align align) {
        this.align = align;
    }

    public void setTextPosCache(@Nonnull Vector2f textPosCache) {
        this.textPosCache = textPosCache;
    }

    public void setText(@Nonnull ITextComponent text) {
        this.text = text;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Nonnull
    public Align getAlign() {
        return align;
    }

    @Nonnull
    public ITextComponent getText() {
        return text;
    }

    @Nonnull
    public Vector2f getTextPosCache() {
        return textPosCache;
    }
}

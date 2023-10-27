package com.sleepwalker.sleeplib.client.widget.base.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;

import javax.annotation.Nonnull;

public class DisposableTextButton extends DisposableButton {

    @Nonnull
    protected ITextComponent text;
    @Nonnull
    protected Vector2f txPosCache = Vector2f.ZERO;
    protected int bottomIndent = 4;
    protected boolean shadow;

    private int color = -1;

    protected final FontRenderer fr;

    public DisposableTextButton(@Nonnull ITextComponent text) {
        fr = Minecraft.getInstance().font;
        this.text = text;
    }

    public void mathSize(int height){
        mathSize(10, height);
    }

    public void mathSize(int indent, int height){
        setSize(fr.width(text) + indent, height);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {
        super.initOnScreen(posX, posY, width, height, screen);
        setText(text);
    }

    @Override
    protected void renderOnCanvas(@Nonnull MatrixStack ms, int x, int y, float pt, int yIntent) {
        if(shadow){
            fr.drawShadow(ms, text, posX + txPosCache.x, posY + txPosCache.y + yIntent, color);
        }
        else {
            fr.draw(ms, text, posX + txPosCache.x, posY + txPosCache.y + yIntent, color);
        }
    }

    @Nonnull
    public ITextComponent getText() {
        return text;
    }

    @Override
    public void setBlocked(boolean blocked) {
        super.setBlocked(blocked);

        color = blocked ? 0x737373 : -1;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public void setText(@Nonnull ITextComponent text){
        this.text = text;
        txPosCache = TextUtils.textAlign(fr, text, Align.CENTER_MIDDLE, width, height - bottomIndent);
    }

    public void setBottomIndent(int bottomIndent) {
        this.bottomIndent = bottomIndent;
    }
}

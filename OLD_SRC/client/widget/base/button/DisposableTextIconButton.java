package com.sleepwalker.sleeplib.client.widget.base.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.SpriteUtils;
import com.sleepwalker.sleeplib.math.Vector2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;
import com.sleepwalker.sleeplib.client.widget.core.math.AlignY;

import javax.annotation.Nonnull;

public class DisposableTextIconButton extends DisposableButton {

    @Nonnull
    public Vector2i imgPos = Vector2i.ZERO;
    public final ISprite sprite;
    private final ISprite.IStateSupplier<DisposableTextIconButton> stateGetter;

    @Nonnull
    protected ITextComponent text = new StringTextComponent("");
    protected Vector2f txPos = Vector2f.ZERO;

    protected final FontRenderer fr;

    public DisposableTextIconButton(@Nonnull ISprite sprite, @Nonnull ITextComponent text) {
        this(sprite, SpriteUtils.defaultStateSupplier(), text);
    }

    public DisposableTextIconButton(
        @Nonnull ISprite sprite,
        @Nonnull ISprite.IStateSupplier<DisposableTextIconButton> stateGetter,
        @Nonnull ITextComponent text
    ) {

        this.sprite = sprite;
        this.stateGetter = stateGetter;

        fr = Minecraft.getInstance().font;
        setContent(text);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {
        super.initOnScreen(posX, posY, width, height, screen);
        updateContentPos();
    }

    @Nonnull
    public ITextComponent getText() {
        return text;
    }

    @Override
    protected void renderOnCanvas(@Nonnull MatrixStack ms, int x, int y, float pt, int yIntent) {

        fr.draw(ms, text, posX + txPos.x, posY + txPos.y + yIntent, -1);
        sprite.render(ms, getPosX() + imgPos.x, getPosY() + imgPos.y + yIntent, stateGetter.getSpriteState(this));
    }

    public void setContent(@Nonnull ITextComponent text) {

        this.text = text;

        updateContentPos();

        setWidth(fr.width(text) + Math.round(txPos.x) + 3);
    }

    public void updateContentPos(){

        imgPos = SpriteUtils.spriteAlign(sprite, Align.LEFT_MIDDLE, width, height - 4);
        imgPos = new Vector2i(imgPos.x + 4, imgPos.y);

        txPos = new Vector2f(
            imgPos.x + sprite.getWidth() + 2,
            AlignY.MIDDLE.math(fr.lineHeight, height - 4)
        );
    }
}

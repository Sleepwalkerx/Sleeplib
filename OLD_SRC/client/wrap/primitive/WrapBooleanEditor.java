package com.sleepwalker.sleeplib.client.wrap.primitive;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.widget.base.button.DisposableTextButton;
import com.sleepwalker.sleeplib.client.widget.grouped.DisposableHSelectionGroup;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.BooleanUtils;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.wrap.IWrapCanvas;
import com.sleepwalker.sleeplib.client.wrap.IWrapHandler;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

public class WrapBooleanEditor extends DisposableHSelectionGroup implements IWrapCanvas {

    private IWrapHandler wrapHandler;

    @Nonnull
    private final BiConsumer<WrapBooleanEditor, Boolean> confirmConsumer;

    public WrapBooleanEditor(@Nonnull BiConsumer<WrapBooleanEditor, Boolean> confirmConsumer, boolean defaultValue) {
        super(
            Lists.newArrayList(
                new DisposableTextButton(new StringTextComponent("false").withStyle(TextFormatting.RED)),
                new DisposableTextButton(new StringTextComponent("true").withStyle(TextFormatting.GREEN))
            ),
            BooleanUtils.toInteger(defaultValue)
        );

        setSize(90, 26);

        this.confirmConsumer = confirmConsumer;
    }

    @Override
    public void onClose() {
        confirmConsumer.accept(this, BooleanUtils.toBoolean(elements.indexOf(selected)));
    }

    @Nonnull
    @Override
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
}

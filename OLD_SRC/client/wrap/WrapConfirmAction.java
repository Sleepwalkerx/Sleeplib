package com.sleepwalker.sleeplib.client.wrap;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.wrap.widget.BaseWrapNestedCanvas;
import com.sleepwalker.sleeplib.client.wrap.widget.WCCloseButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.button.DisposableTextButton;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class WrapConfirmAction extends BaseWrapNestedCanvas {

    protected final DisposableTextButton confirmButton, cancelButton;
    protected final WCCloseButton closeButton;

    @Nonnull
    protected final List<ITextComponent> texts;
    protected final FontRenderer fr;
    @Nonnull
    protected final Consumer<Boolean> confirmConsumer;
    private boolean closeConsume, closeState;

    public WrapConfirmAction(@Nonnull ITextComponent text, @Nonnull Consumer<Boolean> confirmConsumer) {

        texts = Arrays.stream(text.getString().split("\n")).map(StringTextComponent::new).collect(Collectors.toList());

        this.confirmConsumer = confirmConsumer;

        fr = Minecraft.getInstance().font;

        confirmButton = new DisposableTextButton(new TranslationTextComponent(langKey("confirmAction.confirm")).withStyle(TextFormatting.GREEN));
        confirmButton.mathSize(20);
        confirmButton.setClickListener((element, button) -> doAction(true));

        cancelButton = new DisposableTextButton(new TranslationTextComponent(langKey("confirmAction.cancel")).withStyle(TextFormatting.RED));
        cancelButton.mathSize(20);
        cancelButton.setClickListener((element, button) -> doAction(false));

        setSize(confirmButton.getWidth() + cancelButton.getWidth() + confirmButton.getWidth() / 2 + cancelButton.getWidth() / 2, 56 + texts.size() * (fr.lineHeight + 3));

        texts.forEach(textComponent -> {
            if(fr.width(textComponent) + 16 > width){
                setWidth(fr.width(textComponent) + 16);
            }
        });

        closeButton = createCloseButton(() -> doAction(false));
    }

    public void setCloseConsume(boolean closeConsume) {
        this.closeConsume = closeConsume;
    }

    public void setCloseConsume(boolean closeConsume, boolean closeState) {
        this.closeConsume = closeConsume;
        this.closeState = closeState;
    }

    @Override
    public void onClose() {
        if(closeConsume){
            confirmConsumer.accept(closeState);
        }
    }

    private void doAction(boolean confirm){
        confirmConsumer.accept(confirm);
        wrapHandler.deactivateWrapCanvas();
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        int wh = width / 4;

        closeButton.defaultInitOnScreen(this);
        confirmButton.initOnScreen(posX + wh * 3 - confirmButton.getWidth() / 2, posEndY - confirmButton.getHeight() - 4, this);
        cancelButton.initOnScreen(posX + wh - cancelButton.getWidth() / 2, posEndY - cancelButton.getHeight() - 4, this);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        SLSprites.RECT_3.render(ms, posX, posY, width, height);

        for(int i = 0; i < texts.size(); i++){
            fr.drawShadow(ms, texts.get(i),
                posX + width / 2f - fr.width(texts.get(i)) / 2f,
                posY + 16 + ((fr.lineHeight + 3) * i),
                -1
            );
        }

        closeButton.render(ms, mX, mY, pt);
        confirmButton.render(ms, mX, mY, pt);
        cancelButton.render(ms, mX, mY, pt);
    }
}

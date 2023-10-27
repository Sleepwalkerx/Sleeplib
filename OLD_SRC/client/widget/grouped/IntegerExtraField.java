package com.sleepwalker.sleeplib.client.widget.grouped;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.ISavable;
import com.sleepwalker.sleeplib.client.widget.base.button.DisposableImageButton;
import net.minecraft.nbt.StringNBT;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.core.BaseNestedWidget;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.base.inputfield.IntegerField;

import javax.annotation.Nonnull;

public class IntegerExtraField extends BaseNestedWidget implements ISavable<StringNBT> {

    protected final IntegerField integerField;
    protected final DisposableImageButton plus, minus;

    public IntegerExtraField(){

        integerField = new IntegerField();
        plus = new DisposableImageButton(SLSprites.GREY_PLUS_ICON);
        plus.setClickListener((b, i) -> growNumber(1));
        minus = new DisposableImageButton(SLSprites.GREY_MINUS_ICON);
        minus.setClickListener((b, i) -> growNumber(-1));
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {

        // Для понимания
        int imgButtonSize;
        imgButtonSize = height;

        super.initOnScreen(posX, posY, width + imgButtonSize * 2, height, screen);

        minus.initOnScreen(posX, posY, imgButtonSize, imgButtonSize, screen);
        integerField.initOnScreen(minus.getPosEndX(), posY, width, imgButtonSize, screen);
        plus.initOnScreen(integerField.getPosEndX(), posY, imgButtonSize, imgButtonSize, screen);
    }

    @Override
    public void forceRemove(@Nonnull IExtraNestedGuiEventHandler parent) {
        super.forceRemove(parent);

        integerField.forceRemove(parent);
        plus.forceRemove(parent);
        minus.forceRemove(parent);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        integerField.render(ms, mX, mY, pt);
        plus.render(ms, mX, mY, pt);
        minus.render(ms, mX, mY, pt);
    }

    @Override
    public void setPosY(int posY) {
        int delta = posY - this.posY;
        integerField.setPosY(delta + integerField.getPosY());
        plus.setPosY(delta + plus.getPosY());
        minus.setPosY(delta + minus.getPosY());
        super.setPosY(posY);
    }

    public IntegerField getIntegerField() {
        return integerField;
    }

    public void growNumber(int value){
        integerField.setValue(String.valueOf(integerField.getNumber() + value));
    }

    @Nonnull
    @Override
    public StringNBT saveData() {
        return integerField.saveData();
    }

    @Override
    public void readData(@Nonnull StringNBT nbt) {
        integerField.readData(nbt);
    }
}

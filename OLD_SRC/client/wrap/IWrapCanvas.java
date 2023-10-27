package com.sleepwalker.sleeplib.client.wrap;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.client.widget.base.button.DisposableTextButton;
import com.sleepwalker.sleeplib.client.widget.core.IWidget;
import com.sleepwalker.sleeplib.client.wrap.widget.WCCloseButton;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public interface IWrapCanvas extends IWidget {

    default void onClose(){ }

    @Nonnull
    default String langKey(@Nonnull String name){
        return String.format("%s.wrapCanvas.%s", SleepLib.MODID, name);
    }

    @Nonnull
    default DisposableTextButton createConfirmButton(){

        DisposableTextButton confirmButton = new DisposableTextButton(
            new TranslationTextComponent(langKey("confirmAction.confirm"))
        );

        confirmButton.setSize(Minecraft.getInstance().font.width(confirmButton.getText()) + 8, 20);

        return confirmButton;
    }

    @Nonnull
    default WCCloseButton createCloseButton() {
        return new WCCloseButton(this);
    }

    @Nonnull
    default WCCloseButton createCloseButton(Runnable closeAction) {
        return new WCCloseButton(this, closeAction);
    }

    @Nonnull
    IWrapHandler getWrapHandler();

    void setWrapHandler(@Nonnull IWrapHandler wrapHandler);
}

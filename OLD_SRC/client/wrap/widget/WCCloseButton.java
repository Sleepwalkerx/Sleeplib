package com.sleepwalker.sleeplib.client.wrap.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.button.Button;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.wrap.IWrapCanvas;

import javax.annotation.Nonnull;

public class WCCloseButton extends Button {

    public WCCloseButton(@Nonnull IWrapCanvas wrapCanvas){
        this(wrapCanvas, () -> {});
    }

    public WCCloseButton(@Nonnull IWrapCanvas wrapCanvas, @Nonnull Runnable closeAction){
        setSize(SLSprites.CROSS_ICON);
        setClickListener((element, button) -> {
            wrapCanvas.getWrapHandler().deactivateWrapCanvas();
            closeAction.run();
        });
    }

    public void defaultInitOnScreen(@Nonnull IExtraNestedGuiEventHandler parent, int indent){
        initOnScreen(parent.getPosEndX() - width - indent, parent.getPosY() + indent, parent);
    }

    public void defaultInitOnScreen(@Nonnull IExtraNestedGuiEventHandler parent){
        defaultInitOnScreen(parent, 5);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {

        if(isMouseFocused()){
            RenderSystem.color4f(1.0f, 0.8f, 0.9f, 0.5f);
        }

        SLSprites.CROSS_ICON.render(ms, posX, posY);

        if(isMouseFocused()){
            RenderSystem.color4f(1.0f, 1.5f, 1.0f, 1.0f);
        }
    }
}

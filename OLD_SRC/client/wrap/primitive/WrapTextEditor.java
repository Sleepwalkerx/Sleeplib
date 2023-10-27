package com.sleepwalker.sleeplib.client.wrap.primitive;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.fml.client.gui.GuiUtils;
import com.sleepwalker.sleeplib.client.wrap.IWrapCanvas;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.inputfield.TextField;
import com.sleepwalker.sleeplib.client.wrap.IWrapHandler;

import javax.annotation.Nonnull;

public class WrapTextEditor extends TextField implements IWrapCanvas {

    private IWrapHandler wrapHandler;

    public WrapTextEditor(@Nonnull IWrapHandler handler){
        setSize(167, 21);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        SLSprites.RECT_3.render(ms, posX - 13, posY - 13, 193, 45);
        GuiUtils.drawTexturedModalRect(ms, posX - 13, posY - 12, 0, 211, 193, 45, 0);

        super.renderWidget(ms, mX, mY, pt);
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
}

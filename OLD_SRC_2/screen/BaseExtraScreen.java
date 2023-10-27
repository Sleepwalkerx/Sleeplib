package com.sleepwalker.sleeplib.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.ui.widget.Window;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class BaseExtraScreen extends Screen {

    @Nonnull
    protected final Window window;

    public BaseExtraScreen() {
        super(new StringTextComponent("title"));
        window = new Window();
        window.setScreen(this);
    }

    @Override
    protected void init() {
        super.init();

        window.setPosition(0, 0);
        window.setSize(width, height);
        window.updateLayout();
    }

    @Override
    public void render(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        window.render(ms, mX, mY, pt);
        super.render(ms, mX, mY, pt);
    }

    @Override
    public void mouseMoved(double x, double y) {
        window.mouseMoved(x, y);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if(super.mouseClicked(x, y, button)){
            return true;
        }
        return window.mouseClicked(x, y, button);
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        if(super.mouseReleased(x, y, button)){
            return true;
        }
        return window.mouseReleased(x, y, button);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double delta) {
        if(super.mouseScrolled(x, y, delta)){
            return true;
        }
        return window.mouseScrolled(x, y, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

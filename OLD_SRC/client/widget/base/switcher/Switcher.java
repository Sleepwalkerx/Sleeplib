package com.sleepwalker.sleeplib.client.widget.base.switcher;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.ISavable;
import com.sleepwalker.sleeplib.client.widget.base.button.Button;
import net.minecraft.nbt.ByteNBT;
import com.sleepwalker.sleeplib.client.SLSprites;

import javax.annotation.Nonnull;

public class Switcher extends Button implements ISavable<ByteNBT> {

    protected boolean enabled;

    public Switcher(){
        setSize(SLSprites.SWITCHER_BACKGROUND);
    }

    @Override
    public void render(@Nonnull MatrixStack ms, int x, int y, float pt) {
        SLSprites.SWITCHER_BACKGROUND.render(ms, posX, posY, isMouseFocused() ? 1 : 0);
        SLSprites.SWITCHER_BUTTON.render(ms, enabled ? posEndX - SLSprites.SWITCHER_BUTTON.getWidth() : posX, posY - 1, enabled ? 1 : 0);
    }

    @Override
    public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) {

        enabled = !enabled;

        return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Nonnull
    @Override
    public ByteNBT saveData() {
        return ByteNBT.valueOf(enabled);
    }

    @Override
    public void readData(@Nonnull ByteNBT nbt) {
        enabled = nbt.getAsByte() == 1;
    }
}

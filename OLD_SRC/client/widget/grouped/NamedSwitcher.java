package com.sleepwalker.sleeplib.client.widget.grouped;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.ISavable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.util.text.ITextComponent;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.switcher.Switcher;
import com.sleepwalker.sleeplib.client.widget.core.BaseNestedWidget;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;

import javax.annotation.Nonnull;

public class NamedSwitcher extends BaseNestedWidget implements ISavable<ByteNBT> {

    private final Switcher switcher;

    @Nonnull
    private ITextComponent text;
    private final FontRenderer fr;

    private float txYPos;
    private int txWidth;

    public NamedSwitcher(@Nonnull ITextComponent text) {

        switcher = new Switcher();

        this.text = text;
        fr = Minecraft.getInstance().font;

        txYPos = fr.lineHeight / 2f;
        txWidth = fr.width(text);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {

        int eWidth = txWidth + 9 + switcher.getWidth();
        txYPos = posY + 4 + switcher.getHeight() / 2f - (fr.lineHeight / 2f);

        super.initOnScreen(posX, posY, eWidth, switcher.getHeight() + 8, parent);

        switcher.initOnScreen(posX + txWidth + 6, posY + 4, this);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        SLSprites.RECT_4.render(ms, posX, posY, width, height, 0);
        fr.draw(ms, text, posX + 4, txYPos,-1);

        switcher.render(ms, mX, mY, pt);
    }

    public boolean isEnabled() {
        return switcher.isEnabled();
    }

    public void setText(@Nonnull ITextComponent text) {
        this.text = text;
        txWidth = fr.width(text);
        setPosX(width + txWidth + 4);
    }

    public Switcher getSwitcher() {
        return switcher;
    }

    @Nonnull
    @Override
    public ByteNBT saveData() {
        return switcher.saveData();
    }

    @Override
    public void readData(@Nonnull ByteNBT nbt) {
        switcher.readData(nbt);
    }
}

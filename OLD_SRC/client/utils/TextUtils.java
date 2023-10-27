package com.sleepwalker.sleeplib.client.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import com.sleepwalker.sleeplib.client.widget.core.IWidget;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;

import javax.annotation.Nonnull;

public class TextUtils {

    @Nonnull
    public static Vector2f textAlign(@Nonnull FontRenderer font, @Nonnull ITextComponent text, @Nonnull Align align, int width, int height){
        return align.math(font.width(text), font.lineHeight, width, height);
    }

    public static void draw(@Nonnull MatrixStack ms, @Nonnull FontRenderer font, @Nonnull ITextComponent text, @Nonnull Align align, @Nonnull IWidget element){
        font.draw(ms, text,
            element.getPosX() + align.alignX.math(font.width(text), element.getWidth()),
            element.getPosY() + align.alignY.math(font.lineHeight, element.getHeight()),
            -1
        );
    }
}

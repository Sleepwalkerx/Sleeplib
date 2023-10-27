package com.sleepwalker.sleeplib.client.widget.core;

import com.mojang.blaze3d.matrix.MatrixStack;

import javax.annotation.Nonnull;

public interface ITooltipRenderable {

    default void renderTooltips(@Nonnull MatrixStack ms, int mouseX, int mouseY, float pt){}
}

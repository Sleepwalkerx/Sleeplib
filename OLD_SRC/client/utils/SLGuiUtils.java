package com.sleepwalker.sleeplib.client.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.List;

public class SLGuiUtils {

    public static void drawTexturedModalRect(@Nonnull MatrixStack matrixStack, float x, float y, int u, int v, int width, int height, float zLevel) {

        final float uScale = 1f / 0x100;
        final float vScale = 1f / 0x100;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder wr = tessellator.getBuilder();
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        Matrix4f matrix = matrixStack.last().pose();
        wr.vertex(matrix, x        , y + height, zLevel).uv( u          * uScale, ((v + height) * vScale)).endVertex();
        wr.vertex(matrix, x + width, y + height, zLevel).uv((u + width) * uScale, ((v + height) * vScale)).endVertex();
        wr.vertex(matrix, x + width, y         , zLevel).uv((u + width) * uScale, ( v           * vScale)).endVertex();
        wr.vertex(matrix, x        , y         , zLevel).uv( u          * uScale, ( v           * vScale)).endVertex();
        tessellator.end();
    }

    public static void drawContinuousTexturedBox(
        MatrixStack matrixStack, float x, float y, int u, int v, int width, int height, int textureWidth, int textureHeight,
        int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel
    ) {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);

        int fillerWidth = textureWidth - leftBorder - rightBorder;
        int fillerHeight = textureHeight - topBorder - bottomBorder;
        int canvasWidth = width - leftBorder - rightBorder;
        int canvasHeight = height - topBorder - bottomBorder;
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;

        // Draw Border
        // Top Left
        drawTexturedModalRect(matrixStack, x, y, u, v, leftBorder, topBorder, zLevel);
        // Top Right
        drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder, zLevel);
        // Bottom Left
        drawTexturedModalRect(matrixStack, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder, zLevel);
        // Bottom Right
        drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);

        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++) {
            // Top Border
            drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, (i == xPasses ? remainderWidth : fillerWidth), topBorder, zLevel);
            // Bottom Border
            drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder, zLevel);

            // Throw in some filler for good measure
            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++)
                drawTexturedModalRect(matrixStack, x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight), zLevel);
        }

        // Side Borders
        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
            // Left Border
            drawTexturedModalRect(matrixStack, x, y + topBorder + (j * fillerHeight), u, v + topBorder, leftBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel);
            // Right Border
            drawTexturedModalRect(matrixStack, x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel);
        }
    }

    public static void glScissor(int x, int y, int width, int height, @Nonnull MainWindow mw) {

        double scaleW = (double)mw.getScreenWidth() / (double)mw.getGuiScaledWidth();
        double scaleH = (double)mw.getScreenHeight() / (double)mw.getGuiScaledHeight();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(
            (int)Math.floor((double)x * scaleW),
            (int)Math.floor((double)mw.getScreenHeight() - (double)(y + height) * scaleH),
            (int)Math.floor((double)(x + width) * scaleW) - (int)Math.floor((double)x * scaleW),
            (int)Math.floor((double)mw.getScreenHeight() - (double)y * scaleH) - (int)Math.floor((double)mw.getScreenHeight() - (double)(y + height) * scaleH)
        );
    }

    public static void drawHoleRect(Matrix4f mat, float zLevel, int left, int top, int width, int height, int thickness, int hexColor) {
        float alpha      = (float)(hexColor >> 24 & 255) / 255.0F;
        float colorRed   = (float)(hexColor >> 16 & 255) / 255.0F;
        float colorGreen = (float)(hexColor >>  8 & 255) / 255.0F;
        float colorBlue  = (float)(hexColor       & 255) / 255.0F;

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        int bottom = top + height, right = left + width;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        //top
        buffer.vertex(mat, right, top, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left, top, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left, top + thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, right, top + thickness, zLevel).color(colorRed, colorGreen, colorBlue, 1.0f).endVertex();
        //bottom
        buffer.vertex(mat, right, bottom - thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left, bottom - thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left, bottom, zLevel).color(colorRed, colorGreen, colorBlue, 1.0f).endVertex();
        buffer.vertex(mat, right, bottom, zLevel).color(colorRed, colorGreen, colorBlue, 1.0f).endVertex();
        //left
        buffer.vertex(mat, left + thickness, top + thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left, top + thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left, bottom - thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left + thickness, bottom - thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        //right
        buffer.vertex(mat, right, top + thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, right - thickness, top + thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, right - thickness, bottom - thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, right, bottom - thickness, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        tessellator.end();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
    }

    public static void drawColorRect(Matrix4f mat, float zLevel, int left, int top, int width, int height, int hexColor) {

        float alpha      = (float)(hexColor >> 24 & 255) / 255.0F;
        float colorRed   = (float)(hexColor >> 16 & 255) / 255.0F;
        float colorGreen = (float)(hexColor >>  8 & 255) / 255.0F;
        float colorBlue  = (float)(hexColor       & 255) / 255.0F;

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.vertex(mat, left + width, top, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left, top, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left, top + height, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        buffer.vertex(mat, left + width, top + height, zLevel).color(colorRed, colorGreen, colorBlue, alpha).endVertex();
        tessellator.end();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
    }

    public static void renderItemTooltip(@Nonnull MatrixStack ms, @Nonnull ItemStack itemStack, int mouseX, int mouseY) {

        Minecraft mc = Minecraft.getInstance();
        FontRenderer font = itemStack.getItem().getFontRenderer(itemStack);
        net.minecraftforge.fml.client.gui.GuiUtils.preItemToolTip(itemStack);

        net.minecraftforge.fml.client.gui.GuiUtils.drawHoveringText(
            ms,
            itemStack.getTooltipLines(mc.player, mc.options.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL),
            mouseX, mouseY,
            mc.getMainRenderTarget().width, mc.getMainRenderTarget().height,
            -1,
            font == null ? Minecraft.getInstance().font : font
        );

        net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();
    }

    public static void renderTooltip(@Nonnull MatrixStack ms, int mX, int mY, @Nonnull List<ITextComponent> list){

        boolean state = GL11.glIsEnabled(GL11.GL_SCISSOR_TEST);

        if(state){
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        Minecraft mc = Minecraft.getInstance();

        net.minecraftforge.fml.client.gui.GuiUtils.drawHoveringText(
            ms,
            list,
            mX, mY,
            mc.getMainRenderTarget().width, mc.getMainRenderTarget().height,
            -1,
            Minecraft.getInstance().font
        );

        net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();

        if(state){
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        }
    }
}

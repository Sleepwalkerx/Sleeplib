package com.sleepwalker.sleeplib.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.gg.essential.universal.UGraphics;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.List;

public class DrawUtil {

    public static void enableScissorTest(double x, double y, float width, float height) {

        MainWindow mw = Minecraft.getInstance().getMainWindow();

        double screenHeight = mw.getHeight();
        double scaleW = (double)mw.getWidth() / (double)mw.getScaledWidth();
        double scaleH = screenHeight / (double)mw.getScaledHeight();
        double yPos = Math.floor(screenHeight - (y + height) * scaleH);

        RenderSystem.enableScissor(
            (int)Math.floor(x * scaleW), (int)yPos,
            (int)(Math.floor((x + width) * scaleW) - Math.floor(x * scaleW)),
            (int)(Math.floor(screenHeight - y * scaleH) - yPos)
        );
    }

    public static void disableScissorTest(){
        RenderSystem.disableScissor();
    }

    @SuppressWarnings("deprecation")
    public static void drawRect(@Nonnull MatrixStack ms, float x, float y, float z, float width, float height, int colorHex){

        float a = (colorHex >> 24 & 255) / 255f;
        float r = (colorHex >> 16 & 255) / 255f;
        float g = (colorHex >>  8 & 255) / 255f;
        float b = (colorHex       & 255) / 255f;

        if(a != 1){
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        drawRect(ms.getLast().getMatrix(), x, y, z, width, height, r, g, b, a);

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
    }

    @SuppressWarnings("deprecation")
    public static void drawHollowRect(@Nonnull MatrixStack ms, float x, float y, float z, float width, float height, float thickness, int colorHex){

        float a = (colorHex >> 24 & 255) / 255f;
        float r = (colorHex >> 16 & 255) / 255f;
        float g = (colorHex >>  8 & 255) / 255f;
        float b = (colorHex       & 255) / 255f;

        Matrix4f mat = ms.getLast().getMatrix();

        if(a != 1){
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        drawRect(mat, x, y,                      z,     width,              thickness, r, g, b, a);
        drawRect(mat, x, y + height - thickness, z,     width,              thickness, r, g, b, a);
        drawRect(mat, x, y,                      z, thickness, height - thickness * 2, r, g, b, a);
        drawRect(mat, x + width - thickness, y,  z, thickness, height - thickness * 2, r, g, b, a);

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
    }

    public static void drawRect(@Nonnull Matrix4f mat, float x, float y, float z, float width, float height, float r, float g, float b, float a){

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(mat, x + width, y,          z).color(r, g, b, a).endVertex();
        buffer.pos(mat, x,         y,          z).color(r, g, b, a).endVertex();
        buffer.pos(mat, x,         y + height, z).color(r, g, b, a).endVertex();
        buffer.pos(mat, x + width, y + height, z).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    public static void drawRoundedHollowRect(@Nonnull MatrixStack ms, float x, float y, float z, float width, float height, float thickness, float radius, int colorHex){
        drawRoundedHollowRect(ms, x, y, z, width, height, thickness, radius, radius, radius, radius, colorHex);
    }

    @SuppressWarnings("deprecation")
    public static void drawRoundedHollowRect(
        @Nonnull MatrixStack ms,
        float x, float y, float z, float width, float height, float thickness,
        float radiusLT, float radiusRT, float radiusRB, float radiusLB,
        int colorHex
    ){

        float a = (colorHex >> 24 & 255) / 255f;
        float r = (colorHex >> 16 & 255) / 255f;
        float g = (colorHex >>  8 & 255) / 255f;
        float b = (colorHex       & 255) / 255f;

        float radiusLT2 = radiusLT - thickness;
        float radiusRT2 = radiusRT - thickness;
        float radiusRB2 = radiusRB - thickness;
        float radiusLB2 = radiusLB - thickness;

        Matrix4f mat = ms.getLast().getMatrix();

        if(a != 1){
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);

        float radiusDelta = radiusRB - radiusRB2;
        float pX = x + width - radiusRB;
        float pY = y + height - radiusRB;
        float pX2 = pX - thickness + radiusDelta;
        float pY2 = pY - thickness + radiusDelta;
        drawDoublePie(mat, buffer, pX, pY, radiusRB, pX2, pY2, radiusRB2, z, 0f, 14, r, g, b, a);

        radiusDelta = radiusRT - radiusRT2;
        pX = x + width - radiusRT;
        pY = y + radiusRT;
        pX2 = pX - thickness + radiusDelta;
        pY2 = pY + thickness - radiusDelta;
        drawDoublePie(mat, buffer, pX, pY, radiusRT, pX2, pY2, radiusRT2, z, 90f, 14, r, g, b, a);

        radiusDelta = radiusLT - radiusLT2;
        pX = x + radiusLT;
        pY = y + radiusLT;
        pX2 = pX + thickness - radiusDelta;
        pY2 = pY + thickness - radiusDelta;
        drawDoublePie(mat, buffer, pX, pY, radiusLT, pX2, pY2, radiusLT2, z, 180f, 14, r, g, b, a);

        radiusDelta = radiusLB - radiusLB2;
        pX = x + radiusLB;
        pY = y + height - radiusLB;
        pX2 = pX + thickness - radiusDelta;
        pY2 = pY - thickness + radiusDelta;
        drawDoublePie(mat, buffer, pX, pY, radiusLB, pX2, pY2, radiusLB2, z, 270f, 14, r, g, b, a);

        radiusDelta = radiusRB - radiusRB2;
        pX = x + width - radiusRB;
        pY = y + height - radiusRB;
        pX2 = pX - thickness + radiusDelta;
        pY2 = pY - thickness + radiusDelta;
        double radians = Math.toRadians(0f + 90f / 14 * 0);
        float aX = (float) (Math.sin(radians) * radiusRB2);
        float aY = (float) (Math.cos(radians) * radiusRB2);
        buffer.pos(mat, pX2 + aX, pY2 + aY, z).color(r, g, b, a).endVertex();

        aX = (float) (Math.sin(radians) * radiusRB);
        aY = (float) (Math.cos(radians) * radiusRB);
        buffer.pos(mat, pX + aX, pY + aY, z).color(r, g, b, a).endVertex();

        tessellator.draw();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
    }

    public static void drawDoublePie(
        @Nonnull Matrix4f mat, @Nonnull BufferBuilder buffer,
        float x1, float y1, float radius1,
        float x2, float y2, float radius2,
        float z, float degrees, int vertexCount,
        float r, float g, float b, float a
    ) {

        for(int i = 0; i <= vertexCount; i++){

            double radians = Math.toRadians(degrees + 90f / vertexCount * i);

            float aX = (float) (Math.sin(radians) * radius2);
            float aY = (float) (Math.cos(radians) * radius2);
            buffer.pos(mat, x2 + aX, y2 + aY, z).color(r, g, b, a).endVertex();

            aX = (float) (Math.sin(radians) * radius1);
            aY = (float) (Math.cos(radians) * radius1);
            buffer.pos(mat, x1 + aX, y1 + aY, z).color(r, g, b, a).endVertex();
        }
    }

    public static void drawRoundedRect(@Nonnull MatrixStack ms, float x, float y, float z, float width, float height, float radius, int colorHex){
        drawRoundedRect(ms, x, y, z, width, height, radius, radius, radius, radius, colorHex);
    }

    @SuppressWarnings("deprecation")
    public static void drawRoundedRect(
        @Nonnull MatrixStack ms,
        float x, float y, float z, float width, float height,
        float radiusLT, float radiusRT, float radiusRB, float radiusLB,
        int colorHex
    ){

        float a = (colorHex >> 24 & 255) / 255f;
        float r = (colorHex >> 16 & 255) / 255f;
        float g = (colorHex >>  8 & 255) / 255f;
        float b = (colorHex       & 255) / 255f;

        Matrix4f mat = ms.getLast().getMatrix();

        if(a != 1){
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x + width / 2, y + height / 2, z).color(r, g, b, a).endVertex();
        drawPie(mat, buffer, x + width - radiusRB, y + height - radiusRB, z, radiusRB, 0f,   18, r, g, b, a);
        drawPie(mat, buffer, x + width - radiusRT, y + radiusRT,          z, radiusRT, 90f,  18, r, g, b, a);
        drawPie(mat, buffer, x + radiusLT,         y + radiusLT,          z, radiusLT, 180f, 18, r, g, b, a);
        drawPie(mat, buffer, x + radiusLB,         y + height - radiusLB, z, radiusLB, 270f, 18, r, g, b, a);
        buffer.pos(mat, x + width - radiusRB, y + height, z).color(r, g, b, a).endVertex();

        tessellator.draw();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
    }

    public static void drawPie(
        @Nonnull Matrix4f mat, @Nonnull BufferBuilder buffer,
        float x, float y, float z,
        float radius, float degrees, int vertexCount,
        float r, float g, float b, float a
    ) {

        if(radius == 0){
            vertexCount = 1;
        }

        for(int i = 0; i <= vertexCount; i++){
            double radians = Math.toRadians(degrees + 90f / vertexCount * i);
            float aX = (float) (Math.sin(radians) * radius);
            float aY = (float) (Math.cos(radians) * radius);
            buffer.pos(mat, x + aX, y + aY, z).color(r, g, b, a).endVertex();
        }
    }

    public static void drawEllipse(@Nonnull MatrixStack ms, float x, float y, float z, float xRadius, float yRadius, int colorHex){
        drawEllipse(ms, x, y, z, xRadius, yRadius, 64, colorHex);
    }

    @SuppressWarnings("deprecation")
    public static void drawEllipse(@Nonnull MatrixStack ms, float x, float y, float z, float width, float height, int vertexCount, int colorHex){

        float a = (colorHex >> 24 & 255) / 255f;
        float r = (colorHex >> 16 & 255) / 255f;
        float g = (colorHex >>  8 & 255) / 255f;
        float b = (colorHex       & 255) / 255f;

        Matrix4f mat = ms.getLast().getMatrix();

        if(a != 1){
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        float hw = width / 2f, hh = height / 2f;
        float cx = x + hw, cy = y + hh;

        buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(mat, cx, cy, z).color(r, g, b, a).endVertex();
        float deg = 360f / vertexCount;
        for(int i = vertexCount; i >= 0; i--){
            float rad = (float) Math.toRadians(deg * i);
            buffer.pos(mat, MathHelper.cos(rad) * hw + cx, MathHelper.sin(rad) * hh + cy, z).color(r, g, b, a).endVertex();
        }
        tessellator.draw();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
    }

    public static void drawHollowEllipse(@Nonnull MatrixStack ms, float x, float y, float z, float width, float height, float thickness, int colorHex){
        drawHollowEllipse(ms, x, y, z, width, height, thickness, 64, colorHex);
    }

    @SuppressWarnings("deprecation")
    public static void drawHollowEllipse(@Nonnull MatrixStack ms, float x, float y, float z, float width, float height, float thickness, int vertexCount, int colorHex){

        float a = (colorHex >> 24 & 255) / 255f;
        float r = (colorHex >> 16 & 255) / 255f;
        float g = (colorHex >>  8 & 255) / 255f;
        float b = (colorHex       & 255) / 255f;

        Matrix4f mat = ms.getLast().getMatrix();

        if(a != 1){
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        float hw = width / 2f, hh = height / 2f;
        float hw2 = (width - thickness * 2) / 2f, hh2 = (height - thickness * 2) / 2f;
        float cx = x + hw, cy = y + hh;

        buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX);
        float deg = 360f / vertexCount;
        for(int i = vertexCount; i >= 0; i--){
            float rad = (float) Math.toRadians(deg * i);
            float cos = MathHelper.cos(rad);
            float sin = MathHelper.sin(rad);

            buffer.pos(mat, sin * hw + cx, cos * hh + cy, z).color(r, g, b, a).endVertex();
            buffer.pos(mat, sin * hw2 + cx, cos * hh2 + cy, z).color(r, g, b, a).endVertex();
        }
        tessellator.getBuffer();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.enableTexture();
    }

    public static void drawItemTooltipInScissor(@Nonnull MatrixStack ms, @Nonnull ItemStack itemStack, int mX, int mY){
        boolean state = GL11.glIsEnabled(GL11.GL_SCISSOR_TEST);
        if(state){
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        drawItemTooltip(ms, itemStack, mX, mY);
        if(state){
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        }
    }

    public static void drawItemTooltip(@Nonnull MatrixStack ms, @Nonnull ItemStack itemStack, int mX, int mY) {

        Minecraft mc = Minecraft.getInstance();
        FontRenderer font = itemStack.getItem().getFontRenderer(itemStack);
        GuiUtils.preItemToolTip(itemStack);

        GuiUtils.drawHoveringText(ms,
            itemStack.getTooltip(mc.player, mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL),
            mX, mY,
            mc.getMainWindow().getWidth(), mc.getMainWindow().getHeight(),
            -1,
            font == null ? Minecraft.getInstance().fontRenderer : font
        );

        GuiUtils.postItemToolTip();
    }
}

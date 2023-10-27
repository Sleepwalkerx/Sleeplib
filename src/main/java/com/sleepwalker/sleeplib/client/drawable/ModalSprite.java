package com.sleepwalker.sleeplib.client.drawable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.awt.*;

public class ModalSprite implements Drawable {

    @Nonnull protected final TextureSource texture;
    protected final int uOffset;
    protected final int vOffset;
    protected final int uWidth;
    protected final int vHeight;

    public ModalSprite(@Nonnull TextureSource texture, int uOffset, int vOffset, int uWidth, int vHeight) {
        this.texture = texture;
        this.uOffset = uOffset;
        this.vOffset = vOffset;
        this.uWidth = uWidth;
        this.vHeight = vHeight;
    }

    @Override
    public void drawImage(@Nonnull UMatrixStack ms, double x, double y, double width, double height, @Nonnull Color color) {
        texture.bind();
        blit(ms.toMC(), color, (float) x, (float) y, 0, uOffset, vOffset, uWidth, vHeight);
    }

    @Override
    public int getWidth() {
        return uWidth;
    }

    @Override
    public int getHeight() {
        return vHeight;
    }

    protected void blit(@Nonnull MatrixStack ms, @Nonnull Color color, float pX, float pY, float zLevel, float pUOffset, float pVOffset, int pUWidth, int pVHeight) {
        innerBlit(ms, color, pX, pX + pUWidth, pY, pY + pVHeight, zLevel, pUWidth, pVHeight, pUOffset, pVOffset, texture.getWidth(), texture.getHeight());
    }

    public static void blit(@Nonnull MatrixStack ms, @Nonnull Color color, float pX, float pY, float zLevel, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight) {
        innerBlit(ms, color, pX, pX + pUWidth, pY, pY + pVHeight, zLevel, pUWidth, pVHeight, pUOffset, pVOffset, pTextureWidth, pTextureHeight);
    }

    public static void blit(MatrixStack ms, @Nonnull Color color, float x, float y, float width, float height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        innerBlit(ms, color, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void innerBlit(@Nonnull MatrixStack ms, @Nonnull Color color, float x1, float x2, float y1, float y2, float z, int pUWidth, int pVHeight, float pUOffset, float pVOffset, int pTextureWidth, int pTextureHeight) {
        innerBlit(ms.getLast().getMatrix(), color, x1, x2, y1, y2, z, (pUOffset + 0.0F) / (float)pTextureWidth, (pUOffset + (float)pUWidth) / (float)pTextureWidth, (pVOffset + 0.0F) / (float)pTextureHeight, (pVOffset + (float)pVHeight) / (float)pTextureHeight);
    }

    @SuppressWarnings("deprecation")
    public static void innerBlit(@Nonnull Matrix4f mat, @Nonnull Color color, float x1, float x2, float y1, float y2, float z, float minU, float maxU, float minV, float maxV) {
        float r = (float) color.getRed() / 255f;
        float g = (float) color.getGreen() / 255f;
        float b = (float) color.getBlue() / 255f;
        float a = (float) color.getAlpha() / 255f;

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(mat, x1, y2, z).tex(minU, maxV).color(r, g, b, a).endVertex();
        buffer.pos(mat, x2, y2, z).tex(maxU, maxV).color(r, g, b, a).endVertex();
        buffer.pos(mat, x2, y1, z).tex(maxU, minV).color(r, g, b, a).endVertex();
        buffer.pos(mat, x1, y1, z).tex(minU, minV).color(r, g, b, a).endVertex();
        buffer.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(buffer);
    }
}

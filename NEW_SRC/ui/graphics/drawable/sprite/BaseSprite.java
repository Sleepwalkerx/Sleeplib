package com.sleepwalker.sleeplib.ui.graphics.drawable.sprite;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;

import javax.annotation.Nonnull;

public abstract class BaseSprite implements Drawable {

    @Nonnull
    protected final TextureSource source;
    protected final int u, v;
    protected final int width, height;

    protected BaseSprite(@Nonnull TextureSource source, int u, int v, int width, int height) {
        this.source = source;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
    }

    protected void blit(MatrixStack pMatrixStack, float pX, float pY, float zLevel, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight) {
        innerBlit(pMatrixStack, pX, pX + pUWidth, pY, pY + pVHeight, zLevel, pUWidth, pVHeight, pUOffset, pVOffset, pTextureWidth, pTextureHeight);
    }

    protected void innerBlit(@Nonnull MatrixStack pMatrixStack, float pX1, float pX2, float pY1, float pY2, float zLevel, int pUWidth, int pVHeight, float pUOffset, float pVOffset, int pTextureWidth, int pTextureHeight) {
        innerBlit(pMatrixStack.last().pose(), pX1, pX2, pY1, pY2, zLevel, (pUOffset + 0.0F) / (float)pTextureWidth, (pUOffset + (float)pUWidth) / (float)pTextureWidth, (pVOffset + 0.0F) / (float)pTextureHeight, (pVOffset + (float)pVHeight) / (float)pTextureHeight);
    }

    @SuppressWarnings("deprecation")
    protected void innerBlit(Matrix4f pMatrix, float pX1, float pX2, float pY1, float pY2, float zLevel, float pMinU, float pMaxU, float pMinV, float pMaxV) {
        RenderSystem.enableAlphaTest();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(pMatrix, pX1, pY2, zLevel).uv(pMinU, pMaxV).endVertex();
        bufferbuilder.vertex(pMatrix, pX2, pY2, zLevel).uv(pMaxU, pMaxV).endVertex();
        bufferbuilder.vertex(pMatrix, pX2, pY1, zLevel).uv(pMaxU, pMinV).endVertex();
        bufferbuilder.vertex(pMatrix, pX1, pY1, zLevel).uv(pMinU, pMinV).endVertex();
        bufferbuilder.end();
        WorldVertexBufferUploader.end(bufferbuilder);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}

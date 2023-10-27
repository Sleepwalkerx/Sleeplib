package com.sleepwalker.sleeplib.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.client.drawable.ModalSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.awt.*;

public final class SLItemRenderer extends ItemRenderer {

    private static SLItemRenderer INSTANCE;

    private static Framebuffer iconFrameBuffer = null;
    @Nonnull private final Minecraft mc;

    public SLItemRenderer(TextureManager textureManagerIn, ModelManager modelManagerIn, ItemColors itemColorsIn, @Nonnull Minecraft mcIn) {
        super(textureManagerIn, modelManagerIn, itemColorsIn);
        INSTANCE = this;
        mc = mcIn;
        if (iconFrameBuffer == null) {
            iconFrameBuffer = new Framebuffer(96, 96, true, Minecraft.IS_RUNNING_ON_MAC);
            iconFrameBuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    @SuppressWarnings("deprecation")
    public void drawGuiItemStack(@Nonnull MatrixStack ms, @Nonnull ItemStack stack, float x, float y, float width, float height, float r, float g, float b, float a) {

        IBakedModel bakedModel = mc.getItemRenderer().getItemModelWithOverrides(stack, null, null);
        Framebuffer lastFrameBuffer = mc.getFramebuffer();

        iconFrameBuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        iconFrameBuffer.bindFramebuffer(true);

        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, Minecraft.IS_RUNNING_ON_MAC);
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0.0D, iconFrameBuffer.framebufferWidth, iconFrameBuffer.framebufferHeight, 0.0D, 1000.0D, 3000.0D);
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
        RenderHelper.setupGui3DDiffuseLighting();

        mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        mc.getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.translatef(48.0f, 48.0f, 150.0f + this.zLevel);
        RenderSystem.scalef(1.0f, -1.0f, 1.0f);
        RenderSystem.scalef(96.0f, 96.0f, 96.0f);
        IRenderTypeBuffer.Impl renderBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        boolean isSideLit = !bakedModel.isSideLit();

        if (isSideLit) {
            RenderHelper.setupGuiFlatDiffuseLighting();
        }

        renderItem(stack, ItemCameraTransforms.TransformType.GUI, false, ms, renderBuffer, 0xF000F0, OverlayTexture.NO_OVERLAY, bakedModel);
        renderBuffer.finish();
        RenderSystem.enableDepthTest();
        if (isSideLit) {
            RenderHelper.setupGui3DDiffuseLighting();
        }

        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);

        if (lastFrameBuffer != null) {
            lastFrameBuffer.bindFramebuffer(true);

            iconFrameBuffer.bindFramebufferTexture();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.defaultAlphaFunc();
            RenderSystem.disableCull();
            RenderSystem.color4f(r, g, b, a);
            RenderSystem.scalef(1.0f, -1.0f, 1.0f);

            ModalSprite.blit(ms, Color.WHITE, x, y - 18, width, height, 0, 0,
                iconFrameBuffer.framebufferTextureWidth, iconFrameBuffer.framebufferTextureHeight, iconFrameBuffer.framebufferTextureWidth, iconFrameBuffer.framebufferTextureHeight);
        }
        else {
            iconFrameBuffer.unbindFramebuffer();
        }
    }

    public void drawGuiItemStack(@Nonnull ItemStack stack, float x, float y, float width, float height, @Nonnull Color color) {
        drawGuiItemStack(new MatrixStack(), stack, x, y, width, height, (float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f, (float) color.getAlpha() / 255f);
    }

    public void drawGuiItemStack(@Nonnull ItemStack stack, float x, float y, float width, float height){
        drawGuiItemStack(stack, x, y, width, height, Color.WHITE);
    }

    public void drawGuiItemStack(@Nonnull ItemStack stack, float x, float y){
        drawGuiItemStack(stack, x, y, 16, 16);
    }

    public static SLItemRenderer get() {
        return INSTANCE;
    }
}

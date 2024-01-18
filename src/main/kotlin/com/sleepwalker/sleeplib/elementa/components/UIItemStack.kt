package com.sleepwalker.sleeplib.elementa.components

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.sleepwalker.sleeplib.elementa.effect.ItemTooltipEffect
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.PixelConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixel
import com.sleepwalker.sleeplib.gg.essential.elementa.state.BasicState
import com.sleepwalker.sleeplib.gg.essential.elementa.state.State
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.model.IBakedModel
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.texture.AtlasTexture
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.item.ItemStack
import org.lwjgl.opengl.GL11

class  UIItemStack  @JvmOverloads constructor(
    var itemStack: ItemStack,
    val padding: Float = 0f
) : UIComponent() {

    private val countState: State<String> = BasicState("")
    private val overlay: UIText = UIText(countState)

    var drawOverlay: Boolean = false
        set(value) {
            field = value
            if (isInitialized) {
                if (drawOverlay) {
                    if (!overlay.parent.children.contains(overlay)) {
                        overlay.unhide(false)
                    }
                } else {
                    if (overlay.parent.children.contains(overlay)) {
                        overlay.hide()
                    }
                }
            }
        }

    init {
        setWidth(16f.pixel)
        setHeight(16f.pixel)

        countState.onSetValue {
            if (it.length > 2) {
                overlay.setTextScale(PixelConstraint(0.5f))
            } else {
                overlay.setTextScale(PixelConstraint(1f))
            }
        }

        overlay
            .setTextScale(PixelConstraint(1f))
            .setX(PixelConstraint(0f, true))
            .setY(PixelConstraint(-1f, true))
        overlay.parent = this

        setCount(itemStack.count)
    }

    fun setCount(count: Int) = apply {
        countState.set(if (count > 0) count.toString() else "")
    }

    fun withTooltip() = apply {
        enableEffect(ItemTooltipEffect(::itemStack))
    }

    fun withOverlay() = apply {
        drawOverlay = true
    }

    override fun afterInitialization() {
        super.afterInitialization()
        //init
        drawOverlay = drawOverlay
    }

    @Suppress("DEPRECATION")
    override fun draw(matrixStack: UMatrixStack) {
        beforeDrawCompat(matrixStack)

        val color = this.getColor()
        if (color.alpha == 0) {
            return super.draw(matrixStack)
        }

        val x = this.getLeft() + padding
        val y = this.getTop() + padding
        val width = this.getWidth() - padding * 2
        val height = this.getHeight() - padding * 2

        val ir = Minecraft.getInstance().itemRenderer
        val tm = Minecraft.getInstance().getTextureManager()
        val model: IBakedModel = ir.getItemModelWithOverrides(itemStack, null, null)
        matrixStack.push()

        //TODO: временное решение бага с рендером предметов
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, false)
        matrixStack.translate(0f, 0f, -100f)

        tm.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE)
        tm.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE)?.setBlurMipmapDirect(false, false)
        RenderSystem.enableRescaleNormal()
        RenderSystem.enableAlphaTest()
        RenderSystem.defaultAlphaFunc()
        RenderSystem.enableBlend()
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        matrixStack.translate(x, y, 0f)
        matrixStack.translate(width / 2f, height / 2f, 0f)
        matrixStack.scale(1.0f, -1.0f, 1.0f)
        matrixStack.scale(width, height, 1f)
        val buffer: IRenderTypeBuffer.Impl = Minecraft.getInstance().renderTypeBuffers.bufferSource
        val isSileLit: Boolean = !model.isSideLit
        if (isSileLit) {
            RenderHelper.setupGuiFlatDiffuseLighting()
        }

        ir.renderItem(
            itemStack,
            ItemCameraTransforms.TransformType.GUI,
            false,
            matrixStack.toMC(),
            buffer,
            15728880,
            OverlayTexture.NO_OVERLAY,
            model
        )
        buffer.finish()
        RenderSystem.enableDepthTest()
        if (isSileLit) {
            RenderHelper.setupGui3DDiffuseLighting()
        }

        RenderSystem.disableAlphaTest()
        RenderSystem.disableRescaleNormal()
        matrixStack.pop()

        super.draw(matrixStack)
    }
}
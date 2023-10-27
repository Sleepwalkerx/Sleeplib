package com.sleepwalker.sleeplib.elementa.components.image

import com.sleepwalker.sleeplib.gg.essential.universal.UGraphics
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.utils.ReleasedDynamicTexture
import org.lwjgl.opengl.GL11
import java.awt.Color

open class ModalDrawer (
    protected val uOffset: Int,
    protected val vOffset: Int,
    protected val uWidth: Int,
    protected val vHeight: Int
) : SizableDrawer {

    override fun drawTexture(
        matrixStack: UMatrixStack, texture: ReleasedDynamicTexture, color: Color,
        x: Double, y: Double, width: Double, height: Double,
        textureMinFilter: Int, textureMagFilter: Int
    ) {

        matrixStack.push()

        UGraphics.enableBlend()
        UGraphics.enableAlpha()
        matrixStack.scale(1f, 1f, 50f)
        val glId = texture.dynamicGlId
        UGraphics.bindTexture(0, glId)
        val worldRenderer = UGraphics.getFromTessellator()
        UGraphics.configureTexture(glId) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, textureMinFilter)
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, textureMagFilter)
        }

        drawTextureImpl(worldRenderer, matrixStack, texture, color, x, y, height, width)

        matrixStack.pop()
    }

    override fun getWidth(): Int {
        return uWidth
    }

    override fun getHeight(): Int {
        return vHeight
    }

    protected open fun drawTextureImpl(
        graphic: UGraphics, ms: UMatrixStack, texture: ReleasedDynamicTexture,
        color: Color, x: Double, y: Double, height: Double,
        width: Double
    ){

        val red = color.red.toFloat() / 255f
        val green = color.green.toFloat() / 255f
        val blue = color.blue.toFloat() / 255f
        val alpha = color.alpha.toFloat() / 255f

        drawQuard(graphic, ms, texture, x, y, width, height, red, green, blue, alpha, uOffset, vOffset, uWidth, vHeight)
    }

    protected fun drawQuard(
        graphic: UGraphics, ms: UMatrixStack, texture: ReleasedDynamicTexture,
        x: Double, y: Double, width: Double, height: Double,
        red: Float, green: Float, blue: Float, alpha: Float,
        uOffset: Int, vOffset: Int, uWidth: Int, vHeight: Int
    ){
        drawQuard(graphic, ms, texture.width, texture.height, x, y, width, height, red, green, blue, alpha, uOffset, vOffset, uWidth, vHeight)
    }

    protected fun drawQuard(
        graphic: UGraphics, ms: UMatrixStack, textureWidth: Int, textureHeight: Int,
        x: Double, y: Double, width: Double, height: Double,
        red: Float, green: Float, blue: Float, alpha: Float,
        uOffset: Int, vOffset: Int, uWidth: Int, vHeight: Int
    ){

        val u0 = uOffset.toDouble() / textureWidth
        val v0 = vOffset.toDouble() / textureHeight
        val u1 = (uOffset.toDouble() + uWidth) / textureWidth
        val v1 = (vOffset.toDouble() + vHeight) / textureHeight

        graphic.beginWithDefaultShader(UGraphics.DrawMode.QUADS, UGraphics.CommonVertexFormats.POSITION_TEXTURE_COLOR)
        graphic.pos(ms, x, y + height, 0.0).tex(u0, v1).color(red, green, blue, alpha).endVertex()
        graphic.pos(ms, x + width, y + height, 0.0).tex(u1, v1).color(red, green, blue, alpha).endVertex()
        graphic.pos(ms, x + width, y, 0.0).tex(u1, v0).color(red, green, blue, alpha).endVertex()
        graphic.pos(ms, x, y, 0.0).tex(u0, v0).color(red, green, blue, alpha).endVertex()
        graphic.drawDirect()
    }
}
package com.sleepwalker.sleeplib.elementa.drawable

import com.sleepwalker.sleeplib.gg.essential.universal.UGraphics
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import org.lwjgl.opengl.GL11
import java.awt.Color

open class ModalSprite(
    val texture: TextureSource,
    val uOffset: Int,
    val vOffset: Int,
    val uWidth: Int,
    val vHeight: Int
) : Drawable {

    constructor(texture: TextureSource, uWidth: Int, vHeight: Int) : this(texture, 0, 0, uWidth, vHeight)
    constructor(texture: TextureSource) : this(texture, texture.width, texture.height)

    override val width: Int
        get() = uWidth
    override val height: Int
        get() = vHeight

    override fun drawImage(matrixStack: UMatrixStack, x: Double, y: Double, width: Double, height: Double, color: Color) {
        texture.bind()
        blit(matrixStack, color, x, y, 0.0, uOffset.toDouble(), vOffset.toDouble(), uWidth, vHeight)
    }

    protected fun blit(ms: UMatrixStack, color: Color, pX: Double, pY: Double, zLevel: Double, pUOffset: Double, pVOffset: Double, pUWidth: Int, pVHeight: Int) {
        innerBlit(ms, color, pX, pX + pUWidth, pY, pY + pVHeight, zLevel, pUWidth, pVHeight, pUOffset, pVOffset, texture.width, texture.height)
    }

    companion object {

        fun blit(ms: UMatrixStack, color: Color, pX: Double, pY: Double, zLevel: Double, pUOffset: Double, pVOffset: Double, pUWidth: Int, pVHeight: Int, pTextureWidth: Int, pTextureHeight: Int) {
            innerBlit(ms, color, pX, pX + pUWidth, pY, pY + pVHeight, zLevel, pUWidth, pVHeight, pUOffset, pVOffset, pTextureWidth, pTextureHeight)
        }

        fun blit(ms: UMatrixStack, color: Color, x: Double, y: Double, width: Double, height: Double, uOffset: Double, vOffset: Double, uWidth: Int, vHeight: Int, textureWidth: Int, textureHeight: Int) {
            innerBlit(ms, color, x, x + width, y, y + height, 0.0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight)
        }

        fun innerBlit(ms: UMatrixStack, color: Color, x1: Double, x2: Double, y1: Double, y2: Double, z: Double, pUWidth: Int, pVHeight: Int, pUOffset: Double, pVOffset: Double, pTextureWidth: Int, pTextureHeight: Int) {
            innerBlit(ms, color, x1, x2, y1, y2, z,
                (pUOffset + 0.0f) / pTextureWidth.toDouble(),
                (pUOffset + pUWidth.toDouble()) / pTextureWidth.toDouble(),
                (pVOffset + 0.0f) / pTextureHeight.toDouble(),
                (pVOffset + pVHeight.toDouble()) / pTextureHeight.toDouble()
            )
        }

        fun innerBlit(ms: UMatrixStack, color: Color, x1: Double, x2: Double, y1: Double, y2: Double, z: Double, minU: Double, maxU: Double, minV: Double, maxV: Double) {
            val r = color.red.toFloat() / 255f
            val g = color.green.toFloat() / 255f
            val b = color.blue.toFloat() / 255f
            val a = color.alpha.toFloat() / 255f

            val worldRenderer = UGraphics.getFromTessellator()
            worldRenderer.beginWithDefaultShader(UGraphics.DrawMode.QUADS, UGraphics.CommonVertexFormats.POSITION_TEXTURE_COLOR)
            worldRenderer.pos(ms, x1, y2, z).tex(minU, maxV).color(r, g, b, a).endVertex()
            worldRenderer.pos(ms, x2, y2, z).tex(maxU, maxV).color(r, g, b, a).endVertex()
            worldRenderer.pos(ms, x2, y1, z).tex(maxU, minV).color(r, g, b, a).endVertex()
            worldRenderer.pos(ms, x1, y1, z).tex(minU, minV).color(r, g, b, a).endVertex()

            UGraphics.enableBlend()
            UGraphics.enableAlpha()
            UGraphics.enableDepth()
            UGraphics.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO)
            worldRenderer.drawDirect()
        }
    }
}
package com.sleepwalker.sleeplib.gg.essential.elementa.components

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixels
import com.sleepwalker.sleeplib.gg.essential.elementa.utils.readFromLegacyShader
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.shader.BlendState
import com.sleepwalker.sleeplib.gg.essential.universal.shader.Float4Uniform
import com.sleepwalker.sleeplib.gg.essential.universal.shader.FloatUniform
import com.sleepwalker.sleeplib.gg.essential.universal.shader.UShader
import java.awt.Color

/**
 * Alternative to [UIBlock] with rounded corners.
 *
 * @param radius corner radius.
 */
open class UIRoundedRectangle(radius: Float) : UIComponent() {
    init {
        setRadius(radius.pixels())
    }

    override fun draw(matrixStack: UMatrixStack) {
        beforeDrawCompat(matrixStack)

        val radius = getRadius()

        val color = getColor()
        if (color.alpha != 0)
            drawRoundedRectangle(matrixStack, getLeft(), getTop(), getRight(), getBottom(), radius, color)

        super.draw(matrixStack)
    }

    companion object {
        private lateinit var shader: UShader
        private lateinit var shaderRadiusUniform: FloatUniform
        private lateinit var shaderInnerRectUniform: Float4Uniform

        fun initShaders() {
            if (Companion::shader.isInitialized)
                return

            shader = UShader.readFromLegacyShader("rect", "rounded_rect", BlendState.NORMAL)
            if (!shader.usable) {
                println("Failed to load Elementa UIRoundedRectangle shader")
                return
            }
            shaderRadiusUniform = shader.getFloatUniform("u_Radius")
            shaderInnerRectUniform = shader.getFloat4Uniform("u_InnerRect")
        }

        @Deprecated(
            UMatrixStack.Compat.DEPRECATED,
            ReplaceWith("drawRoundedRectangle(matrixStack, left, top, right, bottom, radius, color)"),
        )
        fun drawRoundedRectangle(left: Float, top: Float, right: Float, bottom: Float, radius: Float, color: Color) =
            drawRoundedRectangle(UMatrixStack(), left, top, right, bottom, radius, color)

        /**
         * Draws a rounded rectangle
         */
        fun drawRoundedRectangle(matrixStack: UMatrixStack, left: Float, top: Float, right: Float, bottom: Float, radius: Float, color: Color) {
            if (!Companion::shader.isInitialized || !shader.usable)
                return

            shader.bind()
            shaderRadiusUniform.setValue(radius)
            shaderInnerRectUniform.setValue(left + radius, top + radius, right - radius, bottom - radius)

            UIBlock.drawBlockWithActiveShader(
                matrixStack,
                color,
                left.toDouble(),
                top.toDouble(),
                right.toDouble(),
                bottom.toDouble()
            )

            shader.unbind()
        }
    }
}

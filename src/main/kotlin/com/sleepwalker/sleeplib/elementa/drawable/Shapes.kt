package com.sleepwalker.sleeplib.elementa.drawable

import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIBlock
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import java.awt.Color

object Shapes {

    val HOLLOW_BLOCK = hollowBlock(1f)

    fun hollowBlock(thickness: Float) = object : Shape {
        override fun drawImage(
            matrixStack: UMatrixStack,
            x: Double,
            y: Double,
            width: Double,
            height: Double,
            color: Color
        ) {
            val left: Double = x
            val right: Double = x + width
            val top: Double = y
            val bottom: Double = y + height
            UIBlock.drawBlock(matrixStack, color, left, top, right, (top + thickness))
            UIBlock.drawBlock(matrixStack, color, left, (bottom - thickness), right, bottom)
            UIBlock.drawBlock(matrixStack, color, left, (top + thickness), (left + thickness), (bottom - thickness))
            UIBlock.drawBlock(matrixStack, color, (right - thickness), (top + thickness), right, (bottom - thickness))
        }
    }
}
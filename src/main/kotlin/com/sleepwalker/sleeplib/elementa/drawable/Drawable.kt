package com.sleepwalker.sleeplib.elementa.drawable

import com.sleepwalker.sleeplib.gg.essential.elementa.components.image.ImageProvider
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import java.awt.Color

interface Drawable : ImageProvider {
    val width: Int
    val height: Int

    override fun drawImage(matrixStack: UMatrixStack, x: Double, y: Double, width: Double, height: Double, color: Color)

    companion object {
        val EMPTY = object : Drawable {
            override val width: Int
                get() = 0
            override val height: Int
                get() = 0

            override fun drawImage(matrixStack: UMatrixStack, x: Double, y: Double, width: Double, height: Double, color: Color) {}
        }
    }
}
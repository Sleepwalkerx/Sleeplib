package com.sleepwalker.sleeplib.elementa.drawable

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import java.awt.Color

typealias Drawer = (UMatrixStack, Double, Double, Double, Double, Color) -> Unit

class ComplexSprite(override val width: Int, override val height: Int, private val drawer: Drawer) : Drawable {

    constructor(origin: Drawable, drawer: Drawer) : this(origin.width, origin.height, drawer)

    override fun drawImage(matrixStack: UMatrixStack, x: Double, y: Double, width: Double, height: Double, color: Color) {
        drawer(matrixStack, x, y, width, height, color)
    }
}
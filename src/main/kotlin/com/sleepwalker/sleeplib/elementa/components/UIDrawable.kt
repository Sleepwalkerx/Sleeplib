package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Drawable
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.image.ImageProvider
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixel
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import java.awt.Color

open class UIDrawable(
    override var drawable : Drawable
) : UIComponent(), ImageProvider, DrawableProvider {

    init {
        setWidth(drawable.width.pixel)
        setHeight(drawable.height.pixel)
    }

    override fun drawImage(matrixStack: UMatrixStack, x: Double, y: Double, width: Double, height: Double, color: Color) {
        drawable.drawImage(matrixStack, x, y, width, height, color)
    }

    override fun draw(matrixStack: UMatrixStack) {
        beforeDrawCompat(matrixStack)

        val x = this.getLeft().toDouble()
        val y = this.getTop().toDouble()
        val width = this.getWidth().toDouble()
        val height = this.getHeight().toDouble()
        val color = this.getColor()

        if (color.alpha == 0) {
            return super.draw(matrixStack)
        }

        drawImage(matrixStack, x, y, width, height, color)

        super.draw(matrixStack)
    }
}
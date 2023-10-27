package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.client.renderer.SLItemRenderer
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.item.ItemStack

open class UIItemStack(
     var itemStack: ItemStack
) : UIComponent() {

    override fun draw(matrixStack: UMatrixStack) {
        beforeDrawCompat(matrixStack)

        val color = this.getColor()
        if (color.alpha == 0) {
            return super.draw(matrixStack)
        }

        val x = this.getLeft()
        val y = this.getTop()
        val width = this.getWidth()
        val height = this.getHeight()
        SLItemRenderer.get().drawGuiItemStack(itemStack, x, y, width, height, color)
        super.draw(matrixStack)
    }
}
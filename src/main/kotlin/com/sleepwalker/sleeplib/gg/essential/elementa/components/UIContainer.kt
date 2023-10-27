package com.sleepwalker.sleeplib.gg.essential.elementa.components

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack

/**
 * Bare-bones component that does no rendering and simply offers a bounding box.
 */
open class UIContainer : UIComponent() {
    override fun draw(matrixStack: UMatrixStack) {
        // This is necessary because if it isn't here, effects will never be applied.
        beforeDrawCompat(matrixStack)

        // no-op

        super.draw(matrixStack)
    }
}
package com.sleepwalker.sleeplib.gg.essential.elementa.components.inspector

import com.sleepwalker.sleeplib.gg.essential.elementa.components.TreeArrowComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIImage
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.CenterConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.childOf
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.constrain
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixels
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack

class ArrowComponent(private val empty: Boolean) : TreeArrowComponent() {
    private val closedIcon = UIImage.ofResourceCached("/textures/inspector/square_plus.png").constrain {
        width = 7.pixels
        height = 7.pixels
        x = CenterConstraint()
        y = CenterConstraint()
    }
    private val openIcon = UIImage.ofResourceCached("/textures/inspector/square_minus.png").constrain {
        width = 7.pixels
        height = 7.pixels
        x = CenterConstraint()
        y = CenterConstraint()
    }

    init {
        constrain {
            width = 10.pixels
            height = 10.pixels
        }

        if (!empty)
            closedIcon childOf this
    }

    override fun open() {
        if (!empty)
            replaceChild(openIcon, closedIcon)
    }

    override fun close() {
        if (!empty)
            replaceChild(closedIcon, openIcon)
    }

    override fun draw(matrixStack: UMatrixStack) {
        beforeDraw(matrixStack)
        super.draw(matrixStack)
    }
}
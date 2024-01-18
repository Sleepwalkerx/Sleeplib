package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIBlock.Companion.drawBlock
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ColorConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.toConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.state.State
import com.sleepwalker.sleeplib.gg.essential.elementa.state.toConstraint
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import java.awt.Color
import javax.annotation.Nonnull

class UIHollowBlock(
    colorConstraint: ColorConstraint = Color.WHITE.toConstraint(),
    var thickness: Float
) : UIComponent() {

    constructor(color: Color, thickness: Float) : this(color.toConstraint(), thickness)

    constructor(colorState: State<Color>, thickness: Float) : this(colorState.toConstraint(), thickness)

    init {
        setColor(colorConstraint)
    }

    override fun draw(@Nonnull matrixStack: UMatrixStack) {
        beforeDrawCompat(matrixStack)
        val color: Color = getColor()
        val left: Float = getLeft()
        val right: Float = getRight()
        val top: Float = getTop()
        val bottom: Float = getBottom()
        drawBlock(matrixStack, color, left.toDouble(), top.toDouble(), right.toDouble(), (top + thickness).toDouble())
        drawBlock(matrixStack, color, left.toDouble(), (bottom - thickness).toDouble(), right.toDouble(), bottom.toDouble())
        drawBlock(
            matrixStack,
            color,
            left.toDouble(),
            (top + thickness).toDouble(),
            (left + thickness).toDouble(),
            (bottom - thickness).toDouble()
        )
        drawBlock(
            matrixStack,
            color,
            (right - thickness).toDouble(),
            (top + thickness).toDouble(),
            right.toDouble(),
            (bottom - thickness).toDouble()
        )
        super.draw(matrixStack)
    }
}
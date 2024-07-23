package com.sleepwalker.sleeplib.elementa.constraints

import com.sleepwalker.sleeplib.elementa.components.DrawableProvider
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.HeightConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.WidthConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.resolution.ConstraintVisitor

class SizeDrawableConstraint : WidthConstraint, HeightConstraint {
    override var cachedValue = 0f
    override var recalculate = true
    override var constrainTo: UIComponent? = null

    override fun getWidthImpl(component: UIComponent): Float {
        val target = constrainTo ?: component
        val ui = target as? DrawableProvider ?: throw IllegalStateException("SizeDrawableConstraint can only be used in DrawableProvider components")
        return ui.drawable.width.toFloat()
    }

    override fun getHeightImpl(component: UIComponent): Float {
        val target = constrainTo ?: component
        val ui = target as? DrawableProvider ?: throw IllegalStateException("SizeDrawableConstraint can only be used in DrawableProvider components")
        return ui.drawable.height.toFloat()
    }

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) {
        when (type) {
            ConstraintType.WIDTH -> visitor.visitSelf(ConstraintType.HEIGHT)
            ConstraintType.HEIGHT -> visitor.visitSelf(ConstraintType.WIDTH)
            else -> throw IllegalArgumentException(type.prettyName)
        }
    }
}
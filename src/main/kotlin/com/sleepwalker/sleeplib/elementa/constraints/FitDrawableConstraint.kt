package com.sleepwalker.sleeplib.elementa.constraints

import com.sleepwalker.sleeplib.elementa.components.DrawableProvider
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.HeightConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.WidthConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.resolution.ConstraintVisitor
import kotlin.math.max

class FitDrawableConstraint : WidthConstraint, HeightConstraint {
    override var cachedValue = 0f
    override var recalculate = true
    override var constrainTo: UIComponent? = null

    override fun getWidthImpl(component: UIComponent): Float {
        val target = constrainTo ?: component.parent
        val provider = component as? DrawableProvider ?: throw IllegalStateException("DrawableAspectConstraint can only be used in DrawableProvider components")
        return provider.drawable.width.toFloat() * getFactor(provider, target)
    }

    override fun getHeightImpl(component: UIComponent): Float {
        val target = constrainTo ?: component.parent
        val provider = component as? DrawableProvider ?: throw IllegalStateException("DrawableAspectConstraint can only be used in DrawableProvider components")
        return provider.drawable.height.toFloat() * getFactor(provider, target)
    }

    private fun getFactor(provider: DrawableProvider, target: UIComponent): Float {
        val drawable = provider.drawable
        return if (drawable.width == 0 || drawable.height == 0){
            0f
        } else {
            max(target.getWidth() / provider.drawable.width.toFloat(), target.getHeight() / provider.drawable.height.toFloat())
        }
    }

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) {
        when (type) {
            ConstraintType.WIDTH -> visitor.visitSelf(ConstraintType.HEIGHT)
            ConstraintType.HEIGHT -> visitor.visitSelf(ConstraintType.WIDTH)
            else -> throw IllegalArgumentException(type.prettyName)
        }
    }
}
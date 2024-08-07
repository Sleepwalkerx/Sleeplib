package com.sleepwalker.sleeplib.gg.essential.elementa.constraints.debug

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.HeightConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.RadiusConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.SuperConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.WidthConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.XConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.YConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.utils.roundToRealPixels

internal interface ConstraintDebugger {
    fun evaluate(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float

    fun invokeImpl(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float =
        when (type) {
            ConstraintType.X -> (constraint as XConstraint).getXPositionImpl(component).roundToRealPixels()
            ConstraintType.Y -> (constraint as YConstraint).getYPositionImpl(component).roundToRealPixels()
            ConstraintType.WIDTH -> (constraint as WidthConstraint).getWidthImpl(component).roundToRealPixels()
            ConstraintType.HEIGHT -> (constraint as HeightConstraint).getHeightImpl(component).roundToRealPixels()
            ConstraintType.RADIUS -> (constraint as RadiusConstraint).getRadiusImpl(component)
            else -> throw UnsupportedOperationException()
        }
}

internal var constraintDebugger: ConstraintDebugger? = null

internal inline fun withDebugger(debugger: ConstraintDebugger, block: () -> Unit) {
    val prevDebugger = constraintDebugger
    constraintDebugger = debugger
    try {
        block()
    } finally {
        constraintDebugger = prevDebugger
    }
}

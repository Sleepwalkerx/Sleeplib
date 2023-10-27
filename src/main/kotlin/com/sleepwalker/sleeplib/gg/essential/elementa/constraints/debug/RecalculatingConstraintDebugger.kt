package com.sleepwalker.sleeplib.gg.essential.elementa.constraints.debug

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.SuperConstraint

internal class RecalculatingConstraintDebugger(
    private val inner: ConstraintDebugger = NoopConstraintDebugger(),
) : ConstraintDebugger {
    private val visited = mutableSetOf<SuperConstraint<*>>()

    override fun evaluate(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float {
        if (visited.add(constraint)) {
            constraint.recalculate = true
        }
        return inner.evaluate(constraint, type, component)
    }
}
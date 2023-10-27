package com.sleepwalker.sleeplib.gg.essential.elementa.constraints.debug

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.SuperConstraint

internal class NoopConstraintDebugger : ConstraintDebugger {
    override fun evaluate(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float {
        if (constraint.recalculate) {
            constraint.cachedValue = invokeImpl(constraint, type, component)
            constraint.recalculate = false
        }

        return constraint.cachedValue
    }
}

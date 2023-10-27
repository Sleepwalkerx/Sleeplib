package com.sleepwalker.sleeplib.gg.essential.elementa.constraints.resolution

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.SuperConstraint

data class ResolverNode(
    val component: UIComponent,
    val constraint: SuperConstraint<*>,
    val constraintType: ConstraintType
)
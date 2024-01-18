package com.sleepwalker.sleeplib.elementa.transition

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.animation.AnimatingConstraints
import com.sleepwalker.sleeplib.gg.essential.elementa.transitions.Transition

class HideTransition : Transition() {

    override fun doTransition(component: UIComponent, constraints: AnimatingConstraints) {}

    override fun afterTransition(component: UIComponent) {
        component.hide(true)
    }
}
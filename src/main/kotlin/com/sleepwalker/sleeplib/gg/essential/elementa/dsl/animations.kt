package com.sleepwalker.sleeplib.gg.essential.elementa.dsl

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.animation.AnimatingConstraints

/**
 * Wrapper around [UIComponent.makeAnimation] and [UIComponent.animateTo],
 * providing a handy dandy DSL.
 */
inline fun <T : UIComponent> T.animate(animation: AnimatingConstraints.() -> Unit) = apply {
    val anim = this.makeAnimation()
    anim.animation()
    this.animateTo(anim)
}

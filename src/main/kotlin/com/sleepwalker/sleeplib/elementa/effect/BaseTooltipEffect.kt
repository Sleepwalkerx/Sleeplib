package com.sleepwalker.sleeplib.elementa.effect

import com.sleepwalker.sleeplib.elementa.components.ComponentDrawable
import com.sleepwalker.sleeplib.gg.essential.elementa.components.Window
import com.sleepwalker.sleeplib.gg.essential.elementa.effects.Effect

abstract class BaseTooltipEffect : Effect(), ComponentDrawable {

    override fun animationFrame() {
        if(boundComponent.isHovered() && boundComponent.isActive()){
            Window.ofOrNull(boundComponent)?.let {
                it.tooltip = this
            }
        }
    }
}
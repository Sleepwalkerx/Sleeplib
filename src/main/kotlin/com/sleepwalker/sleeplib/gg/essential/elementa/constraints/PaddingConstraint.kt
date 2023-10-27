package com.sleepwalker.sleeplib.gg.essential.elementa.constraints

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent

interface PaddingConstraint {

    fun getVerticalPadding(component: UIComponent): Float

    fun getHorizontalPadding(component: UIComponent) : Float
}
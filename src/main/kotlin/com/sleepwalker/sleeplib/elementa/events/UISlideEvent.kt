package com.sleepwalker.sleeplib.elementa.events

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.events.UIEvent

data class UISlideEvent(
    val value: Float,
    val xValue: Float,
    val yValue: Float,
    val target: UIComponent,
    val currentTarget: UIComponent
) : UIEvent()

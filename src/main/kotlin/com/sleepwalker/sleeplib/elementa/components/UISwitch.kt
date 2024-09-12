package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Drawables
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.AspectConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.PixelConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.childOf
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.constrain
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixel

class UISwitch : UIButton(
    Drawables.SWITCH_BACKGROUND_ENABLED,
    Drawables.SWITCH_BACKGROUND_FOCUSED,
    Drawables.SWITCH_BACKGROUND_ENABLED,
    Drawables.SWITCH_BACKGROUND_ENABLED
) {

    private val button: UIDrawable = UIDrawable(Drawables.SWITCH_BUTTON_ENABLED).constrain {
        x = PixelConstraint(0f, true)
        y = PixelConstraint(0f, true)
        width = AspectConstraint()
        height = 12.pixel
    } childOf this

    private val valueListeners = mutableListOf<(UISwitch) -> Unit>()

    var value = true
        set(value) {
            field = value
            button.drawable = if(value) Drawables.SWITCH_BUTTON_ENABLED else Drawables.SWITCH_BUTTON_DISABLED
            button.setX(if(value) PixelConstraint(0f, true) else 0.pixel)
        }

    init {
        onMouseClick {
            value = !value
            for (listener in valueListeners){
                listener(this@UISwitch)
            }
        }
    }

    fun onValueChanged(listener: (UISwitch) -> Unit) = apply {
        valueListeners.add(listener)
    }
}
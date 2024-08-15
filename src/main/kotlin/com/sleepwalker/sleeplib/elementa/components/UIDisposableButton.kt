package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.style.Style
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIContainer
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.CenterConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.RelativeConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.childOf
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.constrain
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.minus
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixel

class UIDisposableButton(style: Style, text: String) : UITextButton(style, UIText(text).constrain {
    x = CenterConstraint()
    y = CenterConstraint()
}) {

    init {
        val panel = UIContainer().constrain {
            width = RelativeConstraint()
            height = RelativeConstraint() - 3.pixel
        } childOf this
        onMouseClick { panel.setY(1.pixel) }
        onMouseRelease { panel.setY(0.pixel) }
        super.text childOf panel
    }
}
package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Drawable
import com.sleepwalker.sleeplib.elementa.style.*
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.CenterConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.childOf
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.constrain

open class UITextButton: UIButton {

    val text: UIText

    constructor(
        enabledDrawable: Drawable,
        focusedDrawable: Drawable,
        pressedDrawable: Drawable,
        disabledDrawable: Drawable,
        text: UIText
    ) : super(enabledDrawable, focusedDrawable, pressedDrawable, disabledDrawable) {
        this.text = text
    }

    constructor(style: Style, text: UIText) : super(style){
        this.text = text
    }

    constructor(style: Style, text: String) : super(style){
        this.text = UIText(text).constrain {
            x = CenterConstraint()
            y = CenterConstraint()
        } childOf this
    }
}
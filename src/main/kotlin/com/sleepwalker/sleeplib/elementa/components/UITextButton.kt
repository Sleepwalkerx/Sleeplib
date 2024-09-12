package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Drawable
import com.sleepwalker.sleeplib.elementa.style.*
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.CenterConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.WidthConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.*

open class UITextButton : UIButton {

    val text: UIText
    val padding: WidthConstraint

    constructor(
        enabledDrawable: Drawable,
        focusedDrawable: Drawable,
        pressedDrawable: Drawable,
        disabledDrawable: Drawable,
        text: UIText,
        padding: WidthConstraint = 8.pixel
    ) : super(enabledDrawable, focusedDrawable, pressedDrawable, disabledDrawable) {
        this.text = text
        this.padding = padding
    }

    constructor(style: Style, text: UIText, padding: WidthConstraint = 8.pixel) : super(style){
        this.text = text
        this.padding = padding
    }

    constructor(style: Style, text: String, padding: WidthConstraint = 8.pixel) : super(style){
        this.text = UIText(text).constrain {
            x = CenterConstraint()
            y = CenterConstraint()
        } childOf this
        this.padding = padding
    }

    override fun afterInitialization() {
        super.afterInitialization()
        text.setWidth(min(text.constraints.width, constraints.width - padding))
        text.constraints.y.recalculate = true
    }
}
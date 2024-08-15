package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Drawable
import com.sleepwalker.sleeplib.elementa.style.*
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText

open class UITextButton(
    enabledDrawable: Drawable,
    focusedDrawable: Drawable,
    pressedDrawable: Drawable,
    disabledDrawable: Drawable,
    val text: UIText
) : UIButton(enabledDrawable, focusedDrawable, pressedDrawable, disabledDrawable) {

    constructor(style: Style, text: UIText) : this(
        style.getValueOrThrow(ENABLED),
        style.getValueOrThrow(FOCUSED),
        style.getValueOrThrow(PRESSED),
        style.getValueOrThrow(DISABLED),
        text
    )
}
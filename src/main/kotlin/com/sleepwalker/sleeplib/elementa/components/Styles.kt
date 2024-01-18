package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.client.SLSprites
import com.sleepwalker.sleeplib.client.drawable.Drawable

object Styles {

    val VERTICAL_SLIDER = Button(SLSprites.VERTICAL_SLIDER_ENABLED, SLSprites.VERTICAL_SLIDER_FOCUSED, SLSprites.VERTICAL_SLIDER_PRESSED, SLSprites.VERTICAL_SLIDER_DISABLED)
    val DISPOSABLE_BUTTON = Button(SLSprites.DISPOSABLE_BUTTON_ENABLED, SLSprites.DISPOSABLE_BUTTON_FOCUSED, SLSprites.DISPOSABLE_BUTTON_PRESSED, SLSprites.DISPOSABLE_BUTTON_DISABLED)

    data class Button(val enabled: Drawable, val focused: Drawable, val pressed: Drawable, val disabled: Drawable)
}
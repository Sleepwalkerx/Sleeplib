package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Drawables.DISPOSABLE_BUTTON_DISABLED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.DISPOSABLE_BUTTON_ENABLED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.DISPOSABLE_BUTTON_FOCUSED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.DISPOSABLE_BUTTON_PRESSED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.DISPOSABLE_BUTTON_SELECTED_ENABLED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.DISPOSABLE_BUTTON_SELECTED_FOCUSED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.VERTICAL_SLIDER_DISABLED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.VERTICAL_SLIDER_ENABLED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.VERTICAL_SLIDER_FOCUSED
import com.sleepwalker.sleeplib.elementa.drawable.Drawables.VERTICAL_SLIDER_PRESSED
import com.sleepwalker.sleeplib.elementa.style.*

object Styles {
    val DISPOSABLE_SELECTED_BUTTON = Style(
        ENABLED to DISPOSABLE_BUTTON_SELECTED_ENABLED,
        FOCUSED to DISPOSABLE_BUTTON_SELECTED_FOCUSED,
        PRESSED to DISPOSABLE_BUTTON_PRESSED,
        DISABLED to DISPOSABLE_BUTTON_DISABLED
    )
    val DISPOSABLE_BUTTON = Style(
        ENABLED to DISPOSABLE_BUTTON_ENABLED,
        FOCUSED to DISPOSABLE_BUTTON_FOCUSED,
        PRESSED to DISPOSABLE_BUTTON_PRESSED,
        DISABLED to DISPOSABLE_BUTTON_DISABLED
    )
    val VERTICAL_SLIDER = Style(
        ENABLED to VERTICAL_SLIDER_ENABLED,
        FOCUSED to VERTICAL_SLIDER_FOCUSED,
        PRESSED to VERTICAL_SLIDER_PRESSED,
        DISABLED to VERTICAL_SLIDER_DISABLED
    )
}
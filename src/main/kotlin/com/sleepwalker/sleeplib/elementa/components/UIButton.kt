package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Drawable
import com.sleepwalker.sleeplib.elementa.style.*
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.universal.USound
import javax.annotation.Nonnull

open class UIButton(
    var enabledDrawable: Drawable,
    var focusedDrawable: Drawable,
    var pressedDrawable: Drawable,
    var disabledDrawable: Drawable
) : UIDrawable(enabledDrawable) {

    constructor(style: Style) : this(
        style.getValueOrThrow(ENABLED),
        style.getValueOrThrow(FOCUSED),
        style.getValueOrThrow(PRESSED),
        style.getValueOrThrow(DISABLED)
    )

    private var clickSound: UIComponent.() -> Unit = { USound.playButtonPress() }
    private var focused: Boolean = false
    private var pressed: Boolean = false

    init {
        setSmartHoverHandler(true)
        onMouseClick {
            clickSound.invoke(this)
            pressed = true
            updateSprite()
        }
        onMouseRelease {
            pressed = false
            updateSprite()
        }
        onMouseEnter {
            focused = true
            updateSprite()
        }
        onMouseLeave {
            focused = false
            updateSprite()
        }

        updateSprite()

        activeState.onSetValue {
            if (!active) {
                focused = false
                pressed = false
            }
            updateSprite()
        }
    }

    fun setStyle(style: Style) = apply {
        enabledDrawable = style.getValueOrThrow(ENABLED)
        focusedDrawable = style.getValueOrThrow(FOCUSED)
        pressedDrawable = style.getValueOrThrow(PRESSED)
        disabledDrawable = style.getValueOrThrow(DISABLED)
    }

    fun setClickSound(clickSound: UIComponent.() -> Unit) = apply {
        this.clickSound = clickSound
    }

    private fun setDrawableIfAbsent(@Nonnull drawable: Drawable) {
        if (super.drawable !== drawable) {
            super.drawable = drawable
        }
    }

    private fun updateSprite() {
        if (active) {
            if (pressed) {
                setDrawableIfAbsent(pressedDrawable)
            } else {
                setDrawableIfAbsent(if (focused) focusedDrawable else enabledDrawable)
            }
        } else {
            setDrawableIfAbsent(disabledDrawable)
        }
    }
}
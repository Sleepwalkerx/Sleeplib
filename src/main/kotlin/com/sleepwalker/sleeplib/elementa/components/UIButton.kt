package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.client.drawable.Drawable
import com.sleepwalker.sleeplib.client.util.DrawUtil
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.USound
import net.minecraft.item.ItemStack
import javax.annotation.Nonnull

class UIButton(
    private val enabledDrawable: Drawable,
    private val focusedDrawable: Drawable,
    private val pressedDrawable: Drawable,
    private val disabledDrawable: Drawable
) : UIDrawable(enabledDrawable) {

    constructor(style: Styles.Button) : this(style.enabled, style.focused, style.pressed, style.disabled)

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
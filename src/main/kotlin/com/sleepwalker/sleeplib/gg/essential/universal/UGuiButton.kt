package com.sleepwalker.sleeplib.gg.essential.universal

import net.minecraft.client.gui.widget.Widget

object UGuiButton {
    @JvmStatic
    fun getX(button: Widget): Int {
        return button.x
    }

    @JvmStatic
    fun getY(button: Widget): Int {
        return button.y
    }
}

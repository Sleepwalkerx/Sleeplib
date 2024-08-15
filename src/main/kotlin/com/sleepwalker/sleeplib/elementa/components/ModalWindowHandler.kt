package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent

interface ModalWindowHandler {
    fun hasAnyWindow(): Boolean
    fun hasWindow(window: UIComponent): Boolean
    fun pushWindow(window: UIComponent)
    fun replaceWindow(window: UIComponent)
    fun popWindow()
    fun peekWindow(): UIComponent?
    fun clearWindows()
}
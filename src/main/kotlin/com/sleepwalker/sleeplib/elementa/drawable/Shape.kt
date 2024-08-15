package com.sleepwalker.sleeplib.elementa.drawable

interface Shape : Drawable {
    override val width: Int
        get() = 0
    override val height: Int
        get() = 0
}
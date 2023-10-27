package com.sleepwalker.sleeplib.gg.essential.elementa.shaders

import com.sleepwalker.sleeplib.gg.essential.universal.UGraphics

@Deprecated("Use UniversalCraft's UShader instead.")
object Shaders {
    val newShaders: Boolean get() = UGraphics.areShadersSupported()
}

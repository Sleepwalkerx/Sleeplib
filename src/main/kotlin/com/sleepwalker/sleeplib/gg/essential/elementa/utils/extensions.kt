package com.sleepwalker.sleeplib.gg.essential.elementa.utils

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.Window
import com.sleepwalker.sleeplib.gg.essential.universal.UResolution
import com.sleepwalker.sleeplib.gg.essential.universal.shader.BlendState
import com.sleepwalker.sleeplib.gg.essential.universal.shader.UShader
import java.awt.Color
import kotlin.math.round
import kotlin.math.sign

fun Float.guiHint(roundDown: Boolean) = UIComponent.guiHint(this, roundDown)
fun Double.guiHint(roundDown: Boolean) = UIComponent.guiHint(this, roundDown)

fun Float.roundToRealPixels(): Float {
    val factor = UResolution.scaleFactor.toFloat()
    return round(this * factor).let { if (it == 0f && this != 0f) sign(this) else it } / factor
}
fun Double.roundToRealPixels(): Double {
    val factor = UResolution.scaleFactor
    return round(this * factor).let { if (it == 0.0 && this != 0.0) sign(this) else it } / factor
}

fun Color.withAlpha(alpha: Int) = Color(this.red, this.green, this.blue, alpha)
fun Color.withAlpha(alpha: Float) = Color(this.red, this.green, this.blue, (alpha * 255).toInt())
fun Color.invisible() = withAlpha(0)

operator fun Color.component1() = this.red
operator fun Color.component2() = this.green
operator fun Color.component3() = this.blue
operator fun Color.component4() = this.alpha

internal fun UShader.Companion.readFromLegacyShader(vertName: String, fragName: String, blendState: BlendState) =
    fromLegacyShader(readShader(vertName, "vsh"), readShader(fragName, "fsh"), blendState)
private fun readShader(name: String, ext: String) =
    Window::class.java.getResource("/shaders/$name.$ext").readText()

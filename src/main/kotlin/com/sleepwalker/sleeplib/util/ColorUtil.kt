package com.sleepwalker.sleeplib.util

import java.awt.Color
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

fun HSVtoRGB(h: Float, s: Float, v: Float): Triple<Float, Float, Float> {
    var h = h
    h *= 6f
    val m: Float
    val n: Float
    var f: Float
    val i: Int
    val hsv = FloatArray(3)
    val rgb = FloatArray(3)
    hsv[0] = h
    hsv[1] = s
    hsv[2] = v
    if (hsv[0] == -1f) {
        rgb[2] = hsv[2]
        rgb[1] = rgb[2]
        rgb[0] = rgb[1]
        return Triple(rgb[0], rgb[1], rgb[2])
    }
    i = floor(hsv[0].toDouble()).toInt()
    f = hsv[0] - i
    if (i % 2 == 0) {
        f = 1 - f
    }
    m = hsv[2] * (1 - hsv[1])
    n = hsv[2] * (1 - hsv[1] * f)
    when (i) {
        6, 0 -> {
            rgb[0] = hsv[2]
            rgb[1] = n
            rgb[2] = m
        }
        1 -> {
            rgb[0] = n
            rgb[1] = hsv[2]
            rgb[2] = m
        }
        2 -> {
            rgb[0] = m
            rgb[1] = hsv[2]
            rgb[2] = n
        }
        3 -> {
            rgb[0] = m
            rgb[1] = n
            rgb[2] = hsv[2]
        }
        4 -> {
            rgb[0] = n
            rgb[1] = m
            rgb[2] = hsv[2]
        }
        5 -> {
            rgb[0] = hsv[2]
            rgb[1] = m
            rgb[2] = n
        }
    }
    return Triple(rgb[0], rgb[1], rgb[2])
}

fun RGBtoHSV(r: Float, g: Float, b: Float): Triple<Float, Float, Float> {
    var h: Float
    val s: Float
    val v: Float
    val delta: Float
    val min: Float = min(min(r.toDouble(), g.toDouble()), b.toDouble()).toFloat()
    val max: Float = max(max(r.toDouble(), g.toDouble()), b.toDouble()).toFloat()
    v = max
    delta = (max - min)
    if (max != 0f) s = (delta / max) else {
        s = 0f
        h = -1f
        return Triple(h, s, v)
    }
    h = if (r == max) {
        if (delta == 0f) 0f else (g - b) / delta
    } else if (g == max) {
        if (delta == 0f) 0f else (2f + (b - r) / delta)
    } else {
        if (delta == 0f) 0f else (4f + (r - g) / delta)
    }
    h *= 60f
    if (h < 0) h += 360f
    return Triple(h / 360f, s, v)
}

fun Color.toHSV(): Triple<Float, Float, Float> = RGBtoHSV(this.red / 255f, this.green / 255f, this.blue / 255f)

fun Triple<Float, Float, Float>.fromHSV(): Color {
    val (r, g, b) = HSVtoRGB(this.first, this.second, this.third)
    return Color(r, g, b)
}
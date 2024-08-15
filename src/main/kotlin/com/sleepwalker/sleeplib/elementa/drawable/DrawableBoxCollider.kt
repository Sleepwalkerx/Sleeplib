package com.sleepwalker.sleeplib.elementa.drawable

import java.util.*

class DrawableBoxCollider(val points: List<Point>) {

    fun isInside(mX: Double, mY: Double, pX: Double, pY: Double, width: Double, height: Double): Boolean {
        for ((u0, v0, w, h) in points) {
            val pointX = pX + width * u0
            val pointY = pY + height * v0
            if (mX >= pointX && pointX + width * w > mX && mY >= pointY && pointY + height * h > mY) {
                return true
            }
        }
        return false
    }

    data class Point(val u0: Float, val v0: Float, val w: Float, val h: Float) {

        constructor(sprite: Drawable, x: Int, y: Int, w: Int, h: Int) : this(
            x / sprite.width.toFloat(),
            y / sprite.height.toFloat(),
            w / sprite.width.toFloat(),
            h / sprite.height.toFloat()
        )
    }

    class Builder(private val sprite: Drawable) {

        private val points: MutableList<Point> = mutableListOf()

        fun add(x: Int, y: Int, w: Int, h: Int) = apply {
            points.add(Point(sprite, x, y, w, h))
            return this
        }

        fun build(): DrawableBoxCollider {
            return DrawableBoxCollider(Collections.unmodifiableList(points))
        }
    }
}
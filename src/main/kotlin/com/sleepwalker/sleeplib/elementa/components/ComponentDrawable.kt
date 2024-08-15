package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack

interface ComponentDrawable {
    fun draw(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float)
}
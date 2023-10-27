package com.sleepwalker.sleeplib.elementa.components.image

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.utils.ReleasedDynamicTexture
import org.lwjgl.opengl.GL11
import java.awt.Color

interface Drawer {

    fun drawTexture(
        matrixStack: UMatrixStack, texture: ReleasedDynamicTexture, color: Color, x: Double, y: Double,
        width: Double, height: Double, textureMinFilter: Int = GL11.GL_NEAREST, textureMagFilter: Int = GL11.GL_NEAREST
    )
}
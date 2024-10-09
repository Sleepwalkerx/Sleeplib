package com.sleepwalker.sleeplib.elementa.drawable

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import java.awt.Color

class ModalFillSprite(texture: TextureSource, uOffset: Int, vOffset: Int, uWidth: Int, vHeight: Int) :
    ModalSprite(texture, uOffset, vOffset, uWidth, vHeight) {

    constructor(texture: TextureSource, uOffset: Int, vOffset: Int) : this(texture, uOffset, vOffset, texture.width, texture.height)
    constructor(texture: TextureSource) : this(texture, texture.width, texture.height)

    override fun drawImage(matrixStack: UMatrixStack, x: Double, y: Double, width: Double, height: Double, color: Color) {
        texture.bind()
        innerBlit(matrixStack, color, x, x + width, y, y + height, 0.0, uWidth, vHeight, uOffset.toDouble(), vOffset.toDouble(), texture.width, texture.height)
    }
}
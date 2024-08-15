package com.sleepwalker.sleeplib.elementa.drawable

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import java.awt.Color

class NineSliceSprite(
    texture: TextureSource, uOffset: Int, vOffset: Int, uWidth: Int, vHeight: Int,
    val left: Int, val top: Int, val right: Int, val bottom: Int,
) : ModalSprite(texture, uOffset, vOffset, uWidth, vHeight) {

    constructor(texture: TextureSource, uOffset: Int, vOffset: Int, uWidth: Int, vHeight: Int, border: Int) :
            this(texture, uOffset, vOffset, uWidth, vHeight, border, border, border, border)

    override fun drawImage(matrixStack: UMatrixStack, x: Double, y: Double, width: Double, height: Double, color: Color) {
        super.drawImage(matrixStack, x, y, width, height, color)

        texture.bind()
        val fillerWidth = uWidth - left - right
        val fillerHeight = vHeight - top - bottom
        val canvasWidth = (width - left - right).toInt()
        val canvasHeight = (height - top - bottom).toInt()
        val xPasses = canvasWidth / fillerWidth
        val remainderWidth = canvasWidth % fillerWidth
        val yPasses = canvasHeight / fillerHeight
        val remainderHeight = canvasHeight % fillerHeight

        blit(matrixStack, color, x, y, 0.0, uOffset.toDouble(), vOffset.toDouble(), left, top)
        blit(
            matrixStack, color, x + left + canvasWidth, y, 0.0, (left + fillerWidth + uOffset).toDouble(),
            vOffset.toDouble(), right, top
        )
        blit(
            matrixStack, color, x, y + top + canvasHeight, 0.0, uOffset.toDouble(),
            (top + fillerHeight + vOffset).toDouble(), left, bottom
        )
        blit(
            matrixStack, color, x + left + canvasWidth, y + top + canvasHeight, 0.0,
            (left + fillerWidth + uOffset).toDouble(), (top + fillerHeight + vOffset).toDouble(), right, bottom
        )

        for (i in 0 until xPasses + if (remainderWidth > 0) 1 else 0) {
            blit(
                matrixStack, color, x + left + i * fillerWidth, y, 0.0,
                (left + uOffset).toDouble(), vOffset.toDouble(), if (i == xPasses) remainderWidth else fillerWidth, top
            )
            blit(
                matrixStack, color, x + left + i * fillerWidth, y + top + canvasHeight, 0.0,
                (left + uOffset).toDouble(),
                (top + fillerHeight + vOffset).toDouble(), if (i == xPasses) remainderWidth else fillerWidth, bottom
            )
            for (j in 0 until yPasses + if (remainderHeight > 0) 1 else 0) {
                blit(
                    matrixStack,
                    color,
                    x + left + i * fillerWidth,
                    y + top + j * fillerHeight,
                    0.0,
                    (left + uOffset).toDouble(),
                    (top + vOffset).toDouble(),
                    if (i == xPasses) remainderWidth else fillerWidth,
                    if (j == yPasses) remainderHeight else fillerHeight
                )
            }
        }

        for (j in 0 until yPasses + if (remainderHeight > 0) 1 else 0) {
            blit(
                matrixStack,
                color,
                x,
                y + top + j * fillerHeight,
                0.0,
                uOffset.toDouble(),
                (top + vOffset).toDouble(),
                left,
                if (j == yPasses) remainderHeight else fillerHeight
            )
            blit(
                matrixStack,
                color,
                x + left + canvasWidth,
                y + top + j * fillerHeight,
                0.0,
                (left + fillerWidth + uOffset).toDouble(),
                (top + vOffset).toDouble(),
                right,
                if (j == yPasses) remainderHeight else fillerHeight
            )
        }
    }
}
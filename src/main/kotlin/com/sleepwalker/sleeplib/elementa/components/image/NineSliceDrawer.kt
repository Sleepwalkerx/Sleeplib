package com.sleepwalker.sleeplib.elementa.components.image

import com.sleepwalker.sleeplib.gg.essential.universal.UGraphics
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.utils.ReleasedDynamicTexture
import java.awt.Color

class NineSliceDrawer(
    uOffset: Int,
    vOffset: Int,
    uWidth: Int,
    vHeight: Int,
    val left: Int,
    val top: Int,
    val right : Int,
    val bottom : Int
) : ModalDrawer(uOffset, vOffset, uWidth, vHeight) {

    override fun drawTextureImpl(
        graphic: UGraphics, ms: UMatrixStack, texture: ReleasedDynamicTexture,
        color: Color, x: Double, y: Double, height: Double,
        width: Double
    ) {

        val red = color.red.toFloat() / 255f
        val green = color.green.toFloat() / 255f
        val blue = color.blue.toFloat() / 255f
        val alpha = color.alpha.toFloat() / 255f
        
        val fillerWidth = uWidth - left - right
        val fillerHeight = vHeight - top - bottom
        val canvasWidth = width.toInt() - left - right
        val canvasHeight = height.toInt() - top - bottom
        val xPasses = canvasWidth / fillerWidth
        val remainderWidth = canvasWidth % fillerWidth
        val yPasses = canvasHeight / fillerHeight
        val remainderHeight = canvasHeight % fillerHeight

        drawQuard(graphic, ms, texture, red, green, blue, alpha, x, y, uOffset, vOffset, left, top)
        drawQuard(
            graphic, ms, texture, red, green, blue, alpha,
            x + left + canvasWidth, y, left + fillerWidth + uOffset, vOffset, right, top
        )
        drawQuard(
            graphic, ms, texture, red, green, blue, alpha,
            x, y + top + canvasHeight, uOffset, top + fillerHeight + vOffset, left, bottom
        )
        drawQuard(
            graphic, ms, texture, red, green, blue, alpha,
            x + left + canvasWidth, y + top + canvasHeight, left + fillerWidth + uOffset, top + fillerHeight + vOffset, right, bottom
        )

        var i = 0
        while (i < xPasses + if(remainderWidth > 0) 1 else 0) {

            drawQuard(
                graphic, ms, texture, red, green, blue, alpha,
                x + left + (i * fillerWidth), y, left + uOffset, vOffset, if(i == xPasses) remainderWidth else fillerWidth, top
            )
            drawQuard(
                graphic, ms, texture, red, green, blue, alpha,
                x + left + (i * fillerWidth), y + top + canvasHeight, left + uOffset, top + fillerHeight + vOffset, if(i == xPasses) remainderWidth else fillerWidth, bottom
            )

            var j = 0
            while (j < yPasses + if(remainderHeight > 0) 1 else 0) {
                drawQuard(
                    graphic, ms, texture, red, green, blue, alpha,
                    x + left + (i * fillerWidth), y + top + (j * fillerHeight), left + uOffset, top + vOffset,
                    if(i == xPasses) remainderWidth else fillerWidth,
                    if(j == yPasses) remainderHeight else fillerHeight
                )
                j++
            }

            i++
        }

        i = 0
        while (i < yPasses + if(remainderHeight > 0) 1 else 0) {

            drawQuard(
                graphic, ms, texture, red, green, blue, alpha,
                x, y + top + (i * fillerHeight), uOffset, top + vOffset, left, if(i == yPasses) remainderHeight else fillerHeight
            )
            drawQuard(
                graphic, ms, texture, red, green, blue, alpha,
                x + left + canvasWidth, y + top + (i * fillerHeight), left + fillerWidth + uOffset, top + vOffset,
                right, if(i == yPasses) remainderHeight else fillerHeight
            )

            i++
        }
    }

    private fun drawQuard(
        graphic: UGraphics, ms: UMatrixStack, texture: ReleasedDynamicTexture,
        red: Float, green: Float,
        blue: Float, alpha: Float, x: Double, y: Double,
        uOffset: Int, vOffset: Int, uWidth: Int, vHeight: Int
    ){
        drawQuard(
            graphic, ms, texture,
            x, y,
            uWidth.toDouble(), vHeight.toDouble(),
            red, green, blue, alpha,
            uOffset, vOffset, uWidth, vHeight
        )
    }
}
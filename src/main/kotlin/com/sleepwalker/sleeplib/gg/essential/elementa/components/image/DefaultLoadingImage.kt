package com.sleepwalker.sleeplib.gg.essential.elementa.components.image

import com.sleepwalker.sleeplib.gg.essential.elementa.utils.drawTexture
import com.sleepwalker.sleeplib.gg.essential.universal.UGraphics
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.utils.ReleasedDynamicTexture
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object DefaultLoadingImage : ImageProvider {
    private var loadingImage: BufferedImage? = ImageIO.read(this::class.java.getResourceAsStream("/loading.png"))
    private lateinit var loadingTexture: ReleasedDynamicTexture

    override fun drawImage(
        matrixStack: UMatrixStack,
        x: Double,
        y: Double,
        width: Double,
        height: Double,
        color: Color,
    ) {
        if (!DefaultLoadingImage::loadingTexture.isInitialized) {
            loadingTexture = UGraphics.getTexture(loadingImage!!)
            loadingImage = null
        }

        drawTexture(
            matrixStack,
            loadingTexture,
            color,
            x,
            y,
            width,
            height,
            textureMinFilter = GL11.GL_LINEAR,
            textureMagFilter = GL11.GL_LINEAR,
        )
    }
}

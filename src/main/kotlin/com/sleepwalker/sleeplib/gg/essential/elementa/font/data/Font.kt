package com.sleepwalker.sleeplib.gg.essential.elementa.font.data

import com.sleepwalker.sleeplib.gg.essential.universal.UGraphics
import com.sleepwalker.sleeplib.gg.essential.universal.utils.ReleasedDynamicTexture
import com.google.gson.JsonParser
import java.io.InputStream

class Font @JvmOverloads constructor(
    val fontInfo: FontInfo,
    private val atlas: InputStream,
    val blur: Boolean = false,
    val mipmap: Boolean = false
) {
    private lateinit var texture: ReleasedDynamicTexture

    fun getTexture(): ReleasedDynamicTexture {
        if (!::texture.isInitialized) {
            texture = UGraphics.getTexture(atlas)
            texture.setBlurMipmap(blur, mipmap)
        }

        return texture
    }

    companion object {
        fun fromResource(path: String): Font {
            val json = this::class.java.getResourceAsStream("$path.json")
            val fontInfo = FontInfo.fromJson(JsonParser().parse(json.reader()).asJsonObject)

            return Font(fontInfo, this::class.java.getResourceAsStream("$path.png"))
        }
    }
}


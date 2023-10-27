package com.sleepwalker.sleeplib.gg.essential.elementa.components.image

import com.sleepwalker.sleeplib.gg.essential.universal.utils.ReleasedDynamicTexture

interface CacheableImage {

    fun supply(image: CacheableImage)

    fun applyTexture(texture: ReleasedDynamicTexture?)

}
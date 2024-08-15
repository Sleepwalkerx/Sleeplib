package com.sleepwalker.sleeplib.elementa.drawable

import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation

class TextureSource(val location: ResourceLocation, val width: Int, val height: Int) {

    constructor(namespace: String, path: String, width: Int, height: Int): this(ResourceLocation(namespace, path), width, height)

    fun bind() {
        Minecraft.getInstance().getTextureManager().bindTexture(location)
    }
}
package com.sleepwalker.sleeplib.util

import net.minecraft.util.ResourceLocation
import net.minecraft.util.ResourceLocationException

fun parseResourceLocationOrNull(resourceLocation: String, defNamespace: String): ResourceLocation? {
    return if (resourceLocation.indexOf(':') == -1) {
        ResourceLocation.tryCreate("$defNamespace:$resourceLocation")
    } else {
        ResourceLocation.tryCreate(resourceLocation)
    }
}

@Throws(ResourceLocationException::class)
fun parseResourceLocation(resourceLocation: String, defNamespace: String): ResourceLocation {
    return if (resourceLocation.indexOf(':') == -1) {
        ResourceLocation("$defNamespace:$resourceLocation")
    } else {
        ResourceLocation(resourceLocation)
    }
}
package com.sleepwalker.sleeplib.integration

import net.minecraftforge.fml.ModList

object IntegrationHelper {
    fun hasJei(): Boolean = has("jei")
    fun has(target: String): Boolean = ModList.get().isLoaded(target)
}
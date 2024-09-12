package com.sleepwalker.sleeplib.integration.jei

import com.sleepwalker.sleeplib.SleepLib
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.runtime.IJeiRuntime
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

@JeiPlugin
class SleepLibJeiPlugin : IModPlugin {

    var jeiRuntime: IJeiRuntime? = null
        private set

    init {
        instance = this
    }

    override fun getPluginUid(): ResourceLocation = ResourceLocation(SleepLib.MODID, "main")

    override fun onRuntimeAvailable(jeiRuntime: IJeiRuntime) {
        this.jeiRuntime = jeiRuntime
    }

    fun get(): SleepLibJeiPlugin? = instance

    companion object {
        private var instance: SleepLibJeiPlugin? = null
        fun getAllItems(): Collection<ItemStack> {
            return instance?.jeiRuntime?.ingredientManager?.getAllIngredients(VanillaTypes.ITEM) ?: return emptySet()
        }
    }
}
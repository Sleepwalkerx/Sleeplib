package com.sleepwalker.sleeplib.translations

import com.sleepwalker.sleeplib.SleepLib
import net.minecraft.util.text.TranslationTextComponent

object SleepLibTranslations {
    fun response(key: String): TranslationTextComponent = TranslationTextComponent("response.${SleepLib.MODID}.$key")
    fun widget(key: String): TranslationTextComponent = TranslationTextComponent("widget.${SleepLib.MODID}.$key")
}
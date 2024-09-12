package com.sleepwalker.sleeplib.util

import com.sleepwalker.sleeplib.translations.SleepLibTranslations
import net.minecraft.util.IReorderingProcessor
import net.minecraft.util.text.*

object C {

    val FUCKING_HACKERS = response("fucking_hackers", true)
    val NO_PERMISSION = response("no_permission", true)
    val UNEXPECTED_ERROR = response("unexpected", true)
    val NO_IMPLEMENTATION = response("no_implementation", true)
    val UNSUPPORTED = response("unsupported", true)
    val BAD_DATA = response("bad_data", true)
    val REQUEST_EXECUTING = response("request_executing", false)

    val EMPTY: IFormattableTextComponent = object : IFormattableTextComponent {
        override fun setStyle(style: Style): IFormattableTextComponent = this
        override fun appendSibling(sibling: ITextComponent): IFormattableTextComponent = this
        override fun getStyle(): Style = Style.EMPTY
        override fun getUnformattedComponentText(): String = ""
        override fun getSiblings(): List<ITextComponent> = emptyList()
        override fun copyRaw(): IFormattableTextComponent = this
        override fun deepCopy(): IFormattableTextComponent = this
        override fun func_241878_f(): IReorderingProcessor = IReorderingProcessor.field_242232_a
    }

    private fun response(key: String, error: Boolean): ITextComponent = SleepLibTranslations.response(key)
        .mergeStyle(if(error) TextFormatting.RED else TextFormatting.YELLOW)
}
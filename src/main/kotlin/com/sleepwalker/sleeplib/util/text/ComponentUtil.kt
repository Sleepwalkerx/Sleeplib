package com.sleepwalker.sleeplib.util.text

import net.minecraft.util.text.*
import java.util.Optional

fun String.literal(): IFormattableTextComponent = StringTextComponent(this)
fun String.literal(formatting: TextFormatting): IFormattableTextComponent = StringTextComponent(this).mergeStyle(formatting)

fun ITextComponent.getStringWithFormatting(): String {
    val builder = StringBuilder()
    appendComponentTextWithFormatting(builder, this, this.style)
    return builder.toString()
}

private fun appendComponentTextWithFormatting(builder: StringBuilder, component: ITextComponent, parentStyle: Style?) {
    val currentStyle = /*parentStyle?.applyTo(component.style) ?:*/ component.style
    currentStyle.color?.let { color ->
        TextFormatting.getValueByName(color.toString())?.let { builder.append(it.toString()) }
    }
    if (currentStyle.bold) builder.append(TextFormatting.BOLD.toString())
    if (currentStyle.italic) builder.append(TextFormatting.ITALIC.toString())
    if (currentStyle.underlined) builder.append(TextFormatting.UNDERLINE.toString())
    if (currentStyle.strikethrough) builder.append(TextFormatting.STRIKETHROUGH.toString())
    if (currentStyle.obfuscated) builder.append(TextFormatting.OBFUSCATED.toString())
    component.func_230533_b_ {
        builder.append(it)
        Optional.empty<String>()
    }
    for (sibling in component.siblings) {
        appendComponentTextWithFormatting(builder, sibling, currentStyle)
    }
    builder.append(TextFormatting.RESET.toString())
}
package com.sleepwalker.sleeplib.util

import net.minecraft.util.text.IFormattableTextComponent
import net.minecraft.util.text.StringTextComponent

fun String.literal(): IFormattableTextComponent = StringTextComponent(this.replace("&", "§"))
fun String.literal(vararg args: Any): IFormattableTextComponent = StringTextComponent(String.format(this, args).replace("&", "§"))
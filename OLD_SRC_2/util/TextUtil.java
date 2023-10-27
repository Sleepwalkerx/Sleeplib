package com.sleepwalker.sleeplib.util;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public final class TextUtil {

    @Nonnull
    public static IFormattableTextComponent str(@Nonnull String text){
        return new StringTextComponent(text.replace("&", "§"));
    }

    @Nonnull
    public static IFormattableTextComponent str(@Nonnull String text, Object... args){
        return new StringTextComponent(String.format(text, args).replace("&", "§"));
    }
}

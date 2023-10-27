package com.sleepwalker.sleeplib.util;

import com.sleepwalker.sleeplib.SleepLib;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.*;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class C {

    @Nonnull
    public static final IFormattableTextComponent
        FUCKING_HACKERS = srm("error.fucking_hackers.text", TextFormatting.RED),
        NO_PERMISSION = srm("error.no_permission.text", TextFormatting.RED),
        UNEXPECTED_ERROR = srm("error.unexpected.text", TextFormatting.RED),
        NO_IMPLEMENTATION = srm("error.no_implementation.text", TextFormatting.RED),
        UNSUPPORTED = srm("error.unsupported.text", TextFormatting.RED),
        BAD_DATA = srm("error.bad_data.text", TextFormatting.RED),

        REQUEST_EXECUTING = srm("info.request_executing.text", TextFormatting.YELLOW);

    @Nonnull
    public static final IFormattableTextComponent EMPTY = new IFormattableTextComponent() {
        @Nonnull
        @Override
        public IFormattableTextComponent setStyle(@Nonnull Style style) {
            return this;
        }
        @Nonnull
        @Override
        public IFormattableTextComponent appendSibling(@Nonnull ITextComponent sibling) {
            return this;
        }
        @Nonnull
        @Override
        public Style getStyle() {
            return Style.EMPTY;
        }
        @Nonnull
        @Override
        public String getUnformattedComponentText() {
            return "";
        }
        @Nonnull
        @Override
        public List<ITextComponent> getSiblings() {
            return Collections.emptyList();
        }
        @Nonnull
        @Override
        public IFormattableTextComponent copyRaw() {
            return this;
        }

        @Nonnull
        @Override
        public IFormattableTextComponent deepCopy() {
            return this;
        }
        @Nonnull
        @Override
        public IReorderingProcessor func_241878_f() {
            return IReorderingProcessor.field_242232_a;
        }
    };

    @Nonnull
    private static IFormattableTextComponent srm(@Nonnull String path, @Nonnull TextFormatting color){
        return new TranslationTextComponent(String.format("response.%s.%s", SleepLib.MODID, path)).mergeStyle(color);
    }
}

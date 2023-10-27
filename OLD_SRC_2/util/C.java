package com.sleepwalker.sleeplib.util;

import com.sleepwalker.sleeplib.SleepLib;
import net.minecraft.util.text.*;

import javax.annotation.Nonnull;
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
    public static final ITextProperties EMPTY = new ITextProperties() {
        @Nonnull
        @Override
        public <T> Optional<T> visit(@Nonnull ITextAcceptor<T> acceptor) {
            return Optional.empty();
        }
        @Nonnull
        @Override
        public <T> Optional<T> visit(@Nonnull IStyledTextAcceptor<T> acceptor, @Nonnull Style style) {
            return Optional.empty();
        }
    };

    @Nonnull
    private static IFormattableTextComponent srm(@Nonnull String path, @Nonnull TextFormatting color){
        return new TranslationTextComponent(String.format("response.%s.%s", SleepLib.MODID, path)).withStyle(color);
    }
}

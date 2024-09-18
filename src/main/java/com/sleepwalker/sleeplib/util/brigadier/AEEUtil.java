package com.sleepwalker.sleeplib.util.brigadier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sleepwalker.sleeplib.util.exception.ActionExecutionException;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.NotNull;

public class AEEUtil {

    @NotNull
    public static <S extends CommandSource, T extends ArgumentBuilder<S, T>> Command<S> safeExecutes(SafeCommand<S> command) {
        return context -> {
            try {
                command.execute(context);
                return 1;
            }
            catch (ActionExecutionException e){
                context.getSource().sendErrorMessage(e.getReason());
                return 0;
            }
        };
    }

    @FunctionalInterface
    public interface SafeCommand<S> {
        void execute(CommandContext<S> context) throws ActionExecutionException, CommandSyntaxException;
    }

    @NotNull
    public static <S extends CommandSource, T extends ArgumentBuilder<S, T>> Command<S> safeReturnExecutes(SafeReturnCommand<S> command){
        return context -> {
            try {
                return command.execute(context);
            }
            catch (ActionExecutionException e){
                context.getSource().sendErrorMessage(e.getReason());
                return 0;
            }
        };
    }

    @FunctionalInterface
    public interface SafeReturnCommand<S> {
        int execute(CommandContext<S> context) throws ActionExecutionException, CommandSyntaxException;
    }
}

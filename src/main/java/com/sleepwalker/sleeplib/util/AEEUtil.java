package com.sleepwalker.sleeplib.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sleepwalker.sleeplib.util.exception.ActionExecutionException;
import net.minecraft.command.CommandSource;

import javax.annotation.Nonnull;

public class AEEUtil {

    @Nonnull
    public static Command<CommandSource> runAndReturn(@Nonnull AEEActionWithReturn<CommandSource> action){
        return context -> {
            try {
                return action.run(context);
            }
            catch (ActionExecutionException e){
                context.getSource().sendErrorMessage(e.getReason());
                return 0;
            }
        };
    }

    @Nonnull
    public static Command<CommandSource> run(@Nonnull AEEAction<CommandSource> action){
        return context -> {
            try {
                action.run(context);
                return 1;
            }
            catch (ActionExecutionException e){
                context.getSource().sendErrorMessage(e.getReason());
                return 0;
            }
        };
    }

    public interface AEEActionWithReturn<S> {
        int run(@Nonnull CommandContext<S> c) throws ActionExecutionException, CommandSyntaxException;
    }

    public interface AEEAction<S> {
        void run(@Nonnull CommandContext<S> c) throws ActionExecutionException, CommandSyntaxException;
    }
}

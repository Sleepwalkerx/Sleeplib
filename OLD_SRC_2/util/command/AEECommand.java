package com.sleepwalker.sleeplib.util.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sleepwalker.sleeplib.util.exception.ActionExecutionException;
import net.minecraft.command.CommandSource;

import javax.annotation.Nonnull;

public class AEECommand {

    @Nonnull
    public static Command<CommandSource> run(@Nonnull AEEAction<CommandSource> action){
        return context -> {
            try {
                action.run(context);
                return 1;
            }
            catch (ActionExecutionException e){
                context.getSource().sendFailure(e.getReason());
                return 0;
            }
        };
    }

    public interface AEEAction<S> {
        void run(@Nonnull CommandContext<S> c) throws ActionExecutionException, CommandSyntaxException;
    }
}

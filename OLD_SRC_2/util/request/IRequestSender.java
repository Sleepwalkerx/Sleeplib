package com.sleepwalker.sleeplib.util.request;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public interface IRequestSender {

    @Nonnull
    ConsoleRequestSender CONSOLE = new ConsoleRequestSender();
    @Nonnull
    UnknownRequestSender UNKNOWN = new UnknownRequestSender();

    boolean hasPermission(@Nonnull String node);

    void sendMessage(@Nonnull ITextComponent message);

    @Nonnull
    String getName();

    @Nonnull
    static IRequestSender fromCommandSource(@Nonnull CommandSource source){
        if(source.getEntity() == null){
            return CONSOLE;
        }
        else if(source.getEntity() instanceof ServerPlayerEntity){
            return new PlayerRequestSender((ServerPlayerEntity) source.getEntity());
        }
        else {
            return UNKNOWN;
        }
    }
}

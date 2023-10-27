package com.sleepwalker.sleeplib.util;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public final class PermissionUtil {

    @Nonnull
    public static Predicate<CommandSource> requires(@Nonnull String node){
        return source -> {
            if(source.getEntity() == null){
                return true;
            }
            else if(source.getEntity() instanceof ServerPlayerEntity){
                return PermissionAPI.hasPermission((ServerPlayerEntity) source.getEntity(), node);
            }
            else return false;
        };
    }
}

package com.sleepwalker.sleeplib.util.brigadier

import com.mojang.brigadier.builder.ArgumentBuilder
import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.server.permission.PermissionAPI

fun <S : CommandSource, T : ArgumentBuilder<S, T>> T.requiresNode(node: String): T = this.requires {
    when(it.entity){
        null -> true
        is ServerPlayerEntity -> PermissionAPI.hasPermission(it.entity as ServerPlayerEntity, node)
        else -> false
    }
}
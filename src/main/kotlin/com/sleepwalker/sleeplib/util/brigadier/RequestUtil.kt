package com.sleepwalker.sleeplib.util.brigadier

import com.mojang.brigadier.context.CommandContext
import com.sleepwalker.sleeplib.util.request.ConsoleRequestSender
import com.sleepwalker.sleeplib.util.request.IRequestSender
import com.sleepwalker.sleeplib.util.request.PlayerRequestSender
import com.sleepwalker.sleeplib.util.request.UnknownRequestSender
import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity

fun CommandSource.asRequestSender(): IRequestSender = when(entity){
    null -> ConsoleRequestSender
    is ServerPlayerEntity -> PlayerRequestSender(entity as ServerPlayerEntity)
    else -> UnknownRequestSender
}

fun CommandContext<CommandSource>.asRequestSender(): IRequestSender = source.asRequestSender()
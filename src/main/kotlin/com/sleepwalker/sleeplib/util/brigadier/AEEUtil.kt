package com.sleepwalker.sleeplib.util.brigadier

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.sleepwalker.sleeplib.util.exception.ActionExecutionException
import net.minecraft.command.CommandSource

typealias Action<S> = (CommandContext<S>) -> Unit
typealias ActionWithReturn<S> = (CommandContext<S>) -> Int

fun <S : CommandSource, T : ArgumentBuilder<S, T>> T.safeReturnExecutes(action: ActionWithReturn<S>): T = this.executes {
    try {
        action(it)
    } catch (e: ActionExecutionException) {
        it.source.sendErrorMessage(e.reason)
        0
    }
}

fun <S : CommandSource, T : ArgumentBuilder<S, T>> T.safeExecutes(action: Action<S>): T = this.executes {
    try {
        action(it)
        1
    } catch (e: ActionExecutionException) {
        it.source.sendErrorMessage(e.reason)
        0
    }
}
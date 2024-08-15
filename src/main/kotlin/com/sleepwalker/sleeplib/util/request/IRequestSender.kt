package com.sleepwalker.sleeplib.util.request

import net.minecraft.command.CommandSource
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.ITextComponent
import java.util.*

interface IRequestSender {
    val name: String
    val uuid: UUID

    fun hasPermission(node: String): Boolean
    fun sendMessage(message: ITextComponent, notifyAdmins: Boolean)
    fun sendMessage(message: ITextComponent) = sendMessage(message, false)

    companion object {
        fun fromCommandSource(source: CommandSource): IRequestSender = when(source.entity){
            null -> ConsoleRequestSender
            is ServerPlayerEntity -> PlayerRequestSender(source.entity as ServerPlayerEntity)
            else -> UnknownRequestSender
        }
    }
}
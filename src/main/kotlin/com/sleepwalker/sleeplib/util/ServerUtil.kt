package com.sleepwalker.sleeplib.util

import com.sleepwalker.sleeplib.util.request.ConsoleRequestSender
import com.sleepwalker.sleeplib.util.request.IRequestSender
import net.minecraft.util.Util
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.GameRules
import net.minecraftforge.fml.server.ServerLifecycleHooks

fun broadcastToAdmins(sender: IRequestSender, message: ITextComponent) {
    val server = ServerLifecycleHooks.getCurrentServer() ?: return
    val text: ITextComponent = TranslationTextComponent("chat.type.admin", sender.name, message)
        .mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC)
    if (server.gameRules.getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {
        for (player in server.playerList.players) {
            if (player.gameProfile.id != sender.uuid && server.playerList.canSendCommands(player.gameProfile)) {
                player.sendMessage(text, Util.DUMMY_UUID)
            }
        }
    }
    if (sender !== ConsoleRequestSender && server.gameRules.getBoolean(GameRules.LOG_ADMIN_COMMANDS)) {
        server.sendMessage(text, Util.DUMMY_UUID)
    }
}
package com.sleepwalker.sleeplib.util;

import com.sleepwalker.sleeplib.util.request.IRequestSender;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;

public final class ServerUtil {

    public static void broadcastToAdmins(@Nonnull IRequestSender sender, @Nonnull ITextComponent message) {

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server == null){
            return;
        }

        ITextComponent text = (new TranslationTextComponent("chat.type.admin", sender.getName(), message)).mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC);
        if (server.getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {
            for(ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
                if (!player.getGameProfile().getId().equals(sender.getUUID()) && server.getPlayerList().canSendCommands(player.getGameProfile())) {
                    player.sendMessage(text, Util.DUMMY_UUID);
                }
            }
        }

        if (sender != IRequestSender.CONSOLE && server.getGameRules().getBoolean(GameRules.LOG_ADMIN_COMMANDS)) {
            server.sendMessage(text, Util.DUMMY_UUID);
        }
    }
}

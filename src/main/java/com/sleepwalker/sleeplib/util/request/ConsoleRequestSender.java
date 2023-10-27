package com.sleepwalker.sleeplib.util.request;

import com.sleepwalker.sleeplib.util.ServerUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ConsoleRequestSender implements IRequestSender {

    @Override
    public boolean hasPermission(@Nonnull String node) {
        return true;
    }

    @Override
    public void sendMessage(@Nonnull ITextComponent message, boolean notifyAdmins) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null){
            server.sendMessage(message, Util.DUMMY_UUID);
        }
        if(notifyAdmins){
            ServerUtil.broadcastToAdmins(this, message);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Console";
    }

    @Nonnull
    @Override
    public UUID getUUID() {
        return Util.DUMMY_UUID;
    }
}

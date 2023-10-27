package com.sleepwalker.sleeplib.util.request;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;

public class ConsoleRequestSender implements IRequestSender {

    @Override
    public boolean hasPermission(@Nonnull String node) {
        return true;
    }

    @Override
    public void sendMessage(@Nonnull ITextComponent message) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null){
            server.sendMessage(message, Util.NIL_UUID);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Console";
    }
}

package com.sleepwalker.sleeplib.util.request;

import com.sleepwalker.sleeplib.util.ServerUtil;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.UUID;

public class UnknownRequestSender implements IRequestSender {

    @Override
    public boolean hasPermission(@Nonnull String node) {
        return false;
    }

    @Override
    public void sendMessage(@Nonnull ITextComponent message, boolean notifyAdmins) {
        if(notifyAdmins){
            ServerUtil.broadcastToAdmins(this, message);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "?Unknown?";
    }

    @Nonnull
    @Override
    public UUID getUUID() {
        return Util.DUMMY_UUID;
    }
}

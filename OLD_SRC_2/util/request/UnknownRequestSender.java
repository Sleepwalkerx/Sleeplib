package com.sleepwalker.sleeplib.util.request;

import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class UnknownRequestSender implements IRequestSender {

    @Override
    public boolean hasPermission(@Nonnull String node) {
        return false;
    }

    @Override
    public void sendMessage(@Nonnull ITextComponent message) { }

    @Nonnull
    @Override
    public String getName() {
        return "?Unknown?";
    }
}

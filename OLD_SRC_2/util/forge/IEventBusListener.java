package com.sleepwalker.sleeplib.util.forge;

import net.minecraftforge.eventbus.api.IEventBus;

import javax.annotation.Nonnull;

public interface IEventBusListener {
    
    void register(@Nonnull IEventBus modBus, @Nonnull IEventBus forgeBus);
}

package com.sleepwalker.sleeplib.util.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

public final class EventBusHelper {

    public static void addEventBusListener(@Nonnull IEventBusListener listener){
        listener.register(FMLJavaModLoadingContext.get().getModEventBus(), MinecraftForge.EVENT_BUS);
    }
}

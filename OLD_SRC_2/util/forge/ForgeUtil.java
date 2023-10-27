package com.sleepwalker.sleeplib.util.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

public class ForgeUtil {

    @Nonnull
    public static IEventBus getModBus(){
        return FMLJavaModLoadingContext.get().getModEventBus();
    }
}

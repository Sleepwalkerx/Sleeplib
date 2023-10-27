package com.sleepwalker.sleeplib.client;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.client.renderer.SLItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = SleepLib.MODID)
public final class ClientModBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(@Nonnull FMLClientSetupEvent event){
        event.enqueueWork(event.getMinecraftSupplier()).thenAccept(mc -> new SLItemRenderer(mc.getTextureManager(), mc.getModelManager(), mc.getItemColors(), mc));
    }
}

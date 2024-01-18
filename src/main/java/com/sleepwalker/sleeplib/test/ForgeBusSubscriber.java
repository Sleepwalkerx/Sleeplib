package com.sleepwalker.sleeplib.test;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.test.screen.TestoScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.command.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SleepLib.MODID, value = Dist.CLIENT)
public class ForgeBusSubscriber {

    @SubscribeEvent
    public static void onRegisterCommands(@Nonnull RegisterCommandsEvent event){
        if(FMLLoader.isProduction()){
            return;
        }

        event.getDispatcher().register(Commands.literal(SleepLib.MODID)
            .then(Commands.literal("testo")
                .executes(c -> {
                    try {
                        Minecraft.getInstance().displayGuiScreen(new TestoScreen());
                    }
                    catch (Exception e){
                        SleepLib.LOGGER.throwing(e);
                    }
                    return 1;
                })
            )
        );
    }
}

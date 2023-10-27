package com.sleepwalker.sleeplib.server;

import com.mojang.brigadier.CommandDispatcher;
import com.sleepwalker.sleeplib.SleepLib;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = SleepLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SLCommands {

    @SubscribeEvent
    public static void registerCommandsEvent(@Nonnull RegisterCommandsEvent event){

        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("sleeplib")
            .then(Commands.literal("openTest")
                .executes(c -> {
                    //Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(new TestScreen()));
                    return 1;
                })
            )
        );
    }
}

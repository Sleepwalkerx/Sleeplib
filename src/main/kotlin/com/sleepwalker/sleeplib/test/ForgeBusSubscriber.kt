package com.sleepwalker.sleeplib.test

import com.sleepwalker.sleeplib.SleepLib
import com.sleepwalker.sleeplib.test.screen.TestoScreen
import com.sleepwalker.sleeplib.util.brigadier.requiresNode
import net.minecraft.client.Minecraft
import net.minecraft.command.Commands
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.loading.FMLLoader
import javax.annotation.Nonnull

@EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE, modid = SleepLib.MODID, value = [Dist.CLIENT])
class ForgeBusSubscriber {

    @SubscribeEvent
    fun onRegisterCommands(@Nonnull event: RegisterCommandsEvent) {

        if (FMLLoader.isProduction()) {
            return
        }
        event.dispatcher.register(
            Commands.literal(SleepLib.MODID)
                .requiresNode("123")
                .then(Commands.literal("testo")

                    .executes {
                        try {
                            Minecraft.getInstance().displayGuiScreen(TestoScreen())
                        } catch (e: Exception) {
                            SleepLib.LOGGER.throwing(e)
                        }
                        1
                    }
                )
        )
    }
}
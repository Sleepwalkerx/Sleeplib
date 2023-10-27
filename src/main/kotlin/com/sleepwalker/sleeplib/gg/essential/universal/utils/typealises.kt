package com.sleepwalker.sleeplib.gg.essential.universal.utils

typealias MCMinecraft = net.minecraft.client.Minecraft
typealias MCFontRenderer = net.minecraft.client.gui.FontRenderer

typealias MCClickEventAction = net.minecraft.util.text.event.ClickEvent.Action
//#if MC>=11600
typealias MCHoverEventAction = net.minecraft.util.text.event.HoverEvent.Action<*>
//#else
//$$ typealias MCHoverEventAction = net.minecraft.util.text.event.HoverEvent.Action
//#endif
//#if MC>=11600
typealias MCIMutableText = net.minecraft.util.text.IFormattableTextComponent
//#else
//$$ typealias MCIMutableText = net.minecraft.util.text.ITextComponent
//#endif
typealias MCITextComponent = net.minecraft.util.text.ITextComponent
typealias MCClickEvent = net.minecraft.util.text.event.ClickEvent
typealias MCHoverEvent = net.minecraft.util.text.event.HoverEvent

typealias MCSettings = net.minecraft.client.GameSettings
typealias MCWorld = net.minecraft.client.world.ClientWorld
typealias MCEntityPlayerSP = net.minecraft.client.entity.player.ClientPlayerEntity
typealias MCScreen = net.minecraft.client.gui.screen.Screen
typealias MCChatScreen = net.minecraft.client.gui.NewChatGui
typealias MCMainMenuScreen = net.minecraft.client.gui.screen.MainMenuScreen
typealias MCClientNetworkHandler = net.minecraft.client.network.play.ClientPlayNetHandler

//#if MC>=11502
typealias MCButton = net.minecraft.client.gui.widget.button.Button
//#else
//$$ typealias MCButton = net.minecraft.client.gui.GuiButton
//#endif

typealias MCStringTextComponent = net.minecraft.util.text.StringTextComponent
typealias MCSChatPacket = net.minecraft.network.play.server.SChatPacket

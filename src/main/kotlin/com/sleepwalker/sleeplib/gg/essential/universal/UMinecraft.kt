package com.sleepwalker.sleeplib.gg.essential.universal

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.player.ClientPlayerEntity
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.NewChatGui
import net.minecraft.client.world.ClientWorld
import net.minecraft.client.network.play.ClientPlayNetHandler
import net.minecraft.client.GameSettings

//#if MC>=11502
import net.minecraft.client.util.NativeUtil
//#endif

object UMinecraft {
    //#if MC>=11900
    //$$ private var guiScaleValue: Int
    //$$     get() = getSettings().guiScale.value
    //$$     set(value) { getSettings().guiScale.value = value }
    //#else
    private var guiScaleValue: Int
        get() = getSettings().guiScale
        set(value) { getSettings().guiScale = value }
    //#endif

    @JvmStatic
    var guiScale: Int
        get() = guiScaleValue
        set(value) {
            guiScaleValue = value
            //#if MC>=11502
            val mc = getMinecraft()
            val window = mc.mainWindow
            val scaleFactor = window.calcGuiScale(value, mc.forceUnicodeFont)
            window.setGuiScale(scaleFactor.toDouble())
            //#endif
        }

    @JvmField
    val isRunningOnMac: Boolean =
        Minecraft.IS_RUNNING_ON_MAC

    @JvmStatic
    fun getMinecraft(): Minecraft {
        return Minecraft.getInstance()
    }

    @JvmStatic
    fun getWorld(): ClientWorld? {
        return getMinecraft().world
    }

    @JvmStatic
    fun getNetHandler(): ClientPlayNetHandler? {
        return getMinecraft().connection
    }

    @JvmStatic
    fun getPlayer(): ClientPlayerEntity? {
        return getMinecraft().player
    }

    @JvmStatic
    fun getFontRenderer(): FontRenderer {
        return getMinecraft().fontRenderer
    }

    @JvmStatic
    fun getTime(): Long {
        //#if MC>=11502
        return (NativeUtil.getTime() * 1000).toLong()
        //#else
        //$$ return Minecraft.getSystemTime()
        //#endif
    }

    @JvmStatic
    fun getChatGUI(): NewChatGui? = getMinecraft().ingameGUI?.chatGUI

    @JvmStatic
    fun getSettings(): GameSettings = getMinecraft().gameSettings
}

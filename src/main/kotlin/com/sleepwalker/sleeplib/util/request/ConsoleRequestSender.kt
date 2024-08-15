package com.sleepwalker.sleeplib.util.request

import com.sleepwalker.sleeplib.util.broadcastToAdmins
import net.minecraft.util.Util
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.fml.server.ServerLifecycleHooks
import java.util.*

object ConsoleRequestSender : IRequestSender {
    override val name: String
        get() = "Console"
    override val uuid: UUID
        get() = Util.DUMMY_UUID
    override fun hasPermission(node: String): Boolean = true
    override fun sendMessage(message: ITextComponent, notifyAdmins: Boolean) {
        ServerLifecycleHooks.getCurrentServer()?.sendMessage(message, Util.DUMMY_UUID)
        if (notifyAdmins) {
            broadcastToAdmins(this, message)
        }
    }
}
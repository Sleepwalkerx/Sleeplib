package com.sleepwalker.sleeplib.util.request

import com.sleepwalker.sleeplib.util.broadcastToAdmins
import net.minecraft.util.Util
import net.minecraft.util.text.ITextComponent
import java.util.*

object UnknownRequestSender : IRequestSender {
    override val name: String
        get() = "?Unknown?"
    override val uuid: UUID
        get() = Util.DUMMY_UUID
    override fun hasPermission(node: String): Boolean = false
    override fun sendMessage(message: ITextComponent, notifyAdmins: Boolean) {
        if (notifyAdmins) {
            broadcastToAdmins(this, message)
        }
    }
}
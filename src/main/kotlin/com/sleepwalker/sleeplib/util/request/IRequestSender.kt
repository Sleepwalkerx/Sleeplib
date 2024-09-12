package com.sleepwalker.sleeplib.util.request

import net.minecraft.util.text.ITextComponent
import java.util.*

interface IRequestSender {
    val name: String
    val uuid: UUID

    fun hasPermission(node: String): Boolean
    fun sendMessage(message: ITextComponent, notifyAdmins: Boolean)
    fun sendMessage(message: ITextComponent) = sendMessage(message, false)
}
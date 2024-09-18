package com.sleepwalker.sleeplib.util.request

import com.mojang.authlib.GameProfile
import com.sleepwalker.sleeplib.util.broadcastToAdmins
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.Util
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.fml.server.ServerLifecycleHooks
import net.minecraftforge.server.permission.PermissionAPI
import java.util.*

class PlayerRequestSender(val profile: GameProfile) : IRequestSender {

    constructor(player: ServerPlayerEntity) : this(player.gameProfile)

    override val name: String
        get() = profile.name
    override val uuid: UUID
        get() = profile.id

    override fun hasPermission(node: String): Boolean = PermissionAPI.hasPermission(profile, node, null)

    val player: ServerPlayerEntity?
        get() = ServerLifecycleHooks.getCurrentServer()?.playerList?.getPlayerByUUID(profile.id)

    override fun sendMessage(message: ITextComponent, notifyAdmins: Boolean) {
        player?.sendMessage(message, Util.DUMMY_UUID)
        if (notifyAdmins) {
            broadcastToAdmins(this, message)
        }
    }
}

fun ServerPlayerEntity.toRequestSender(): PlayerRequestSender = PlayerRequestSender(this)
package com.sleepwalker.sleeplib.util.network

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent

interface IServerPacket : INetworkPacket {
    override val networkDirection: NetworkDirection
        get() = NetworkDirection.PLAY_TO_CLIENT

    override fun handle(context: NetworkEvent.Context) {
        handleClient(context)
    }

    @OnlyIn(Dist.CLIENT)
    fun handleClient(context: NetworkEvent.Context)
}
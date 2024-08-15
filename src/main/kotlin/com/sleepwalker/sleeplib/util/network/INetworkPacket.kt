package com.sleepwalker.sleeplib.util.network

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent

interface INetworkPacket {
    val networkDirection: NetworkDirection
    fun write(buffer: PacketBuffer)
    fun handle(context: NetworkEvent.Context)
}
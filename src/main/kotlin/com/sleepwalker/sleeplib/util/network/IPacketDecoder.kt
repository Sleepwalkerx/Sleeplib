package com.sleepwalker.sleeplib.util.network

import net.minecraft.network.PacketBuffer

interface IPacketDecoder<P : INetworkPacket> {
    fun decode(buffer: PacketBuffer): P
}
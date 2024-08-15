package com.sleepwalker.sleeplib.util.network

import net.minecraftforge.fml.network.NetworkDirection

interface IServerPacket : INetworkPacket {
    override val networkDirection: NetworkDirection
        get() = NetworkDirection.PLAY_TO_CLIENT
}
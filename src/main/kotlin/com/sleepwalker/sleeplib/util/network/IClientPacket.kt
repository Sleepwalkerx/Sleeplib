package com.sleepwalker.sleeplib.util.network

import net.minecraftforge.fml.network.NetworkDirection

interface IClientPacket : INetworkPacket {
    override val networkDirection: NetworkDirection
        get() = NetworkDirection.PLAY_TO_SERVER
}
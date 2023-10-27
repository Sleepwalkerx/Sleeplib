package com.sleepwalker.sleeplib.util.network;

import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nonnull;

public interface IClientPacket extends INetworkPacket {

    @Nonnull
    @Override
    default NetworkDirection getNetworkDirection(){
        return NetworkDirection.PLAY_TO_SERVER;
    }
}

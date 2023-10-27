package com.sleepwalker.sleeplib.util.network;

import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface IPacketDecoder<P extends INetworkPacket> {

    @Nonnull P decode(@Nonnull PacketBuffer pf);
}

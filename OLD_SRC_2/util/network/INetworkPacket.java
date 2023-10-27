package com.sleepwalker.sleeplib.util.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nonnull;

public interface INetworkPacket {

    @Nonnull
    NetworkDirection getNetworkDirection();

    void write(@Nonnull PacketBuffer pf);

    void handle(@Nonnull NetworkEvent.Context context);
}

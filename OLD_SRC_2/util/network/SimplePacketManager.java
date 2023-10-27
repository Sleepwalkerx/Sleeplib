package com.sleepwalker.sleeplib.util.network;

import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Supplier;

public class SimplePacketManager {

    @Nonnull
    private final SimpleChannel channel;
    private int nextMsgId;

    public SimplePacketManager(@Nonnull SimpleChannel channel) {
        this.channel = channel;
    }

    public <P extends IClientPacket> void rgCP(@Nonnull IPacketDecoder<P> decoder, @Nonnull Class<P> type) {
        rg(decoder, type, NetworkDirection.PLAY_TO_SERVER);
    }

    public <P extends IServerPacket> void rgSP(@Nonnull IPacketDecoder<P> decoder, @Nonnull Class<P> type){
        rg(decoder, type, NetworkDirection.PLAY_TO_CLIENT);
    }

    public <P extends INetworkPacket> void rg(@Nonnull IPacketDecoder<P> decoder, @Nonnull Class<P> type, @Nonnull NetworkDirection direction){
        channel.registerMessage(nextMsgId++, type, INetworkPacket::write, decoder::decode, this::handlePacket, Optional.of(direction));
    }

    protected <P extends INetworkPacket> void handlePacket(@Nonnull P packet, @Nonnull Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        if(context.getDirection() == packet.getNetworkDirection()){
            context.enqueueWork(() -> packet.handle(context));
        }
        context.setPacketHandled(true);
    }
}

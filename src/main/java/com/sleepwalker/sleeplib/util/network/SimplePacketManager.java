package com.sleepwalker.sleeplib.util.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Supplier;

public class SimplePacketManager {

    @Nonnull protected final SimpleChannel channel;
    protected int nextMsgId;

    public SimplePacketManager(@Nonnull SimpleChannel channel) {
        this.channel = channel;
    }

    public SimplePacketManager(@Nonnull String version, @Nonnull ResourceLocation name) {
        channel = NetworkRegistry.newSimpleChannel(name, () -> version, version::equals, version::equals);
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

    @Nonnull
    public SimpleChannel getChannel() {
        return channel;
    }
}

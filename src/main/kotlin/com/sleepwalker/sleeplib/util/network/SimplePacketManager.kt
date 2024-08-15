package com.sleepwalker.sleeplib.util.network

import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkEvent
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.PacketDistributor
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.*
import java.util.function.Supplier

open class SimplePacketManager(val channel: SimpleChannel) {

    constructor(version: String, name: ResourceLocation) : this(
        NetworkRegistry.newSimpleChannel(name, { version }, { version == it }, { version == it })
    )

    protected var nextMsgId = 0

    private fun <P : INetworkPacket> rg(decoder: IPacketDecoder<P>, type: Class<P>, direction: NetworkDirection) {
        channel.registerMessage(nextMsgId++, type,
            { packet, buffer -> packet.write(buffer) },
            { buffer -> decoder.decode(buffer) },
            { packet, contextSupplier -> this.handlePacket(packet, contextSupplier) },
            Optional.of(direction)
        )
    }

    fun <P : IClientPacket> rgCP(decoder: IPacketDecoder<P>, type: Class<P>) = rg(decoder, type, NetworkDirection.PLAY_TO_SERVER)
    inline fun <reified P : IClientPacket> rgCP(decoder: IPacketDecoder<P>) = rgCP(decoder, P::class.java)
    fun <P : IServerPacket> rgSP(decoder: IPacketDecoder<P>, type: Class<P>) = rg(decoder, type, NetworkDirection.PLAY_TO_CLIENT)
    inline fun <reified P : IServerPacket> rgSP(decoder: IPacketDecoder<P>) = rgSP(decoder, P::class.java)

    protected fun <P : INetworkPacket> handlePacket(packet: P, contextSupplier: Supplier<NetworkEvent.Context>) {
        val context = contextSupplier.get()
        if (context.direction == packet.networkDirection) {
            context.enqueueWork { packet.handle(context) }
        }
        context.packetHandled = true
    }

    fun sendToPlayer(player: ServerPlayerEntity, packet: IServerPacket) {
        channel.send(PacketDistributor.PLAYER.with { player }, packet)
    }

    fun sendToAll(packet: IServerPacket) {
        channel.send(PacketDistributor.ALL.noArg(), packet)
    }

    @OnlyIn(Dist.CLIENT)
    fun sendToServer(packet: IClientPacket) {
        channel.sendToServer(packet)
    }
}
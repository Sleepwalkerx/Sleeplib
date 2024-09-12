package com.sleepwalker.sleeplib.util.network

import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.PacketBuffer
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

    private var nextMsgId = 0

    fun <P : INetworkPacket> rg(type: Class<P>, decoder: (PacketBuffer) -> P, handler: (P, Supplier<NetworkEvent.Context>) -> Unit, direction: NetworkDirection) {
        channel.registerMessage(nextMsgId++, type,
            { packet, buffer -> packet.write(buffer) },
            { buffer -> decoder(buffer) },
            handler,
            Optional.of(direction)
        )
    }

    fun <P : INetworkPacket> rg(type: Class<P>, decoder: (PacketBuffer) -> P, direction: NetworkDirection) {
        rg(type, decoder, { packet, contextSupplier -> this.handlePacket(packet, contextSupplier) }, direction)
    }

    fun <P : IClientPacket> rgCP(decoder: (PacketBuffer) -> P, type: Class<P>) = rg(type, decoder,  NetworkDirection.PLAY_TO_SERVER)
    inline fun <reified P : IClientPacket> rgCP(noinline decoder: (PacketBuffer) -> P) = rgCP(decoder, P::class.java)
    fun <P : IServerPacket> rgSP(decoder: (PacketBuffer) -> P, type: Class<P>) = rg(type, decoder, NetworkDirection.PLAY_TO_CLIENT)
    inline fun <reified P : IServerPacket> rgSP(noinline decoder: (PacketBuffer) -> P) = rgSP(decoder, P::class.java)

    private fun <P : INetworkPacket> handlePacket(packet: P, contextSupplier: Supplier<NetworkEvent.Context>) {
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
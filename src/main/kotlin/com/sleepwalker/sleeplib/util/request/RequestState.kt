package com.sleepwalker.sleeplib.util.request

import com.sleepwalker.sleeplib.util.literal
import net.minecraft.network.PacketBuffer
import net.minecraft.util.text.ITextComponent

class RequestState(val status: Status, val info: ITextComponent) {

    fun isSuccess(): Boolean = status === Status.SUCCESS
    fun isPending(): Boolean = status === Status.PENDING
    fun isFailed(): Boolean = status === Status.FAILED

    fun toNetwork(buffer: PacketBuffer) {
        buffer.writeEnumValue(status)
        buffer.writeTextComponent(info)
    }

    enum class Status {
        SUCCESS,
        PENDING,
        FAILED
    }

    companion object {
        fun fromNetwork(buffer: PacketBuffer): RequestState {
            return RequestState(buffer.readEnumValue(Status::class.java), buffer.readTextComponent())
        }

        fun ITextComponent.success(): RequestState = RequestState(Status.SUCCESS, this)
        fun String.success(): RequestState = RequestState(Status.SUCCESS, this.literal())
        fun String.success(vararg args: Any): RequestState = RequestState(Status.SUCCESS, this.literal(args))

        fun ITextComponent.pending(): RequestState = RequestState(Status.PENDING, this)
        fun String.pending(): RequestState = RequestState(Status.PENDING, this.literal())
        fun String.pending(vararg args: Any): RequestState = RequestState(Status.PENDING, this.literal(args))

        fun ITextComponent.failed(): RequestState = RequestState(Status.FAILED, this)
        fun String.failed(): RequestState = RequestState(Status.FAILED, this.literal())
        fun String.failed(vararg args: Any): RequestState = RequestState(Status.FAILED, this.literal(args))
    }
}
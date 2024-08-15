package com.sleepwalker.sleeplib.util.serialization

import net.minecraft.network.PacketBuffer
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import javax.annotation.Nonnull

fun PacketBuffer.writeDecimal(bigDecimal: BigDecimal) {
    this.writeByteArray(bigDecimal.unscaledValue().toByteArray())
    this.writeVarInt(bigDecimal.scale())
}

fun PacketBuffer.readDecimal(context: MathContext): BigDecimal {
    val value = BigInteger(this.readByteArray())
    return BigDecimal(value, this.readVarInt(), context)
}

fun PacketBuffer.readDecimal(): BigDecimal {
    val value = BigInteger(this.readByteArray())
    return BigDecimal(value, this.readVarInt())
}

fun <K, V> PacketBuffer.writeMap(map: Map<K, V>, keyWriter: PacketBuffer.(K) -> Unit, valueWriter: PacketBuffer.(V) -> Unit) {
    this.writeVarInt(map.size)
    map.forEach { (k: K, v: V) ->
        keyWriter(this, k)
        valueWriter(this, v)
    }
}

fun <K, V> PacketBuffer.readMap(keyReader: PacketBuffer.() -> K, valueReader: PacketBuffer.() -> V): MutableMap<K, V> {
    val size = this.readVarInt()
    val map: MutableMap<K, V> = HashMap(size)
    for (i in 0 until size) {
        map[keyReader(this)] = valueReader(this)
    }
    return map
}

fun <K, V, M : MutableMap<K, V>> PacketBuffer.readMap(map: M, keyReader: PacketBuffer.() -> K, valueReader: PacketBuffer.() -> V): M {
    val size = this.readVarInt()
    for (i in 0 until size) {
        map.put(keyReader(this), valueReader(this))
    }
    return map
}

fun <V> PacketBuffer.writeCollection(collection: Collection<V>, valueWriter: PacketBuffer.(V) -> Unit) {
    this.writeVarInt(collection.size)
    collection.forEach { valueWriter(it) }
}

fun <V, C : MutableCollection<V>> PacketBuffer.readCollection(collection: C, valueReader: PacketBuffer.() -> V): C {
    val size = this.readVarInt()
    for (i in 0 until size) {
        collection.add(valueReader(this))
    }
    return collection
}

fun <V> PacketBuffer.readArrayList(valueReader: PacketBuffer.() -> V): MutableList<V> {
    return this.readCollection(ArrayList(), valueReader)
}

@Nonnull
fun <V> PacketBuffer.readHashSet(valueReader: PacketBuffer.() -> V): MutableSet<V> {
    return this.readCollection(HashSet(), valueReader)
}

fun PacketBuffer.writeStringArray(array: Array<String>) {
    this.writeVarInt(array.size)
    for (str in array) {
        this.writeString(str)
    }
}

fun PacketBuffer.readStringArray(): Array<String> {
    val len = this.readVarInt()
    val array = Array(len) { "" }
    for (i in 0 until len) {
        array[i] = this.readString()
    }
    return array
}

fun <T> PacketBuffer.writeNullable(value: T?, writer: PacketBuffer.(T) -> Unit) {
    this.writeBoolean(value != null)
    if (value != null) {
        writer(this, value)
    }
}

fun <T> PacketBuffer.readNullable(reader: PacketBuffer.() -> T): T? {
    return if (this.readBoolean()) reader(this) else null
}
package com.sleepwalker.sleeplib.util.serialization

import net.minecraft.nbt.*
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.util.Constants
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import java.awt.Color
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.collections.Collection

fun <T : Enum<T>> CompoundNBT.getEnum(name: String, def: T): T {
    if(contains(name, Constants.NBT.TAG_STRING)){
        try {
            return java.lang.Enum.valueOf(def.declaringJavaClass, getString(name))
        }
        catch (ignore: IllegalArgumentException){
        }
    }
    return def;
}

fun <T : Enum<T>> CompoundNBT.putEnum(name: String, value: T) {
    putString(name, value.name)
}

fun <T : IForgeRegistryEntry<T>> CompoundNBT.putRegistryEntry(name: String, value: T) {
    putString(name, Objects.requireNonNull(value.registryName).toString())
}


fun <T : IForgeRegistryEntry<T>> CompoundNBT.getRegistryEntry(name: String, registry: IForgeRegistry<T>, defKey: ResourceLocation?): T? {
    if (contains(name, Constants.NBT.TAG_STRING)) {
        val loc = ResourceLocation.tryCreate(getString(name))
        if (loc != null) {
            val type = registry.getValue(loc)
            if (type != null) {
                return type
            }
        }
    }
    return if (defKey != null) registry.getValue(defKey) else null
}

fun <T : IForgeRegistryEntry<T>> CompoundNBT.getRegistryEntry(name: String, registry: IForgeRegistry<T>): T {
    return this.getRegistryEntry(name, registry, registry.defaultKey)!!
}

fun <T : IForgeRegistryEntry<T>> CompoundNBT.getRegistryEntry(name: String, registry: () -> IForgeRegistry<T>): T {
    return this.getRegistryEntry(name, registry())
}

fun CompoundNBT.putBigInteger(name: String, value: BigInteger) {
    this.putString(name, value.toString())
}

fun CompoundNBT.getBigInteger(name: String, def: BigInteger?): BigInteger? {
    if (this.contains(name)) {
        val nbt = this[name]
        if (nbt is NumberNBT) {
            return BigInteger.valueOf(nbt.long)
        } else if (nbt is StringNBT) {
            return BigInteger(nbt.getString())
        }
    }
    return def
}

fun CompoundNBT.getBigInteger(name: String): BigInteger? {
    return this.getBigInteger(name, null)
}

fun CompoundNBT.putBigDecimal(name: String, value: BigDecimal) {
    this.putString(name, value.toString())
}

fun CompoundNBT.getBigDecimal(name: String, def: BigDecimal?): BigDecimal? {
    if (this.contains(name)) {
        val nbt = this[name]
        if (nbt is NumberNBT) {
            return BigDecimal.valueOf(nbt.double)
        } else if (nbt is StringNBT) {
            return BigDecimal(nbt.getString())
        }
    }
    return def
}

fun CompoundNBT.getBigDecimal(name: String): BigDecimal? {
    return this.getBigDecimal(name, null)
}

fun CompoundNBT.putColor(name: String, value: Color) {
    this.putString(name, "#" + java.lang.Long.toHexString((if (value.alpha == 255) value.rgb and 0xFFFFFF else value.rgb).toLong()))
}

fun CompoundNBT.getColor(name: String, def: Color?): Color? {
    if (this.contains(name)) {
        val nbt = this[name]
        if (nbt is StringNBT) {
            var `val` = nbt.getString()
            if (`val`.startsWith("#")) {
                `val` = `val`.substring(1)
                try {
                    val i = `val`.toLong(16)
                    return Color(
                        (i shr 16 and 0xFFL).toFloat(),
                        (i shr 8 and 0xFFL).toFloat(),
                        (i and 0xFFL).toFloat(),
                        (i shr 24 and 0xFFL).toFloat()
                    )
                } catch (ignore: NumberFormatException) {
                }
            }
            return Color.decode(`val`)
        } else if (nbt is NumberNBT) {
            try {
                val i = nbt.long
                return Color(
                    (i shr 16 and 0xFFL).toFloat(),
                    (i shr 8 and 0xFFL).toFloat(),
                    (i and 0xFFL).toFloat(),
                    (i shr 24 and 0xFFL).toFloat()
                )
            } catch (ignore: NumberFormatException) {
            }
        }
    }
    return def
}

fun CompoundNBT.getColor(name: String): Color? {
    return this.getColor(name, null)
}

fun CompoundNBT.putBlockPos(name: String, value: BlockPos) {
    putLong(name, value.toLong())
}

fun CompoundNBT.getBlockPosList(name: String): List<BlockPos> {
    val positions = mutableListOf<BlockPos>()
    val list = getList(name, Constants.NBT.TAG_LONG)
    for (i in list.indices){
        positions.add(list.getBlockPos(i))
    }
    return positions
}

fun CompoundNBT.putBlockPosCollection(name: String, positions: Collection<BlockPos>) {
    val list = ListNBT()
    for (pos in positions){
        list.addBlockPos(pos)
    }
    put(name, list)
}

fun ListNBT.addBlockPos(value: BlockPos){
    add(LongNBT.valueOf(value.toLong()))
}

fun ListNBT.getBlockPos(index: Int): BlockPos {
    return BlockPos.fromLong((get(index) as LongNBT).long)
}

fun CompoundNBT.getBlockPosOr(name: String, blockPos: BlockPos): BlockPos {
    return if(contains(name)) BlockPos.fromLong(getLong(name)) else blockPos
}

fun CompoundNBT.getBlockPosOrNull(name: String): BlockPos? {
    return if(contains(name)) BlockPos.fromLong(getLong(name)) else null
}
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

fun <T : Enum<T>> CompoundNBT.getEnum(name: String, def: T): T {
    if(this.contains(name, Constants.NBT.TAG_STRING)){
        try {
            return java.lang.Enum.valueOf(def.declaringJavaClass, def.name);
        }
        catch (ignore: IllegalArgumentException){
        }
    }
    return def;
}

fun <T : Enum<T>> CompoundNBT.putEnum(name: String, value: T) {
    this.putString(name, value.name)
}

fun <T : IForgeRegistryEntry<T>> CompoundNBT.putRegistryEntry(name: String, value: T) {
    this.putString(name, Objects.requireNonNull(value.registryName).toString())
}


fun <T : IForgeRegistryEntry<T>> CompoundNBT.getRegistryEntry(name: String, registry: IForgeRegistry<T>, defKey: ResourceLocation?): T? {
    if (this.contains(name, Constants.NBT.TAG_STRING)) {
        val loc = ResourceLocation.tryCreate(this.getString(name))
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
    this.putIntArray(name, intArrayOf(value.x, value.y, value.z))
}

fun CompoundNBT.getBlockPos(name: String, blockPos: BlockPos?): BlockPos? {
    if (this.contains(name)) {
        val list = this[name]
        if (list is ListNBT) {
            if (list.size == 3) {
                return BlockPos(list.getInt(0), list.getInt(1), list.getInt(2))
            }
        } else if (list is IntArrayNBT) {
            val array = this.getIntArray(name)
            if (array.size == 3) {
                return BlockPos(array[0], array[1], array[2])
            }
        } else if (list is LongArrayNBT) {
            val array = this.getLongArray(name)
            if (array.size == 3) {
                return BlockPos(array[0].toDouble(), array[1].toDouble(), array[2].toDouble())
            }
        }
    }
    return blockPos
}

fun CompoundNBT.getBlockPos(name: String): BlockPos? {
    return this.getBlockPos(name, null)
}
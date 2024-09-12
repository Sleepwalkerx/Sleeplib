package com.sleepwalker.sleeplib.util.serialization

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.nbt.*

fun INBT.toJson(): JsonElement {
    return when(this){
        is StringNBT -> JsonPrimitive(string)
        is NumberNBT -> JsonPrimitive(asNumber)
        is CollectionNBT<*> -> JsonArray().apply {
            for (nbt in this@toJson) {
                this.add(nbt.toJson())
            }
        }
        is CompoundNBT -> JsonObject().apply {
            for (key in this@toJson.keySet()){
                this.add(key, this@toJson[key]!!.toJson())
            }
        }
        else -> throw RuntimeException("Unknown INBT type -> " + type.name)
    }
}
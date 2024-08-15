package com.sleepwalker.sleeplib.util.serialization

import com.google.gson.*
import com.google.gson.stream.JsonWriter
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.sleepwalker.sleeplib.util.ObjectResourceLocation
import net.minecraft.item.ItemStack
import net.minecraft.nbt.JsonToNBT
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import java.awt.Color
import java.io.*
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.util.*
import javax.annotation.Nonnull

val GSON: Gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
val GSON_WITHOUT_PRETTY: Gson = GsonBuilder().disableHtmlEscaping().create()

@Throws(IOException::class)
fun toJson(gson: Gson, jsonObject: JsonObject, file: File) {
    FileOutputStream(file).use { fileOutputStream ->
        OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8).use { writer ->
            JsonWriter(writer).use { jsonWriter ->
                jsonWriter.setIndent("    ")
                gson.toJson(jsonObject, jsonWriter)
            }
        }
    }
}

@Throws(IOException::class)
fun toJson(jsonObject: JsonObject, file: File) {
    toJson(GSON, jsonObject, file)
}

@Throws(IOException::class)
fun fromJsonAsObject(file: File): JsonObject {
    FileInputStream(file).use { fileInputStream ->
        InputStreamReader(fileInputStream, StandardCharsets.UTF_8).use { reader ->
            return fromJsonAsObject(reader)
        }
    }
}

@Throws(IOException::class)
fun fromJsonAsArray(file: File): JsonArray {
    FileInputStream(file).use { fileInputStream ->
        InputStreamReader(fileInputStream, StandardCharsets.UTF_8).use { reader ->
            return fromJsonAsArray(reader)
        }
    }
}

@Throws(JsonSyntaxException::class, JsonIOException::class)
fun fromJsonAsObject(gson: Gson, reader: Reader): JsonObject {
    val jsonObject = gson.fromJson(reader, JsonObject::class.java)
    return jsonObject ?: JsonObject()
}

@Throws(JsonSyntaxException::class, JsonIOException::class)
fun fromJsonAsObject(reader: Reader): JsonObject {
    return fromJsonAsObject(GSON, reader)
}

@Throws(JsonSyntaxException::class, JsonIOException::class)
fun fromJsonAsObject(gson: Gson, json: String): JsonObject {
    val jsonObject = gson.fromJson(json, JsonObject::class.java)
    return jsonObject ?: JsonObject()
}

@Throws(JsonSyntaxException::class, JsonIOException::class)
fun fromJsonAsObject(json: String): JsonObject {
    return fromJsonAsObject(GSON, json)
}

@Throws(JsonSyntaxException::class, JsonIOException::class)
fun fromJsonAsArray(gson: Gson, reader: Reader): JsonArray {
    val jsonArray = gson.fromJson(reader, JsonArray::class.java)
    return jsonArray ?: JsonArray()
}

@Throws(JsonSyntaxException::class, JsonIOException::class)
fun fromJsonAsArray(reader: Reader): JsonArray {
    return fromJsonAsArray(GSON, reader)
}

@Throws(JsonSyntaxException::class, JsonIOException::class)
fun fromJsonAsArray(gson: Gson, json: String): JsonArray {
    val jsonArray = gson.fromJson(json, JsonArray::class.java)
    return jsonArray ?: JsonArray()
}

@Throws(JsonSyntaxException::class, JsonIOException::class)
fun fromJsonAsArray(json: String): JsonArray {
    return fromJsonAsArray(GSON, json)
}

@JvmOverloads
fun JsonObject.getObjectOrCreate(name: String, def: () -> JsonObject = { JsonObject() }): JsonObject {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonObject) {
            return element.getAsJsonObject()
        }
    }
    val `object` = def()
    this.add(name, `object`)
    return `object`
}

@JvmOverloads
fun JsonObject.getArrayOrCreate(name: String, def: () -> JsonArray = { JsonArray() }): JsonArray {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonArray) {
            return element.getAsJsonArray()
        }
    }
    val array = def()
    this.add(name, array)
    return array
}

fun JsonObject.getStringOrCreate(name: String, def: String): String {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isString) {
                return primitive.asString
            }
        }
    }
    this.addProperty(name, def)
    return def
}

fun <E : Enum<E>> JsonObject.getEnumOrCreate(name: String, def: E): E {
    if(this.has(name)){
        val element = this.get(name)
        if(element.isJsonPrimitive){
            val primitive = element.getAsJsonPrimitive()
            if(primitive.isString){
                return try {
                    java.lang.Enum.valueOf(def.declaringJavaClass, primitive.asString);
                } catch (ignore: IllegalArgumentException){
                    def
                }
            }
        }
    }
    this.addProperty(name, def.name)
    return def
}

fun <T : IForgeRegistryEntry<T>> JsonObject.getRegistryEntryOrCreate(name: String, registry: IForgeRegistry<T>, defKey: ResourceLocation): T {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isString) {
                val loc = ResourceLocation.tryCreate(primitive.asString)
                if (loc != null) {
                    val type = registry.getValue(loc)
                    if (type != null) {
                        return type
                    }
                }
            }
        }
    }
    val def = registry.getValue(defKey) ?: throw IllegalArgumentException("Registry ${registry.registryName} does not have a default value - $defKey")
    this.addProperty(name, defKey.toString())
    return def
}

fun <T : IForgeRegistryEntry<T>> JsonObject.getRegistryEntryOrCreate(name: String, registry: IForgeRegistry<T>): T {
    val defKey = registry.defaultKey ?: throw IllegalArgumentException("Registry ${registry.registryName} does not have a default key")
    return this.getRegistryEntryOrCreate(name, registry, defKey)
}

fun <T : IForgeRegistryEntry<T>> JsonObject.getRegistryEntryOrCreate(name: String, registry: () -> IForgeRegistry<T>): T {
    return this.getRegistryEntryOrCreate(name, registry())
}

fun getIntOrCreate(jsonObject: JsonObject, name: String?, def: Int): Int {
    if (jsonObject.has(name)) {
        val element = jsonObject[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isNumber) {
                return primitive.asInt
            }
        }
    }
    jsonObject.addProperty(name, def)
    return def
}

fun getLongOrCreate(jsonObject: JsonObject, name: String?, def: Long): Long {
    if (jsonObject.has(name)) {
        val element = jsonObject[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isNumber) {
                return primitive.asLong
            }
        }
    }
    jsonObject.addProperty(name, def)
    return def
}

fun getFloatOrCreate(jsonObject: JsonObject, name: String?, def: Float): Float {
    if (jsonObject.has(name)) {
        val element = jsonObject[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isNumber) {
                return primitive.asFloat
            }
        }
    }
    jsonObject.addProperty(name, def)
    return def
}

fun getDoubleOrCreate(jsonObject: JsonObject, name: String?, def: Double): Double {
    if (jsonObject.has(name)) {
        val element = jsonObject[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isNumber) {
                return primitive.asDouble
            }
        }
    }
    jsonObject.addProperty(name, def)
    return def
}


fun JsonObject.getBigIntegerOrCreate(name: String, def: BigInteger): BigInteger {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isNumber) {
                return primitive.asBigInteger
            }
        }
    }
    this.addProperty(name, def)
    return def
}


fun JsonObject.getDecimalOrCreate(name: String, def: BigDecimal): BigDecimal {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isNumber) {
                return primitive.asBigDecimal
            }
        }
    }
    this.addProperty(name, def)
    return def
}

fun JsonObject.getBoolOrCreate(name: String, def: Boolean): Boolean {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isBoolean) {
                return primitive.asBoolean
            }
        }
    }
    this.addProperty(name, def)
    return def
}


fun JsonObject.getResourceLocationOrCreate(name: String, def: ResourceLocation): ResourceLocation {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isString) {
                val resourceLocation = ResourceLocation.tryCreate(primitive.asString)
                if (resourceLocation != null) {
                    return resourceLocation
                }
            }
        }
    }
    this.addProperty(name, def.toString())
    return def
}

fun JsonObject.getObjectResourceLocationOrCreate(name: String, def: ObjectResourceLocation): ObjectResourceLocation {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isString) {
                val objectResourceLocation = ObjectResourceLocation.tryCreate(primitive.asString)
                if (objectResourceLocation != null) {
                    return objectResourceLocation
                }
            }
        }
    }
    this.addProperty(name, def.toString())
    return def
}

fun JsonObject.getColorOrCreate(name: String, @Nonnull def: Color): Color {
    if (this.has(name)) {
        val element = this[name]
        if (element.isJsonPrimitive) {
            val primitive = element.getAsJsonPrimitive()
            if (primitive.isString) {
                var `val` = primitive.asString
                if (`val`.startsWith("#")) {
                    `val` = `val`.substring(1)
                    try {
                        var i = `val`.toLong(16)
                        if (i <= 0xFFFFFFL) {
                            i += 0xFF000000L
                        }
                        return Color(
                            (i shr 16 and 0xFFL).toInt(),
                            (i shr 8 and 0xFFL).toInt(),
                            (i and 0xFFL).toInt(),
                            (i shr 24 and 0xFFL).toInt()
                        )
                    } catch (ignore: NumberFormatException) {
                    }
                }
                return Color.decode(primitive.asString)
            } else if (primitive.isNumber) {
                try {
                    var i = primitive.asLong
                    if (i <= 0xFFFFFFL) {
                        i += 0xFF000000L
                    }
                    return Color(
                        (i shr 16 and 0xFFL).toInt(),
                        (i shr 8 and 0xFFL).toInt(),
                        (i and 0xFFL).toInt(),
                        (i shr 24 and 0xFFL).toInt()
                    )
                } catch (ignore: NumberFormatException) {
                }
            }
        }
    }
    this.addProperty(name, "#" + Integer.toHexString(if (def.alpha == 255) def.rgb and 0xFFFFFF else def.rgb))
    return def
}

fun JsonObject.getPropertiesOrCreate(jsonObject: JsonObject, name: String, def: () -> Properties): Properties {
    if (jsonObject.has(name)) {
        val element = jsonObject[name]
        if (element.isJsonObject) {
            val properties = Properties()
            for ((key, value) in element.getAsJsonObject().entrySet()) {
                if (value.isJsonPrimitive) {
                    properties[key] = value.asString
                }
            }
            return properties
        }
    }
    val defObj = JsonObject()
    val defProperties = def()
    for ((key, value) in defProperties) {
        defObj.addProperty(key.toString(), value.toString())
    }
    jsonObject.add(name, defObj)
    return defProperties
}

fun JsonObject.getComponentOrCreate(name: String, def: ITextComponent): ITextComponent {
    if (this.has(name)) {
        val component: ITextComponent? = ITextComponent.Serializer.getComponentFromJson(this[name])
        if (component != null) {
            return component
        }
    }
    this.add(name, ITextComponent.Serializer.toJsonTree(def))
    return def
}

fun JsonElement.getAsIntOrCreate(def: Int): Int {
    if (this.isJsonPrimitive) {
        val primitive = this.getAsJsonPrimitive()
        if (primitive.isNumber) {
            return primitive.asInt
        }
    }
    return def
}

fun ItemStack.itemToJson(): JsonObject {
    val jsonObject = JsonObject()
    jsonObject.addProperty("id", Objects.requireNonNull(this.item.registryName).toString())
    jsonObject.addProperty("count", this.count)
    val tag = this.tag
    if (tag != null) {
        jsonObject.addProperty("nbt", tag.toString())
    }
    return jsonObject
}

fun ItemStack.itemToJsonString(): String {
    return GSON_WITHOUT_PRETTY.toJson(this.itemToJson())
}

fun JsonObject.getItemStackOrEmpty(): ItemStack {
    if (!this.has("id")) {
        return ItemStack.EMPTY
    }
    val element = this["id"]
    if (!(element.isJsonPrimitive && element.getAsJsonPrimitive().isString)) {
        return ItemStack.EMPTY
    }
    val id = ResourceLocation.tryCreate(element.asString) ?: return ItemStack.EMPTY
    val item = ForgeRegistries.ITEMS.getValue(id) ?: return ItemStack.EMPTY
    val stack = ItemStack(item, JSONUtils.getInt(this, "count", 1))
    if (this.has("nbt")) {
        val reader = StringReader(JSONUtils.getString(this, "nbt", ""))
        val jsonToNBT = JsonToNBT(reader)
        try {
            stack.setTag(jsonToNBT.readStruct())
        } catch (ignore: CommandSyntaxException) {
        }
    }
    return stack
}

fun parseItemStackOrEmpty(json: String): ItemStack {
    return try {
        GSON_WITHOUT_PRETTY.fromJson(json, JsonObject::class.java).getItemStackOrEmpty()
    } catch (ignore: JsonSyntaxException) {
        ItemStack.EMPTY
    }
}
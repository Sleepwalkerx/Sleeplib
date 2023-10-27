package com.sleepwalker.sleeplib.util.serialization;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.function.Supplier;

public final class JsonUtil {

    @Nonnull
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    @Nonnull
    public static final Gson GSON_WITHOUT_PRETTY = new GsonBuilder().disableHtmlEscaping().create();

    public static void toJson(@Nonnull JsonObject jsonObject, @Nonnull File file) throws IOException {
        try(
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            JsonWriter jsonWriter = new JsonWriter(writer)
        ) {
            jsonWriter.setIndent("    ");
            GSON.toJson(jsonObject, jsonWriter);
        }
    }

    @Nonnull
    public static JsonObject fromJsonAsObject(@Nonnull Reader json) throws JsonSyntaxException, JsonIOException {
        JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);
        return jsonObject == null ? new JsonObject() : jsonObject;
    }

    @Nonnull
    public static JsonObject fromJsonAsObject(@Nonnull String json) throws JsonSyntaxException, JsonIOException {
        JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);
        return jsonObject == null ? new JsonObject() : jsonObject;
    }

    @Nonnull
    public static JsonArray fromJsonAsArray(@Nonnull Reader json) throws JsonSyntaxException, JsonIOException {
        JsonArray jsonArray = GSON.fromJson(json, JsonArray.class);
        return jsonArray == null ? new JsonArray() : jsonArray;
    }

    @Nonnull
    public static JsonArray fromJsonAsArray(@Nonnull String json) throws JsonSyntaxException, JsonIOException {
        JsonArray jsonArray = GSON.fromJson(json, JsonArray.class);
        return jsonArray == null ? new JsonArray() : jsonArray;
    }

    @Nonnull
    public static JsonObject getObjectOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name){
        return getObjectOrCreate(jsonObject, name, JsonObject::new);
    }

    @Nonnull
    public static JsonObject getObjectOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull Supplier<JsonObject> def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonObject()){
                return element.getAsJsonObject();
            }
        }

        JsonObject object = def.get();
        jsonObject.add(name, object);
        return object;
    }

    @Nonnull
    public static JsonArray getArrayOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name){
        return getArrayOrCreate(jsonObject, name, JsonArray::new);
    }

    @Nonnull
    public static JsonArray getArrayOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull Supplier<JsonArray> defaultValue){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonArray()){
                return element.getAsJsonArray();
            }
        }

        JsonArray def = defaultValue.get();
        jsonObject.add(name, def);
        return def;
    }

    @Nonnull
    public static String getStringOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull String def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isString()){
                    return primitive.getAsString();
                }
            }
        }

        jsonObject.addProperty(name, def);
        return def;
    }

    @Nonnull
    public static <E extends Enum<E>> E getEnumOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull Class<E> classType, @Nonnull E def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isString()){
                    try {
                        return Enum.valueOf(classType, primitive.getAsString().toUpperCase(Locale.ROOT));
                    }
                    catch (IllegalArgumentException ignore){
                        return def;
                    }
                }
            }
        }

        jsonObject.addProperty(name, def.name().toLowerCase(Locale.ROOT));
        return def;
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntryOrCreate(
        @Nonnull JsonObject jsonObject, @Nonnull String name,
        @Nonnull Supplier<IForgeRegistry<T>> registry
    ){
        return getRegistryEntryOrCreate(jsonObject, name, registry.get());
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntryOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull IForgeRegistry<T> registry){

        ResourceLocation defKey = registry.getDefaultKey();
        if(defKey == null){
            throw new IllegalArgumentException(String.format("Registry %s does not have a default key", registry.getRegistryName()));
        }

        return getRegistryEntryOrCreate(jsonObject, name, registry, defKey);
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T getRegistryEntryOrCreate(
        @Nonnull JsonObject jsonObject, @Nonnull String name,
        @Nonnull IForgeRegistry<T> registry, @Nonnull ResourceLocation defKey
    ){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isString()){

                    ResourceLocation loc = ResourceLocation.tryParse(primitive.getAsString());

                    if(loc != null){
                        T type = registry.getValue(loc);
                        if(type != null){
                            return type;
                        }
                    }
                }
            }
        }

        T def = registry.getValue(defKey);
        if(def == null){
            throw new IllegalArgumentException(String.format("Registry %s does not have a default value - %s", registry.getRegistryName(), defKey));
        }

        jsonObject.addProperty(name, defKey.toString());
        return def;
    }

    public static int getIntOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, int def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isNumber()){
                    return primitive.getAsInt();
                }
            }
        }

        jsonObject.addProperty(name, def);
        return def;
    }

    public static long getLongOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, long def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isNumber()){
                    return primitive.getAsLong();
                }
            }
        }

        jsonObject.addProperty(name, def);
        return def;
    }

    public static float getFloatOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, float def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isNumber()){
                    return primitive.getAsFloat();
                }
            }
        }

        jsonObject.addProperty(name, def);
        return def;
    }

    public static double getDoubleOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, double def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isNumber()){
                    return primitive.getAsDouble();
                }
            }
        }

        jsonObject.addProperty(name, def);
        return def;
    }

    @Nonnull
    public static BigInteger getBigIntegerOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull BigInteger def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isNumber()){
                    return primitive.getAsBigInteger();
                }
            }
        }

        jsonObject.addProperty(name, def);
        return def;
    }

    @Nonnull
    public static BigDecimal getDecimalOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull BigDecimal def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isNumber()){
                    return primitive.getAsBigDecimal();
                }
            }
        }

        jsonObject.addProperty(name, def);
        return def;
    }

    public static boolean getBoolOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, boolean def){

        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isBoolean()){
                    return primitive.getAsBoolean();
                }
            }
        }

        jsonObject.addProperty(name, def);
        return def;
    }

    public static int convertToInt(@Nonnull JsonElement element, int def){
        if(element.isJsonPrimitive()){
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if(primitive.isNumber()){
                return primitive.getAsInt();
            }
        }
        return def;
    }
}

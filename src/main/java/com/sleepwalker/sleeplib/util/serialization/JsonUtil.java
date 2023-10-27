package com.sleepwalker.sleeplib.util.serialization;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;

public final class JsonUtil {

    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    public static final Gson GSON_WITHOUT_PRETTY = new GsonBuilder().disableHtmlEscaping().create();

    public static void toJson(@Nonnull Gson gson, @Nonnull JsonObject jsonObject, @Nonnull File file) throws IOException {
        try(
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            JsonWriter jsonWriter = new JsonWriter(writer)
        ) {
            jsonWriter.setIndent("    ");
            gson.toJson(jsonObject, jsonWriter);
        }
    }

    public static void toJson(@Nonnull JsonObject jsonObject, @Nonnull File file) throws IOException {
        toJson(GSON, jsonObject, file);
    }

    @Nonnull
    public static JsonObject fromJsonAsObject(@Nonnull File file) throws IOException {
        try(
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)
        ) {
            return fromJsonAsObject(reader);
        }
    }

    @Nonnull
    public static JsonArray fromJsonAsArray(@Nonnull File file) throws IOException {
        try(
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)
        ) {
            return fromJsonAsArray(reader);
        }
    }

    @Nonnull
    public static JsonObject fromJsonAsObject(@Nonnull Gson gson, @Nonnull Reader reader) throws JsonSyntaxException, JsonIOException {
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        return jsonObject == null ? new JsonObject() : jsonObject;
    }

    @Nonnull
    public static JsonObject fromJsonAsObject(@Nonnull Reader reader) throws JsonSyntaxException, JsonIOException {
        return fromJsonAsObject(GSON, reader);
    }

    @Nonnull
    public static JsonObject fromJsonAsObject(@Nonnull Gson gson, @Nonnull String json) throws JsonSyntaxException, JsonIOException {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        return jsonObject == null ? new JsonObject() : jsonObject;
    }

    @Nonnull
    public static JsonObject fromJsonAsObject(@Nonnull String json) throws JsonSyntaxException, JsonIOException {
        return fromJsonAsObject(GSON, json);
    }

    @Nonnull
    public static JsonArray fromJsonAsArray(@Nonnull Gson gson, @Nonnull Reader reader) throws JsonSyntaxException, JsonIOException {
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
        return jsonArray == null ? new JsonArray() : jsonArray;
    }

    @Nonnull
    public static JsonArray fromJsonAsArray(@Nonnull Reader reader) throws JsonSyntaxException, JsonIOException {
        return fromJsonAsArray(GSON, reader);
    }

    @Nonnull
    public static JsonArray fromJsonAsArray(@Nonnull Gson gson, @Nonnull String json) throws JsonSyntaxException, JsonIOException {
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);
        return jsonArray == null ? new JsonArray() : jsonArray;
    }

    @Nonnull
    public static JsonArray fromJsonAsArray(@Nonnull String json) throws JsonSyntaxException, JsonIOException {
        return fromJsonAsArray(GSON, json);
    }

    @Nonnull
    public static JsonObject getObjectOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name){
        return getObjectOrCreate(jsonObject, name, JsonObject::new);
    }

    @Nonnull
    public static JsonObject getObjectOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull JsonObject def){
        return getObjectOrCreate(jsonObject, name, () -> def);
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
    public static <E extends Enum<E>> E getEnumOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull E def){
        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isString()){
                    try {
                        return Enum.valueOf(def.getDeclaringClass(), primitive.getAsString().toUpperCase(Locale.ROOT));
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

                    ResourceLocation loc = ResourceLocation.tryCreate(primitive.getAsString());

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

    @Nonnull
    public static ResourceLocation getResourceLocationOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull ResourceLocation def){
        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isString()){
                    ResourceLocation resourceLocation = ResourceLocation.tryCreate(primitive.getAsString());
                    if(resourceLocation != null){
                        return resourceLocation;
                    }
                }
            }
        }

        jsonObject.addProperty(name, def.toString());
        return def;
    }

    @Nonnull
    public static Color getColorOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull Color def){
        if(jsonObject.has(name)){
            JsonElement element = jsonObject.get(name);
            if(element.isJsonPrimitive()){
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if(primitive.isString()){
                    String val = primitive.getAsString();
                    if(val.startsWith("#")){
                        val = val.substring(1);
                        try {
                            long i = Long.parseLong(val, 16);
                            return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, (i >> 24) & 0xFF);
                        }
                        catch (NumberFormatException ignore){}
                    }
                    return Color.decode(primitive.getAsString());
                }
                else if(primitive.isNumber()){
                    try {
                        long i = primitive.getAsLong();
                        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, (i >> 24) & 0xFF);
                    }
                    catch (NumberFormatException ignore){}
                }
            }
        }

        jsonObject.addProperty(name, "#" + Integer.toHexString(def.getAlpha() == 255 ? def.getRGB() & 0xFFFFFF : def.getRGB()));
        return def;
    }

    @Nonnull
    public static Properties getPropertiesOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull Supplier<Properties> defSupplier){
        if (jsonObject.has(name)) {
            JsonElement element = jsonObject.get(name);
            if(element.isJsonObject()){
                Properties properties = new Properties();
                for(Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()){
                    if(entry.getValue().isJsonPrimitive()){
                        properties.put(entry.getKey(), entry.getValue().getAsString());
                    }
                }
                return properties;
            }
        }

        JsonObject def = new JsonObject();
        Properties defProperties = defSupplier.get();
        for(Map.Entry<Object, Object> entry : defProperties.entrySet()){
            def.addProperty(entry.getKey().toString(), entry.getValue().toString());
        }
        jsonObject.add(name, def);
        return defProperties;
    }

    @Nonnull
    public static ITextComponent getComponentOrCreate(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nonnull ITextComponent def){
        if(jsonObject.has(name)){
            ITextComponent component = ITextComponent.Serializer.getComponentFromJson(jsonObject.get(name));
            if(component != null){
                return component;
            }
        }

        jsonObject.add(name, ITextComponent.Serializer.toJsonTree(def));
        return def;
    }

    public static int getAsIntOrCreate(@Nonnull JsonElement element, int def){
        if(element.isJsonPrimitive()){
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if(primitive.isNumber()){
                return primitive.getAsInt();
            }
        }
        return def;
    }

    @Nonnull
    public static String itemToJsonString(@Nonnull ItemStack stack){
        return JsonUtil.GSON_WITHOUT_PRETTY.toJson(itemToJson(stack));
    }

    @Nonnull
    public static JsonObject itemToJson(@Nonnull ItemStack stack){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
        jsonObject.addProperty("count", stack.getCount());

        CompoundNBT tag = stack.getTag();
        if(tag != null){
            jsonObject.addProperty("nbt", tag.toString());
        }

        return jsonObject;
    }

    @Nonnull
    public static ItemStack parseItem(@Nonnull String json){
        try {
            return parseItem(JsonUtil.GSON_WITHOUT_PRETTY.fromJson(json, JsonObject.class));
        }
        catch (JsonSyntaxException ignore){
            return ItemStack.EMPTY;
        }
    }

    @Nonnull
    public static ItemStack parseItem(@Nonnull JsonObject json){
        if(!json.has("id")){
            return ItemStack.EMPTY;
        }

        JsonElement element = json.get("id");
        if(!(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString())){
            return ItemStack.EMPTY;
        }

        ResourceLocation id = ResourceLocation.tryCreate(element.getAsString());
        if(id == null){
            return ItemStack.EMPTY;
        }

        Item item = ForgeRegistries.ITEMS.getValue(id);
        if(item == null){
            return ItemStack.EMPTY;
        }

        ItemStack stack = new ItemStack(item, JSONUtils.getInt(json, "count", 1));
        if(json.has("nbt")){
            com.mojang.brigadier.StringReader reader = new StringReader(JSONUtils.getString(json, "nbt", ""));
            JsonToNBT jsonToNBT = new JsonToNBT(reader);

            try {
                stack.setTag(jsonToNBT.readStruct());
            }
            catch (CommandSyntaxException ignore){
            }
        }

        return stack;
    }
}

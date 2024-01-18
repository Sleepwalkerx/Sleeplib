package com.sleepwalker.sleeplib.util;

import com.google.gson.*;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Objects;

public class ObjectResourceLocation implements Comparable<ObjectResourceLocation> {

    public static final Codec<ObjectResourceLocation> CODEC = Codec.STRING.comapFlatMap(ObjectResourceLocation::decodeResourceLocation, ObjectResourceLocation::toString).stable();
    private static final SimpleCommandExceptionType INVALID_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("argument.id.invalid"));
    protected final String object;
    protected final String namespace;
    protected final String path;

    protected ObjectResourceLocation(@Nonnull String[] resourceParts) {
        if(resourceParts.length != 3){
            throw new ObjectResourceLocationException("ObjectResourceLocation is invalide");
        }

        this.object = resourceParts[0];
        this.namespace = resourceParts[1];
        this.path = resourceParts[2];
        if(object.isEmpty() || !isValidObject(this.object)){
            throw new ObjectResourceLocationException("Non [a-z0-9_.-] character in object of location: " + this.object + ':' + this.namespace + ':' + this.path);
        }
        else if (namespace.isEmpty() || !isValidNamespace(this.namespace)) {
            throw new ObjectResourceLocationException("Non [a-z0-9_.-] character in namespace of location: " + this.object + ':' + this.namespace + ':' + this.path);
        }
        else if (path.isEmpty() || !isPathValid(this.path)) {
            throw new ObjectResourceLocationException("Non [a-z0-9/._-] character in path of location: " + this.object + ':' + this.namespace + ':' + this.path);
        }
    }

    public ObjectResourceLocation(@Nonnull String objectResourceName) {
        this(decompose(objectResourceName, ':'));
    }

    public ObjectResourceLocation(@Nonnull String objectIn, @Nonnull String namespaceIn, @Nonnull String pathIn) {
        this(new String[]{objectIn, namespaceIn, pathIn});
    }

    public ObjectResourceLocation(@Nonnull String objectIn, @Nonnull ResourceLocation resourceLocation) {
        this(new String[]{objectIn, resourceLocation.getNamespace(), resourceLocation.getPath()});
    }

    /**
     * Constructs a ResourceLocation by splitting a String representation of a valid location on a specified character.
     */
    @Nonnull
    public static ObjectResourceLocation create(@Nonnull String objectResourceName, char splitOn) {
        return new ObjectResourceLocation(decompose(objectResourceName, splitOn));
    }

    @Nullable
    public static ObjectResourceLocation tryCreate(@Nonnull String string) {
        try {
            return new ObjectResourceLocation(string);
        }
        catch (ObjectResourceLocationException ignore) {
            return null;
        }
    }

    @Nonnull
    protected static String[] decompose(@Nonnull String objectResourceName, char splitOn) {
        return objectResourceName.split(String.valueOf(splitOn), 3);
    }

    private static DataResult<ObjectResourceLocation> decodeResourceLocation(String encoded) {
        try {
            return DataResult.success(new ObjectResourceLocation(encoded));
        }
        catch (ObjectResourceLocationException e) {
            return DataResult.error("Not a valid object resource location: " + encoded + " " + e.getMessage());
        }
    }

    @Nonnull
    public String getObject() {
        return object;
    }

    @Nonnull
    public String getPath() {
        return this.path;
    }

    @Nonnull
    public String getNamespace() {
        return this.namespace;
    }

    @Nonnull
    public ResourceLocation getResourceLocation(){
        return new ResourceLocation(namespace, path);
    }

    public String toString() {
        return this.object + ':' + this.namespace + ':' + this.path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectResourceLocation that = (ObjectResourceLocation) o;
        return Objects.equals(object, that.object) && Objects.equals(namespace, that.namespace) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, namespace, path);
    }

    public int compareTo(@NotNull ObjectResourceLocation other) {
        int i = this.path.compareTo(other.path);
        if (i == 0) {
            i = this.namespace.compareTo(other.namespace);
            if(i == 0){
                i = this.object.compareTo(other.object);
            }
        }

        return i;
    }

    @Nonnull
    public static ObjectResourceLocation read(@Nonnull StringReader reader) throws CommandSyntaxException {
        int i = reader.getCursor();

        while(reader.canRead() && isValidPathCharacter(reader.peek())) {
            reader.skip();
        }

        String s = reader.getString().substring(i, reader.getCursor());

        try {
            return new ObjectResourceLocation(s);
        }
        catch (ResourceLocationException resourcelocationexception) {
            reader.setCursor(i);
            throw INVALID_EXCEPTION.createWithContext(reader);
        }
    }

    public static boolean isValidPathCharacter(char charIn) {
        return charIn >= '0' && charIn <= '9' || charIn >= 'a' && charIn <= 'z' || charIn == '_' || charIn == ':' || charIn == '/' || charIn == '.' || charIn == '-';
    }

    /**
     * Checks if the path contains invalid characters.
     */
    private static boolean isPathValid(String pathIn) {
        for(int i = 0; i < pathIn.length(); ++i) {
            if (!validatePathChar(pathIn.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if given object only consists of allowed characters.
     */
    private static boolean isValidObject(@NotNull String objectIn) {
        for(int i = 0; i < objectIn.length(); ++i) {
            if (!validateObjectChar(objectIn.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if given namespace only consists of allowed characters.
     */
    private static boolean isValidNamespace(String namespaceIn) {
        for(int i = 0; i < namespaceIn.length(); ++i) {
            if (!validateNamespaceChar(namespaceIn.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validatePathChar(char charValue) {
        return charValue == '_' || charValue == '-' || charValue >= 'a' && charValue <= 'z' || charValue >= '0' && charValue <= '9' || charValue == '/' || charValue == '.';
    }

    private static boolean validateObjectChar(char charValue) {
        return charValue == '_' || charValue == '-' || charValue >= 'a' && charValue <= 'z' || charValue >= '0' && charValue <= '9' || charValue == '.';
    }

    private static boolean validateNamespaceChar(char charValue) {
        return charValue == '_' || charValue == '-' || charValue >= 'a' && charValue <= 'z' || charValue >= '0' && charValue <= '9' || charValue == '.';
    }

    public static class Serializer implements JsonDeserializer<ObjectResourceLocation>, JsonSerializer<ObjectResourceLocation> {
        public ObjectResourceLocation deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return new ObjectResourceLocation(JSONUtils.getString(json, "location"));
        }

        public JsonElement serialize(@NotNull ObjectResourceLocation src, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }
}

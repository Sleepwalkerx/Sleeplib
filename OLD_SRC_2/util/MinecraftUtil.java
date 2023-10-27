package com.sleepwalker.sleeplib.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class MinecraftUtil {

    @Nullable
    public static ResourceLocation tryParseResourceLocation(@Nonnull String resourceLocation, @Nonnull String defNamespace){
        if(resourceLocation.indexOf(':') == -1){
            return ResourceLocation.tryParse(defNamespace + ":" + resourceLocation);
        }
        else {
            return ResourceLocation.tryParse(resourceLocation);
        }
    }

    @Nonnull
    public static ResourceLocation parseResourceLocation(@Nonnull String resourceLocation, @Nonnull String defNamespace) throws ResourceLocationException {
        if(resourceLocation.indexOf(':') == -1){
            return new ResourceLocation(defNamespace + ":" + resourceLocation);
        }
        else {
            return new ResourceLocation(resourceLocation);
        }
    }
}

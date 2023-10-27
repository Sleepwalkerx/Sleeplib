package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class RuntimeDeserializerContext implements DeserializeContext {

    @Nonnull private final Map<ResourceLocation, Drawable> drawableCache = new HashMap<>();

    @Nullable
    @Override
    public Drawable findDrawableCache(@Nonnull ResourceLocation resourceLocation) {
        return drawableCache.get(resourceLocation);
    }

    @Override
    public void addDrawableToCache(@Nonnull ResourceLocation resourceLocation, @Nonnull Drawable drawable) {
        drawableCache.put(resourceLocation, drawable);
    }
}

package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SourceMapPropertySource implements PropertySource {

    @Nonnull private final Map<Property<?, ?>, PropertySource> sourceMap;

    public SourceMapPropertySource(@Nonnull Map<Property<?, ?>, PropertySource> sourceMap) {
        this.sourceMap = sourceMap;
    }

    @Nonnull
    public static Builder builder(){
        return new Builder();
    }

    @Override
    public void onStart() {
        sourceMap.values().forEach(PropertySource::onStart);
    }

    @Override
    public void onEnd() {
        sourceMap.values().forEach(PropertySource::onEnd);
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T object) {
        return sourceMap.getOrDefault(property, PropertySource.EMPTY).getPropertyValue(property, object);
    }

    @Override
    public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T object) {
        return sourceMap.getOrDefault(property, PropertySource.EMPTY).hasProperty(property, object);
    }

    public static class Builder implements Supplier<PropertySource> {

        @Nonnull private final Map<Property<?, ?>, PropertySource> sourceMap = new HashMap<>();

        @Nonnull
        public <T extends Widget, V> Builder put(@Nonnull Property<T, V> property, @Nonnull V value){
            return put(property, new ValuePropertySource(property, value));
        }

        @Nonnull
        public <T extends Widget, V> Builder put(@Nonnull Supplier<Property<T, V>> property, @Nonnull V value){
            return put(property.get(), value);
        }

        @Nonnull
        public <T extends Widget, V> Builder put(@Nonnull Property<T, V> property, @Nonnull PropertySource propertySource){
            sourceMap.put(property, propertySource);
            return this;
        }

        @Nonnull
        public <T extends Widget, V> Builder put(@Nonnull Supplier<Property<T, V>> property, @Nonnull PropertySource propertySource){
            return put(property.get(), propertySource);
        }

        @Nonnull
        public <T extends Widget, V> Builder put(@Nonnull Property<T, V> property, @Nonnull Supplier<PropertySource> propertySource){
            return put(property, propertySource.get());
        }

        @Nonnull
        public <T extends Widget, V> Builder put(@Nonnull Supplier<Property<T, V>> property, @Nonnull Supplier<PropertySource> propertySource){
            return put(property.get(), propertySource.get());
        }

        @Nonnull
        public SourceMapPropertySource build(){
            return new SourceMapPropertySource(Collections.unmodifiableMap(sourceMap));
        }

        @Override
        public PropertySource get() {
            return build();
        }
    }
}

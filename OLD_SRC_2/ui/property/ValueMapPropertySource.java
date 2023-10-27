package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ValueMapPropertySource implements PropertySource {

    @Nonnull private final Map<Property<?, ?>, Object> valueMap;

    public ValueMapPropertySource(@Nonnull Map<Property<?, ?>, Object> valueMap) {
        this.valueMap = valueMap;
    }

    @Nonnull
    public static Builder builder(){
        return new Builder();
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target) {
        Object value = valueMap.get(property);
        return value == null ? property.getDefaultValue(target) : property.getValueType().cast(value);
    }

    @Override
    public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target) {
        return valueMap.containsKey(property);
    }

    public static class Builder implements Supplier<PropertySource> {

        @Nonnull private final Map<Property<?, ?>, Object> propertyMap = new HashMap<>();

        public <T extends Widget, V> Builder put(@Nonnull Property<T, V> property, V value){
            propertyMap.put(property, value);
            return this;
        }

        public <T extends Widget, V> Builder put(@Nonnull Supplier<Property<T, V>> property, V value){
            return put(property.get(), value);
        }

        @Nonnull
        public ValueMapPropertySource build(){
            return new ValueMapPropertySource(Collections.unmodifiableMap(propertyMap));
        }

        @Override
        public PropertySource get() {
            return build();
        }
    }
}

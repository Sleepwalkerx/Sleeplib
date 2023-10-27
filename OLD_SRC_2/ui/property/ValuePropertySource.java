package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public class ValuePropertySource implements PropertySource {

    @Nonnull private final Property<?, ?> property;
    @Nonnull private final Object value;

    public <T extends Widget, V> ValuePropertySource(@Nonnull Property<T, V> property, @Nonnull V value) {
        this.property = property;
        this.value = value;
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target) {
        return this.property == property ? property.getValueType().cast(value) : property.getDefaultValue(target);
    }

    @Override
    public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target) {
        return this.property == property;
    }
}

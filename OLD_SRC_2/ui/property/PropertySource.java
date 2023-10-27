package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;

public interface PropertySource {

    PropertySource EMPTY = new PropertySource() {
        @Nonnull
        @Override
        public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target) {
            return property.getDefaultValue(target);
        }
        @Override
        public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target) {
            return false;
        }
    };

    default void onStart(){ }
    default void onEnd(){ }

    @Nonnull <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target);

    <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target);
}

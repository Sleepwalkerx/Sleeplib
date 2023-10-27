package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public interface PropertyManager {

    @Nonnull PropertyManager EMPTY = new EmptyPropertyManager();

    void fireStateChanged(@Nonnull WidgetState state, @Nonnull Widget target, boolean activity);
    default void fireStateActivated(@Nonnull WidgetState state, @Nonnull Widget target){
        fireStateChanged(state, target, true);
    }
    default void fireStateDisabled(@Nonnull WidgetState state, @Nonnull Widget target){
        fireStateChanged(state, target, false);
    }
    boolean getStateActivity(@Nonnull WidgetState state, @Nonnull Widget target);

    <T extends Widget, V> void setPropertySource(@Nonnull Property<T, V> property, @Nonnull PropertySource source, @Nonnull Widget target);
    @Nullable <T extends Widget, V> PropertySource getPropertySource(@Nonnull Property<T, V> property, @Nonnull Widget target);

    void setPropertySource(@Nonnull PropertySource propertySource, @Nonnull Widget target);
    @Nullable PropertySource getPropertySource(@Nonnull Widget target);

    <T extends Widget, V> void setPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target, @Nonnull V value);
    <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target);
    @Nonnull <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target);
    @Nonnull <T extends Widget, V> V getPropertyValue(@Nonnull Supplier<Property<T, V>> property, @Nonnull T target);

    class EmptyPropertyManager implements PropertyManager {

        @Override
        public void fireStateChanged(@Nonnull WidgetState state, @Nonnull Widget target, boolean activity) { }

        @Override
        public void fireStateActivated(@Nonnull WidgetState state, @Nonnull Widget target) { }

        @Override
        public void fireStateDisabled(@Nonnull WidgetState state, @Nonnull Widget target) { }

        @Override
        public boolean getStateActivity(@Nonnull WidgetState state, @Nonnull Widget target) {
            return false;
        }

        @Override
        public <T extends Widget, V> void setPropertySource(@Nonnull Property<T, V> property, @Nonnull PropertySource source, @Nonnull Widget target) { }

        @Nullable
        @Override
        public <T extends Widget, V> PropertySource getPropertySource(@Nonnull Property<T, V> property, @Nonnull Widget target) {
            return null;
        }

        @Override
        public void setPropertySource(@Nonnull PropertySource propertySource, @Nonnull Widget target) { }

        @Nullable
        @Override
        public PropertySource getPropertySource(@Nonnull Widget target) {
            return null;
        }

        @Override
        public <T extends Widget, V> void setPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target, @Nonnull V value) { }

        @Override
        public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target) {
            return false;
        }

        @Nonnull
        @Override
        public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target) {
            return property.getDefaultValue(target);
        }

        @Nonnull
        @Override
        public <T extends Widget, V> V getPropertyValue(@Nonnull Supplier<Property<T, V>> property, @Nonnull T target) {
            return getPropertyValue(property.get(), target);
        }
    }
}

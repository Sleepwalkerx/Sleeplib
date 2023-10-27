package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.function.Function;

public abstract class BaseProperty<T extends Widget, V> extends ForgeRegistryEntry<Property<?, ?>> implements Property<T, V> {

    @Nonnull private final DefaultValueSupplier<T, V> defaultValueSupplier;
    @Nonnull private final Class<T> hostType;
    @Nonnull private final Listener<T, V> listener;

    public BaseProperty(@Nonnull DefaultValueSupplier<T, V> defaultValueSupplier, @Nonnull Class<T> hostType, @Nonnull Listener<T, V> listener) {
        this.defaultValueSupplier = defaultValueSupplier;
        this.hostType = hostType;
        this.listener = listener;
    }

    @Override
    public void onSet(@Nonnull T object, @Nonnull V value) {
        listener.onPropertyUpdate(object, value);
    }

    @Override
    @Nonnull
    public V getDefaultValue(@Nonnull T object) {
        return defaultValueSupplier.apply(object);
    }

    @Override
    @Nonnull
    public Class<T> getHostType() {
        return hostType;
    }

    public interface Listener<T, V> {
        void onPropertyUpdate(@Nonnull T object, @Nonnull V value);
    }

    public interface DefaultValueSupplier<T, V> extends Function<T, V> {
        @Override
        @Nonnull V apply(@Nonnull T t);
    }

    public static class ConstDefaultValue<T, V> implements DefaultValueSupplier<T, V> {

        @Nonnull private final V value;

        public ConstDefaultValue(@Nonnull V value) {
            this.value = value;
        }

        @Nonnull
        @Override
        public V apply(@Nonnull T t) {
            return value;
        }
    }

    @Nonnull
    public static <T, V> ConstDefaultValue<T, V> constDefaultValue(@Nonnull V value){
        return new ConstDefaultValue<>(value);
    }
}

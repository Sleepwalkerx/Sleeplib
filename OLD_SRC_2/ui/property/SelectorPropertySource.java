package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class SelectorPropertySource implements PropertySource {

    @Nonnull private final Property<?, ?> property;
    @Nonnull private final Set<Entry> entries;

    public <T extends Widget, V> SelectorPropertySource(@Nonnull Property<T, V> property, @Nonnull Set<Entry> entries) {
        this.property = property;
        this.entries = entries;
    }

    @Nonnull
    public static <T extends Widget, V> Builder<T, V> builder(@Nonnull Property<T, V> property){
        return new Builder<>(property);
    }

    @Nonnull
    public static <T extends Widget, V> Builder<T, V> builder(@Nonnull Supplier<Property<T, V>> property){
        return builder(property.get());
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target) {

        if(this.property == property){
            PropertyManager propertyManager = target.getPropertyManager();
            for(Entry entry : entries){
                if(entry.isTriggered(target, propertyManager)){
                    return property.getValueType().cast(entry.getValue());
                }
            }
        }

        return property.getDefaultValue(target);
    }

    @Override
    public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target) {
        return this.property == property;
    }

    public static class Entry {

        @Nonnull private final Object2BooleanMap<WidgetState> states;
        @Nonnull private final Object value;

        public Entry(@Nonnull Object2BooleanMap<WidgetState> states, @Nonnull Object value) {
            this.states = states;
            this.value = value;
        }

        public <T extends Widget> boolean isTriggered(@Nonnull T target, @Nonnull PropertyManager propertyManager){
            return states.object2BooleanEntrySet().stream().allMatch(e -> e.getBooleanValue() == propertyManager.getStateActivity(e.getKey(), target));
        }

        @Nonnull
        public Object getValue() {
            return value;
        }
    }

    public static class Builder<T extends Widget, V> implements Supplier<PropertySource> {

        @Nonnull private final Property<T, V> property;
        @Nonnull private final Set<Entry> entries = new LinkedHashSet<>();

        private Entry entry;

        public Builder(@Nonnull Property<T, V> property) {
            this.property = property;
        }

        @Nonnull
        public Builder<T, V> forValue(@Nonnull V value){
            this.entry = new Entry(new Object2BooleanOpenHashMap<>(), value);
            entries.add(entry);
            return this;
        }

        @Nonnull
        public Builder<T, V> condition(@Nonnull WidgetState state, boolean activity){
            entry.states.put(state, activity);
            return this;
        }

        @Nonnull
        public SelectorPropertySource build(){

            Set<Entry> entries1 = new LinkedHashSet<>();
            for(Entry entry : entries){
                entries1.add(new Entry(Object2BooleanMaps.unmodifiable(entry.states), entry.value));
            }
            return new SelectorPropertySource(property, Collections.unmodifiableSet(entries1));
        }

        @Override
        public PropertySource get() {
            return build();
        }
    }
}

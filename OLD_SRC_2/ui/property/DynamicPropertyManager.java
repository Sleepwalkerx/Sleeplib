package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

public class DynamicPropertyManager implements PropertyManager {

    @Nonnull private final WeakHashMap<Widget, PropertySource> sourceProperties = new WeakHashMap<>();
    @Nonnull private final WeakHashMap<Widget, StateMap> states = new WeakHashMap<>();

    @Override
    public void fireStateChanged(@Nonnull WidgetState state, @Nonnull Widget target, boolean activity) {
        StateMap stateMap = states.computeIfAbsent(target, t -> new StateMap());
        stateMap.setStateActivity(state, activity);
    }

    @Override
    public boolean getStateActivity(@Nonnull WidgetState state, @Nonnull Widget target) {
        StateMap stateMap = states.get(target);
        return stateMap == null ? state.getDefaultActivity() : stateMap.getStateActivity(state);
    }

    @Override
    public <T extends Widget, V> void setPropertySource(@Nonnull Property<T, V> property, @Nonnull PropertySource source, @Nonnull Widget target) {

        PropertySource propertySource = sourceProperties.get(target);
        if(propertySource == null){
            DynamicDelegateSource dynamicDelegateSource = new DynamicDelegateSource(PropertySource.EMPTY);
            dynamicDelegateSource.setDynamicPropertySource(property, source);
            sourceProperties.put(target, dynamicDelegateSource);
        }
        else if(propertySource instanceof DynamicDelegateSource) {
            ((DynamicDelegateSource) propertySource).setDynamicPropertySource(property, source);
        }
        else {
            DynamicDelegateSource dynamicDelegateSource = new DynamicDelegateSource(propertySource);
            dynamicDelegateSource.setDynamicPropertySource(property, source);
            sourceProperties.put(target, dynamicDelegateSource);
        }
    }

    @Nullable
    @Override
    public <T extends Widget, V> PropertySource getPropertySource(@Nonnull Property<T, V> property, @Nonnull Widget target) {
        PropertySource propertySource = sourceProperties.get(target);
        if(propertySource instanceof DynamicDelegateSource){
            return ((DynamicDelegateSource) propertySource).dynamicSources.get(property);
        }
        return null;
    }

    @Override
    public void setPropertySource(@Nonnull PropertySource propertySource, @Nonnull Widget target) {

        PropertySource oldSource = sourceProperties.get(target);
        if(oldSource instanceof DynamicDelegateSource){
            ((DynamicDelegateSource) oldSource).setDelegate(propertySource);
        }
        else {
            PropertySource old = sourceProperties.put(target, propertySource);
            if(old != null){
                old.onEnd();
            }
            propertySource.onStart();
        }
    }

    @Nullable
    @Override
    public PropertySource getPropertySource(@Nonnull Widget target) {
        return sourceProperties.get(target);
    }

    @Override
    public <T extends Widget, V> void setPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target, @Nonnull V value) {
        setPropertySource(property, new ValuePropertySource(property, value), target);
    }

    @Override
    public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target) {
        return sourceProperties.getOrDefault(target, PropertySource.EMPTY).hasProperty(property, target);
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target) {
        return sourceProperties.getOrDefault(target, PropertySource.EMPTY).getPropertyValue(property, target);
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Supplier<Property<T, V>> property, @Nonnull T target) {
        return getPropertyValue(property.get(), target);
    }

    public static class StateMap {

        @Nonnull private final Object2BooleanMap<WidgetState> activityMap = new Object2BooleanOpenHashMap<>();

        public void setStateActivity(@Nonnull WidgetState state, boolean activity){
            activityMap.put(state, activity);
        }

        public boolean getStateActivity(@Nonnull WidgetState state){
            return activityMap.getOrDefault(state, state.getDefaultActivity());
        }
    }

    public static class DynamicDelegateSource implements PropertySource {

        @Nonnull private final Map<Property<?, ?>, PropertySource> dynamicSources = new HashMap<>();
        @Nonnull private PropertySource delegate;

        public DynamicDelegateSource(@Nonnull PropertySource delegate){
            this.delegate = delegate;
            delegate.onStart();
        }

        public void setDelegate(@Nonnull PropertySource delegate) {
            this.delegate.onEnd();
            this.delegate = delegate;
            delegate.onStart();
        }

        public <T extends Widget, V> void setDynamicPropertySource(@Nonnull Property<T, V> property, @Nonnull PropertySource source){
            PropertySource old = dynamicSources.put(property, source);
            source.onStart();
            if(old != null){
                old.onEnd();
            }
        }

        @Nonnull
        @Override
        public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target) {
            return dynamicSources.getOrDefault(property, delegate).getPropertyValue(property, target);
        }

        @Override
        public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target) {
            return dynamicSources.containsKey(property) || delegate.hasProperty(property, target);
        }
    }
}

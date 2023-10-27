package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.Root;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.layer.Layer;
import com.sleepwalker.sleeplib.ui.property.Property;
import com.sleepwalker.sleeplib.ui.property.PropertyManager;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializer;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public interface Widget {

    void draw(@Nonnull Canvas canvas);

    boolean isPointerOverUI(@Nonnull Pointer pointer);

    void setParent(@Nonnull WidgetGroup parent);
    @Nonnull WidgetGroup getParent();

    void setRoot(@Nonnull Root root);
    @Nonnull Root getRoot();

    void setPropertyManager(@Nonnull PropertyManager propertyManager);
    @Nonnull PropertyManager getPropertyManager();

    <T extends Widget, V> void setPropertyValue(@Nonnull Property<T, V> property, @Nonnull V value);
    <T extends Widget, V> void setPropertyValue(@Nonnull Supplier<Property<T, V>> property, @Nonnull V value);
    @Nonnull <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property);
    @Nonnull <T extends Widget, V> V getPropertyValue(@Nonnull Supplier<Property<T, V>> property);

    float getX();
    float getY();
    float getWidth();
    float getHeight();

    void setPosition(float x, float y);
    void updateSize();

    void setLayer(@Nonnull Layer layer);
    @Nonnull Layer getLayer();

    void setEnable(boolean enable);
    boolean isEnable();

    void setId(@Nonnull String id);
    @Nonnull String getId();
    void setName(@Nonnull String name);
    @Nonnull String getName();
    void setHierarchyName(@Nonnull String name);
    @Nonnull String getHierarchyName();

    @Nonnull WidgetSerializer<?> getSerializer();
}

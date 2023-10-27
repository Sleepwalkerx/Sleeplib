package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.Root;
import com.sleepwalker.sleeplib.ui.math.Dimension;
import com.sleepwalker.sleeplib.ui.property.Properties;
import com.sleepwalker.sleeplib.ui.property.Property;
import com.sleepwalker.sleeplib.ui.property.DynamicPropertyMap;
import com.sleepwalker.sleeplib.ui.property.SimpleDynamicPropertyMap;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializer;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializers;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class BaseWidget implements Widget {

    protected Root root;
    protected WidgetGroup parent;

    protected SimpleDynamicPropertyMap propertyMap;

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected float layer;

    protected String id;
    protected String name;
    protected String hierarchyName;

    public BaseWidget(){
        id = "widget";
        name = "Widget";
        hierarchyName = "/widget";
        propertyMap = new SimpleDynamicPropertyMap();
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property){
        return getDynamicPropertyMap().getPropertyValue(property, (T)this);
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Supplier<Property<T, V>> property) {
        return getPropertyValue(property.get());
    }

    public int getIntX(){
        return (int) x;
    }

    public int getIntY(){
        return (int) y;
    }

    public int getIntWidth(){
        return (int) width;
    }

    public int getIntHeight(){
        return (int) height;
    }

    @Override
    public boolean isPointerOverUI(@Nonnull Pointer pointer) {
        return
            getX() > pointer.getX() && getX() + getWidth() <= pointer.getX() &&
            getY() > pointer.getY() && getY() + getHeight() <= pointer.getY();
    }

    @Override
    public void setRoot(@Nonnull Root root) {
        this.root = root;
    }

    @Nonnull
    @Override
    public Root getRoot() {
        return root;
    }

    @Override
    public void setParent(@Nonnull WidgetGroup parent) {
        this.parent = parent;
    }

    @Nonnull
    @Override
    public WidgetGroup getParent() {
        return parent;
    }

    @Nonnull
    @Override
    public DynamicPropertyMap getDynamicPropertyMap() {
        return propertyMap;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void updateSize() {
        width = getPropertyValue(Properties.WIDTH).compute(this, Dimension.WIDTH);
        height = getPropertyValue(Properties.HEIGHT).compute(this, Dimension.HEIGHT);
    }

    @Override
    public void setLayer(float layer) {
        this.layer = layer;
        getParent().updateLayer();
    }

    @Override
    public float getLayer() {
        return layer;
    }

    @Override
    public void setId(@Nonnull String id) {
        this.id = id;
    }

    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setHierarchyName(@Nonnull String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }

    @Nonnull
    @Override
    public String getHierarchyName() {
        return hierarchyName;
    }

    @Nonnull
    @Override
    public WidgetSerializer<?> getSerializer() {
        return WidgetSerializers.BASIC.get();
    }
}

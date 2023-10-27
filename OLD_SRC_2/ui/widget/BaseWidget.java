package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.Root;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.layer.Layer;
import com.sleepwalker.sleeplib.ui.layer.Layers;
import com.sleepwalker.sleeplib.ui.layout.Dimension;
import com.sleepwalker.sleeplib.ui.property.*;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializer;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializers;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class BaseWidget implements Widget {

    protected Root root;
    protected WidgetGroup parent;

    protected PropertyManager propertyManager;

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected Layer layer;
    protected boolean enable;

    protected String id;
    protected String name;
    protected String hierarchyName;

    public BaseWidget(){
        id = "widget";
        name = "Widget";
        hierarchyName = "/widget";
        propertyManager = PropertyManager.EMPTY;
        layer = Layers.DEFAULT;
        enable = true;
    }

    @Override
    public <T extends Widget, V> void setPropertyValue(@Nonnull Property<T, V> property, @Nonnull V value) {
        getPropertyManager().setPropertyValue(property, property.getHostType().cast(this), value);
    }

    @Override
    public <T extends Widget, V> void setPropertyValue(@Nonnull Supplier<Property<T, V>> property, @Nonnull V value) {
        setPropertyValue(property.get(), value);
    }

    @Nonnull
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property){
        return getPropertyManager().getPropertyValue(property, property.getHostType().cast(this));
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Supplier<Property<T, V>> property) {
        return getPropertyValue(property.get());
    }

    protected void setupCanvas(@Nonnull Canvas canvas){
        canvas.setScale(getWidth(), getHeight());
        canvas.setPosition(getX(), getY(), getLayer().getOffset());
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
            pointer.getX() > getX() && getX() + getWidth() >= pointer.getX() &&
            pointer.getY() > getY() && getY() + getHeight() >= pointer.getY();
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

    public void setPropertyManager(@Nonnull PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
        propertyManager.fireStateChanged(WidgetStates.ENABLED, this, enable);
    }

    @Nonnull
    @Override
    public PropertyManager getPropertyManager() {
        return propertyManager;
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
        width = getPropertyValue(Properties.WIDTH).calculate(this, Dimension.WIDTH);
        height = getPropertyValue(Properties.HEIGHT).calculate(this, Dimension.HEIGHT);
    }

    @Override
    public void setLayer(@Nonnull Layer layer) {
        this.layer = layer;
        getParent().updateLayer();
    }

    @Nonnull
    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
        getPropertyManager().fireStateChanged(WidgetStates.ENABLED, this, enable);
    }

    @Override
    public boolean isEnable() {
        return enable;
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

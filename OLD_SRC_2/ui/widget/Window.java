package com.sleepwalker.sleeplib.ui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.ui.ConstPointer;
import com.sleepwalker.sleeplib.ui.DynamicPointer;
import com.sleepwalker.sleeplib.ui.Pointer;
import com.sleepwalker.sleeplib.ui.Root;
import com.sleepwalker.sleeplib.ui.event.PointerEvent;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.graphics.DynamicCanvas;
import com.sleepwalker.sleeplib.ui.layer.Layer;
import com.sleepwalker.sleeplib.ui.layer.LayerManager;
import com.sleepwalker.sleeplib.ui.layer.Layers;
import com.sleepwalker.sleeplib.ui.layer.SimpleLayerManager;
import com.sleepwalker.sleeplib.ui.listener.*;
import com.sleepwalker.sleeplib.ui.property.Property;
import com.sleepwalker.sleeplib.ui.property.PropertyManager;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializer;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializers;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class Window implements WidgetGroup, IGuiEventListener, IRenderable, Root {

    @Nonnull private final ListenerStack<PointerEvent> pointerDownEventListeners = ListenerStack.newStack();
    @Nonnull private final ListenerStack<PointerEvent> pointerUpEventListeners = ListenerStack.newStack();
    @Nonnull private final ListenerStack<PointerEvent> pointerMoveEventListeners = ListenerStack.newStack();
    @Nonnull private final ListenerStack<PointerEvent> pointerEnterEventListeners = ListenerStack.newStack();
    @Nonnull private final ListenerStack<PointerEvent> pointerOutEventListeners = ListenerStack.newStack();
    @Nonnull private final ListenerStack<PointerEvent> pointerScrollEventListeners = ListenerStack.newStack();

    @Nonnull private final SimpleLayerManager layerManager;

    private Screen screen;

    @Nonnull protected final DynamicCanvas canvas;
    @Nonnull protected final DynamicPointer pointer;

    private int x, y;
    private int width, height;

    private WidgetGroup content;
    private boolean entered, pinched;

    public Window(){
        canvas = new DynamicCanvas();
        pointer = new DynamicPointer();
        layerManager = new SimpleLayerManager();
        content = new EmptyWidget();
    }

    @Override
    public void render(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        canvas.setMatrix(ms);
        canvas.setPartialTick(pt);
        draw(canvas);
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {
        content.draw(canvas);
    }

    public void setContent(WidgetGroup content) {
        this.content = content;
    }

    public WidgetGroup getContent() {
        return content;
    }

    @Override
    public boolean isPointerOverUI(@Nonnull Pointer pointer) {
        return content.isPointerOverUI(pointer);
    }

    @Override
    public void mouseMoved(double x, double y) {

        pointer.setDelta(x - pointer.getX(), y - pointer.getY());
        pointer.setPosition(x, y);

        ConstPointer pointer = new ConstPointer(x, y, this.pointer.getDeltaX(), this.pointer.getDeltaY(), 0, -1);

        PointerEvent event = new PointerEvent(pointer, null);
        pointerMoveEventListeners.post(event);

        updateEnteredState(pointer);

        PointerMoveEventHandler.fireIfInstance(pointer, content);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {

        ConstPointer pointer = new ConstPointer(x, y, 0, 0, 0, button);
        updatePitchedState(pointer);

        PointerEvent event = new PointerEvent(pointer, null);
        pointerDownEventListeners.post(event);

        if(content.isPointerOverUI(pointer)){
            pinched = true;
            return PointerDownEventHandler.fireIfInstance(pointer, content);
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {

        ConstPointer pointer = new ConstPointer(x, y, 0, 0, 0, button);
        updatePitchedState(pointer);

        PointerEvent event = new PointerEvent(pointer, null);
        pointerUpEventListeners.post(event);

        return content.isPointerOverUI(pointer) && PointerUpEventHandler.fireIfInstance(pointer, content);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double delta) {

        ConstPointer pointer = new ConstPointer(x, y, 0, 0, delta, -1);

        PointerEvent event = new PointerEvent(pointer, null);
        pointerUpEventListeners.post(event);

        return content.isPointerOverUI(pointer) && PointerScrollEventHandler.fireIfInstance(pointer, content);
    }

    public void updateEnteredState(@Nonnull Pointer pointer){

        if(content.isPointerOverUI(pointer) != entered){

            if(entered){
                PointerOutEventHandler.fireIfInstance(pointer, content);
            }
            else {
                PointerEnterEventHandler.fireIfInstance(pointer, content);
            }

            entered = !entered;
        }
    }

    public void updatePitchedState(@Nonnull Pointer pointer){
        if(pinched){
            PointerReleasedEventHandler.fireIfInstance(pointer, content);
            pinched = false;
        }
    }

    @Override
    public void setParent(@Nonnull WidgetGroup parent) { }

    @Nonnull
    @Override
    public WidgetGroup getParent() {
        return this;
    }

    @Override
    public void setRoot(@Nonnull Root root) { }

    @Nonnull
    @Override
    public Root getRoot() {
        return this;
    }

    @Override
    public void setPropertyManager(@Nonnull PropertyManager propertyManager) { }

    @Nonnull
    @Override
    public PropertyManager getPropertyManager() {
        return PropertyManager.EMPTY;
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
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property) {
        return getPropertyManager().getPropertyValue(property, property.getHostType().cast(this));
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Supplier<Property<T, V>> property) {
        return getPropertyValue(property.get());
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
        this.x = (int) x;
        this.y = (int) y;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void updateSize() { }

    @Override
    public void setLayer(@Nonnull Layer layer) { }

    @Nonnull
    @Override
    public Layer getLayer() {
        return Layers.DEFAULT;
    }

    @Override
    public void setEnable(boolean enable) { }

    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public void setId(@Nonnull String id) { }

    @Nonnull
    @Override
    public String getId() {
        return "window";
    }

    @Override
    public void setName(@Nonnull String name) { }

    @Nonnull
    @Override
    public String getName() {
        return "Window";
    }

    @Override
    public void setHierarchyName(@Nonnull String name) { }

    @Nonnull
    @Override
    public String getHierarchyName() {
        return "window";
    }

    @Nonnull
    @Override
    public WidgetSerializer<?> getSerializer() {
        return WidgetSerializers.BASIC.get();
    }

    @Override
    public void updateLayout() {
        content.updateSize();
        content.setPosition(x, y);
        content.updateLayout();
    }

    @Override
    public void updateLayer() { }

    @Override
    public boolean addChild(@Nonnull Widget widget) {
        return false;
    }

    @Override
    public boolean addChild(@Nonnull Widget widget, int index) {
        return false;
    }

    @Override
    public boolean removeChild(@Nonnull Widget widget) {
        return false;
    }

    @Override
    public boolean removeChild(int index) {
        return false;
    }

    @Override
    public int getChildrenCount() {
        return 0;
    }

    @Nonnull
    @Override
    public Collection<Widget> getChildren() {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Collection<Widget> getByChildrenLayer() {
        return Collections.emptySet();
    }

    @Nullable
    @Override
    public Widget getChildAt(int index) {
        return null;
    }

    @Nullable
    @Override
    public Widget getChildByName(@Nonnull String name) {
        return null;
    }

    @Nullable
    @Override
    public Widget getChildById(@Nonnull String id) {
        return null;
    }

    @Nullable
    @Override
    public Widget findWidgetById(@Nonnull String id) {
        //TODO: in development
        return null;
    }

    @Nullable
    @Override
    public Widget findWidgetByName(@Nonnull String name) {
        //TODO: in development
        return null;
    }

    @Nullable
    @Override
    public Widget findWidgetByHierarchy(@Nonnull String hierarchy) {
        //TODO: in development
        return null;
    }

    @Override
    @Nonnull
    public ListenerStack<PointerEvent> getPointerDownEventListeners() {
        return pointerDownEventListeners;
    }

    @Override
    @Nonnull
    public ListenerStack<PointerEvent> getPointerUpEventListeners() {
        return pointerUpEventListeners;
    }

    @Override
    @Nonnull
    public ListenerStack<PointerEvent> getPointerMoveEventListeners() {
        return pointerMoveEventListeners;
    }

    @Override
    @Nonnull
    public ListenerStack<PointerEvent> getPointerEnterEventListeners() {
        return pointerEnterEventListeners;
    }

    @Override
    @Nonnull
    public ListenerStack<PointerEvent> getPointerOutEventListeners() {
        return pointerOutEventListeners;
    }

    @Override
    @Nonnull
    public ListenerStack<PointerEvent> getPointerScrollEventListeners() {
        return pointerScrollEventListeners;
    }

    @Nonnull
    @Override
    public LayerManager getLayerManager() {
        return layerManager;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Nonnull
    @Override
    public Screen getScreen() {
        return screen;
    }

    @Override
    @Nonnull
    public DynamicPointer getPointer() {
        return pointer;
    }
}

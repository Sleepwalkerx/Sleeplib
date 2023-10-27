package com.sleepwalker.sleeplib.client.widget.core;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public abstract class BaseNestedWidget extends BaseWidget implements IExtraNestedGuiEventHandler, ITooltipRenderable {

    private IRoot root;

    @Nonnull
    protected final List<IGuiEventListener> children = new ArrayList<>();
    @Nonnull
    protected final List<ITickable> tickables = new ArrayList<>();

    @Nullable
    private IGuiEventListener focused;
    private boolean isDragging;
    protected boolean pressed;

    @Nullable
    protected IGuiEventListener lastMouseFocused;

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        setRoot(parent.getRoot());
        super.initOnScreen(posX, posY, width, height, parent);

        children.clear();
        lastMouseFocused = null;
        focused = null;
        isDragging = false;
    }

    @Override
    public void setRoot(@Nonnull IRoot root) {
        this.root = root;
    }

    @Nonnull
    @Override
    public IRoot getRoot() {
        return root;
    }

    public final boolean isDragging() {
        return this.isDragging;
    }

    public final void setDragging(boolean pDragging) {
        this.isDragging = pDragging;
    }

    @Override
    @Nullable
    public IGuiEventListener getLastMouseFocused() {
        return lastMouseFocused;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        pressed = !IExtraNestedGuiEventHandler.super.mouseClicked(pMouseX, pMouseY, pButton);
        return !pressed;
    }

    @Override
    public void setMouseFocused(boolean mouseFocused) {
        super.setMouseFocused(mouseFocused);

        if(!mouseFocused && lastMouseFocused != null && lastMouseFocused instanceof IWidget){
            ((IWidget) lastMouseFocused).setMouseFocused(false);
            lastMouseFocused = null;
        }
    }

    //int delta = posY - this.posY;
    @Override
    public void setPosY(int posY) {
        super.setPosY(posY);
    }

    @Override
    public void setPosX(int posX) {
        super.setPosX(posX);
    }

    @Override
    public void tick(double mouseX, double mouseY) {

        if(isMouseFocused()){

            Optional<IGuiEventListener> optional = children
                .stream()
                .filter(listener -> listener.isMouseOver(mouseX, mouseY))
                .findFirst();

            if(optional.isPresent()){

                if(lastMouseFocused != optional.get()){

                    if(lastMouseFocused != null && lastMouseFocused instanceof IWidget){
                        ((IWidget) lastMouseFocused).setMouseFocused(false);
                    }

                    lastMouseFocused = optional.get();

                    if(lastMouseFocused instanceof IWidget){
                        ((IWidget) lastMouseFocused).setMouseFocused(true);
                    }
                }
            }
            else if(lastMouseFocused != null){

                if(lastMouseFocused instanceof IWidget){
                    ((IWidget) lastMouseFocused).setMouseFocused(false);
                }

                lastMouseFocused = null;
            }
        }

        tickables.forEach(e -> e.tick(mouseX, mouseY));
    }

    @Override
    public void forceRemove(@Nonnull IExtraNestedGuiEventHandler parent) {
        super.forceRemove(parent);

        for(int i = 0; i < children.size(); i++){

            IGuiEventListener e = children.get(i);

            if(e instanceof IWidget){
                ((IWidget) e).forceRemove(this);
            }
        }

        children.clear();
    }

    @Override
    public void renderTooltips(@Nonnull MatrixStack ms, int mouseX, int mouseY, float pt) {
        if(lastMouseFocused instanceof ITooltipRenderable){
            ((ITooltipRenderable) lastMouseFocused).renderTooltips(ms, mouseX, mouseY, pt);
        }
    }

    @Nonnull
    @Override
    public List<ITickable> getTickables() {
        return tickables;
    }

    @Nullable
    public IGuiEventListener getFocused() {
        return this.focused;
    }

    public void setFocused(@Nullable IGuiEventListener pListener) {
        this.focused = pListener;
    }

    @Override
    public void addChildren(@Nonnull IGuiEventListener element) {
        children.add(0, element);
    }

    @Override
    public void removeChildren(@Nonnull IGuiEventListener element) {
        children.remove(element);
    }

    @Override
    public void addTickable(@Nonnull ITickable element) {
        tickables.add(element);
    }

    @Override
    public void removeTickable(@Nonnull ITickable element) {
        tickables.remove(element);
    }

    @Nonnull
    @Override
    public List<? extends IGuiEventListener> children() {
        return children;
    }
}

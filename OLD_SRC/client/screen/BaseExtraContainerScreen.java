package com.sleepwalker.sleeplib.client.screen;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.IRoot;
import com.sleepwalker.sleeplib.client.widget.core.event.IMouseListener;
import com.sleepwalker.sleeplib.client.widget.core.ITickable;
import com.sleepwalker.sleeplib.client.widget.core.IWidget;
import com.sleepwalker.sleeplib.client.widget.core.event.ListenerStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseExtraContainerScreen<T extends Container> extends ContainerScreen<T> implements IExtraNestedGuiEventHandler, IRoot {

    @Nullable
    protected IGuiEventListener lastMouseFocused;
    protected final MouseHelper mouseHelper;
    protected final MainWindow mainWindow;

    @Nonnull
    protected final List<ITickable> tickables;
    @Nonnull
    protected final ListenerStack<IMouseListener> mouseListener;

    public BaseExtraContainerScreen(T pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        mouseHelper = Minecraft.getInstance().mouseHandler;
        mainWindow = Minecraft.getInstance().getWindow();

        tickables = new ArrayList<>();
        mouseListener = ListenerStack.newStack();
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        super.mouseMoved(pMouseX, pMouseY);
        mouseListener.post(l -> l.onMouseMoved(pMouseX, pMouseY));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        boolean result = super.mouseClicked(pMouseX, pMouseY, pButton);
        mouseListener.post(l -> l.onMouseClicked(pMouseX, pMouseY, pButton));
        return result;
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        boolean result = super.mouseReleased(x, y, button);
        mouseListener.post(l -> l.onMouseReleased(x, y, button));
        return result;
    }

    @Override
    public boolean mouseDragged(double mX, double mY, int button, double dragX, double dragY) {
        boolean result = super.mouseDragged(mX, mY, button, dragX, dragY);
        mouseListener.post(l -> l.onMouseDragged(mX, mY, button, dragX, dragY));
        return result;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        boolean result = super.mouseScrolled(pMouseX, pMouseY, pDelta);
        mouseListener.post(l -> l.onMouseScrolled(pMouseX, pMouseY, pDelta));
        return result;
    }

    @Override
    public void setRoot(@Nonnull IRoot root) { }

    @Nonnull
    @Override
    public IRoot getRoot() {
        return this;
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

    @Override
    @Nullable
    public IGuiEventListener getLastMouseFocused() {
        return lastMouseFocused;
    }

    @Override
    public void tick(double mouseX, double mouseY) {

        Optional<IGuiEventListener> optional = children
            .stream()
            .filter(listener -> listener.isMouseOver(mouseX, mouseY))
            .findFirst();

        if(optional.isPresent()){

            if(lastMouseFocused == optional.get()){
                return;
            }

            if(lastMouseFocused != null && lastMouseFocused instanceof IWidget){
                ((IWidget) lastMouseFocused).setMouseFocused(false);
            }

            lastMouseFocused = optional.get();

            if(lastMouseFocused instanceof IWidget){
                ((IWidget) lastMouseFocused).setMouseFocused(true);
            }
        }
        else if(lastMouseFocused != null){

            if(lastMouseFocused instanceof IWidget){
                ((IWidget) lastMouseFocused).setMouseFocused(false);
            }

            lastMouseFocused = null;
        }
    }

    @Override
    public void tick() {

        double mouseX = mouseHelper.xpos() * (double) mainWindow.getGuiScaledWidth() / (double) mainWindow.getScreenWidth();
        double mouseY = mouseHelper.ypos() * (double) mainWindow.getGuiScaledHeight() / (double) mainWindow.getScreenHeight();

        tick(mouseX, mouseY);

        tickables.forEach(e -> e.tick(mouseX, mouseY));
    }

    @Nonnull
    @Override
    public List<ITickable> getTickables() {
        return tickables;
    }

    @Override
    @Nonnull
    public ListenerStack<IMouseListener> getMouseListener() {
        return mouseListener;
    }

    @Override
    public int getHeight() {
        return imageHeight;
    }

    @Override
    public int getWidth() {
        return imageWidth;
    }

    @Override
    public int getPosX() {
        return leftPos;
    }

    @Override
    public int getPosY() {
        return topPos;
    }

    @Override
    public int getPosEndX() {
        return leftPos + imageWidth;
    }

    @Override
    public int getPosEndY() {
        return topPos + imageHeight;
    }
}

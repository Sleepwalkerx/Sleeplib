package com.sleepwalker.sleeplib.client.widget.core;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;

import javax.annotation.Nonnull;

public interface IWidget extends IGuiEventListener, IRenderable {

    default void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent){
        forceRemove(parent);
        parent.addChildren(this);
        if(this instanceof ITickable){
            parent.addTickable((ITickable) this);
        }
        setSize(width, height);
        setPosition(posX, posY);
    }

    default void initOnScreen(int posX, int posY, @Nonnull IExtraNestedGuiEventHandler parent){
        initOnScreen(posX, posY, getWidth(), getHeight(), parent);
    }

    default void initOnScreen(@Nonnull IExtraNestedGuiEventHandler parent){
        initOnScreen(getPosX(), getPosY(), getWidth(), getHeight(), parent);
    }

    default void duplicate(@Nonnull IWidget element, @Nonnull IExtraNestedGuiEventHandler parent){
        initOnScreen(element.getPosX(), element.getPosY(), element.getWidth(), element.getHeight(), parent);
    }

    @Override
    default void render(@Nonnull MatrixStack ms, int mX, int mY, float pt){
        if(isActive()){
            renderWidget(ms, mX, mY, pt);
        }
    }

    void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt);

    default void mouseReleasedFocus(double x, double y, int key){
        mouseReleased(x, y, key);
    }

    default void forceRemove(@Nonnull IExtraNestedGuiEventHandler parent) {
        parent.removeChildren(this);
        if(this instanceof ITickable){
            parent.removeTickable((ITickable) this);
        }
    }

    @Override
    default boolean isMouseOver(double mouseX, double mouseY){
        return isActive() && (mouseX >= getPosX() && getPosEndX() > mouseX && mouseY >= getPosY() && getPosEndY() > mouseY);
    }

    default void setPosition(int posX, int posY){
        setPosX(posX);
        setPosY(posY);
    }

    default void setSize(int width, int height){
        setWidth(width);
        setHeight(height);
    }

    default void setSize(@Nonnull ISprite sprite){
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
    }

    boolean isMouseFocused();

    void setMouseFocused(boolean focused);

    boolean isActive();

    void setActive(boolean active);

    int getHeight();

    int getWidth();

    int getPosX();

    int getPosY();

    int getPosEndY();

    int getPosEndX();

    void setHeight(int height);

    void setWidth(int width);

    void setPosX(int posX);

    void setPosY(int posY);
}

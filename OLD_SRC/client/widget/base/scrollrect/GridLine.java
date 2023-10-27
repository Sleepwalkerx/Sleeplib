package com.sleepwalker.sleeplib.client.widget.base.scrollrect;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.widget.core.BaseNestedWidget;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.IWidget;

import javax.annotation.Nonnull;
import java.util.List;

public class GridLine<T extends IWidget> extends BaseNestedWidget {

    @Nonnull
    protected final List<T> elements;

    public GridLine(@Nonnull List<T> elements) {
        this.elements = elements;

        width = elements.stream().mapToInt(IWidget::getWidth).sum();
        height = elements.stream().mapToInt(IWidget::getHeight).max().orElse(0);
    }

    public void clear(){
        elements.forEach(t -> t.forceRemove(this));
        elements.clear();
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        recalculateElements();
        elements.forEach(t -> t.initOnScreen(this));
    }

    @Override
    public void setPosition(int posX, int posY) {
        super.setPosition(posX, posY);
        recalculateElements();
    }

    @Override
    public void setPosY(int posY) {
        super.setPosY(posY);
        recalculateElements();
    }

    @Override
    public void setPosX(int posX) {
        super.setPosX(posX);
        recalculateElements();
    }

    public void addElement(T element){
        elements.add(element);

        if(element.getHeight() > height){
            setHeight(element.getHeight());
        }

        setWidth(width + element.getWidth());

        recalculateElements();

        element.initOnScreen(this);
    }

    public boolean removeElement(T element){

        if(elements.remove(element)) {

            if(lastMouseFocused == element){
                lastMouseFocused = null;
            }

            element.forceRemove(this);

            setWidth(width - element.getWidth());

            recalculateElements();

            return true;
        }
        else return false;
    }

    protected void recalculateElements(){

        int relative = 0;
        for(T element : elements){

            element.setPosition(relative + posX, posY);

            relative += element.getWidth();
        }
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        elements.forEach(t -> t.render(ms, mX, mY, pt));
    }

    @Nonnull
    public List<T> getElements() {
        return elements;
    }
}

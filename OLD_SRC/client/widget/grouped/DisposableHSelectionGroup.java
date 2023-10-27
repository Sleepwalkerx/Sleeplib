package com.sleepwalker.sleeplib.client.widget.grouped;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.ISavable;
import net.minecraft.nbt.IntNBT;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.button.Button;
import com.sleepwalker.sleeplib.client.widget.base.button.DisposableButton;
import com.sleepwalker.sleeplib.client.widget.core.BaseNestedWidget;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DisposableHSelectionGroup extends BaseNestedWidget implements ISavable<IntNBT> {

    @Nonnull
    protected final List<DisposableButton> elements;

    @Nullable
    protected DisposableButton selected;

    public DisposableHSelectionGroup(@Nonnull List<DisposableButton> elements, int defaultIndex) {
        this.elements = elements;

        if(elements.isEmpty()){
            throw new IllegalArgumentException("SelectionGroup can not be empty");
        }

        elements.forEach(selectable -> selectable.setClickListener(this::onClicked));

        if(defaultIndex >= 0 && defaultIndex < elements.size()){
            selected = elements.get(defaultIndex);
        }
        else {
            selected = elements.get(0);
        }
        selected.setSelected(true);
    }

    private void onClicked(@Nonnull Button button, int bIndex){

        if(button == selected){
            return;
        }

        if(selected != null){
            selected.setSelected(false);
        }

        selected = (DisposableButton) button;
        selected.setSelected(true);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {
        super.initOnScreen(posX, posY, width, height, screen);

        int widthElement = (width - 2) / elements.size();

        int indentX = posX + 1;
        for(DisposableButton e : elements){
            e.initOnScreen(indentX, posY + 1, widthElement, height - 2, screen);
            indentX += widthElement;
        }
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        SLSprites.RECT_5.render(ms, posX, posY, width, height);

        elements.forEach(e -> e.render(ms, mX, mY, pt));
    }

    @Override
    public void forceRemove(@Nonnull IExtraNestedGuiEventHandler parent) {
        super.forceRemove(parent);
        elements.forEach(e -> e.forceRemove(parent));
    }

    @Nonnull
    public List<DisposableButton> getElements() {
        return elements;
    }

    @Nullable
    public DisposableButton getSelected() {
        return selected;
    }

    @Nonnull
    @Override
    public IntNBT saveData() {
        return IntNBT.valueOf(elements.indexOf(selected));
    }

    @DefaultDepended
    @Override
    public void readData(@Nonnull IntNBT nbt) {

        int index = nbt.getAsInt();

        if(index >= 0 && index < elements.size()){

            if(selected != null){
                selected.setSelected(false);
            }

            selected = elements.get(index);
            selected.setSelected(true);
        }
    }
}

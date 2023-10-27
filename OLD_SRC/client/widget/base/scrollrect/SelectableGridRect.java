package com.sleepwalker.sleeplib.client.widget.base.scrollrect;

import com.sleepwalker.sleeplib.client.widget.base.button.Button;
import com.sleepwalker.sleeplib.client.widget.base.slider.VerticalSlider;
import com.sleepwalker.sleeplib.client.widget.core.ISelectable;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SelectableGridRect<T extends Button & ISelectable> extends GridRectVertical<T> {

    @Nullable
    protected T selected;

    public SelectableGridRect(@Nonnull List<GridLine<T>> sortedElements, @Nonnull VerticalSlider slider){
        super(sortedElements, slider);
    }

    public SelectableGridRect(){
        super();
    }

    @Override
    public void addGridElements(@Nonnull Iterable<? extends T> iterable) {
        super.addGridElements(iterable);

        for (T t : iterable){
            t.setClickListener(this::onCellSelect);
        }
    }

    @Override
    public void addGridElement(@Nonnull T element) {
        super.addGridElement(element);

        element.setClickListener(this::onCellSelect);
    }

    @Nullable
    public T getSelected() {
        return selected;
    }

    @SuppressWarnings("unchecked")
    protected boolean onCellSelect(@Nonnull Button gridItem, int button){

        if(button != 0){
            return false;
        }

        if(selected != null){
            selected.setSelected(false);
        }

        if(selected == gridItem){
            selected = null;
            return true;
        }

        selected = (T) gridItem;

        selected.setSelected(true);

        return true;
    }

    @Nonnull
    @Override
    public CompoundNBT saveData() {

        CompoundNBT compoundNBT = super.saveData();

        if(selected != null){

            for(int i = 0; i < sortedElements.size(); i++){

                GridLine<T> grid = sortedElements.get(i);

                for(int k = 0; k < grid.getElements().size(); k++){

                    if(grid.getElements().get(k) == selected){

                        compoundNBT.putInt("selectRow", i);
                        compoundNBT.putInt("selectCol", k);

                        i = sortedElements.size();

                        break;
                    }
                }
            }
        }

        return compoundNBT;
    }

    @Override
    public void readData(@Nonnull CompoundNBT nbt) {
        super.readData(nbt);

        if(nbt.contains("selectRow") && nbt.contains("selectCol")){

            int selectRow = nbt.getInt("selectRow");
            int selectCol = nbt.getInt("selectCol");

            if(selectRow >= 0 && selectCol >= 0 && selectRow < sortedElements.size() && selectCol < sortedElements.get(selectRow).getElements().size()){

                onCellSelect(sortedElements.get(selectRow).getElements().get(selectCol), 0);
            }
        }
    }
}

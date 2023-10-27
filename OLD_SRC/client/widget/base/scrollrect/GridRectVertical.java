package com.sleepwalker.sleeplib.client.widget.base.scrollrect;

import com.google.common.collect.Lists;
import com.sleepwalker.sleeplib.client.widget.base.slider.VerticalSlider;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.IWidget;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class GridRectVertical<T extends IWidget> extends VerticalScrollRect<GridLine<T>> {

    protected final Map<T, GridLine<T>> elementsCache = new HashMap<>();

    public GridRectVertical(@Nonnull List<GridLine<T>> sortedElements, @Nonnull VerticalSlider slider){
        super(sortedElements, slider);
    }

    public GridRectVertical(){
        super();
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        defaultInitOnScreen(posX, posY, width, height, parent);

        List<T> elements = sortedElements.stream().flatMap(line -> line.getElements().stream()).collect(Collectors.toList());
        clearSortedElements();

        addGridElements(elements);

        init(posX, posY, width, height, parent);
    }

    @Override
    protected void clearSortedElements() {
        sortedElements.forEach(tGridLine -> {
            tGridLine.clear();
            tGridLine.forceRemove(this);
        });
        elementsCache.clear();
        sortedElements.clear();
    }

    public void addGridElement(@Nonnull T element){

        for(GridLine<T> scrollElement : sortedElements){

            if(scrollElement.getWidth() + element.getWidth() <= width){
                scrollElement.addElement(element);
                elementsCache.put(element, scrollElement);
                return;
            }
        }

        GridLine<T> gridElement = new GridLine<>(Lists.newArrayList(element));
        elementsCache.put(element, gridElement);
        addScrollElement(gridElement);
    }

    public void addGridElements(@Nonnull Iterable<? extends T> iterable){

        Iterator<? extends T> iterator = iterable.iterator();

        if(!iterator.hasNext()){
            return;
        }

        T element = iterator.next();

        for(GridLine<T> scrollElement : sortedElements){

            while(scrollElement.getWidth() + element.getWidth() <= width){

                scrollElement.addElement(element);
                elementsCache.put(element, scrollElement);

                if(iterator.hasNext()){
                    element = iterator.next();
                }
                else return;
            }
        }

        List<T> listGridElements = new ArrayList<>();
        int gridWidth = 0;
        boolean end = true;

        while(end){

            do {

                listGridElements.add(element);
                gridWidth += element.getWidth();

                if(iterator.hasNext()){
                    element = iterator.next();
                }
                else {
                    end = false;
                    break;
                }
            }
            while(element.getWidth() + gridWidth <= width);

            GridLine<T> gridElement = new GridLine<>(listGridElements);
            listGridElements.forEach(t -> elementsCache.put(t, gridElement));
            addScrollElement(gridElement);

            listGridElements = new ArrayList<>();
            gridWidth = 0;
        }
    }

    public void removeGridElementAndUpdate(T element){

        GridLine<T> gridLine = elementsCache.get(element);

        if(gridLine == null){
            return;
        }

        if(!gridLine.removeElement(element)){
            return;
        }
        elementsCache.remove(element);

        int index = getSortedElements().indexOf(gridLine);

        if(gridLine.getElements().isEmpty()){

            removeElement(gridLine);

            if(index == 0 || index == getSortedElements().size()){
                return;
            }

            index--;
            gridLine = getSortedElements().get(index);
        }
        else if(index + 1 == getSortedElements().size()){
            return;
        }

        //index -> gridLine constant

        GridLine<T> nextLine = getSortedElements().get(index + 1);

        if(gridLine.getWidth() + nextLine.getElements().get(0).getWidth() > width){
            return;
        }

        List<T> nextElements = new ArrayList<>(getSortedElements().size() - index + 1);
        for(int i = index + 1; i < getSortedElements().size(); i++){

            GridLine<T> line = getSortedElements().get(index + 1);

            nextElements.addAll(line.getElements());
            line.getElements().forEach(elementsCache::remove);

            removeElement(line);
        }

        addGridElements(nextElements);
    }

    @Nonnull
    public List<T> getElements() {

        List<T> elements = new ArrayList<>();

        for (Iterator<GridLine<T>> it = super.iterator(); it.hasNext(); ) {
            GridLine<T> gridLine = it.next();
            elements.addAll(gridLine.getElements());
        }

        return elements;
    }
}

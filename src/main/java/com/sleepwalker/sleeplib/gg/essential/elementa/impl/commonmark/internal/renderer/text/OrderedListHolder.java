package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.internal.renderer.text;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.OrderedList;

public class OrderedListHolder extends ListHolder {
    private final char delimiter;
    private int counter;

    public OrderedListHolder(ListHolder parent, OrderedList list) {
        super(parent);
        delimiter = list.getDelimiter();
        counter = list.getStartNumber();
    }

    public char getDelimiter() {
        return delimiter;
    }

    public int getCounter() {
        return counter;
    }

    public void increaseCounter() {
        counter++;
    }
}

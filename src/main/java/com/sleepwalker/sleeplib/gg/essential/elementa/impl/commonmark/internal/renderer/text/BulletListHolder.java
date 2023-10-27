package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.internal.renderer.text;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.BulletList;

public class BulletListHolder extends ListHolder {
    private final char marker;

    public BulletListHolder(ListHolder parent, BulletList list) {
        super(parent);
        marker = list.getBulletMarker();
    }

    public char getMarker() {
        return marker;
    }
}

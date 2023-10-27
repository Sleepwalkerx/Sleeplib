package com.sleepwalker.sleeplib.ui.layer;

import javax.annotation.Nonnull;

public class SimpleLayer implements Layer {

    @Nonnull private final String name;
    private final float offset;

    public SimpleLayer(@Nonnull String name, float offset) {
        this.name = name;
        this.offset = offset;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getOffset() {
        return offset;
    }

    @Override
    public int compareTo(@Nonnull Layer o) {
        return Float.compare(o.getOffset(), getOffset());
    }
}

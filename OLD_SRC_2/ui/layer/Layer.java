package com.sleepwalker.sleeplib.ui.layer;

import javax.annotation.Nonnull;

public interface Layer extends Comparable<Layer> {

    @Nonnull String getName();
    float getOffset();
}

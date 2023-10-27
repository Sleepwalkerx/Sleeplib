package com.sleepwalker.sleeplib.util;

import javax.annotation.Nonnull;

public interface Buildable<T> {

    @Nonnull T build();
}

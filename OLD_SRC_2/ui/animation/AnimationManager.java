package com.sleepwalker.sleeplib.ui.animation;

import javax.annotation.Nonnull;

public interface AnimationManager {

    void addAnimation(@Nonnull Animation animation);
    void removeAnimation(@Nonnull Animation animation);
}
